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
    
    public User login(LoginForm data){
        UserDAO.createConnection();
        return UserDAO.selectUser(data);
    }
}
