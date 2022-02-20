<%@page import="java.util.Random"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.*,backend.*" isThreadSafe="false"%>
<% 
   String email = request.getParameter("email");
   String domain = request.getServerName();
   if(!email.isEmpty()){
       //check if user exist
       JDBC jdbc = new JDBC();
       Connection connect = jdbc.connect();
       Statement stmt = jdbc.statement(connect);
       ResultSet rs = jdbc.query(stmt, "select email from account where email='"+email+"'" );
       if(rs.next()){
           //fetch email server details
                String sql = "select facebook, instagram, twitter,email,server,user,password,port from editor";
                rs = jdbc.query(stmt,sql);
                String host = "";
                String from = "";
                String password = "";
                String port = "";
                String user ="";
                String facebook = "";
                String instagram = "";
                String twitter = "";
                if(rs.next()){
                    host = rs.getString("server");
                    user = rs.getString("user");
                    password = rs.getString("password");
                    port = rs.getString("port");
                    from = UrlEntities.decode(rs.getString("email"));
                    facebook = UrlEntities.decode(rs.getString("facebook"));
                    instagram = UrlEntities.decode(rs.getString("instagram"));
                    twitter = UrlEntities.decode(rs.getString("twitter"));
                } 

                String subject = "motor crate user account password reset link";
                String values = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwsyz";
                String code = "";
                int range = values.length();
                Random rand = new Random();
                for(int i = 1; i < 60; i++){
                    code += values.charAt(rand.nextInt(range));
                }
                //reset url
                String resetUrl = "https://"+domain+"/reset?code="+code+"&email="+email;
                String message = "<p>click link below to reset Password:</p>"
                        + "<p><a href='"+resetUrl+"'>reset password link</a></p>";
                
                //save to database
                jdbc.update(stmt, "insert into reset values('"+email+"','"+code+"')");
                jdbc.close(connect, stmt, rs);
                
                try{
                   Email mail = new Email();
                   mail.doSend(host, password, user, port, from, email, subject, message,
                           facebook,instagram, twitter);

                   //create session
                   session.setAttribute("reset", email);
                   out.print("authenticated");

               }catch(Exception e){
                   out.print("oops! try again later some error occured");
               }
           
       }else{
           jdbc.close(connect, stmt, rs);
           
           out.println("sorry this account does not exist");
       }
       
       
       
   }else{
       out.println("please enter your email address");
   }
%>