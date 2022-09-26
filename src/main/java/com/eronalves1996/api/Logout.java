package com.eronalves1996.api;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;  

import java.io.IOException;
import java.io.PrintWriter;

import com.eronalves1996.api.resources.UserDAO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.DELETE;

@Path("/logout")
public class Logout {
    
    private UserDAO DAO = new UserDAO();
    
    @DELETE
    public void doLogout(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.setStatus(400);
            PrintWriter pw = response.getWriter();
            pw.println("Login not found");
            return;
        }

        String user = null;
        String date = null;

        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            String cookieType = cookie.getName();
            if (cookieType.equals("user"))
                user = cookie.getValue();
            else if (cookieType.equals("created_at"))
                date = cookie.getValue();
        }
        
        try {
            DAO.deleteLogin(user, date);
        } catch(Exception ex) {
            
            response.setStatus(400);
            response.getWriter().println("Session not found");
         
        }
        
        response.addCookie(cookies[0]);
        response.addCookie(cookies[1]);
        response.setStatus(200);
        response.getWriter().println("Logged out sucessfully");
        
    }
    
}
