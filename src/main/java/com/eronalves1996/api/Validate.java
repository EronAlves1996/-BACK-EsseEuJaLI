package com.eronalves1996.api;

import com.eronalves1996.api.resources.User;
import com.eronalves1996.api.resources.UserDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.servlet.http.Cookie;

@Path("/validate")
public class Validate {

    @GET
    public Response doValidate(@Context HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return Response.status(400).build();

        String user = null;
        String date = null;
        User actualUser = null;

        for (Cookie cookie : cookies) {
            String cookieType = cookie.getName();
            if (cookieType.equals("user"))
                user = cookie.getValue();
            else if (cookieType.equals("created_at"))
                date = cookie.getValue();
        }

        try {
            actualUser = UserDAO.verifyActiveLogin(user, date);
        } catch (Exception ex) {
            return Response.status(401).entity(new Object() {
                @SuppressWarnings("unused")
                public String status = "Login not found";
            }).cookie(new NewCookie("user", user, "/api", "localhost", "", 0, false),
                    new NewCookie("created_at", date, "/api", "localhost", "", 0, false))
                    .header("WWW-Authenticate", "xBasic realm=\"Access to the restricted area\", charset=\"UTF-8\"")
                    .build();
        }

        return Response.status(200).entity(actualUser).build();
    }
}
