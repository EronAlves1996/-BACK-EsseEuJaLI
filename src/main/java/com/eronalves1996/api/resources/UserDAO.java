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

    private Connection conn = null;
    private Statement stmt = null;

    @SuppressWarnings("deprecation")
    public void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            // Get a connection
            conn = DriverManager.getConnection(URL);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public User selectUser(LoginForm lf) {
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

    public void createLoginEntry(String email, Date loggedAt) throws InvalidLoginException, SQLException {
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

    public  User verifyActiveLogin(String user, String date) throws InvalidLoginException, SQLException {
        openConnections();
        ResultSet results = stmt.executeQuery("SELECT * FROM Login_Control WHERE email='" + user + "'");

        results.next();
        if (!date.equals(results.getString(2)))
            throw new InvalidLoginException("Login not found");
        closeConnections(results);
        return selectUser(new LoginForm(user, ""));
    }

    public void deleteLogin(String user, String date) throws SQLException {
        String sql = "DELETE FROM Login_Control WHERE email ='" + user + "' AND Logged_At='" + date + "'";
        openConnections();
        stmt.executeUpdate(sql);
        closeConnections();
    }

    public List<BookReaded> getAllBooksReaded(String user) throws SQLException {
        List<BookReaded> books = null;
        String sql = "SELECT * FROM Readed_Books WHERE email = '" + user + "'";
        openConnections();
        ResultSet results = stmt.executeQuery(sql);
        books = new ArrayList<>();
        while (results.next()) {
            books.add(new BookReaded(results.getString(1), results.getString(2), results.getString(3), results.getInt(4)));
        }
        closeConnections(results);
        return books;
    }

    public void markBookAsRead(BookReaded book) throws SQLException {
        openConnections();
        String sql = "INSERT INTO Readed_Books VALUES ('" + book.getUser() + "', '" + book.getBook_isbn() + "', '"
                + book.getCategorie() + "', " + book.getRelated_points() + ")";
        stmt.executeUpdate(sql);
        closeConnections();
    }

    public BookReaded verifyIfBookIsRead(String user, String id) throws SQLException{ 
        String sql = "SELECT * FROM Readed_Books WHERE email='" + user + "' AND book_isbn='" + id + "'";
        openConnections();
        ResultSet results = stmt.executeQuery(sql);
        results.next();
        BookReaded br = new BookReaded(results.getString(1), results.getString(2), results.getString(3), results.getInt(4));
        closeConnections(results);
        return br;
    }

    private void openConnections() throws SQLException {
        if (conn == null || conn.isClosed())
            createConnection();
        if (stmt == null || stmt.isClosed())
            stmt = conn.createStatement();
    }

    private void closeConnections() throws SQLException {
        if (!stmt.isClosed())
            stmt.close();
        if (!conn.isClosed())
            conn.close();
    }

    private void closeConnections(ResultSet result) throws SQLException {
        if (!result.isClosed())
            result.close();
        if (!stmt.isClosed())
            stmt.close();
        if (!conn.isClosed())
            conn.close();

    }

}
