/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;

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

    private static final String ROOT_DOMAIN = "localhost";
    private static final String GENERAL_PATH = "/api";

    @GET
    @Produces("application/json")
    public Response doLogin(@Context HttpServletRequest request) {
        UserController userController = new UserController();
        Decoder decoder = Base64.getDecoder();
        
        User userLoggedIn;
        Cookie userCookie;
        Cookie loginDateCookie;
        
        try {
            String auth = request.getHeader("Authorization");
            if(auth == null) throw new InvalidLoginException("Bad request");
            
            String[] rawUser = (new String(decoder.decode(auth), StandardCharsets.UTF_8)).split(":");
            String email = rawUser[0];
            String password = rawUser[1];            
            userLoggedIn = userController.login(new LoginForm(email, password));
            userCookie = new Cookie("user", userLoggedIn.getEmail(), GENERAL_PATH, ROOT_DOMAIN);
            loginDateCookie = new Cookie("created_at", userController.createUserSession(userLoggedIn.getEmail()).toString(), GENERAL_PATH, ROOT_DOMAIN);
            
        } catch (InvalidLoginException ex) {
            return Response
                    .status(ex.getMessage().equals("Bad request") ? 400 : 401)
                    .entity(new Object() {
                        @SuppressWarnings("unused")
                        public String status = ex.getMessage();
                    })
                    .header("WWW-Authenticate", "Basic realm=\"Access to the restricted area\", charset=\"UTF-8\"")
                    .build();
        }
        return Response
                .status(200)
                .entity(userLoggedIn)
                .cookie(new NewCookie(userCookie), new NewCookie(loginDateCookie))
                .build();
    }

}
