/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 *
 * @author eronads
 */
public class LoginForm{
    @JsonbProperty("email")
    public String email;
    @JsonbProperty("password")
    public String password;
    
    public LoginForm(){
    }
    
    public LoginForm(String email, String password){
        this.email = email;
        this.password = password;
    }
}
