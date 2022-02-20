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
public class Month {
    int jan;
    int feb;
    int mar;
    int apr;
    int may;
    int jun ;
    int jul;
    int aug;
    int sep;
    int oct;
    int nov;
    int dec;
    public Month(int jan, int feb, int mar, int apr, int may, int jun, int jul,int aug, int sep,int oct,
            int nov, int dec){
        this.jan = jan;
        this.feb = feb;
        this.mar = mar;
        this.apr = apr;
        this.may = may;
        this.jun = jun;
        this.jul = jul;
        this.aug = aug;
        this.sep = sep;
        this.oct = oct;
        this.nov = nov;
        this.dec = dec;
    }
    public void setJanuary(int jan){
        this.jan = jan;
    }
    public int getJanuary(){
        return this.jan;
    }
    public void setFebuary(int feb){
        this.feb = feb;
    }
    public int getFebuary(){
        return this.feb;
    }
    public void setMarch(int mar){
        this.mar = mar;
    }
    public int getMarch(){
        return this.mar;
    }
    public void setApril(int apr){
        this.apr = apr;
    }
    public int getApril(){
        return this.apr;
    }
    public void setMay(int may){
        this.may = may;
    }
    public int getMay(){
        return this.may;
    }
    public void setJune(int jun){
        this.jun = jun;
    }
    public int getJune(){
        return this.jun;
    }
    public void setJuly(int jul){
        this.jul = jul;
    }
    public int getJuly(){
        return this.jul;
    }
    public void setAugust(int aug){
        this.aug = aug;
    }
    public int getAugust(){
        return this.aug;
    }
    public void setSeptember(int sep){
        this.sep = sep;
    }
    public int getSeptember(){
        return this.sep;
    }
    public void setOctober(int oct){
        this.oct = oct;
    }
    public int getOctober(){
        return this.oct;
    }
    public void setNovember(int nov){
        this.nov = nov;
    }
    public int getNovember(){
        return this.nov;
    }
    public void setDecember(int dec){
        this.dec = dec;
    }
    public int getDecember(){
        return this.dec;
    }
}