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
@Path("invoice")
public class InvoiceResource {
    InvoiceData []data = null;
    @GET
    @Path("view")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("view") String view) {
        try{
            
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from editor");
            String address = "";
            String email = "";
            String facebook = "";
            String instagram = "";
            String twitter = "";
            String telephone = "";
            try{
                if(rs.next()){
                    address = rs.getString("address");
                    email = rs.getString("email");
                    facebook = rs.getString("facebook");
                    instagram = rs.getString("instagram");
                    twitter = rs.getString("twitter");
                    telephone =rs.getString("telephone");
                }
            }catch(Exception e){
                
            }
            
            
             rs = jdbc.query(stmt, "select * from account where subscription='active'");
             int count = 0;
             data = new InvoiceData[jdbc.getRowSizeByQuery("SELECT COUNT(*) FROM account "
                     + "where subscription='active'")];
             while(rs.next()){
                 InvoiceData d = new InvoiceData();
                 d.setAddress(address);
                 d.setEmail(email);
                 d.setTelephone(telephone);
                 d.setFacebook(facebook);
                 d.setTwitter(twitter);
                 d.setInstagram(instagram);
                 d.setShippingName(rs.getString("shippingFirstName")+" "+rs.getString("shippingLastName"));
                 d.setCountry(rs.getString("country"));
                 d.setState(rs.getString("state"));
                 d.setCity(rs.getString("city"));
                 d.setPostal(rs.getString("zip"));
                 d.setStreet(rs.getString("street"));
                 d.setPlan(rs.getString("duration"));
                 d.setTotal(rs.getDouble("total"));
                 d.setDiscount(rs.getDouble("discount"));
                 d.setPrice(rs.getDouble("cost"));
                 d.setShippingCost(rs.getDouble("shippingCost"));
                 data[count] = d;
                 count++;
             }
            jdbc.close(connect, stmt, rs);
        }catch(Exception e){
            
        }
         return new Gson().toJson(data);
        
    }

}
