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
@Path("search")
public class SearchResource {

    @GET
    @Path("{searcher}")
    @Produces(MediaType.APPLICATION_JSON)
    public String customerSearch(@PathParam("searcher") String searcher) {
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        ResultSet rs = jdbc.query(stmt, "select * from account where firstname LIKE'"+searcher+"%' or lastname LIKE'"+
                searcher+"%' or shippingFirstName LIKE'"+searcher+"%' or shippingLastName LIKE'"
                +searcher+"%' or city LIKE'"+searcher+"%' or country LIKE'"+searcher+"%' or state Like'"+
                searcher+"%' or street Like'"+searcher+"%' or category LIKE'"+searcher+"%' or duration LIKE'"+
                searcher+"%' or landmark LIKE'"+searcher+"%' or email LIKE'"+searcher+"%' or subscription LIKE'"+
                searcher+"%' or zip LIKE'"+searcher+"%'");
        
        String query = "SELECT COUNT(*) FROM account where firstname LIKE'"+searcher+"%' or lastname LIKE'"+
                searcher+"%' or shippingFirstName LIKE'"+searcher+"%' or shippingLastName LIKE'"
                +searcher+"%' or city LIKE'"+searcher+"%' or country LIKE'"+searcher+"%' or state Like'"+
                searcher+"%' or street Like'"+searcher+"%' or category LIKE'"+searcher+"%' or duration LIKE'"+
                searcher+"%' or landmark LIKE'"+searcher+"%' or email LIKE'"+searcher+"%' or subscription LIKE'"+
                searcher+"%' or zip LIKE'"+searcher+"%'";
        Accounts[]acc = new Accounts[jdbc.getRowSizeByQuery(query)] ;
        int count = 0;
        try{
            
            while(rs.next()){
              Accounts p = new Accounts();
              p.setId(rs.getInt("id"));
              p.setFirstName(rs.getString("firstname"));
              p.setLastName(rs.getString("lastname"));
              p.setEmail(rs.getString("email"));
              p.setSubscription(rs.getString("subscription"));
              p.setShippingName(rs.getString("shippingFirstName")+rs.getString("shippingLastName"));
              p.setCountry(rs.getString("country"));
              p.setState(rs.getString("state"));
              p.setCity(rs.getString("city"));
              p.setZip(rs.getString("zip"));
              p.setStreet(rs.getString("street"));
              p.setLandmark(rs.getString("landmark"));
              p.setDuration(rs.getString("duration"));
              p.setTotalCost(rs.getDouble("total"));
              acc[count] = p;
              
              count++;
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(acc);
        
    }
}