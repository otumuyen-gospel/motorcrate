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
@Path("schedule")
public class ScheduleResource {
   TextMessage message;
   public ScheduleResource(){
       message = new TextMessage();
   }
   
    @GET
    @Path("{id}/{onemonth}/{threemonth}/{sixmonth}/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("id") int id, @PathParam("onemonth") String onemonth,
            @PathParam("threemonth") String threemonth,@PathParam("sixmonth") String sixmonth,
            @PathParam("year") String year) {
        try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from schedule");
            String sql;
            if(jdbc.getRowSize("schedule") == 0){
                sql = "insert into schedule values(0,'"+onemonth+"','"+threemonth+"','"+sixmonth+"','"+year+"')";
            }else{
                sql = "update schedule set monthly='"+onemonth+"', threemonthly='"+threemonth+"',sixmonthly='"+
                    sixmonth+"',yearly='"+year+"' where id ="+id;
            }
            
            jdbc.update(stmt, sql);
            message.setMessage("Schedule update Successful");
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
        ResultSet rs = jdbc.query(stmt, "select * from schedule");
        Schedules p = new Schedules() ;
        try{
            
            if(rs.next()){
              p.setId(rs.getInt(1));
              p.setOneMonthly(rs.getString(2));
              p.setThreeMonthly(rs.getString(3));
              p.setSixMonthly(rs.getString(4));
              p.setYearly(rs.getString(5));
              
            }
            
            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            
        }
        return new Gson().toJson(p);
        
    }
    
}

class Schedules{
    private int id;
    private String oneMonth;
    private String threeMonth;
    private String sixMonth;
    private String year;
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setOneMonthly(String val){
        this.oneMonth = val;
    }
    public String getOneMonthly(){
        return this.oneMonth;
    }
    public void setThreeMonthly(String val){
        this.threeMonth = val;
    }
    public String getThreeMonthly(){
        return this.threeMonth;
    }
    public void setSixMonthly(String val){
        this.sixMonth = val;
    }
    public String getSixMonthly(){
        return this.sixMonth;
    }
    public void setYearly(String val){
        this.year = val;
    }
    public String getYearly(){
        return this.year;
    }
    
    
}
