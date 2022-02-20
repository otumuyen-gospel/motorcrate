/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author user1
 */
public class InstantMail {
    
    public String sendMessage(String recipient, String sub, String body) {
        String status = "";
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
                to = recipient;
            }
            
            jdbc.close(connect, stmt, rs);
            
            //set subject and html message
            String subject = sub;
           
           Email mail = new Email();
           mail.doSend(host, password, user, port, from, to, subject, body,
                   facebook,instagram, twitter);
          status = Email.status;
            
        }catch(Exception e){
             status = Email.status;
        }
        return status;
        
    }
    
}
