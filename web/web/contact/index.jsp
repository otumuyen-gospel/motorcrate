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
    ResultSet rs = jdbc.query(stmt, "select address,telephone,email,facebook,twitter,instagram from editor");
    String facebook = "https://";
    String instagram = "https://";
    String twitter = "https://";
    String email = "";
    String address = "";
    String telephone= "";
    if(rs.next()){
        facebook += UrlEntities.decode(rs.getString("facebook"));
        instagram += UrlEntities.decode(rs.getString("instagram"));
        twitter += UrlEntities.decode(rs.getString("twitter"));
        email = UrlEntities.decode(rs.getString("email"));
        address = UrlEntities.decode(rs.getString("address"));
        telephone = UrlEntities.decode(rs.getString("telephone"));
    }
    
    jdbc.close(connect, stmt, rs);
%>
<html>
    <head>
        <title>Contact Us</title>
        <meta charset="UTF-8">
        <link rel="icon" href="../assets/logo.png"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans&display=swap" rel="stylesheet"> 
        <link href="https://fonts.googleapis.com/css?family=Barlow&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../responsive_stylesheet/contact.css"/>
        <meta name="description" content="<%=address.isEmpty() ? "" :address%>,
              <%=telephone.isEmpty() ? "" :telephone%>"/>
        <meta name="robots" content="index,follow"/>
        <meta name="DC.title" content="Contact us- motor crate gift box"/>
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
            
            <div id="form">
                <h1>Contact Us</h1>
                <div class="box">
                    <h4>Please fill the form below, and we will get back to you</h4>
                    <nav class="bottomNav">
                        <label>Contact Name</label><br/>
                        <input type="text" name="name" id="name"/>
                    </nav>
                    
                    <nav class="bottomNav">
                        <label>Email</label><br/>
                        <input type="text"  class="pass" name="email" id="email"/>
                    </nav>
                    
                    <nav class="bottomNav">
                        <label>Subject</label><br/>
                        <input type="text"  class="pass" name="subject" id="subject"/>
                    </nav>
                    
                    <nav class="bottomNav">
                        <label>Description</label><br/>
                        <textarea name="description" id="description"></textarea>
                    </nav>
                    
                    <p id="result"></p>
                    <button onclick="sendMail();">Submit</button>
                    
                </div>
                <div class="cb"></div>
                <table>
                    <tr>
                        <td><h3>Call us</h3></td>
                        <td>
                            <h2><%=address.isEmpty() ? "" :address%></h2>
                            <p></p>
                        </td>
                    </tr>
                    <tr>
                        <td><h3>Call us</h3></td>
                        <td>
                            <h2><%=telephone.isEmpty() ? "" :telephone%></h2>
                            <p>Mon to Fri 9am - 5pm</p>
                        </td>
                    </tr>
                    <tr>
                        <td><h3>Send us a mail</h3></td>
                        <td>
                            <h2><%=email.isEmpty() ? "" :email %></h2>
                            <p>Send us feedback anytime</p>
                        </td>
                    </tr>
                </table>
                
                
            </div>
            <p class="ruler"></p>
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
        <script src="../javascript/ajax.js" type="text/javascript"></script>
    </body>
</html>
