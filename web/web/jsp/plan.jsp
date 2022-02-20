<% 
   String category = request.getParameter("category");
   String duration = request.getParameter("duration");
   if(!duration.equalsIgnoreCase("select a plan")){
       session.setAttribute("category", category);
       session.setAttribute("duration", duration);
       if(session.getAttribute("login") == null){
           out.println("login");
       }else{
           out.println("authenticated");
       }
       
   }else{
       out.println("please select a plan duration");
   }
   
%>