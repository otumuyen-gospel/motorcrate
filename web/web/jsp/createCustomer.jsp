<%@page import="backend.Referral" isThreadSafe="false"%>
<%@page import="backend.InstantMail"%>
<%@page import="java.time.LocalDate"%>
<%@page import="backend.Payment"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="backend.Billing"%>
<%@page import="backend.JDBC"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%
    JDBC jdbc = new JDBC();
    Connection connect = jdbc.connect();
    Statement stmt = jdbc.statement(connect);
    Billing billing = (Billing)session.getAttribute("billing");
    if(!billing.getSubscription().equals("active")){
        out.println("Your Subscription has been created successfully");
        //fetch subscription detail and update subscription
        ResultSet rs = jdbc.query(stmt, "select planId from plan where"
                + " planName='"+billing.getDuration()+"'");
        String plan_id = rs.next()?rs.getString("planId"):"";

        rs = jdbc.query(stmt, "select secret_keys from stripes_keys");
        String secret_keys = rs.next()?rs.getString(1):"";
        
        //check if he has a pending discount from winners table(1-yes,0-no)
        //and apply it
        rs = jdbc.query(stmt, "select status from winners where "
                + "username ='"+billing.getEmail()+"'");
        int state = 0;
        String coupon_id =null;
        if(rs.next()){
            state = rs.getInt(1);  
        }
        //grab coupon if he has a pending discount
        if(state == 1){
            rs = jdbc.query(stmt, "select code from coupon");
            coupon_id = rs.next()?rs.getString(1):null;
        }
        
        Payment pay = new Payment(secret_keys);
        String cust_id = pay.createCustomer(request.getParameter("stripeEmail"));
        if(cust_id != null){
            String[] values = pay.createSubscription(cust_id, plan_id, coupon_id);
            String status = values[0];
            String sub_id = values[1];
            LocalDate today = LocalDate.now().plusMonths(Integer.parseInt(billing.getDuration().split("")[0]));
            billing.setDueDate(today.getDayOfMonth()+"/"+today.getMonthValue()+"/"+today.getYear());

            if(status.equals("active")){
                //if subscription is created successfully then no more pending
                //discount for this customer
                jdbc.update(stmt, "update winners set status=0 where username"
                    + "='"+billing.getEmail()+"'");
                jdbc.update(stmt, "insert into subscription values(0,'"+billing.getEmail()+"','"+cust_id+"','"+
                        sub_id+"','"+status+"')");
                jdbc.update(stmt, "update account set subscription='active' where email='"+
                        billing.getEmail()+"'"); 
                //next due date
                String duedate = LocalDate.now().plusMonths(Integer.parseInt(billing.getDuration().split("")[0])).toString();
                String card = pay.setCard(cust_id, request.getParameter("stripeToken"));
                jdbc.update(stmt, "insert into dates values(0,'"+duedate+"','"+billing.getEmail()
                        +"','"+billing.getDuration()+"','"+card+"')");
                
                jdbc.close(connect, stmt, rs);
                //send user email
                String subject = "Motor Crate New Subscription Notification";
                String body = "<h3>Thank You, "+billing.getShippingFirstName()+ " "+billing.getShippingLastName()+
                        "</h3>"+"<p>for subscribing to our "+billing.getDuration()+".</p> You are now Eligible to "
                        + "enjoy all our shipping package to the plan you have opted for.</p>";
                new InstantMail().sendMessage(billing.getEmail(), subject, body);
                
                Referral ref = new Referral(billing.getEmail());
                ref.creditReferral();
                ref.creditReferred();
                
                request.getRequestDispatcher("../payment successful/").forward(request, response);
            
            }else{
                jdbc.close(connect, stmt, rs);
                
                request.getRequestDispatcher("../payment failed/").forward(request, response);
            }
            
        }
        
      jdbc.close(connect, stmt, rs);
        
    }
    jdbc.close(connect, stmt);
    
%>