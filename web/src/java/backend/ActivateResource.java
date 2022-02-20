/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.Statement;
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
@Path("activate")
public class ActivateResource {
 
    TextMessage message= new TextMessage();
    @GET
    @Path("{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("status")String status) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            jdbc.update(stmt, "update coupon set status='"+status+"'");
            jdbc.close(connect, stmt);
            message.setMessage("coupon code successfully "+status);
            
        }catch(Exception e){
             message.setMessage(JDBC.status);
        }
        return new Gson().toJson(message);
        
    }
    
    
}
