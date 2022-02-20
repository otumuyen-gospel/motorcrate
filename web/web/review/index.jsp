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
    ResultSet rs = jdbc.query(stmt, "select facebook,twitter,instagram from editor");
    String facebook = "https://";
    String instagram = "https://";
    String twitter = "https://";
    if(rs.next()){
        facebook += UrlEntities.decode(rs.getString("facebook"));
        instagram += UrlEntities.decode(rs.getString("instagram"));
        twitter += UrlEntities.decode(rs.getString("twitter"));
    }
    
    ArrayList<Reviews> reviews = new ArrayList();
    rs = jdbc.query(stmt, "select * from review");
    while(rs.next()){
        Reviews review = new Reviews();
        review.setName(UrlEntities.decode(rs.getString("name")));
        review.setRating(UrlEntities.decode(rs.getString("rating")));
        review.setReview(UrlEntities.decode(rs.getString("review")));
        reviews.add(review);
    }
    jdbc.close(connect, stmt, rs);
%>
<html>
    <head>
        <title>Reviews</title>
        <meta charset="UTF-8">
        <link rel="icon" href="../assets/logo.png"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans&display=swap" rel="stylesheet"> 
        <link href="https://fonts.googleapis.com/css?family=Barlow&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../responsive_stylesheet/review.css"/>
        <meta name="description" content="See what motor crate gift box customer are saying about our services"/>
        <meta name="robots" content="index,follow"/>
        <meta name="DC.title" content="Motor crate gift box customers reviews"/>
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
             <% if(reviews.size() != 0) {%>
            <div id="review">
                <h1>Reviews & Testimonials</h1>
                <p class="p1">
                   We take your car maintenance seriously.Thats why we walk hard to create
                   a quality service that delivers great value and excitement to all our
                   subscribers. We receive reviews everyday from our subscribers thanking
                   us and telling us how much they like our service. Here are some of the
                   testimonials we have received.
                </p>
                <p class="p2"><a href="../plan/">SUBSCRIBE TO OUR GIFTBOX</a></p>
                <div class="reviewholder">
                    <div class="butt"><img src="../assets/arrow1.png" class="buttons" id="prev"/></div>
                    <div>
                        <% 
                            int firstEight = 1;
                            for(int i=0;i<reviews.size();i++){ 
                                 if(firstEight <= 8){

                        %>
                        <div class="reviews firstEightreview">
                            <h1><%= reviews.get(i).getName().length()>26 ? 
                                    reviews.get(i).getName().substring(0, 22)+"..." 
                                    : reviews.get(i).getName()%></h1>

                            <nav>
                                <%
                                    int rating = Integer.parseInt(reviews.get(i).getRating().split("")[0]);
                                    for(int k = 0; k< rating; k++){
                                        out.println("<img src='../assets/star.png'/>");
                                    }
                                %>

                            </nav>
                            <p>
                                <%= reviews.get(i).getReview().length()>150 ? 
                                    reviews.get(i).getReview().substring(0, 146)+"..." 
                                    : reviews.get(i).getReview() %>
                            </p>
                        </div>
                        <%

                            }else{

                        %>
                        <div class="reviews">
                            <h1><%= reviews.get(i).getName().length()>26 ? 
                                    reviews.get(i).getName().substring(0, 22)+"..." 
                                    : reviews.get(i).getName()%></h1>

                            <nav>
                                <%
                                    int rating = Integer.parseInt(reviews.get(i).getRating().split("")[0]);
                                    for(int k = 0; k< rating; k++){
                                        out.println("<img src='../assets/star.png'/>");
                                    }
                                %>

                            </nav>
                            <p>
                                <%= reviews.get(i).getReview().length()>150 ? 
                                    reviews.get(i).getReview().substring(0, 146)+"..." 
                                    : reviews.get(i).getReview() %>
                            </p>
                        </div>
                        <%
                              }

                              firstEight++;
                           }//close loop
                        %>
                    </div>
                   <div class="butt lastbutt"><img src="../assets/arrow2.png" class="buttons" id="next"/></div>
            
                </div>
            </div>
             <%  } //close final if-statement%>
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
        <script src="../javascript/review.js" type="text/javascript"></script>
    
    </body>
</html>