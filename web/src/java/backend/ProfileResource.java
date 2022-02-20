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
@Path("profile")
public class ProfileResource {
   TextMessage message;
   public ProfileResource(){
       message = new TextMessage();
   }
   
    @GET
    @Path("{id}/{firstname}/{lastname}/{email}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("id") int id, @PathParam("firstname") String firstname,
            @PathParam("lastname") String lastname,@PathParam("email") String email,
            @PathParam("password") String password) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from admin");
            String sql;
            if(jdbc.getRowSize("admin") == 0){
                sql = "insert into admin values(0,'"+firstname+"','"+lastname+"','"+email+"','"+password+"')";
            }else{
                sql = "update admin set firstname='"+firstname+"', lastname='"+lastname+"', email='"+email+
                    "', password='"+password+"' where id="+id;
            }
            
            jdbc.update(stmt, sql);
            message.setMessage("profile update Successful");
            jdbc.close(connect, stmt,rs);
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
        ResultSet rs = jdbc.query(stmt, "select * from admin");
        int count = 0;
        Profile[]profile = new Profile[jdbc.getRowSize("admin")] ;
        try{
            
            while(rs.next()){
              Profile p = new Profile();
              p.setId(rs.getInt(1));
              p.setFirstName(rs.getString(2));
              p.setLastName(rs.getString(3));
              p.setEmail(rs.getString(4));
              p.setPassword(rs.getString(5));
              
              profile[count] = p;
              
              count++;
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(profile);
        
    }
    
}

class Profile{
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    
    
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setFirstName(String name){
        this.firstname = name;
    }
    public String getFirstName(){
        return firstname;
    }
    public void setLastName(String name){
        this.lastname = name;
    }
    public String getLastName(){
        return lastname;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
    
}
