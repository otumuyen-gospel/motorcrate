<%@page import="java.sql.*,backend.*" isThreadSafe="false"%>
<% 
   String name = request.getParameter("name");
   String email = request.getParameter("email");
   String description = request.getParameter("description");
   String subject = request.getParameter("subject");
   if(!name.trim().isEmpty()){
       if(!email.trim().isEmpty() && email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
           if(!description.trim().isEmpty()){
               if(!subject.trim().isEmpty()){
                   try{
                        //fetch email server details
                        JDBC jdbc = new JDBC();
                        Connection connect = jdbc.connect();
                        Statement stmt = jdbc.statement(connect);
                        String sql = "select email,server,user,password,port from editor";
                        ResultSet rs = jdbc.query(stmt,sql);
                        String host = "";
                        String to = "";
                        String password = "";
                        String port = "";
                        String user ="";
                        if(rs.next()){
                            host = rs.getString("server");
                            user = rs.getString("user");
                            password = rs.getString("password");
                            port = rs.getString("port");
                            to = UrlEntities.decode(rs.getString("email"));
                        } 


                        jdbc.close(connect, stmt, rs);

                       Email mail = new Email();
                       mail.Contact(host, password, user, port, email, to, subject, description);

                       out.println(Email.status);

                   }catch(Exception e){
                       out.println(e.getMessage());
                   }
                   
               }else{
                   out.println("please enter the subject of your message");
               }
               
           }else{
               out.println("please enter description information ");
           }
           
       }else{
           out.println("please enter valid email");
       }
   }else{
       out.println("please enter your name");
   }
%>