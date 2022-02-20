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
public class Accounts{
   private int id;
   private String firstname;
   private String lastname;
   private String email;
   private String subscription;
   private String shippingname;
   private String country;
   private String state;
   private String city;
   private String zip;
   private String street;
   private String landmark;
   private String duration;
   private double totalcost;
   
   public void setId(int id){
       this.id = id;
   }
   public int getId(){
       return this.id;
   }
   public void setFirstName(String value){
       this.firstname = value;
   }
   public String getFirstName(){
       return this.firstname;
   }
   public void setLastName(String value){
       this.lastname = value;
   }
   public String getLastName(){
       return this.lastname;
   }
   public void setEmail(String value){
       this.email = value;
   }
   public String getEmail(){
       return this.email;
   }
   public void setSubscription(String value){
       this.subscription = value;
   }
   public String getSubscription(){
       return this.subscription;
   }
   public void setShippingName(String value){
       this.shippingname = value;
   }
   public String getShippingName(){
       return this.shippingname;
   }
   public void setCountry(String value){
       this.country = value;
   }
   public String getCountry(){
       return this.country;
   }
   public void setState(String value){
       this.state = value;
   }
   public String getState(){
       return this.state;
   }
   public void setCity(String value){
       this.city = value;
   }
   public String getCity(){
       return this.city;
   }
   public void setZip(String value){
       this.zip = value;
   }
   public String getZip(){
       return this.zip;
   }
   public void setStreet(String value){
       this.street = value;
   }
   public String getStreet(){
       return this.street;
   }
   public void setLandmark(String value){
       this.landmark = value;
   }
   public String getLandmark(){
       return this.landmark;
   }
   public void setDuration(String value){
       this.duration = value;
   }
   public String getDuration(){
       return this.duration;
   }
   public void setTotalCost(double value){
       this.totalcost = value;
   }
   public double getTotalCost(){
       return this.totalcost;
   }
}