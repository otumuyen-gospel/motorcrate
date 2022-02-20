/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@Path("motor")
public class Authenticate {
   TextMessage message;
   public Authenticate(){
       message = new TextMessage();
   }
    @GET
    @Path("{email}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("email") String email, @PathParam("password") String password) {
        
        
       try {
           
               JDBC jdbc = new JDBC();
               Connection connect = jdbc.connect();
               Statement stmt = jdbc.statement(connect);
               ResultSet rs = jdbc.query(stmt, "select * from admin");
               
               if(jdbc.getRowSize("admin") == 0){
                   String sql = "insert into admin values(0,'','','"+email+"','"+password+"')";
                   jdbc.update(stmt, sql);
                   if(JDBC.status == "database update executed successfully"){
                        message.setMessage("authenticated");
                   }
               }else{
                   rs = jdbc.query(stmt, "select * from admin where password='"+
                        password+"' and email='"+email+"'");

                   if(rs.next()){
                       message.setMessage("authenticated");
                   }else{
                       message.setMessage("please enter the correct email and/or password");
                   }
               }
               
                
               jdbc.close(connect, stmt, rs);
               
       } catch (SQLException ex) {
           message.setMessage(JDBC.status);
       }

        
       return new Gson().toJson(message);
    }
    

}
