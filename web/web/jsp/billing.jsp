<%@page import="java.time.LocalDate" isThreadSafe="false"%>
<%@page import="backend.InstantMail"%>
<%@page import="backend.Payment"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="backend.JDBC"%>
<%@page import="backend.Billing"%>
<% 
    String firstname = request.getParameter("firstname");
    String lastname = request.getParameter("lastname");
    String country = request.getParameter("country");
    String state = request.getParameter("state");
    String city = request.getParameter("city");
    String street = request.getParameter("street");
    String zip = request.getParameter("zip");
    String landmark = request.getParameter("landmark");
    if(session.getAttribute("login") != null && session.getAttribute("billing") != null){
        
        if(!firstname.isEmpty()){
            if(!lastname.isEmpty()){
                if(!country.isEmpty()){
                    if(!state.isEmpty()){
                        if(!city.isEmpty()){
                            if(!zip.isEmpty()){
                                if(!street.isEmpty()){
                                    if(!landmark.isEmpty()){
                                        Billing billing = (Billing)session.getAttribute("billing");
                                        billing.setShippingFirstName(firstname);
                                        billing.setShippingLastName(lastname);
                                        billing.setCountry(country);
                                        billing.setState(state);
                                        billing.setStreet(street);
                                        billing.setZip(zip);
                                        billing.setCity(city);
                                        billing.setLandmark(landmark);
                                        billing.setSubscribeDate();
                                        String sql = "update account set shippingFirstName='"+
                                                billing.getShippingFirstName()+"', shippingLastName='"+billing.getShippingLastName()+
                                                "', country='"+billing.getCountry()+"',state='"+billing.getState()+"', city='"+
                                                billing.getCity()+"', zip='"+billing.getZip()+"', street='"+billing.getStreet()+
                                                "', landmark='"+billing.getLandmark()+"', category='"+billing.getCategory()+
                                                "', duration='"+billing.getDuration()+"', cost="+billing.getCost()+", shippingCost="+
                                                billing.getShippingCost()+", discount="+billing.getTotalDiscount()+", total="+billing.getTotal()+
                                                ", subscribedate='"+billing.getSubscribeDate()+"' where email='"+session.getAttribute("login")+
                                                "'";
                                        JDBC jdbc = new JDBC();
                                        Connection connect = jdbc.connect();
                                        Statement stmt = jdbc.statement(connect);
                                        ResultSet rs = null;
                                        if(billing.getSubscription().equals("active")){
                                            out.println("Your Subscription has been updated successfully");
                                            //fetch subscription detail and update subscription
                                            rs = jdbc.query(stmt, "select planId from plan where"
                                                    + " planName='"+billing.getDuration()+"'");
                                            String plan_id = rs.next()?rs.getString("planId"):"";
                                            
                                            rs = jdbc.query(stmt, "select sub_id from subscription where"
                                                    + " email='"+billing.getEmail()+"'");
                                            String sub_id = rs.next()?rs.getString(1):"";
                                            
                                            rs = jdbc.query(stmt, "select secret_keys from stripes_keys");
                                            String secret_keys = rs.next()?rs.getString(1):"";
                                            
                                            //check if he has a pending discount from winners table(1-yes,0-no)
                                            //and apply it
                                            rs = jdbc.query(stmt, "select status from winners where "
                                                    + "username ='"+billing.getEmail()+"'");
                                            int status = 0;
                                            String coupon_id =null;
                                            if(rs.next()){
                                                status = rs.getInt(1);  
                                            }
                                            //grab coupon if he has a pending discount
                                            if(status == 1){
                                                rs = jdbc.query(stmt, "select code from coupon");
                                                coupon_id = rs.next()?rs.getString(1):null;
                                            }
                                            
                                            Payment pay = new Payment(secret_keys);
                                            pay.updateSubscription(sub_id, plan_id, coupon_id);
                                            //if subscription is updated successfully then no more pending
                                            //discount for this customer
                                            jdbc.update(stmt, "update account set subscribedate='"+LocalDate.now().toString()
                                               +"' where email='"+billing.getEmail()+"'");
                                            //next due date
                                            String duedate = LocalDate.now().plusMonths(Integer.parseInt(billing.getDuration().split("")[0])).toString();
                                            jdbc.update(stmt, "update dates set next_due_date='"+duedate
                                               +"',duration='"+billing.getDuration()+"' where email='"+billing.getEmail()+"'");
                                            
                                            jdbc.update(stmt, "update winners set status=0 where username"
                                                        + "='"+billing.getEmail()+"'");
                                            //update billing info
                                            jdbc.update(stmt, sql);
                                            session.removeAttribute("billing");
                                            //send user email
                                            String subject = "Motor Crate Subscription Notification";
                                            String body = "<h3>Thank You, "+billing.getShippingFirstName()+ " "+billing.getShippingLastName()+
                                                    "</h3>"+"<p>for switching to our "+billing.getDuration()+".</p> You are now Eligible to "
                                                    + "enjoy all our shipping package to the plan you have opted for.</p>";
                                            new InstantMail().sendMessage(billing.getEmail(), subject, body);
                                        }else{
                                             out.println("proceed");
                                             //just update billing info
                                             jdbc.update(stmt, sql);
                                        }
                                        jdbc.close(connect, stmt, rs);
                                    }else{
                                        out.println("please enter your nearest landmark");
                                    }
                                }else{
                                    out.println("please enter street address");
                                }
                            }else{
                                out.println("enter valid postal or zip code");
                            }
                        }else{
                            out.println("enter city name");
                        }
                    }else{
                        out.println("enter state name");
                    }
                }else{
                    out.println("enter country name");
                }
            }else{
                out.println("please enter your last name");
            }
        }else{
            out.println("please enter your first name");
        }
    }
   
%>