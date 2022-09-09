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
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        JsonObjectBuilder j = Json.createObjectBuilder();
        User loggedIn;
        try {
            loggedIn = lc.login(body);
            j.add("status", "logged in");
        } catch (InvalidLoginException ex) {
            j.add("status", ex.getMessage());
        }
        return j.build();
    }

}
