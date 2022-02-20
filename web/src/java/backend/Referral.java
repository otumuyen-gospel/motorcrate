/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

/**
 *
 * @author user1
 */
public class Referral {
    String referral_id, email,firstname,lastname, referral_link, 
            referral_email,status,sub_id,secret_keys,duration,plan_id;
    double cost,discount,total;
    int referred = 0;
    JDBC jdbc = new JDBC();
    Connection connect = jdbc.connect();
    Statement stmt = jdbc.statement(connect);
    ResultSet rs;
    public Referral(String referral_id, String email, String firstname, String lastname){
        this.referral_id = referral_id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        
    }
    public Referral(String email){
        this.email = email;
    }
    public void creditReferral(){
        try{
            rs = jdbc.query(stmt, "select referal,link from referal where email='"+this.email+"'");
            if(rs.next()){
                if(!rs.getString("referal").equalsIgnoreCase("none"))
                  this.referral_link = rs.getString("link").split("=")[0]+"="+rs.getString("referal");
                else
                    return;
            }
            
            rs = jdbc.query(stmt, "select email ,referred from referal where link='"+this.referral_link+"'");
            if(rs.next()){
                this.referred = rs.getInt("referred");
                this.referral_email = rs.getString("email");
            }
            
            rs = jdbc.query(stmt, "select sub_id from subscription where email='"+this.referral_email+"'");
            if(rs.next()){
                this.sub_id = rs.getString("sub_id");
            }

            rs = jdbc.query(stmt, "select cost,duration,discount,total,subscription from account where email='"+
                    this.referral_email+"'");
            if(rs.next()){
                this.status = rs.getString("subscription");
                this.duration = rs.getString("duration");
                this.cost = rs.getDouble("cost");
                this.total = rs.getDouble("total");
                this.discount = rs.getDouble("discount");
            }
            
            rs = jdbc.query(stmt, "select planId from plan where planName='"+this.duration+"'");
            if(rs.next()){
                this.plan_id = rs.getString("planId");
            }

            rs = jdbc.query(stmt, "select secret_keys from stripes_keys");
            if(rs.next()){
                this.secret_keys = rs.getString("secret_keys");
            }
            //10% off the cost
            double per_off = (10*cost)/100;
            if(status.equalsIgnoreCase("active")){
                Payment pay = new Payment(secret_keys);
                String coupon_id = this.chars(10);
                if(pay.createCoupon(coupon_id, per_off) != null){
                    if(pay.updateSubscription(sub_id, plan_id, coupon_id)){
                        //update referal discount
                        this.discount= this.discount + per_off;
                        this.total = this.total - per_off;
                        jdbc.update(stmt, "update account set discount="+this.discount+",total="+this.total+
                                " where email='"+this.referral_email+"'");
                        //send referal email notification
                        String subject = "Your Motor Crate Referral Alert";
                            String body = "<p>Congratulation one of the people you referred to motor crate"
                                    + " has dutifully subscribe to one of our package</p>"
                                    + "<p>Your motor crate account has been promptly credited with 10% discount</p>"
                                    + "<h3>Thank you for your patronage</h3>";
                            new InstantMail().sendMessage(this.referral_email, subject, body);
                    }

                }

            }else{
                referred++;
                jdbc.update(stmt, "update referal set referred="+this.referred+" where email='"+
                        this.referral_email+"'");
            }

            jdbc.update(stmt, "update referal set referal='none' where email='"+this.email+"'");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
    }
    public void creditReferred(){
        try{
            rs = jdbc.query(stmt, "select referred from referal where email='"+this.email+"'");
            if(rs.next()){
                if(rs.getInt("referred")!=0)
                  this.referred = rs.getInt("referred");
                else
                    return;
            }

            rs = jdbc.query(stmt, "select sub_id from subscription where email='"+this.email+"'");
            if(rs.next()){
                this.sub_id = rs.getString("sub_id");
            }

            rs = jdbc.query(stmt, "select cost,duration,discount,total,subscription from account where email='"+
                    this.email+"'");
            if(rs.next()){
                this.status = rs.getString("subscription");
                this.duration = rs.getString("duration");
                this.cost = rs.getDouble("cost");
                this.total = rs.getDouble("total");
                this.discount = rs.getDouble("discount");
            }

            rs = jdbc.query(stmt, "select planId from plan where planName='"+this.duration+"'");
            if(rs.next()){
                this.plan_id = rs.getString("planId");
            }
            
            rs = jdbc.query(stmt, "select secret_keys from stripes_keys");
            if(rs.next()){
                this.secret_keys = rs.getString("secret_keys");
            }

            //10% off cost for all referred
            double per_off = ((10*cost) / 100) * referred;
            System.out.println(per_off);
            if(status.equalsIgnoreCase("active")){
                Payment pay = new Payment(secret_keys);
                String coupon_id = this.chars(10);
                if(pay.createCoupon(coupon_id, per_off) != null){
                    System.out.println(secret_keys);
                    if(pay.updateSubscription(sub_id, plan_id, coupon_id)){
                        //update referal discount
                        this.discount= this.discount + per_off;
                        this.total = this.total - per_off;
                        jdbc.update(stmt, "update account set discount="+this.discount+",total="+this.total+
                                " where email='"+this.email+"'");
                        jdbc.update(stmt, "update referal set referred=0 where email='"+this.email+"'");
                        //send referal email notification
                        String subject = "Your Motor Crate Referral Alert";
                            String body = "<p>Congratulation all of the people you referred to motor crate"
                                    + " has dutifully subscribe to our packages</p>"
                                    + "<p>Your motor crate account has been promptly credited with 10% discount "
                                    + "for each of them</p>"
                                    + "<h3>Thank you for your patronage</h3>";
                            new InstantMail().sendMessage(this.email, subject, body);
                    }
                    
                }
                
            }

            jdbc.close(connect, stmt, rs);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    public String chars(int characters){
        String values = "abcdefghijklmnopqrstuvwsyz";
        String code = "";
        int range = values.length();
        Random rand = new Random();
        for(int i = 1; i < characters; i++){
            code += values.charAt(rand.nextInt(range));
        }
        
        return code;
    }
    public void createLink(String domain){
        String link = "https://"+domain+"/sign up?referral_id="+this.chars(40);
        String name = firstname + " "+ lastname;
        String sql= "insert into referal values(0,'"+name+"','"+email+"','"+link+"','"+this.referral_id+"',0)";
        JDBC jdbc = new JDBC();
        Connection connect = jdbc.connect();
        Statement stmt = jdbc.statement(connect);
        
        jdbc.update(stmt, sql);
        jdbc.close(connect, stmt);
        
    }
    
    
}
