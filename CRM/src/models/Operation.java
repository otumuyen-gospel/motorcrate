/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author user1
 */
public class Operation {
    int id;String email, action, why;
    public void setId(int value){
        this.id = value;
    }
    public int getId(){
        return this.id;
    }
    
    public void setEmail(String value){
        this.email = value;
    }
    public String getEmail(){
        return this.email;
    }
    public void setAction(String value){
        this.action = value;
    }
    public String getAction(){
        return this.action;
    }
    public void setWhy(String value){
        this.why = value;
    }
    public String getWhy(){
        return this.why;
    }
    
}
