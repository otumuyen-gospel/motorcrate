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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author user1
 */
@Path("stripeKey")
public class StripeKeyResource {
   TextMessage message;
   public StripeKeyResource(){
       message = new TextMessage();
   }
   
    @GET
    @Path("{id}/{secret_key}/{publishable_key}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("id")int id, @PathParam("secret_key") String secret_key,
            @PathParam("publishable_key") String publishable_key) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from stripes_keys");
            String sql;
            if(jdbc.getRowSize("stripes_keys") == 0){
                sql = "insert into stripes_keys values(0,'"+secret_key+"','"+publishable_key+"')";
            }else{
                
                sql = "update stripes_keys set secret_keys='"+secret_key+"',publishable_keys='"+publishable_key+
                        "' where id="+id;
            
            }
            jdbc.update(stmt, sql);
            jdbc.close(connect, stmt);
            message.setMessage(JDBC.status);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(message);
        
    }
    
    @GET
    @Path("{fetch}")
    @Produces(MediaType.APPLICATION_JSON)
    public String view(@PathParam("fetch") String fetch) {
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        String sql = "select * from stripes_keys";
        
        ResultSet rs = jdbc.query(stmt,sql);
        
        try{
            if(rs.next()){
                int id = rs.getInt(1);
                String secret_key = rs.getString(2);
                String publishable_key = rs.getString(3);
                message.setMessage(id+"/"+secret_key+"/"+publishable_key);
            }  
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(message);
        
    }
   
    
    
}
