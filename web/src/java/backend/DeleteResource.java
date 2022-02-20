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
@Path("delete")
public class DeleteResource {
   TextMessage message;
   public DeleteResource(){
       message = new TextMessage();
   }
   
    @GET
    @Path("{id}/{table}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("id") int id, @PathParam("table") String table) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            String sql = "delete from "+table+" where id="+id;
            jdbc.update(stmt, sql);
            message.setMessage("delete operation Successful");
            jdbc.close(connect, stmt);
        }catch(Exception e){
             message.setMessage(JDBC.status);
        }
        return new Gson().toJson(message);
        
    }
    
       
}
   