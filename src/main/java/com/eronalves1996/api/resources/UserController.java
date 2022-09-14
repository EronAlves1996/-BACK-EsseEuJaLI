/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api.resources;

import java.util.Date;

import com.eronalves1996.api.LoginForm;

/**
 *
 * @author eronads
 */
public class UserController {
    
    public User login(LoginForm informedCredentials) throws InvalidLoginException {
        User selectedUser = UserDAO.selectUser(informedCredentials);
        if (!selectedUser.getPassword().equals(informedCredentials.password)){
            throw new InvalidLoginException("Incorrect password");
        }
        return selectedUser;
    }
    
    public Date createUserSession(String email) throws InvalidLoginException {
        Date loginDateAndHour = new Date();
        try { 
            UserDAO.createLoginEntry(email, loginDateAndHour);
            return loginDateAndHour;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return loginDateAndHour;
    }
}
