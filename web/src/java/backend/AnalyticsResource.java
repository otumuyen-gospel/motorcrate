/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author user1
 */
@Path("analytics")
public class AnalyticsResource {
    int january = 0;
    int febuary = 0;
    int march = 0;
    int april = 0;
    int may = 0;
    int june = 0;
    int july = 0;
    int august = 0;
    int september = 0;
    int october = 0;
    int november = 0;
    int december = 0;
    
    @GET
    @Path("{column}")
    @Produces(MediaType.APPLICATION_JSON)
    public String analyze(@PathParam("column") String column) {
        int currentYear = LocalDate.now().getYear();
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        String sql = "";
        if(column.equalsIgnoreCase("registration")){
            sql = "select regdate from account";
        }else if(column.equalsIgnoreCase("subscription")){
            sql = "select subscribedate from account where subscription='active' ";
        }
        ResultSet rs = jdbc.query(stmt, sql);
       
        try{
            while(rs.next()){
                String date = rs.getString(1);
                //analyzing the data for the current year
                if(currentYear == Integer.parseInt(date.split("-")[0])){
                    //collate monthly data
                    switch(date.split("-")[1]){
                        case "01":
                            january++;
                            break;
                        case "02":
                            febuary++;
                            break;
                        case "03":
                            march++;
                            break;
                        case "04":
                            april++;
                            break;
                        case "05":
                            may++;
                            break;
                        case "06":
                            june++;
                            break;
                        case "07":
                            july++;
                            break;
                         case "08":
                            august++;
                            break;
                        case "09":
                            september++;
                            break;
                        case "10":
                            october++;
                            break;
                        case "11":
                            november++;
                            break;
                        case "12":
                            december++;
                            break;
                    }
                }
                
            }
            
        }catch(Exception e){
            
        }
        Month month = new Month(january,febuary,march,april,may,june,july,august,september,october,
                    november,december);
            return new Gson().toJson(month);
    }
    

}


class Month {
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