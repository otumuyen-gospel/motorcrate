<%@page import="java.sql.ResultSet" isThreadSafe="false"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="backend.JDBC"%>
<%
    String type = request.getParameter("type");
    String value = request.getParameter("value");
    try{
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "select * from editor");
            String sql ="";
            if(jdbc.getRowSize("editor") == 0){
                if(type.equalsIgnoreCase("terms")){
                   sql = "insert into editor values('"+value+"','','','','','','','','','')";
                }else if(type.equalsIgnoreCase("privacy")){
                   sql = "insert into editor values('','"+value+"','','','','','','','','')";
                }else if(type.equalsIgnoreCase("more")){
                    sql = "insert into editor values('','','"+value+"','','','','','','','')";
                }
            }else{
                sql = "update editor set "+type+"='"+value+"'";
            }
            jdbc.update(stmt, sql);
            out.println("update Successful");
            jdbc.close(connect, stmt,rs);
        }catch(Exception e){
             out.println(JDBC.status);
        }
%>