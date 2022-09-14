/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;

import com.eronalves1996.api.resources.InvalidLoginException;
import com.eronalves1996.api.resources.User;
import com.eronalves1996.api.resources.UserController;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author eronads
 */
@Path("login")
public class Login {

    @GET
    @Produces("application/json")
    public Response doLogin(@Context HttpServletRequest request) {
        UserController lc = new UserController();
        Decoder decoder = Base64.getDecoder();
        
        User loggedIn;
        Cookie nk;
        Cookie nd;
        try {
            String auth = request.getHeader("Authorization");
            if(auth == null) throw new InvalidLoginException("Bad request");
            
            String decodedString = new String(decoder.decode(auth), StandardCharsets.UTF_8);
            String[] rawUser = decodedString.split(":");
            LoginForm lf = new LoginForm(rawUser[0], rawUser[1]);
            loggedIn = lc.login(lf);
            nk = new Cookie("user", loggedIn.getEmail(), "/api", "localhost");
            nd = new Cookie("created_at", lc.createUserSession(loggedIn.getEmail()).toString(), "/api", "localhost");
            
        } catch (InvalidLoginException ex) {
            return Response
                    .status(ex.getMessage().equals("Bad request") ? 400 : 401)
                    .entity(new Object() {
                        public String status = ex.getMessage();
                    })
                    .header("WWW-Authenticate", "Basic realm=\"Access to the restricted area\", charset=\"UTF-8\"")
                    .build();
        }
        return Response
                .status(200)
                .entity(new Object() {
                    public String status = "Authorized";
                    public String user = loggedIn.getName();
                    public Date created = new Date();
                })
                .cookie(new NewCookie(nk), new NewCookie(nd))
                .build();
    }

}
