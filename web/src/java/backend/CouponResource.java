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
@Path("coupon")
public class CouponResource {
   TextMessage message;
   public CouponResource(){
       message = new TextMessage();
   }
   
    @GET
    @Path("{id}/{code}/{discount}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("id")int id, @PathParam("code") String code,
            @PathParam("discount") String discount) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from coupon");
            String sql;
            if(jdbc.getRowSize("coupon") == 0){
                sql = "insert into coupon values(0,'"+code+"',"+discount+",'deactivated')";
            }else{
                if(rs.next()){
                    //if is a new code clear winners table to accept new winners for this new coupon
                    if(!code.equals(rs.getString("code"))){
                        jdbc.update(stmt, "delete from winners");
                    }
                }
                sql = "update coupon set code='"+code+"',discount="+discount+" where id="+id;
            
            }
            rs = jdbc.query(stmt, "select * from stripes_keys");
            String secret_keys="";
            if(rs.next()){
                secret_keys = rs.getString("secret_keys");
            }
            //create stripe coupon with code
            Payment pay = new Payment( secret_keys);
            
            if(pay.createCoupon(code, Double.parseDouble(discount)) != null){
                jdbc.update(stmt, sql);
                message.setMessage(JDBC.status);
            }else{
                message.setMessage("unable to create coupon");
            }
            jdbc.close(connect, stmt,rs);
           
        }catch(Exception e){
            message.setMessage(e.getMessage());
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
        String sql = "select * from coupon";
        
        ResultSet rs = jdbc.query(stmt,sql);
        
        try{
            if(rs.next()){
                int id = rs.getInt(1);
                String code = rs.getString(2);
                double discount = rs.getDouble(3);
                message.setMessage(id+"/"+code+"/"+discount);
            }  
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(message);
        
    }
   
    
    
}
