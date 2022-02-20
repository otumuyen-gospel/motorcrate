<%@page import="backend.InstantMail" isThreadSafe="false"%>
<%@page import="backend.Payment"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="backend.JDBC"%>
<%
    String operation = request.getParameter("type");
    String why = request.getParameter("why");
    String email = session.getAttribute("login").toString();
    JDBC jdbc = new JDBC();
    Connection connect = jdbc.connect();
    Statement stmt = jdbc.statement(connect);
    ResultSet rs = rs = jdbc.query(stmt, "select cust_id from subscription where"
            + " email='"+email+"'");
    String cust_id = rs.next()?rs.getString("cust_id"):"";

    rs = jdbc.query(stmt, "select secret_keys from stripes_keys");
    String secret_keys = rs.next()?rs.getString(1):"";
    
    Payment pay = new Payment(secret_keys);
    
    if(operation.equals("cancel")){
        if(pay.deleteCustomer(cust_id)){
            jdbc.update(stmt, "delete from subscription where cust_id='"+cust_id+"'");
            jdbc.update(stmt, "update account set subscription='inactive',"
                    + "category='',duration='',cost=0,shippingCost=0,discount=0,"
                    + "total=0, subscribedate='' where email='"+email+"'");
            jdbc.update(stmt, "delete from dates where email='"+email+"'");
            if(!why.isEmpty()){
                //save why
                jdbc.update(stmt, "insert into operations values(0,'"+email+"','"+"cancelled subscription','"+
                    why+"')");
                
            }
            out.println("cancelled");
            
            //send user email
            String subject = "Motor Crate Subscription Cancelled";
            String body = "<h3> Hello, "+email+"</h3>"+"<p>Your Subscription has been cancelled. if you"
                    + " have stated why you cancelled your subscription on the website we will kindly review it "
                    + "else you can still email us via our email below.  </p>"+
                    "<p> thank you for your patronage.</p>";
            new InstantMail().sendMessage(email, subject, body);
            
        }else{
                out.println("unable to cancel subscription");
        }
    }
    
    jdbc.close(connect, stmt, rs);

%>