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
    ResultSet rs = jdbc.query(stmt, "select more,facebook,twitter,instagram from editor");
    String facebook = "https://";
    String instagram = "https://";
    String twitter = "https://";
    String more = "";
    if(rs.next()){
        facebook += UrlEntities.decode(rs.getString("facebook"));
        instagram += UrlEntities.decode(rs.getString("instagram"));
        twitter += UrlEntities.decode(rs.getString("twitter"));
        more = UrlEntities.decode(rs.getString("more"));
    }
    
    rs = jdbc.query(stmt, "select * from plan");
    ArrayList plans = new ArrayList();
    ArrayList cost = new ArrayList();
    while(rs.next()){
        plans.add(rs.getString("planName"));
        cost.add(rs.getString("cost"));
    }
    
    jdbc.close(connect, stmt, rs);
%>
<html>
    <head>
        <title>Plan</title>
        <meta charset="UTF-8">
        <link rel="icon" href="../assets/logo.png"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans&display=swap" rel="stylesheet"> 
        <link href="https://fonts.googleapis.com/css?family=Barlow&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Oleo+Script&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=poppins&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../responsive_stylesheet/plan.css"/>
        <meta name="description" content="select your monthly based subscription plan and get amazing gift box
              delivered to your door step"/>
        <meta name="robots" content="index,follow"/>
        <meta name="DC.title" content="motor crate gift box subscription plan"/>
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
            
            <div id="plan">
                <h1>CHOOSE A PLAN</h1>
                <p class="firstp">Free shipping & cancel any time</p>
                <div class="planholder">
                    <div class="plans1">
                        <h3 class="category">Motor Crate For Her</h3>
                        <center><p class="secondp">Auto renews every month.Cancel any time.</p></center><br/>
                        <img src="../assets/female.svg"/>
                        <div class="dropDown">
                            <center>
                                <div class="select">
                                    <nav><span class="duration">select a plan</span>
                                        <img src="../assets/down2.png" id="down"/></nav>
                                </div>
                                <div class="cb"></div>
                                <div class="options">
                                    <% for(int i = 0; i < plans.size(); i++){ %>
                                    <div class="option" onclick="setOptions(0,'<%=plans.get(i) == null ? "" :plans.get(i)%>');">
                                        <h3 id="val"><%=plans.get(i) == null ? "0" :plans.get(i).toString().substring(0, 2)%></h3>
                                        <p>Months Plan<br/><span id="cost">$<%=cost.get(i) == null ? "0" :cost.get(i)%></span></p>
                                    </div>
                                    <div class="cb"></div>
                                    <%}%>
                                </div>
                            </center>
                        </div>
                        <p class="result"></p>
                        <p class="thirdp"><a href="Javascript:plan(0);">SUBSCRIBE</a></p>
                    </div>
                    <div class="plans2">
                        <h3 class="category">Motor Crate For Him</h3>
                        <center><p class="secondp">Auto renews every month.Cancel any time.</p></center><br/>
                        <img src="../assets/male.svg"/>
                        <div class="dropDown">
                            <center>
                                <div class="select">
                                    <nav><span class="duration">select a plan</span>
                                        <img src="../assets/down2.png" id="down" /></nav>
                                </div>
                                <div class="cb"></div>
                                <div class="options">
                                    <% for(int i = 0; i < plans.size(); i++){ %>
                                    <div class="option" onclick="setOptions(1,'<%=plans.get(i) == null ? "" :plans.get(i)%>');">
                                        <h3 id="val"><%=plans.get(i) == null ? "0" :plans.get(i).toString().substring(0, 2)%></h3>
                                        <p>Months Plan<br/><span id="cost">$<%=cost.get(i) == null ? "0" :cost.get(i)%></span></p>
                                    </div>
                                    <div class="cb"></div>
                                    <%}%>
                                </div>
                            </center>
                        </div>
                        <p class="result"></p>
                        <p class="thirdp"><a href="Javascript:plan(1);">SUBSCRIBE</a></p>
                    </div>
                </div>
            </div>
            <div class="cb"></div>
            <div id="care">
                <p class="firstp">Give your car the best</p>
                <h1 class="firsth1">CAR CARE</h1>
                <div class="services">
                    <h1>Why Subscribe?</h1>
                    <div class="service">
                        <img src="../assets/plan1.svg"/>
                        <p>Learn new tips and technique every month</p>
                    </div>
                    <div class="service">
                        <img src="../assets/plan2.svg"/>
                        <p>We provide FREE SHIPPING to anywhere in USA</p>
                    </div>
                    <div class="service">
                        <img src="../assets/plan3.svg"/>
                        <p>Get amazing discount when you invite your friends</p>
                    </div>
                    <div class="service lastservice">
                        <img src="../assets/plan4.svg"/>
                        <p>Yes, you can pause or cancel anytime</p>
                    </div>
                </div>
            </div>
            <div class="cb"></div>
            <div class="learn">
                <h1>LEARN MORE</h1>
                <p>
                    <%=more.isEmpty() ? "" :more%>
                </p>
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
         <script src="../javascript/ajax.js" type="text/javascript"></script>
    </body>
</html>
