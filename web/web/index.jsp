<%-- 
    Document   : index
    Created on : Mar 13, 2020, 11:10:20 PM
    Author     : user1
--%>

<%@page import="java.util.Map"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
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
        <title>Motor Crate Gift Box</title>
        <meta charset="UTF-8">
        <link rel="icon" href="assets/logo.png"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans&display=swap" rel="stylesheet"> 
        <link href="https://fonts.googleapis.com/css?family=Barlow&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=poppins&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="responsive_stylesheet/home.css"/>
        <link rel="stylesheet" type="text/css" href="responsive_stylesheet/animation.css"/>
        <meta name="description" content="Motor Crate Gift Box delivers quality maintenance products for
              your car on monthly bases according to customers subscription"/>
        <meta name="robots" content="index,follow"/>
        <meta name="DC.title" content="Motor crate gift Box car maintenance products and services"/>
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div>
                    <img src="assets/menu.png" id="menu">
                    <a href="plan/" class="target">PLANS</a>
                    <a href="how it work/" class="target cont">HOW IT WORKS</a>
                    <a href="faq/" class="target">FAQ</a>
                    <a href="redeem a motor crate/" class="target">REDEEM A GIFT</a>
                    <a href="#" class="logos"><img src="assets/logo.png" class="logo"/></a>
                    <a href="review/" class="target">REVIEWS</a>
                    <a href="contact/" class="target">CONTACT</a>
                    <a href="plan/" class="subscribe">SUBSCRIBE</a>
                    <% if(session.getAttribute("login") == null){ %>
                    <a href="sign in/" class="signin">SIGN IN</a>
                    <%}else{ %>
                    <a href="account/" class="signin">ACCOUNT</a>
                    <% }%>
                </div>
            </div>
            <div id="sidebar">
                <div>
                    <img src="assets/close.png" id="close">
                    <p><a href="plan/">PLANS</a></p>
                    <p><a href="contact/">CONTACTS</a></p>
                    <p><a href="review/">REVIEWS</a></p>
                    <p><a href="how it work/" >HOW IT WORKS</a></p>
                    <p><a href="faq/">FAQ</a></p>
                    <p><a href="redeem a motor crate/">REDEEM A GIFT</a></p>
                    <p><a href="plan/">SUBSCRIBE</a></p>
                    
                    <nav>
                        <a href="<%=facebook.isEmpty() ? "" :facebook%>" target="_blank"><img src="assets/facebook 1.png"/></a>
                        <a href="<%=instagram.isEmpty() ? "" :instagram%>" target="_blank"><img src="assets/instagram 1.png"/></a>
                        <a href="<%=twitter.isEmpty() ? "" :twitter%>" target="_blank"><img src="assets/twitter 1.png"/></a>
                    </nav>
                </div>
                
            </div>
            <p class="cb"></p>
            <div id="slider">
                <div class="sliderContent">
                    <h1>Motor Crate For You</h1>
                    <p class="paragraph">Get your monthly car product delivered to you directly at your door step</p>
                    <p class="buttons"><a href="plan/" class="subscribe">SUBSCRIBE</a><a href="plan/" class="boxes">VIEW BOXES</a></p>
                </div>
                <img src="assets/her 2.svg" class="sliderBox"/>
            </div>
            <p class="cb"></p>
            <div id="happiness">
                <h1>HAPPINESS IN A BOX</h1>
                <p class="p1">Special Motor Crate for you</p>
                <div class="tools">
                    <img src="assets/tool1.png" class="tool1 tooling"/>
                     <img src="assets/tool2.png" class="tool2 tooling"/>
                     <img src="assets/tool3.png" class="tool3 tooling"/>
                     <img src="assets/tool4.png" class="tool4 tooling"/>
                     <img src="assets/tool5.png" class="tool5 tooling"/>
                     <img src="assets/her 2.svg" class="toolbox"/>
                </div>
                <div class="happyholder">
                    <div class="happy">
                        <img src="assets/edition.svg"/>
                        <div class="happyText">
                            <p class="p2">Exclusive &</p>
                            <p class="p3">Limited Edition</p>
                            <p class="p4">Car Cleaning stuffs</p>
                        </div>
                    </div>
                    <div class="happy">
                        <img src="assets/discount.svg"/>
                        <div class="happyText">
                            <p class="p2">Get discount </p>
                            <p class="p3">Special Codes</p>
                            <p class="p4">on all Subscription</p>
                        </div>
                    </div>
                    <div class="cb"></div>
                    <div class="happy third">
                        <img src="assets/product.svg"/>
                        <div class="happyText">
                            <p class="p2">Get your products </p>
                            <p class="p3">Delivered</p>
                            <p class="p4">To your Doorstep</p>
                        </div>
                    </div>
                    <div class="happy last">
                        <img src="assets/signup.svg"/>
                        <div class="happyText">
                            <p class="p2">Sign up for a </p>
                            <p class="p3">Subscription plan</p>
                            <p class="p4">Today</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="cb"></div>
            <div id="plan">
                <div class="planholder">
                    <h1>OUR PRODUCT PLANS</h1>
                    <p class="p1">Check out our Exclusive</p> 
                    <div class="bottles">
                        <div class="bottle"><img src="assets/brush1.png"/>
                            <br/><span>Cleaning Brush</span>
                        </div>
                        <div class="bottle"><img src="assets/brush2.png"/>
                            <br/><span>Towels</span>
                        </div>
                        <div class="cb"></div>
                         <div class="bottle"><img src="assets/bottle2.png"/>
                            <br/><span>Wheel & Tire Cleaner</span></div>
                        <div class="bottle"><img src="assets/bottle1.png"/>
                            <br/><span>Body Wash</span></div>
                       
                    </div>
                    <div class="gender">
                        <a href="plan/"><img src="assets/her 1.svg" class="gen1"/></a>
                        <a href="plan/"><img src="assets/him 1.svg" class="gen2"/></a>
                    </div>
                </div>
            </div>
            <div class="cb"></div>
            <div id="why">
                <h1>WHY OUR GIFTBOX?</h1>
                <div>
                    <div class="whys">
                        <img src="assets/why1.png"/>
                        <p>Enjoy a new supply box each month</p>
                    </div>
                    <div class="whys">
                        <img src="assets/why2.png"/>
                        <p>Use top quality products for all your supply.</p>
                    </div>
                    <div class="whys">
                        <img src="assets/why3.png"/>
                        <p>Learn professional tips and techniques.</p>
                    </div>
                    <div class="whys lastwhy">
                        <img src="assets/why4.png"/>
                        <p>Save more from our discount codes, on all subscription</p>
                    </div>
                </div>
            </div>
            <div class="cb"></div>
            <% if(reviews.size() != 0) {%>
            <div id="review">
                <img src="assets/Vector 1.png" class="firstline"/>
                <img src="assets/happy 1.png" class="mr"/>
                <h1>HAPPY CUSTOMERS</h1>
                <p class="p1">Don't take our word for it, see our feedback from our current subscribers</p>
                
                <div class="reviewholder">
                    <div class="butt"><img src="assets/arrow1.png" class="buttons" id="prev"/></div>
                    <% 
                        int firstfour = 1;
                        for(int i=0;i<reviews.size();i++){ 
                             if(firstfour <= 4){
                             
                    %>
                    <div class="reviews firstfourreview">
                        <h1><%= reviews.get(i).getName().length()>26 ? 
                                reviews.get(i).getName().substring(0, 22)+"..." 
                                : reviews.get(i).getName()%></h1>
                        
                        <nav>
                            <%
                                int rating = Integer.parseInt(reviews.get(i).getRating().split("")[0]);
                                for(int k = 0; k< rating; k++){
                                    out.println("<img src='assets/star.png'/>");
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
                        <h1><%= reviews.get(i).getName().length()>20 ? 
                                reviews.get(i).getName().substring(0, 16)+"..." 
                                : reviews.get(i).getName()%></h1>
                        
                        <nav>
                            <%
                                int rating = Integer.parseInt(reviews.get(i).getRating().split("")[0]);
                                for(int k = 0; k< rating; k++){
                                    out.println("<img src='assets/star.png'/>");
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
                        
                          firstfour++;
                       }//close loop
                    %>
                    <div class="butt lastbutt"><img src="assets/arrow2.png" class="buttons" id="next"/></div>
                </div>
                <div class="cb"></div>
                <img src="assets/Vector 1.png" class="lastline"/>
            </div>
            <%  } //close final if-statement %>
            <jsp:include page="jsp/youtube_client.jsp"></jsp:include>
            <div id="footer">
                <div class="firstpart">
                    <h2>Ready to join us yet? All it take is a click to register</h2>
                   <p class="firstp"><a href="plan/" class="subscribe">Subscribe</a></p>
                </div>
                
                <div class="foot">
                    <div class="footlet">
                        <h3>SHOP</h3>
                        <p><a href="plan/">Boxes</a></p>
                        <p><a href="plan/">Subscribe</a></p>
                        <p><a href="review/">Reviews</a></p>
                        <p><a href="how it work/">How it Works</a></p>
                        <p><a href="redeem a motor crate/">Redeem a Gift</a></p>
                    </div>
                    <div class="footlet">
                        <h3>MORE</h3>
                        <p><a href="contact/">Contact Us</a></p>
                        <p><a href="faq/">FAQ</a></p>
                        <p><a href="sign in/">Sign In</a></p>
                        
                    </div>
                    <div class="footlet socialfootlet">
                        <nav class="footerfirstnav">
                            <a href="<%=facebook.isEmpty() ? "" :facebook %>" target="_blank"><img src="assets/facebook 1.png"/></a>
                            <a href="<%=instagram.isEmpty() ? "" :instagram %>" target="_blank"><img src="assets/instagram 1.png"/></a>
                            <a href="<%=twitter.isEmpty() ? "" :twitter %>" target="_blank"><img src="assets/twitter 1.png"/></a>
                        </nav>
                        <p><a href="terms and conditions/">Terms and Conditions</a></p>
                        <p><a href="privacy policy/">Privacy Policy</a></p>
                        
                    </div>
                    <div class="footlet lastfootlet">
                        <h3>Payment Types we Accept</h3>
                        <nav> 
                            <img src="assets/Group 41.png"  class="social"/> 
                            <img src="assets/Group 40.png"  class="social"/> 
                            <img src="assets/Group 39.png"  class="social lastsocials"/> 
                        </nav>
                        
                    </div>
                </div>
                
                <div class="cb"></div>
                <p class="lastp">COPYRIGHT © 2020 Motor Crate Gift Box</p>
            </div>
        </div>
        <script src="javascript/header.js" type="text/javascript"></script>
        <script src="javascript/home.js" type="text/javascript"></script>
        <script src="javascript/animation.js" type="text/javascript"></script>
        <% 
            // automatically update next due date for all customers
            new UpdateNextDueDateForAllCustomer().start();
        %>
    </body>
</html>


