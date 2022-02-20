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
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("account")
public class AccountResource {
    
    @GET
    @Path("{viewer}")
    @Produces(MediaType.APPLICATION_JSON)
    public String view(@PathParam("viewer") String viewer) {
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        ResultSet rs = jdbc.query(stmt, "select * from account");
        int count = 0;
        Accounts[]acc = new Accounts[jdbc.getRowSize("account")] ;
        try{
            
            while(rs.next()){
              Accounts p = new Accounts();
              p.setId(rs.getInt("id"));
              p.setFirstName(rs.getString("firstname"));
              p.setLastName(rs.getString("lastname"));
              p.setEmail(rs.getString("email"));
              p.setSubscription(rs.getString("subscription"));
              p.setShippingName(rs.getString("shippingFirstName")+" "+rs.getString("shippingLastName"));
              p.setCountry(rs.getString("country"));
              p.setState(rs.getString("state"));
              p.setCity(rs.getString("city"));
              p.setZip(rs.getString("zip"));
              p.setStreet(rs.getString("street"));
              p.setLandmark(rs.getString("landmark"));
              p.setDuration(rs.getString("duration"));
              p.setTotalCost(rs.getDouble("total"));
              acc[count] = p;
              
              count++;
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(acc);
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String download() {
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        ResultSet rs = jdbc.query(stmt, "select * from account where subscription='active'");
        int count = 0;
        Accounts[]acc = new Accounts[jdbc.getRowSize("account")] ;
        try{
            
            while(rs.next()){
              Accounts p = new Accounts();
              p.setId(rs.getInt("id"));
              p.setFirstName(rs.getString("firstname"));
              p.setLastName(rs.getString("lastname"));
              p.setEmail(rs.getString("email"));
              p.setSubscription(rs.getString("subscription"));
              p.setShippingName(rs.getString("shippingFirstName")+rs.getString("shippingLastName"));
              p.setCountry(rs.getString("country"));
              p.setState(rs.getString("state"));
              p.setCity(rs.getString("city"));
              p.setZip(rs.getString("zip"));
              p.setStreet(rs.getString("street"));
              p.setLandmark(rs.getString("landmark"));
              p.setDuration(rs.getString("duration"));
              p.setTotalCost(rs.getDouble("total"));
              acc[count] = p;
              
              count++;
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(acc);
        
    }

}

class Accounts{
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