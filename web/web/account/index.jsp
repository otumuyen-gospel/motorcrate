<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@page import="java.util.ArrayList,backend.*,java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8" isThreadSafe="false"%>
<%
    String user = null;
    if(session.getAttribute("login") == null){
        response.sendRedirect("../sign in/");
    }else{
        user = session.getAttribute("login").toString();
    }
    JDBC jdbc = new JDBC();
    Connection connect = jdbc.connect();
    Statement stmt = jdbc.statement(connect);
    ResultSet rs = jdbc.query(stmt, "select facebook,twitter,instagram from editor");
    String facebook = "https://";
    String instagram = "https://";
    String twitter = "https://";
    if(rs.next()){
        facebook += UrlEntities.decode(rs.getString("facebook"));
        instagram += UrlEntities.decode(rs.getString("instagram"));
        twitter += UrlEntities.decode(rs.getString("twitter"));
    }
    
    //if user has active plan
    rs = jdbc.query(stmt, "select subscription,category,duration from account where email='"+
            session.getAttribute("login")+"'");
    String sub = "";
    String duration="";
    String category ="";
    if(rs.next()){
        sub = rs.getString("subscription");
        duration = rs.getString("duration");
        category = rs.getString("category");
    }
    
    //fetch referal link
    rs = jdbc.query(stmt, "select link from referal where email='"+
            session.getAttribute("login")+"'");
    String link = "";
    
    if(rs.next()){
        link = rs.getString("link");
    }
    
    jdbc.close(connect, stmt, rs);
    
%>
<html>
    <head>
        <title>Account</title>
        <meta charset="UTF-8">
        <link rel="icon" href="../assets/logo.png"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans&display=swap" rel="stylesheet"> 
        <link href="https://fonts.googleapis.com/css?family=Barlow&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="../responsive_stylesheet/account.css"/>
        <meta name="description" content="sign in to manage your accounts and make payments"/>
        <meta name="robots" content="index,follow"/>
        <meta name="DC.title" content="Motor crate gift box dashboard"/>
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
                    <a href="Javascript:signOut();" class="signin">Log Out</a>
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
            
            <div id="profile">
                <center>
                    <form class="profileForm" id="form" action="../jsp/profile.jsp" method="post">
                        <img src="../assets/close.png" id="closer"/>
                        <h4>Update Your Login Profile</h4>
                        <p>
                            <label>First Name</label><br/>
                            <input type="text" name="firstname" id="firstname"/>
                        </p>
                        <p>
                            <label>Last Name</label><br/>
                            <input type="text" name="lastname" id="lastname"/>
                        </p>
                        <p>
                            <label>Email Address</label><br/>
                            <input type="text" name="email" id="email"/>
                        </p>
                        <p>
                            <label>Password</label><br/>
                            <input type="password" name="password" id="password"/>
                        </p>
                        <p>
                            <label>Confirm Password</label><br/>
                            <input type="password" name="confirm" id="confirm"/>
                        </p>
                        <p>
                            <input type="button" value="Update Profile" class="updateBtn" 
                                   onclick="UpdateProfile();"/>
                        </p>
                        <center><span id="result"></span></center>
                    </form>
                </center>
                
            </div>
            <div id="account">
                <% if(!sub.equals("active")){%>
                <div class="notification">
                    <h2>Hello there</h2>
                    <p>Subscribe to one of our exclusive plans today</p>
                </div>
                <div class="boxes">
                    <div class="one">
                      <h3>Your Account</h3>
                      <h4>You have no active subscription</h4>
                      <p><a href="../plan/">View Plans</a></p>
                    </div>
                    <div class="two">
                        <h3>Billing Information</h3>
                        <h4>No payment Information yet</h4>
                        <p><a href="../billing/">Add</a></p>
                    </div>
                    <div class="three">
                        <h3>Account Information</h3>
                        <h4>Login:<br/><span><%=user == null?"":user %></span></h4>
                        <p><a href="Javascript:openForm();">Edit login and password</a></p>
                    </div>
                </div>
                <%}else if(sub.equals("active")){%>
                <h1 class="dashboard_title">YOUR ACCOUNT</h1>
                <div class="boxes">
                    <div class="one">
                      <h3>Your Account</h3>
                      <h4 class="order">Current Order</h4>
                      <p class="plan_info"><%=category == null?"":category %></p>
                      <p class="plan_info"><%=duration == null?"":duration %></p>
                      <button class="dashboard_btn"><%=new Payment().getDaysUtilDue(session.getAttribute("login").toString())%></button>
                    </div>
                    <div class="two">
                        <h3>Billing Information</h3>
                        <center>
                            <nav>
                                <img src="../assets/accountnumber.png"/><span><%=new Payment().getCard(session.getAttribute("login").toString())%></span>
                            </nav>
                        </center>
                        <button class="dashboard_btn" id="cancel" onclick="cancelSubscription();">Cancel subscription</button>
                    </div>
                    <div class="three">
                        <h3>Account Information</h3>
                        <h4>Login:<br/><span><%=user == null?"":user %></span></h4>
                        <p><a href="Javascript:openForm();">Edit login and password</a></p>
                    </div>
                </div>
                
                <%}%>
                
            </div>
            <div class="cb"></div>
            <div id="dialog" style="position:fixed;top:0;left:0;height: 100vh;width: 100%;
                 background-color: rgba(0,0,0,0.2);z-index: 1000; display: none;">
                <center><form id="form" 
                      style="width: 100%; background-color: #fff; 
                      border:1px solid #233D4D; text-align: center; ">
                    <h1 id="issue" style="font-size: 20px; text-align: center;font-weight: normal;
                        color: #fff; padding: 20px;  background-color: #427AA1;"></h1>
                    <h2 style="font-size: 1em; margin-top: 50px; 
                        font-weight: bold; margin-bottom:20px; color: #233D4D;">please quickly tell us why?</h2>
                    <p>
                        <textarea name="why" id="why" style="height: 140px; width: 80%; 
                                  border:1px solid #ccc; font-family: noto-sans; font-size: 18px;">
                            
                        </textarea>
                    </p>
                    <input type="hidden" name="type" id="type"/>
                    <input type="button" value="cancel" style="border-radius: 50px;padding: 5px 22px;
                           text-align: center;color: #fff;border:2px solid #FE7F2D;background-color: #FE7F2D;
                           margin:20px 5px; " onclick="document.getElementById('dialog').style.display='none'"/>  
                    <input type="button" value="Continue" style="border-radius: 50px;padding: 5px 20px;
                           text-align: center;color: #fff;border:2px solid #233D4D; background-color: #233D4D;
                           margin:20px 5px;" onclick="customerOperation();"/>
                    </form></center>
            </div>
            <div id="operations">
                <div class="referalBox">
                    <div class="referaltext">
                        <h1>GET 10% DISCOUNT</h1>
                        <p>Refer a friend and get a special treat on your next box</p>
                        <p>from us</p>
                    </div>
                    <div class="referallink">
                        <span>Share your link with your friends</span>
                        <nav>
                            <input type="text" value="<%=link.isEmpty() ? "" :link%>" id="myInput"/>
                            <div class="tooltip">
                                <img src="../assets/copy.png" onclick="myFunction()" onmouseout="outFunc()">
                                  <span class="tooltiptext" id="myTooltip">Copy link</span>
                               </img>
                            </div>
                            
                        </nav>
                    </div>
                </div>
                <div class="cb"></div>
            </div>
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
         <script src="../javascript/account.js" type="text/javascript"></script>
         <script src="../javascript/ajax.js" type="text/javascript"></script>
    </body>
</html>
