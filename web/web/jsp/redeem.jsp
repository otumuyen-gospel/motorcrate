<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.*,backend.*" isThreadSafe="false"%>
<% 
    if(session.getAttribute("login") != null){
        
           String code = request.getParameter("code");
           JDBC jdbc = new JDBC();
           Connection connect = jdbc.connect();
           Statement stmt = jdbc.statement(connect);
           ResultSet rs = jdbc.query(stmt, "select * from winners where username='"+
                   session.getAttribute("login")+"'");
           if(rs.next()){
               session.setAttribute("coupon", "coupon");
                out.println("<p>you have already won this coupon before please login with another account "
                    + "<a href='../sign in/'>here</a> </p>");
           }else{
               if(!code.isEmpty()){
                   String sql = "select * from coupon";
                   rs = jdbc.query(stmt,sql);
                   String couponCode = "";
                   String status = "";
                   if(rs.next()){
                       couponCode = rs.getString("code");
                       status = rs.getString("status");
                   }
                   
                   if(couponCode.equals(code)){
                       if(!status.equalsIgnoreCase("deactivated")){
                           session.setAttribute("discount", rs.getString("discount") );
                           String text = "<p>Congrat you just got &dollar;"+session.getAttribute("discount")+" discount"
                                   + " to complete the process click <a href='../billing/'>here</a></p>";
                           out.println(text);
                           jdbc.update(stmt, "insert into winners values(0,'"
                                   +session.getAttribute("login")+"',1)");
                           jdbc.close(connect, stmt, rs);
                           
                           //send user email
                            String subject = "Hurry!!! You Have Won Motor Crate Gift Box Coupon";
                            String body = "<h3>"+session.getAttribute("login")+"</h3>"
                                    + "<p>Congrat you just got $"+session.getAttribute("discount")+" discount </p>"
                                    + "<p>complete the redemption process by creating an account, enter your "
                                    + "shipping address, and checking out to confirm your order is completed.</p>"+
                                    "<h3>THANK YOU FOR PARTICIPATING IN THE COUPON SECTION</p>";
                            new InstantMail().sendMessage(session.getAttribute("login").toString(), subject, body);
                                
                       }else{
                           out.println("sorry no coupon section for now, it has expired");
                           jdbc.close(connect, stmt, rs);
                       }    
                   }else{
                       out.println("sorry your code did not match");
                   }
               }else{
                   out.println("please enter your code in the box");
               }
               
            }
           
           jdbc.close(connect, stmt, rs);
           
    }else{
        session.setAttribute("coupon", "coupon");
        out.println("<p> please you have to login to your account first. sign in "
                + "<a href='../sign in/'>here</a> </p>");
    }
   
   
%>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              