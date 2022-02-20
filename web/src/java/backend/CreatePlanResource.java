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
@Path("createPlan")
public class CreatePlanResource {

    TextMessage message;
   public CreatePlanResource(){
       message = new TextMessage();
   }
   
    @GET
    @Path("{product}/{plan}/{cost}/{shipping}")
    @Produces(MediaType.APPLICATION_JSON)
    public String create(@PathParam("product")String product, @PathParam("plan") String plan,
            @PathParam("cost") String cost,@PathParam("shipping") String shipping) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from plan where planName='"+plan+" Months Plan'");
            
            if(rs.next()){
                message.setMessage("plan already exist");
            }else{
                rs = jdbc.query(stmt, "select * from stripes_keys"); 
                String secret_keys="";
                if(rs.next()){
                    secret_keys = rs.getString("secret_keys");
                }
                //create product
                Payment pay = new Payment( secret_keys);
                String prod_id = pay.createProduct(UrlEntities.decode(product));
                if(!prod_id.isEmpty()){
                    //create plan
                    String plan_id = pay.createPlan(plan+" Months Plan", prod_id, 
                            Double.parseDouble(cost)+Double.parseDouble(shipping), Long.parseLong(plan));
                    if(!plan_id.isEmpty()){
                        //update database
                        String sql = "insert into plan values(0,'"+UrlEntities.decode(product)+
                                "','"+prod_id+"','"+plan+" Months Plan','"+
                                plan_id+"',"+cost+","+shipping+")";
                        jdbc.update(stmt, sql);
                        message.setMessage("plan created successfully");
                    }else{
                        message.setMessage("unable to create plan");
                    }

                }else{
                    message.setMessage("unable to create plan");
                }
            }
            
            jdbc.close(connect, stmt,rs);
           
        }catch(Exception e){
            message.setMessage(e.getMessage());
        }
        return new Gson().toJson(message);
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String view() {
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        String sql = "select * from plan";
        
        ResultSet rs = jdbc.query(stmt,sql);
        Plan[]plans = new Plan[jdbc.getRowSize("plan")];
        try{
            int count = 0;
            while(rs.next()){
                Plan plan = new Plan();
                plan.setId(rs.getInt(1));
                plan.setProductName(rs.getString(2));
                plan.setProductId(rs.getString(3));
                plan.setPlanName(rs.getString(4));
                plan.setPlanId(rs.getString(5));
                plan.setCost(rs.getDouble(6));
                plan.setShipping(rs.getDouble(7));
                plans[count] = plan;
                count++;
            }  
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(plans);
        
    }
    
    @GET
    @Path("{id}/{prod_id}/{plan_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String destroy(@PathParam("id")int id, @PathParam("prod_id") String prod_id,
            @PathParam("plan_id") String plan_id) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from stripes_keys"); 
            String secret_keys="";
            if(rs.next()){
                secret_keys = rs.getString("secret_keys");
            }
            //destroy plan
            Payment pay = new Payment( secret_keys);
            if(pay.destroyPlan(prod_id, plan_id)){
                jdbc.update(stmt, "delete from plan where id="+id);
                message.setMessage("Plan Remove Successfully");
            }else{
                message.setMessage("Unable To Remove Plan");
            }
            jdbc.close(connect, stmt,rs);
           
        }catch(Exception e){
            message.setMessage(e.getMessage());
        }
        return new Gson().toJson(message);
        
    }
    
}
