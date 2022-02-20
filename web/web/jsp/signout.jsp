<%
    if(request.getParameter("req").toString().equalsIgnoreCase("signout")){
        session.removeAttribute("login");
        out.println("out");
    }else{
        out.println("error occured processing your request");
    }
%>