<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.*,backend.*" isThreadSafe="false"%>
<% 
   String firstname = request.getParameter("firstname");
   String email = request.getParameter("email");
   String lastname = request.getParameter("lastname");
   String password = request.getParameter("password");
   String confirm = request.getParameter("confirm");
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
                            //select id from winner table
                            int winner_id = 0;
                            ResultSet rs = jdbc.query(stmt, "select id from winners where username='"+
                                    session.getAttribute("login")+"'");
                            if(rs.next()){
                                winner_id =rs.getInt(1);
                            }
                            //get user id from subscription table
                            String sub_id = "";
                            rs = jdbc.query(stmt, "select sub_id from subscription where email='"+
                                    session.getAttribute("login")+"'");
                            if(rs.next()){
                                sub_id =rs.getString(1);
                            }
                            //get user id from dates table
                            int date_id = 0;
                            rs = jdbc.query(stmt, "select id from dates where email='"+
                                    session.getAttribute("login")+"'");
                            if(rs.next()){
                                date_id =rs.getInt(1);
                            }
                            
                            //get user id from referral table
                            int ref_id = 0;
                            rs = jdbc.query(stmt, "select id from referal where email='"+
                                    session.getAttribute("login")+"'");
                            if(rs.next()){
                                ref_id =rs.getInt(1);
                            }
                            
                            //get user id account table
                            rs = jdbc.query(stmt, "select id from account where email='"+
                                    session.getAttribute("login")+"'");
                            if(rs.next()){
                               int id = rs.getInt(1);
                                String sql = "update account set firstname='"+firstname+"',lastname='"+
                                        lastname+"', email='"+email+"',password='"+password+"' where id="+id;
                                jdbc.update(stmt, sql);
                                if(JDBC.status.equals("database update executed successfully")){
                                    //update subscription table
                                    if(!sub_id.isEmpty()){
                                        jdbc.update(stmt, "update subscription set email='"+email
                                                +"' where sub_id='"+sub_id+"'");
                                    }
                                    //update winners table
                                    if(winner_id > 0){
                                        jdbc.update(stmt, "update winners set username='"+email
                                                +"' where id="+winner_id);
                                    }
                                    //update dates table
                                    if(date_id > 0){
                                        jdbc.update(stmt, "update dates set email='"+email
                                                +"' where id="+date_id);
                                    }
                                    
                                    //update referal table
                                    if(ref_id > 0){
                                        jdbc.update(stmt, "update referal set email='"+email
                                                +"' where id="+ref_id);
                                    }
                                    
                                    //reset login detail to new email
                                    session.setAttribute("login",email);
                                    out.println("updated");
                                
                                }else{
                                    out.println("error: you may need to try another email address");
                                }
                                
                            }
                            
                           jdbc.close(connect, stmt,rs);
                             
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