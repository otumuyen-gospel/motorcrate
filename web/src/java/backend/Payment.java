/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Plan;
import com.stripe.model.Product;
import com.stripe.model.Subscription;
import com.stripe.param.CouponCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PlanCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.SubscriptionUpdateParams;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author user1
 */
public class Payment {
    private String secret_key; 
    public Payment(String secret_key){
        this.secret_key = secret_key;
    }
    public Payment(){}
    
    public String createProduct(String name){
        String prod_id = "";
        try {
            Stripe.apiKey = this.secret_key;
            ProductCreateParams params =
              ProductCreateParams.builder()
                .setName(name)
                .setType(ProductCreateParams.Type.SERVICE)
                .build();
            Product product = Product.create(params);
            prod_id = product.getId();
        } catch (StripeException ex) {
            ex.printStackTrace();
        }
        return prod_id;
    }
    
    public String createPlan(String name,String prod_id,double price,Long freq){
        String plan_id = "";
        try {
            Stripe.apiKey = this.secret_key;
            PlanCreateParams plans =
              PlanCreateParams.builder()
                .setNickname(name)
                .setProduct(prod_id)
                .setAmount(Long.parseLong(String.valueOf((int)(price*100))))
                .setCurrency("usd")
                .setInterval(PlanCreateParams.Interval.MONTH)
                .setIntervalCount(freq)
                .setUsageType(PlanCreateParams.UsageType.LICENSED)
                .build();

            Plan plan = Plan.create(plans);
            plan_id = plan.getId();
        } catch (StripeException ex) {
            ex.printStackTrace();
        }
        return plan_id;
    }
    
    public boolean destroyPlan(String prod_id, String plan_id){
        boolean status = false;
        try{
            Stripe.apiKey = this.secret_key;
            
            Plan plan = Plan.retrieve(plan_id);
            Plan deletedPlan = plan.delete();
           if(deletedPlan.getDeleted()){
                Product product = Product.retrieve(prod_id);
                Product deletedProduct = product.delete();
                if(deletedProduct.getDeleted()){
                    status = true;
                }
           }
        }catch(Exception e){
            status = false;
        }
        
        return status;
        
    }
    
    public String createCustomer(String stripeEmail){
        String cust_id ="";
        try{
            Stripe.apiKey = this.secret_key;
            CustomerCreateParams custParam =
              CustomerCreateParams.builder()
                .setEmail(stripeEmail)
                .setPaymentMethod("pm_card_us")
                .setInvoiceSettings(
                  CustomerCreateParams.InvoiceSettings.builder()
                    .setDefaultPaymentMethod("pm_card_us")
                    .build())
                .build();

            Customer customer = Customer.create(custParam);
            cust_id = customer.getId();
        }catch(StripeException e){
           e.printStackTrace();
        }
        
        return cust_id;
    }
    
    public String[] createSubscription(String cust_id,String plan_id,String coupon_id){
        String[]result = new String[2];
        try{
            Stripe.apiKey = this.secret_key;
            SubscriptionCreateParams sub =
              SubscriptionCreateParams.builder()
                .setCustomer(cust_id)
                .addItem(
                  SubscriptionCreateParams.Item.builder()
                    .setPlan(plan_id)
                    .build())
                   .setCoupon(coupon_id)
                .addExpand("latest_invoice.payment_intent")
                .build();

            Subscription subscription = Subscription.create(sub);
            result[0] = subscription.getStatus();
            result[1] = subscription.getId();
        }catch(StripeException e){
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean updateSubscription(String sub_id,String plan_id,String coupon_id){
        boolean update = false;
        try{
            Stripe.apiKey = this.secret_key;

            Subscription subscription = Subscription.retrieve(sub_id);

            SubscriptionUpdateParams params =
              SubscriptionUpdateParams.builder()
                .addItem(
                  SubscriptionUpdateParams.Item.builder()
                    .setId(subscription.getItems().getData().get(0).getId())
                    .setPlan(plan_id)
                    .build())
                    .setCoupon(coupon_id)
                .setCancelAtPeriodEnd(false)
                .build();

            subscription.update(params);
            update = true;
            
        }catch(StripeException e){
           update = false;
        }
        return update;
    }
    public boolean deleteCustomer(String cust_id){
        boolean delete = false;
        try{
            Stripe.apiKey = this.secret_key;
            Customer cust  = Customer.retrieve(cust_id);
            Customer c = cust.delete();
            if(c.getDeleted()){
                delete =  true;
            }
        }catch(Exception e){
                delete = false;    
        }
        return delete;
    }
    public boolean cancelSubscription(String sub_id){
        boolean cancel = false;
        try {
           Stripe.apiKey = this.secret_key;
           Subscription subscription = Subscription.retrieve(sub_id);
           subscription.cancel();
           cancel = true;
        } catch (StripeException ex) {
            cancel = false;
        }
        return cancel;
    }
    
    public String createCoupon(String coupon_id,double discount){
        Stripe.apiKey = this.secret_key;
        try{
            CouponCreateParams params =
              CouponCreateParams.builder()
                .setDuration(CouponCreateParams.Duration.FOREVER)
                .setId(coupon_id)
                .setAmountOff(Long.parseLong(String.valueOf((int)(discount*100))))
                .setCurrency("usd")
                .build();

            Coupon coupon = Coupon.create(params);  
            coupon_id = coupon.getId();
           
        }catch(StripeException e){
          coupon_id = null;
        }
        
        return coupon_id;
    }
    public String getDaysUtilDue(String email){
        String days = "";
        try {
            JDBC jdbc = new JDBC();
            Connection con = jdbc.connect();
            Statement stmt = jdbc.statement(con);
            ResultSet rs = jdbc.query(stmt, "select next_due_date from dates where email='"+
                    email+"'");
            if(rs.next()){
                LocalDate end = LocalDate.parse(rs.getString("next_due_date"));
                LocalDate now = LocalDate.now();
                long val = ChronoUnit.DAYS.between(now, end);
                days = val+" days remaining";
                
            }
            jdbc.close(con, stmt,rs);
        } catch (Exception ex) {
            days ="0 days remaining";
        }
        return days;
    }
    public String setCard(String cust_id, String stripeToken){
        String paymentCard = "xxxxxxxxxx";
        try{
             Stripe.apiKey = this.secret_key;
             Customer customer =Customer.retrieve(cust_id);
             Map<String, Object> params = new HashMap<>();
             params.put("source",  stripeToken);
             Card card = (Card)customer.getSources().create(params);
             paymentCard = paymentCard + card.getLast4();
        }catch(Exception e){
            paymentCard = paymentCard + "xxxx";
        }
        return paymentCard;
       
    }
    
    public String getCard(String email){
        String card = "";
        try {
            JDBC jdbc = new JDBC();
            Connection con = jdbc.connect();
            Statement stmt = jdbc.statement(con);
            ResultSet rs = jdbc.query(stmt, "select card from dates where email='"+
                    email+"'");
            if(rs.next()){
                card = rs.getString("card");
            }
            jdbc.close(con, stmt,rs);
        } catch (Exception ex) {
            card = "unknown";
        }
        return card;
    }
    
}
