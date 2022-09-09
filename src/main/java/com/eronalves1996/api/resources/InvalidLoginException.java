/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eronalves1996.api.resources;

/**
 *
 * @author eronads
 */
public class InvalidLoginException extends Exception {
    public InvalidLoginException(String msg){
        super(msg);
    }
}
