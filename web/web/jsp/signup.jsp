<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.*,backend.*" isThreadSafe="false"%>
<% 
   String firstname = request.getParameter("firstname");
   String email = request.getParameter("email");
   String lastname = request.getParameter("lastname");
   String password = request.getParameter("password");
   String confirm = request.getParameter("confirm");
   String referral_id = request.getParameter("referral_id");
   String domain = request.getServerName();
   if(!firstname.isEmpty()){
       
       if(!lastname.isEmpty()){
           
           if(!email.isEmpty() && email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
               
               if(!password.isEmpty()){
                   
                   if(!confirm.isEmpty()){
                       
                       if(confirm.equals(password)){
                           
                           //create account
                            JDBC jdbc = new JDBC();
                            Connection connect = jdbc.connect();
                            Statement stmt = jdbc.statement(connect);
                            //check if that email account already exist
                            ResultSet rs = jdbc.query(stmt, "select email from account where email='"+email+"'");
                            if(rs.next()){
                                jdbc.close(connect, stmt, rs);
                                out.println("user with that email account already exist");
                            }else{
                                String sql = "insert into account values(0,'"+firstname+"','"+lastname+"','"+
                                    email+"','"+password+"','inactive','','','','','','','','','','',0,0,0,0,'"+
                                    LocalDate.now()+"','')";
                                jdbc.update(stmt, sql);
                                jdbc.close(connect, stmt);
                                //create and check referral links 
                                new Referral(referral_id,email,firstname,
                                lastname).createLink(domain);
                                out.println("created");
                                session.setAttribute("login", email);
                                
                                //send user email
                                String subject = "MotorCrate account Notification";
                                String body = "<h3>"+firstname+" "+lastname+"</h3>"
                                        + "<p>Thank you for choosen Motorcrate gift Box.</p>"
                                        + "<p>We are happy to partner with you at any level. our services and"
                                        + " plan are superb, flexible, and cost effective. you can follow us on"
                                        + " our social media handle below.</p>"
                                        + "<p>THANK YOU.</p>";
                                new InstantMail().sendMessage(email, subject, body);
                                
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
           }else{
               out.println("please enter a valid email");
           }
       }else{
           out.println("please you must enter your lastname");
       }
   }else{
       out.println("please you must enter your firstname");
   }
%>