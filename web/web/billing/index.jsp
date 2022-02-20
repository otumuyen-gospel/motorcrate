<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@page import="java.util.ArrayList,backend.*,java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8" isThreadSafe="false"%>
<%
    
    JDBC jdbc = new JDBC();
    Connection connect = jdbc.connect();
    Statement stmt = jdbc.statement(connect);
    //set billing object
    Billing billing = new Billing();
    ResultSet rs;
    if(session.getAttribute("login") != null){
        //grab billing info of customer
        rs = jdbc.query(stmt, "select * from account where email='"+session.getAttribute("login")+"'");
        if(rs.next()){
            billing.setCity(rs.getString("city"));
            billing.setSubscription(rs.getString("subscription"));
            billing.setShippingFirstName(rs.getString("shippingFirstName"));
            billing.setShippingLastName(rs.getString("shippingLastName"));
            billing.setCountry(rs.getString("country"));
            billing.setState(rs.getString("state"));
            billing.setZip(rs.getString("zip"));
            billing.setStreet(rs.getString("street"));
            billing.setLandmark(rs.getString("landmark"));
            String category = rs.getString("category");
            String duration = rs.getString("duration");
            double discount = rs.getDouble("discount");
            billing.setEmail(rs.getString("email"));
            
            if(session.getAttribute("category") != null){
                billing.setCategory(session.getAttribute("category").toString());
                category = billing.getCategory();
                session.removeAttribute("category");
                //quickly update category for user
                jdbc.update(stmt, "update account set category='"+billing.getCategory()+"' where email='"+
                        session.getAttribute("login")+"'");
            }else if(!category.isEmpty()){
                billing.setCategory(category);
            }
            
            billing.setSubscribeDate();
            
            if(session.getAttribute("duration") != null){
                billing.setDuration(session.getAttribute("duration").toString());
                duration = billing.getDuration();
                session.removeAttribute("duration");
                //quickly update duration for user
                jdbc.update(stmt, "update account set duration='"+billing.getDuration()+"' where email='"+
                        session.getAttribute("login")+"'");
            }else if(!duration.isEmpty()){
                billing.setDuration(duration);
            }
            
            if((session.getAttribute("category") == null && category.isEmpty()) || 
                    (session.getAttribute("duration") == null && duration.isEmpty()))
            {
                request.getRequestDispatcher("../plan/").forward(request, response);
            }
           
            if(session.getAttribute("discount") != null){
                billing.setDiscount(Double.parseDouble(session.getAttribute("discount").toString()));
                session.removeAttribute("discount");
                billing.setTotalDiscount(discount);
                //quickly update discount for user
                jdbc.update(stmt, "update account set discount="+billing.getTotalDiscount()+" where email='"+
                        session.getAttribute("login")+"'");
            }else{
                billing.setTotalDiscount(discount);
            }
            
        }
        
        rs = jdbc.query(stmt, "select * from plan where planName='"+billing.getDuration()+"'");
        if(rs.next()){
            billing.setCost(rs.getDouble("cost"));
            billing.setShippingCost(rs.getDouble("shipping"));
            billing.setTotal();
        }
        
    }else{
         jdbc.close(connect, stmt);
         request.getRequestDispatcher("../sign in/").forward(request, response);
    }
    
    rs = jdbc.query(stmt, "select facebook,twitter,instagram from editor");
    String facebook = "https://";
    String instagram = "https://";
    String twitter = "https://";
    if(rs.next()){
        facebook += UrlEntities.decode(rs.getString("facebook"));
        instagram += UrlEntities.decode(rs.getString("instagram"));
        twitter += UrlEntities.decode(rs.getString("twitter"));
    }
    
    rs = jdbc.query(stmt, "select * from stripes_keys");
    String publishable_keys = rs.next()?rs.getString("publishable_keys"):"";
    
    
   //creating billing session
   session.setAttribute("billing", billing);
   jdbc.close(connect, stmt, rs);
    
%>
<html>
    <head>
        <title>Billing</title>
        <meta charset="UTF-8">
        <link rel="icon" href="../assets/logo.png"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans&display=swap" rel="stylesheet"> 
        <link href="https://fonts.googleapis.com/css?family=Barlow&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="../responsive_stylesheet/billing.css"/>
        <meta name="description" content="create an account, enter your shipping
                    address, and checking out to confirm your order is completed"/>
        <meta name="robots" content="index,follow"/>
        <meta name="DC.title" content="Motor crate gift box billing page"/>
    </head>
    <body>
        <jsp:include page="../jsp/popups.jsp"></jsp:include>
        <div id="wrapper">
            <div id="header">
                <div>
                    <img src="../assets/menu.png" id="menu">
                    <a href="../plan/" class="target">PLANS</a>
                    <a href="../how it work/" class="target cont">HOW IT WORKS</a>
                    <a href="../faq/" class="target">FAQ</a>
                    <a href="../redeem a motor crate/" class="target">REDEEM A GIFT</a>
                    <a href="../" class="logos"><img src="../assets/logo.png" class="logo"/></a>
                    <a href="../review/" class="target">REVIEWS</a>
                    <a href="../contact/" class="target">CONTACT</a>
                    <a href="../plan/" class="subscribe">SUBSCRIBE</a>
                    <% if(session.getAttribute("login") == null){ %>
                    <a href="../sign in/" class="signin">SIGN IN</a>
                    <%}else{ %>
                    <a href="../account/" class="signin">ACCOUNT</a>
                    <% }%>
                </div>
            </div>
            <div id="sidebar">
                <div>
                    <img src="../assets/close.png" id="close">
                    <p><a href="../plan/">PLANS</a></p>
                    <p><a href="../contact/" class="cont">CONTACTS</a></p>
                    <p><a href="../review/">REVIEWS</a></p>
                    <p><a href="../how it work/" >HOW IT WORKS</a></p>
                    <p><a href="../faq/">FAQ</a></p>
                    <p><a href="../redeem a motor crate/">REDEEM A GIFT</a></p>
                    <p><a href="../plan/">SUBSCRIBE</a></p>
                    
                    <nav>
                        <a href="<%=facebook.isEmpty() ? "" :facebook%>" target="_blank"><img src="../assets/facebook 1.png"/></a>
                        <a href="<%=instagram.isEmpty() ? "" :instagram%>" target="_blank"><img src="../assets/instagram 1.png"/></a>
                        <a href="<%=twitter.isEmpty() ? "" :twitter%>" target="_blank"><img src="../assets/twitter 1.png"/></a>
                    </nav>
                </div>
                
            </div>
            <div class="cb"></div>
            <div id="billing">
                <nav class="nav1"><img src="../assets/lock.png"/><span>Secure Checkout</span></nav>
                <center><h5 id="result" style="color:#427AA1;"></h5></center>
                
                <div class="top">
                    <div class="top1">
                        <nav class="bar1">
                            <span class="number">1</span>
                            <nav>
                                <span>Shipping Address</span>
                                <img src="../assets/down.png" class="drop-down-icon"/>
                            </nav>
                        </nav>
                        <div class="cb"></div>
                        <div id="topfirstTable">
                             <nav class="topNav">
                                <nav>
                                    <input type="text" placeholder="First Name" class="short" 
                                           value="<%=billing.getShippingFirstName()==null ? "" :billing.getShippingFirstName()%>" id="firstname"/>
                                </nav>
                                <nav class="secondNav">
                                    <input type="text" placeholder="Last Name" class="short" 
                                           value="<%=billing.getShippingLastName()== null 
                                                   ? "" :billing.getShippingLastName()%>" id="lastname"/>
                                </nav>
                            </nav>
                            <nav class="topNav">
                                <nav>
                                    <input type="text" placeholder="Country" class="short"
                                           value="<%=billing.getCountry()== null 
                                                   ? "" :billing.getCountry()%>" id="country"/>
                                    
                                </nav>
                                <nav class="secondNav">
                                    <input type="text" placeholder="State" class="short"
                                           value="<%=billing.getState()== null 
                                                   ? "" :billing.getState()%>" id="states"/>
                                </nav>
                            </nav>
                            <nav class="topNav">
                                <nav>
                                    <input type="text" placeholder="City" class="short"
                                           value="<%=billing.getCity()== null 
                                                   ? "" :billing.getCity()%>" id="city"/>
                                    
                                </nav>
                                <nav class="secondNav">
                                    <input type="text" placeholder="Zip / Postal code" class="short"
                                           value="<%=billing.getZip()== null 
                                                   ? "" :billing.getZip()%>" id="zip"/>
                                </nav>
                            </nav>
                            <nav class="bottomNav">
                                <input type="text" placeholder="Street Address" class="long"
                                       value="<%=billing.getStreet()== null 
                                                   ? "" :billing.getStreet()%>" id="street"/>
                            </nav>

                            <nav class="bottomNav">
                                <input type="text" placeholder="Nearest Landmark" class="long"
                                       value="<%=billing.getLandmark()== null 
                                                   ? "" :billing.getLandmark()%>" id="landmark"/>
                            </nav>

                            <nav class="bottomNav">
                                <input type="checkbox" checked/> <span>Save Address</span>
                            </nav>
                        </div>
                        <p><center><button id="shippingBtn" onclick="shipping();">Proceed</button></center></p>
                    </div>
                    
                    <div class="top2" id="paySection">
                        <table>
                            <thead>
                                <tr><th colspan="2"><h2>Order Summary</h2></th></tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><%= billing.getCategory() %></td>
                                    <td>&dollar;<%= billing.getCost() %></td>
                                </tr>
                                <tr>
                                    <td>Shipping & Handling</td>
                                    <td>&dollar;<%= billing.getShippingCost() %></td>
                                </tr>
                                <tr>
                                    <td>Discount</td>
                                    <td>&dollar;<%= billing.getTotalDiscount() %></td>
                                </tr>
                            </tbody>
                            
                            <tfoot>
                                <tr>
                                    <td colspan="2"><p class="left">TOTAL</p> <p class="right">&dollar;<%= billing.getTotal() %></p></td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
                <div class="cb"></div>
                <div class="bottom">
                    <nav class="bar2">
                        <span class="number">2</span>
                        <nav>
                            <span>Secure payment method</span>
                            <img src="../assets/up.png" class="drop-down-icon"/>
                        </nav>
                    </nav>
                    <div class="cb"></div>
                    <div class="bottom1">
                        <p id="paypal">online payment</p>
                    </div>
                    <div class="bottom2">
                        
                        <div class="lastTable" id="lastTable">
                            <div class="thead">
                                    <nav><img src="../assets/paypal.png"/><span>pay with visa,master card,american express</span></nav>
                            </div>
                            <p>
                            <center><form action="../jsp/createCustomer.jsp" method="post" class="frmStripePayment">
                                    <script src="https://checkout.stripe.com/checkout.js" 
                                            class="stripe-button" 
                                            data-key="<%=publishable_keys%>"
                                            data-name="<%=billing.getDuration()== null 
                                                   ? "" :billing.getDuration() %>" 
                                            data-descripition="ABONMENT 1 MOIS"
                                            data-panel-label="Subscribe"
                                            data-label="subscribe to pay"
                                            data-locale="auto" 
                                            data-currency="usd" data-amount="<%=(billing.getCost()*100)%>">
                                    </script>
                                </form></center>
                            </p>
                        </div>
                    </div>      
                </div>
            </div>
            <div class="cb"></div>
            <div id="footer">
                <div class="foot">
                    <div class="footlet">
                        <h3>SHOP</h3>
                        <p><a href="../plan/">Boxes</a></p>
                        <p><a href="../plan/">Subscribe</a></p>
                        <p><a href="../review/">Reviews</a></p>
                        <p><a href="../how it work/">How it Works</a></p>
                        <p><a href="../redeem a motor crate/">Redeem a Gift</a></p>
                    </div>
                    <div class="footlet">
                        <h3>MORE</h3>
                        <p><a href="../contact/">Contact Us</a></p>
                        <p><a href="../faq/">FAQ</a></p>
                        <p><a href="../sign in/">Sign In</a></p>
                        
                    </div>
                    <div class="footlet socialfootlet">
                        <nav class="footerfirstnav">
                           <a href="<%=facebook.isEmpty() ? "" :facebook%>" target="_blank"><img src="../assets/facebook 1.png"/></a>
                           <a href="<%=instagram.isEmpty() ? "" :instagram%>" target="_blank"><img src="../assets/instagram 1.png"/></a>
                           <a href="<%=twitter.isEmpty() ? "" :twitter%>" target="_blank"><img src="../assets/twitter 1.png"/></a>
                        </nav>
                        <p><a href="../terms and conditions/">Terms and Conditions</a></p>
                        <p><a href="../privacy policy/">Privacy Policy</a></p>
                        
                    </div>
                    <div class="footlet lastfootlet">
                        <h3>Payment Types we Accept</h3>
                        <nav> 
                            <img src="../assets/Group 41.png"  class="social"/> 
                            <img src="../assets/Group 40.png"  class="social"/> 
                            <img src="../assets/Group 39.png"  class="social lastsocials"/> 
                        </nav>
                        
                    </div>
                </div>
                
                <div class="cb"></div>
                <p class="lastp">COPYRIGHT © 2020 Motor Crate Gift Box</p>
            </div>
        </div>
        <script src="../javascript/header.js" type="text/javascript"></script>
        <script src="../javascript/billing.js" type="text/javascript"></script>
    </body>
</html>
