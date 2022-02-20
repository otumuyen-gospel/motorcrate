<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.*,backend.*" isThreadSafe="false"%>
<% 
   String email = request.getParameter("email");
   String password = request.getParameter("password");
   if(!email.isEmpty()){
       if(!password.isEmpty()){
           //check if user exist
           JDBC jdbc = new JDBC();
           Connection connect = jdbc.connect();
           Statement stmt = jdbc.statement(connect);
           ResultSet rs = jdbc.query(stmt, "select email,password from account where email='"+email+"' and"+
                   " password='"+password+"'");
           if(rs.next()){
               //create session
               session.setAttribute("login", email);
               if(session.getAttribute("coupon") != null){
                   out.println("coupon");
                   session.removeAttribute("coupon");
               }else if(session.getAttribute("duration") != null){
                   out.println("billing");
               }else{
                    out.print("authenticated");
               }
              
           }else{
               out.println("sorry can't log you in, ensure you've entered your details correctly");
           }
           jdbc.close(connect, stmt, rs);
       }else{
           out.println("please enter your password");
       }
   }else{
       out.println("please enter your email address");
   }
%>