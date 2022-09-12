/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api;

import java.util.Date;

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
        User loggedIn;
        Cookie nk;
        Cookie nd;
        try {
            loggedIn = lc.login(body);
            nk = new Cookie("user", loggedIn.getEmail());
            nd = new Cookie("created at", lc.createUserSession(loggedIn.getEmail()).toString());
        } catch (InvalidLoginException ex) {
            return Response
                    .status(401)
                    .entity(new Object() {
                        public String status = ex.getMessage();
                    })
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
