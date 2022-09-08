/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api.resources;

import com.eronalves1996.api.LoginForm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author eronads
 */
public class UserDAO {

    private final static String URL = "jdbc:derby://localhost:1527/jaLiDb";
    private final static String table = "User_Info";

    private static Connection conn = null;
    private static Statement stmt = null;

    public static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(URL);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }
    
    public static User selectUser(LoginForm lf){
        try{
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM " + table + " WHERE email = '" + lf.email + "'");
            results.next();
            User user = new User();
            user.setEmail(results.getString(1));
            user.setPassword(results.getString(2));
            user.setName(results.getString(3));
            user.setTotalPoints(results.getInt(4));
            results.close();
            stmt.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new User();
    }
}
