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

@Path("close")
public class CloseResource {
   TextMessage message;
   public CloseResource(){
       message = new TextMessage();
   }
   
    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAccount(@PathParam("email")String email) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from stripes_keys");
            String secret_keys="";
            if(rs.next()){
                secret_keys = rs.getString("secret_keys");
            }
            rs = jdbc.query(stmt, "select status,cust_id from subscription where"
            + " email='"+email+"'");
            String status = "";
            String cust_id = "";
            if(rs.next()){
                cust_id = rs.getString("cust_id");
                status = rs.getString("status");
            }
            Payment pay = new Payment( secret_keys);
            
            if(status == "active"){
                if(pay.deleteCustomer(cust_id)){
                    jdbc.update(stmt, "delete from subscription where cust_id='"+cust_id+"'");
                    jdbc.update(stmt, "delete from dates where email='"+email+"'");
                    jdbc.update(stmt, "delete from account where email='"+email+"'");
                    jdbc.update(stmt, "delete from operations where email='"+email+"'");
                    jdbc.update(stmt, "delete from referal where email='"+email+"'");
                    jdbc.update(stmt, "delete from reset where email='"+email+"'");
                    jdbc.update(stmt, "delete from winners where username='"+email+"'");
                    message.setMessage(email+" account successfully closed. \n"
                            + "please send this user email detailing the reason why his account was closed");
                }
            }else{
                    jdbc.update(stmt, "delete from subscription where cust_id='"+cust_id+"'");
                    jdbc.update(stmt, "delete from dates where email='"+email+"'");
                    jdbc.update(stmt, "delete from account where email='"+email+"'");
                    jdbc.update(stmt, "delete from operations where email='"+email+"'");
                    jdbc.update(stmt, "delete from referal where email='"+email+"'");
                    jdbc.update(stmt, "delete from reset where email='"+email+"'");
                    jdbc.update(stmt, "delete from winners where username='"+email+"'");
                    message.setMessage(email+" account successfully closed. \n"
                            + "please send this user email detailing the reason why his account was closed");
            }
            jdbc.close(connect, stmt,rs);
           
        }catch(Exception e){
            message.setMessage(e.getMessage());
        }
        return new Gson().toJson(message);
        
    }
}
