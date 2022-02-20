/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.time.LocalDate;

/**
 *
 * @author user1
 */
public class Billing {
    String subscription;
    String shippingFirstName;
    String shippingLastName;
    String country;
    String state;
    String city;
    String zip;
    String street;
    String landmark;
    String category;
    String duration;
    String subscribeDate;
    String email;
    String nextDueDate;
    double cost = 0;
    double shippingCost =0;
    double discount = 0;
    double total =0;
    double totalDiscount=0;
    public void setDueDate(String value){
        this.nextDueDate = value;
    }
    public String getDueDate(){
        return this.nextDueDate;
    }
    public void setCity(String value){
        this.city = value;
    }
    public String getCity(){
        return this.city;
    }
    public void setSubscription(String value){
        this.subscription = value;
    }
    public String getSubscription(){
        return this.subscription;
    }
    public void setShippingFirstName(String value){
        this.shippingFirstName = value;
    }
    public String getShippingFirstName(){
        return this.shippingFirstName;
    }
    public void setShippingLastName(String value){
        this.shippingLastName = value;
    }
    public String getShippingLastName(){
        return this.shippingLastName;
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
    public void setCategory(String value){
        this.category = value;
    }
    public String getCategory(){
        return this.category;
    }
    public void setDuration(String value){
        this.duration = value;
    }
    public String getDuration(){
        return this.duration;
    }
    public void setSubscribeDate(){
        this.subscribeDate = LocalDate.now().toString();
    }
    public String getSubscribeDate(){
        return this.subscribeDate;
    }
    
    public void setCost(double value){
        this.cost = value;
    }
    public double getCost(){
        return this.cost;
    } 
    public void setShippingCost(double value){
      this.shippingCost = value;   
    }
    public double getShippingCost(){
        return this.shippingCost;
    } 
    public void setDiscount(double value){
      this.discount = value;   
    }
    public double getDiscount(){
        return this.discount;
    }
    public void setTotalDiscount(double value){
      this.totalDiscount = value + discount;   
    }
    public double getTotalDiscount(){
        return Math.round(this.totalDiscount * 100.0) / 100.0;
    }
    
    public void setTotal(){
       total = cost + shippingCost - totalDiscount;
        
    }
    public double getTotal(){
        return Math.round(this.total * 100.0) / 100.0;
    } 
    
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
}
