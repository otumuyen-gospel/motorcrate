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
public class InvoiceData {
    
    String email, facebook, instagram, twitter, telephone, 
            address,shippingName,street,city,state,country,postal,plan;
    double total,price,discount,shippingCost;
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
    public void setShippingName(String value){
       this.shippingName = value;
   }
   public String getShippingName(){
       return this.shippingName;
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
   public void setPostal(String value){
       this.postal = value;
   }
   public String getPostal(){
       return this.postal;
   }
   public void setStreet(String value){
       this.street = value;
   }
   public String getStreet(){
       return this.street;
   }
   public void setPlan(String value){
       this.plan = value;
   }
   public String getPlan(){
       return this.plan;
   }
   public void setTotal(double value){
       this.total = value;
   }
   public double getTotal(){
       return this.total;
   }
   public void setPrice(double value){
       this.price = value;
   }
   public double getPrice(){
       return this.price;
   }
   public double getDiscount(){
       return this.discount;
   }
   public void setDiscount(double value){
       this.discount = value;
   }
   public double getShippingCost(){
       return this.shippingCost;
   }
   public void setShippingCost(double value){
       this.shippingCost = value;
   }
   
   
    
}
