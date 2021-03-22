/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reusables;

import EntityClasses.Users;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class SharedData {
    private static String user = "";
    private static String role ="";
    private  static  Users userObject = null;
    
    
    private static String modal = "";
    public void setUser(String name) {
    
        SharedData.user = name;
        //patientId = id;
    }
    
    public static String getUser(){
    
        return user;
    }
     public void setRole(String role) {
    
        SharedData.role = role;
        //patientId = id;
    }
    public static String getRole(){
    
        return role;
    }
    public void setModal(String panel) {
    
        SharedData.modal = panel;
        //patientId = id;
    }
    
     public static String getModal(){
    
        return modal;
    }
    public void setUserObject(Users currentUser) {
    
        SharedData.userObject = currentUser;
        //patientId = id;
    }
    
     public static Users getUserObject(){
    
        return userObject;
    }
    
 
}
