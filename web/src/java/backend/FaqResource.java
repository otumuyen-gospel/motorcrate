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
@Path("faq")
public class FaqResource {
   TextMessage message;
   public FaqResource(){
       message = new TextMessage();
   }
   
    @GET
    @Path("{id}/{question}/{answer}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("id") int id, @PathParam("question") String question,
            @PathParam("answer") String answer) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            String sql = "update faq set question='"+question+"',answer='"+answer+"' where id="+id;
            jdbc.update(stmt, sql);
            message.setMessage("Faq update Successful");
            jdbc.close(connect, stmt);
        }catch(Exception e){
             message.setMessage(JDBC.status);
        }
        return new Gson().toJson(message);
        
    }
    
    @GET
    @Path("{question}/{answer}")
    @Produces(MediaType.APPLICATION_JSON)
    public String insert(@PathParam("question") String question,
            @PathParam("answer") String answer) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            String sql = "insert into faq values(0,'"+question+"','"+answer+"')";
            jdbc.update(stmt, sql);
            message.setMessage("Faq created Successfully");
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
        ResultSet rs = jdbc.query(stmt, "select * from faq");
        int count = 0;
        Faqs[]faq = new Faqs[jdbc.getRowSize("faq")] ;
        try{
            
            while(rs.next()){
              Faqs p = new Faqs();
              p.setId(rs.getInt(1)); 
              p.setQuestion(rs.getString(2));
              p.setAnswer(rs.getString(3));
              
              faq[count] = p;
              
              count++;
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(faq);
        
    }
    
}
