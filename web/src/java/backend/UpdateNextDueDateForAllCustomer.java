/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UpdateNextDueDateForAllCustomer extends Thread{ 

    @Override
    public void run(){
        try{
            while(true){
                this.Update();
                //every 2 minutes
                Thread.sleep(120000);
            }
        }catch(Exception e){
            
        }
    }
     public synchronized void Update(){
        
        try {
            JDBC db = new JDBC();
            Connection dbcon = db.connect();
            Statement dbstmt = db.statement(dbcon);
            ResultSet dbrs = db.query(dbstmt, "select * from dates");
            while(dbrs.next()){
                LocalDate end = LocalDate.parse(dbrs.getString("next_due_date"));
                LocalDate now = LocalDate.now();
                long val = ChronoUnit.DAYS.between(now, end);
                if(val == 0){
                    //send user email
                    String subject = "Motor Crate Auto Subscription Renewal";
                    String body = "<p>"+dbrs.getString("email")+" we are pleased to inform you that your motor "
                            + "crate subscription payment due date has ended and "
                            + "your account will be charged and your subscription renewed accordingly as usual</p> <p>"
                            + "Thank you for staying with us.</p>";
                    new InstantMail().sendMessage(dbrs.getString("email"), subject, body);
                    
                    //next due date
                    String duedate = end.plusMonths(Integer.parseInt(dbrs.getString("duration").split("")[0])).toString();
                    db.update(dbstmt, "update dates set next_due_date='"+duedate
                       +"' where email='"+dbrs.getString("email")+"'");
                    
                    
                }
                
            }
            
           db.close(dbcon, dbstmt, dbrs);
           System.gc();//collect unused object(garbage) to avoid memory leak
        } catch (Exception ex) {
            
        }
    }
    
}
