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
 * @author Gospel system
 */
@Path("privacy")
public class PrivacyResource {

    TextMessage message = new TextMessage();
    @GET
    @Path("{fetch}")
    @Produces(MediaType.APPLICATION_JSON)
    public String view(@PathParam("fetch") String fetch) {
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        ResultSet rs = jdbc.query(stmt, "select privacy from editor");
        try{
            
            if(rs.next()){
              message.setMessage(rs.getString("privacy"));
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
             message.setMessage(JDBC.status);
        }
        return new Gson().toJson(message);
        
    }
    
}
