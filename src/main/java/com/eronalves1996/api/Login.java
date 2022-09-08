/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api;

import com.eronalves1996.api.resources.User;
import com.eronalves1996.api.resources.UserController;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 *
 * @author eronads
 */
@Path("login")
public class Login {

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public JsonObject doLogin(LoginForm body) {

        UserController lc = new UserController();
        User loggedIn = lc.login(body);
        JsonObject j;
        if (loggedIn == null) {
            j = Json.createObjectBuilder()
                    .add("status", "Unsucessful login")
                    .build();
        } else {
            j = Json.createObjectBuilder()
                    .add("status", "Sucessful login")
                    .add("login from ", loggedIn.getName())
                    .build();
        }
        return j;
    }

}
