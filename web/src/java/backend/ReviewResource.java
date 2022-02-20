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

/**
 * REST Web Service
 *
 * @author user1
 */
@Path("review")
public class ReviewResource {
   TextMessage message;
   public ReviewResource(){
       message = new TextMessage();
   } 
   
   
    @GET
    @Path("{id}/{name}/{rating}/{review}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("id") int id, @PathParam("name") String name,
            @PathParam("rating") String rating, @PathParam("review") String review) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            String sql = "update review set name='"+name+"',rating='"+rating+"',review='"+review+"' where id="+id;
            jdbc.update(stmt, sql);
            message.setMessage("Review update Successful");
            jdbc.close(connect, stmt);
        }catch(Exception e){
             message.setMessage(JDBC.status);
        }
        return new Gson().toJson(message);
        
    }
    
    @GET
    @Path("{name}/{rating}/{review}")
    @Produces(MediaType.APPLICATION_JSON)
    public String insert(@PathParam("name") String name,
            @PathParam("rating") String rating, @PathParam("review") String review) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            String sql = "insert into review values(0,'"+name+"','"+rating+"','"+review+"')";
            jdbc.update(stmt, sql);
            message.setMessage("Review created Successfully");
            jdbc.close(connect, stmt);
        }catch(Exception e){
             message.setMessage(JDBC.status);
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
        ResultSet rs = jdbc.query(stmt, "select * from review");
        int count = 0;
        Reviews[]review = new Reviews[jdbc.getRowSize("review")] ;
        try{
            
            while(rs.next()){
              Reviews p = new Reviews();
              p.setId(rs.getInt(1));
              p.setName(rs.getString(2));
              p.setRating(rs.getString(3));
              p.setReview(rs.getString(4));
              review[count] = p;
              
              count++;
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(review);
        
    }
    
}
