/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

public class Contacts{
    String email, facebook, instagram, twitter, youtube, telephone, address,key;
    public void setEmail(String value){
        this.email = value;
    }
    public String getEmail(){
        return email;
    }
    
    public void setFacebook(String value){
        this.facebook = value;
    }
    public String getFacebook(){
        return facebook;
    }
    public void setInstagram(String value){
        this.instagram = value;
    }
    public String getInstagram(){
        return instagram;
    }
    public void setTwitter(String value){
        this.twitter = value;
    }
    public String getTwitter(){
        return twitter;
    }
    public void setYoutube(String value){
        this.youtube = value;
    }
    public String getYoutube(){
        return youtube;
    }
    public void setKey(String value){
        this.key = value;
    }
    public String getKey(){
        return key;
    }
    public void setTelephone(String value){
        this.telephone = value;
    }
    public String getTelephone(){
        return telephone;
    }
    public void setAddress(String value){
        this.address = value;
    }
    public String getAddress(){
        return address;
    }
}