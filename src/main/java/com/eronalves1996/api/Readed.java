package com.eronalves1996.api;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.eronalves1996.api.resources.BookReaded;
import com.eronalves1996.api.resources.UserDAO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("readed")
public class Readed {
    
    private UserDAO DAO = new UserDAO();
    
    @GET
    @Produces("application/json")
    public Response getAllReadedBooks(@Context HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String user;
        try {
            user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findFirst().get()
                    .getValue();
        } catch (NullPointerException ex) {
            return Response
                    .status(401)
                    .build();
        }

        List<BookReaded> books = null;

        try {
            books = DAO.getAllBooksReaded(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().entity(new Object() {
                @SuppressWarnings("unused")
                public String mss = ex.getMessage();
            }).build();
        }

        return Response.status(books.size() == 0 ? 204 : 200).entity(books).build();
    }
    
    @GET
    @Path("/{id}")
    public Response verifyIfBookIsRead(@Context HttpServletRequest request, @PathParam("id") String id) {
        Cookie[] cookies = request.getCookies();
        String user;
        try {
            user = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("user")).findFirst().get()
                    .getValue();
        } catch (NullPointerException ex) {
            return Response
                    .status(401)
                    .build();
        }
        
        try {
            DAO.verifyIfBookIsRead(user, id);
        } catch (Exception ex) {
            return Response
                    .status(404).build();
        }
        
        return Response
                .ok()
                .build();
    }

    @POST
    @Consumes("application/json")
    public Response markBookAsRead(BookReaded book) {
        try {
            DAO.markBookAsRead(book);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().entity(new Object() {
                @SuppressWarnings("unused")
                public String mss = "That whas not possible to mark as read";
            }).build();
        }
        return Response.accepted().build();
    }
    
    @GET
    @Path("/ranking")
    public Response getRanking() throws SQLException {
        return Response
                .ok()
                .entity(DAO.getRanking())
                .build();
    }
}
