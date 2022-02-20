<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.*,backend.*" isThreadSafe="false"%>
<% 
   String password = request.getParameter("password");
   String confirm = request.getParameter("confirm");
   if(!password.isEmpty()){
                   
       if(!confirm.isEmpty()){

           if(confirm.equals(password)){

               //create account
                JDBC jdbc = new JDBC();
                Connection connect = jdbc.connect();
                Statement stmt = jdbc.statement(connect);
                //get user id
                ResultSet rs = jdbc.query(stmt, "select id from account where email='"+
                        session.getAttribute("reset")+"'");
                if(rs.next()){
                   int id = rs.getInt(1);
                    String sql = "update account set password='"+password+"' where id="+id;
                    jdbc.update(stmt, sql);
                    jdbc.close(connect, stmt);
                    //reset password successful
                    out.println("password reset successful");
                }else{
                    out.println("sorry an unexpected error occured try again later");
                }
                
                jdbc.close(connect, stmt, rs);
                
           }else{
               out.println("password does not match");
           }
       }else{
           out.println("please confirm password");
       }
   }else{
       out.println("please you must enter a password");
   }

%>