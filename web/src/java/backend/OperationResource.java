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
@Path("operation")
public class OperationResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String view() {
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        ResultSet rs = jdbc.query(stmt, "select * from operations");
        int count = 0;
        Operation[]opera = new Operation[jdbc.getRowSize("operations")] ;
        try{
            
            while(rs.next()){
              Operation p = new Operation();
              p.setId(rs.getInt("id"));
              p.setEmail(rs.getString("email"));
              p.setAction(rs.getString("action"));
              p.setWhy(rs.getString("why"));
              opera[count] = p;
              
              count++;
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(opera);
        
    }
}
