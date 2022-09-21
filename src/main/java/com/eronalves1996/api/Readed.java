package com.eronalves1996.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.eronalves1996.api.resources.BookReaded;
import com.eronalves1996.api.resources.UserDAO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("/readed")
public class Readed {

    @GET
    @Produces("application/json")
    public Response getAllReadedBooks(@Context HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findFirst().get()
                .getValue();

        List<BookReaded> books = null;

        try {
            books = UserDAO.getAllBooksReaded(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().entity(new Object() {
                public String mss = ex.getMessage();
            }).build();
        }

        return Response.status(books.size() == 0? 204: 200).entity(books).build();
    }
    
  
}
