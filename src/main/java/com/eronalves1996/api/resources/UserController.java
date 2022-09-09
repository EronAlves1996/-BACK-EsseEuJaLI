/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api.resources;

import com.eronalves1996.api.LoginForm;

/**
 *
 * @author eronads
 */
public class UserController {
    
    public User login(LoginForm data) throws InvalidLoginException {
        UserDAO.createConnection();
        User x = UserDAO.selectUser(data);
        if (!x.getPassword().equals(data.password)){
            throw new InvalidLoginException("Incorrect password");
        }
        return x;
    }
}
