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
@Path("editor")
public class EditorResource {
    TextMessage message = new TextMessage();
    
    @GET
    @Path("{email}/{facebook}/{instagram}/{twitter}/{youtube}/{key}/{address}/{telephone}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("email") String email, @PathParam("facebook") String facebook,
            @PathParam("instagram") String instagram, @PathParam("twitter") String twitter,
            @PathParam("youtube") String youtube,@PathParam("key") String key, @PathParam("address") String address,
            @PathParam("telephone") String telephone) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from editor");
            String sql;
            if(jdbc.getRowSize("editor") == 0){
                sql = "insert into editor values('','','','"+email+"','"+facebook+"','"+instagram+"','"+twitter+"','"+
                        youtube+"','"+key+"','"+address+"','"+telephone+"','','','','')";
            }else{
                sql = "update editor set email='"+email+"', facebook='"+facebook+"', instagram='"+
                    instagram+"', twitter='"+twitter+"', youtube='"+youtube+"', address='"+
                    address+"', telephone='"+telephone+"',api_key='"+key+"'";
            }
            
            jdbc.update(stmt, sql);
            message.setMessage("Contacts update Successful");
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
        ResultSet rs = jdbc.query(stmt, "select * from editor");
        Contacts contacts = new Contacts();
        try{
            
            if(rs.next()){
              contacts.setAddress(rs.getString("address"));
              contacts.setTelephone(rs.getString("telephone"));
              contacts.setEmail(rs.getString("email"));
              contacts.setFacebook(rs.getString("facebook"));
              contacts.setTwitter(rs.getString("twitter"));
              contacts.setInstagram(rs.getString("instagram"));
              contacts.setYoutube(rs.getString("youtube"));
              contacts.setKey(rs.getString("api_key"));
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(contacts);
        
    }
}

class Contacts{
    String email, facebook, instagram, twitter, youtube, telephone, address,key;
    public void setEmail(String value){
        this.email = value;
    }
    public String getEmail(){
        return email;
    }
    
    public void setFacebook(String value){
        this.facebook = value;
    }
    public String getFacebook(){
        return facebook;
    }
    public void setInstagram(String value){
        this.instagram = value;
    }
    public String getInstagram(){
        return instagram;
    }
    public void setTwitter(String value){
        this.twitter = value;
    }
    public String getTwitter(){
        return twitter;
    }
    public void setYoutube(String value){
        this.youtube = value;
    }
    public String getYoutube(){
        return youtube;
    }
    public void setKey(String value){
        this.key = value;
    }
    public String getKey(){
        return key;
    }
    public void setTelephone(String value){
        this.telephone = value;
    }
    public String getTelephone(){
        return telephone;
    }
    public void setAddress(String value){
        this.address = value;
    }
    public String getAddress(){
        return address;
    }
}