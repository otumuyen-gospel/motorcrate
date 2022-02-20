/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

public class Profile{
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    
    
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setFirstName(String name){
        this.firstname = name;
    }
    public String getFirstName(){
        return firstname;
    }
    public void setLastName(String name){
        this.lastname = name;
    }
    public String getLastName(){
        return lastname;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
    
}
