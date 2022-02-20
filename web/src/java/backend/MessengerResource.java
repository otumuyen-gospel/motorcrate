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
@Path("Messenger")
public class MessengerResource {

    TextMessage message = new TextMessage();
    
    @GET
    @Path("{recipient}/{subject}/{html}")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendMessage(@PathParam("recipient") String recipient, 
            @PathParam("subject") String sub, @PathParam("html") String body) {
        try{
            //fetch email server details
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            String sql = "select facebook, instagram, twitter,email,server,user,password,port from editor";
            ResultSet rs = jdbc.query(stmt,sql);
            String host = "";
            String from = "";
            String password = "";
            String port = "";
            String user ="";
            String facebook = "";
            String instagram = "";
            String twitter = "";
            if(rs.next()){
                host = rs.getString("server");
                user = rs.getString("user");
                password = rs.getString("password");
                port = rs.getString("port");
                from = UrlEntities.decode(rs.getString("email"));
                facebook = UrlEntities.decode(rs.getString("facebook"));
                instagram = UrlEntities.decode(rs.getString("instagram"));
                twitter = UrlEntities.decode(rs.getString("twitter"));
            } 
            
            //set recipients
            String to = "";
            if(recipient.equalsIgnoreCase("all")){
                sql = "select email from account";
                rs = jdbc.query(stmt,sql);
                while(rs.next()){
                    to+=rs.getString("email")+",";
                }
            }else{
                to = UrlEntities.decode(recipient);
            }
            
            jdbc.close(connect, stmt, rs);
            
            //set subject and html message
            String subject = UrlEntities.decode(sub);
           
           Email mail = new Email();
           mail.doSend(host, password, user, port, from, to, subject, UrlEntities.decode(body),
                   facebook,instagram, twitter);
           message.setMessage(Email.status);
            
        }catch(Exception e){
             message.setMessage(e.getMessage());
        }
        return new Gson().toJson(message);
        
    }
    
    
    
    @GET
    @Path("{fetch}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmailSettings(@PathParam("fetch") String fetch) {
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        String sql = "select server,user,password,port from editor";
        
        ResultSet rs = jdbc.query(stmt,sql);
        
        try{
            if(rs.next()){
                String server = rs.getString(1);
                String user = rs.getString(2);
                String password = rs.getString(3);
                String port = rs.getString(4);
                message.setMessage(server+"/"+user+"/"+password+"/"+port);
            }  
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(message);
        
    }
    
}
