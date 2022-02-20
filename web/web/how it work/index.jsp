<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@page import="java.util.ArrayList,backend.*,java.sql.*" isThreadSafe="false"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String facebook = "https://";
    String instagram = "https://";
    String twitter = "https://";
    JDBC jdbc = new JDBC();
    Connection connect = jdbc.connect();
    Statement stmt = jdbc.statement(connect);
    ResultSet rs = jdbc.query(stmt, "select facebook,twitter,instagram from editor");
    if(rs.next()){
        facebook += UrlEntities.decode(rs.getString("facebook"));
        instagram += UrlEntities.decode(rs.getString("instagram"));
        twitter += UrlEntities.decode(rs.getString("twitter"));
    }
    jdbc.close(connect, stmt, rs);
 %>
<html>
    <head>
        <title>How it works</title>
        <meta charset="UTF-8">
        <link rel="icon" href="../assets/logo.png"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans&display=swap" rel="stylesheet"> 
        <link href="https://fonts.googleapis.com/css?family=Barlow&display=swap" rel="stylesheet">
         <link rel="stylesheet" type="text/css" href="../responsive_stylesheet/work.css"/>
        <meta name="description" content="How it works-Select between our female or male inspired giftbox
                            according to your preference.We'll ship your box goodness from our warehouse to your
                            front door, free of charge!"/>
        <meta name="robots" content="index,follow"/>
        <meta name="DC.title" content="how it works- motor crate gift box services"/>
    </head>
    <body>
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
            
            <div id="slider">
                <div class="slider2">
                    <h2>How it works</h2>
                    <p>Wide variety of open premium car products,FREE shipping, #happycleaning!</p>
                    <p><a href="../plan/">View Plans</a></p>
                    <div>
                        <h4>You'll love how easy this is</h4>
                        <p>PSST,It's no-risk and you can cancel any time</p>
                    </div>
                </div>
            </div>
            <div class="cb"></div>
            <div id="services">
                <div class="type1">
                    <img src="../assets/services1.svg"/>
                    <div>
                        <h3>Pick A plan</h3>
                        <h1>1</h1>
                        <p>
                            Select between our female or male inspired giftbox
                            according to your preference.
                        </p>
                    </div>
                    
                </div>
                <div class="cb"></div>
                <div class="type2">
                    <div>
                        <h3>Free Shipping!</h3>
                        <h1>2</h1>
                        <p>
                            We'll ship your box goodness from our warehouse to your
                            front door, free of charge!
                        </p>
                    </div>
                    <img src="../assets/services2.svg"/>
                </div>
                <div class="cb"></div>
                <div class="type1">
                    <img src="../assets/services3.svg"/>
                    <div>
                        <h3>Test the products</h3>
                        <h1>3</h1>
                        <p>
                            Test the products. Make something incredibly shiny, and 
                            then enjoy exclusive discounts, when you add your friends.
                        </p>
                    </div>
                    
                </div>
                <div class="cb"></div>
                <div class="type2">
                    <div>
                        <h3>Repeat</h3>
                        <h1>4</h1>
                        <p>
                            We'll keep sending you a box of goodness until you ask
                            us to stop!
                        </p>
                    </div>
                    <img src="../assets/services4.svg"/>
                </div>
                <div class="cb"></div>
                
                <div class="type3">
                    <img src="../assets/slider4.svg" class="first"/>
                    <div class="second">
                        <img src="../assets/services5.png"/>
                        <h1>Start Cleaning</h1>
                        <h3>with the best box</h3>
                        <p>With no commitment and FREE shipping in USA</p>
                        <p><a href="../plan/">View Boxes</a></p>
                    </div>
                    <img src="../assets/slider1.svg" class="third"/>
                </div>
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
    </body>
</html>
