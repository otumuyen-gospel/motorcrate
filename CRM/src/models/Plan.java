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
public class Plan {
    int id;String productName,productId,planName,planId;double cost,shipping;
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setProductName(String value){
        this.productName= value;
    }
    public String getProductName(){
        return this.productName;
    }
    public void setProductId(String value){
        this.productId= value;
    }
    public String getProductId(){
        return this.productId;
    }
    public void setPlanName(String value){
        this.planName= value;
    }
    public String getPlanName(){
        return this.planName;
    }
    public void setPlanId(String value){
        this.planId= value;
    }
    public String getPlanId(){
        return this.planId;
    }
    public void setCost(double value){
        this.cost= value;
    }
    public double getCost(){
        return this.cost;
    }
    public void setShipping(double value){
        this.shipping= value;
    }
    public double getShipping(){
        return this.shipping;
    }
}
