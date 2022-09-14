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

    private static final long serialVersionUID = 3208999515119419278L;

    public InvalidLoginException(String msg){
        super(msg);
    }
}
