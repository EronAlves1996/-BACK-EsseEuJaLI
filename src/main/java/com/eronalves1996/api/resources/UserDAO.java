/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api.resources;

import com.eronalves1996.api.LoginForm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

/**
 *
 * @author eronads
 */
public class UserDAO {

    private final static String URL = "jdbc:derby://localhost:1527/jaLiDb";
    private final static String table = "User_Info";

    private static Connection conn = null;
    private static Statement stmt = null;

    @SuppressWarnings("deprecation")
    public static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            // Get a connection
            conn = DriverManager.getConnection(URL);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public static User selectUser(LoginForm lf) {
        try {
            openConnections();
            ResultSet results = stmt.executeQuery("SELECT * FROM " + table + " WHERE email = '" + lf.email + "'");
            results.next();
            User user = new User();
            user.setEmail(results.getString(1));
            user.setPassword(results.getString(2));
            user.setName(results.getString(3));
            closeConnections(results);
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new User();
    }

    public static void createLoginEntry(String email, Date loggedAt) throws InvalidLoginException, SQLException {
        try {
            openConnections();
            String sql = "INSERT INTO Login_Control VALUES ('" + email + "', '" + loggedAt.toString() + "')";
            stmt.executeUpdate(sql);
            closeConnections();
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            String sql = "DELETE FROM Login_Control WHERE email = '" + email + "'";
            stmt.executeUpdate(sql);
            createLoginEntry(email, loggedAt);
        } catch (Exception ex) {
            System.out.println("Não foi possível realizar login");
            ex.printStackTrace();
            throw new InvalidLoginException("Erro interno de servidor");
        }

    }

    public static User verifyActiveLogin(String user, String date) throws InvalidLoginException, SQLException {
        openConnections();
        ResultSet results = stmt.executeQuery("SELECT * FROM Login_Control WHERE email='" + user + "'");

        results.next();
        if (!date.equals(results.getString(2)))
            throw new InvalidLoginException("Login not found");
        closeConnections(results);
        return selectUser(new LoginForm(user, ""));
    }

    public static void deleteLogin(String user, String date) throws SQLException {
        openConnections();
        String sql = "DELETE FROM Login_Control WHERE email ='" + user + "' AND Logged_At='" + date + "'";
        System.out.println(stmt.executeUpdate(sql));
        closeConnections();
    }

    public static List<BookReaded> getAllBooksReaded(String user) throws SQLException {
        List<BookReaded> books = null;
        openConnections();
        String sql = "SELECT * FROM Readed_Books WHERE email = '" + user + "'";
        ResultSet results = stmt.executeQuery(sql);
        books = new ArrayList<>();
        while (results.next()) {
            books.add(new BookReaded(results.getString(1), results.getString(2), results.getString(3)));
        }
        closeConnections(results);
        return books;
    }

    public static void markBookAsRead(BookReaded book) throws SQLException {
        openConnections();
        String sql = "INSERT INTO Readed_Books VALUES ('" + book.getUser() + "', '" + book.getBook_id() + "', '"
                + book.getCategorie() + "')";
        stmt.executeUpdate(sql);
        closeConnections();
    }

    public static BookReaded verifyIfBookIsRead(String user, String id) throws SQLException{ 
        openConnections();
        String sql = "SELECT * FROM Readed_Books WHERE email='" + user + "' AND book_id='" + id + "'";
        ResultSet results = stmt.executeQuery(sql);
        results.next();
        BookReaded br = new BookReaded(results.getString(1), results.getString(2), results.getString(3));
        closeConnections(results);
        return br;
    }

    private static void openConnections() throws SQLException {
        if (conn == null || conn.isClosed())
            createConnection();
        if (stmt == null || stmt.isClosed())
            stmt = conn.createStatement();
    }

    private static void closeConnections() throws SQLException {
        if (!stmt.isClosed())
            stmt.close();
        if (!conn.isClosed())
            conn.close();
    }

    private static void closeConnections(ResultSet result) throws SQLException {
        if (!stmt.isClosed())
            stmt.close();
        if (!conn.isClosed())
            conn.close();
        if (!result.isClosed())
            result.close();
    }

}
