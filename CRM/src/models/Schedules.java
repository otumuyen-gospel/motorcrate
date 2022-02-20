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
public class Schedules{
    private int id;
    private String oneMonth;
    private String threeMonth;
    private String sixMonth;
    private String year;
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setOneMonthly(String val){
        this.oneMonth = val;
    }
    public String getOneMonthly(){
        return this.oneMonth;
    }
    public void setThreeMonthly(String val){
        this.threeMonth = val;
    }
    public String getThreeMonthly(){
        return this.threeMonth;
    }
    public void setSixMonthly(String val){
        this.sixMonth = val;
    }
    public String getSixMonthly(){
        return this.sixMonth;
    }
    public void setYearly(String val){
        this.year = val;
    }
    public String getYearly(){
        return this.year;
    }
    
    
}
