/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api;

import com.eronalves1996.api.resources.InvalidLoginException;
import com.eronalves1996.api.resources.User;
import com.eronalves1996.api.resources.UserController;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
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

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response doLogin(LoginForm body) {
        UserController lc = new UserController();
        JsonObjectBuilder j = Json.createObjectBuilder();
        User loggedIn;
        try {
            loggedIn = lc.login(body);
            j.add("status", "logged in")
            .add("as ", loggedIn.getName());
        } catch (InvalidLoginException ex) {
            return Response
                    .status(401)
                    .entity(new Object() {
                        public String status = "Unauthorized";
                    })
                    .cookie()
                    .build();
        }
        Cookie nk = new Cookie("user", loggedIn.getEmail());
        return Response
                .status(200)
                .entity(new Object() {
                    public String status = "Authorized";
                    public String user = loggedIn.getName();
                })
                .cookie(new NewCookie(nk))
                .build();
    }

}
