<%@page import="java.sql.ResultSet" isThreadSafe="false"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="backend.JDBC"%>
<%
    String server = request.getParameter("server");
    String user = request.getParameter("user");
    String port = request.getParameter("port");
    String password = request.getParameter("password");
    try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            String sql;
            if(jdbc.getRowSize("editor") > 0){
                sql = "update editor set server='"+server+"',user='"+user+"', password='"+password+"',port='"+
                        port+"'";
            }else{
                sql = "insert into editor values('','','','','','','','','','','"+server+"','"+user+"','"+
                        password+"','"+port+"')";
            }
            jdbc.update(stmt, sql);
            out.println("update Successful");
            jdbc.close(connect, stmt);
        }catch(Exception e){
             out.println(JDBC.status);
        }
%>