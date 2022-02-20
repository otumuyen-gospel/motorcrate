<%@page import="backend.UrlEntities" isThreadSafe="false"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="backend.JDBC"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Map"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ArrayList"%>
<%  

    JDBC jd = new JDBC();
    Connection con = jd.connect();
    Statement st = jd.statement(con);
    ResultSet r = jd.query(st, "select youtube, api_key from editor");
    String channel_id = "";
    String api_key = "";
    int max_result = 5;
    if(r.next()){

        channel_id = UrlEntities.decode(r.getString("youtube"));
        api_key = UrlEntities.decode(r.getString("api_key"));
    }
    String url = "https://www.googleapis.com/youtube/v3/search?key="+api_key+"&channelId="+channel_id+
            "&part=snippet&maxResults="+
            max_result+"&type=video&videoEmbeddable=true";

    String json = "";
    String stripped = "";
    ArrayList array = new ArrayList();
    jd.close(con, st, r);//close database
    try{
            InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
            Map<?,?> map = new Gson().fromJson( reader, Map.class );
            for(Map.Entry<?,?> entry:map.entrySet()){
               json += entry.getValue().toString();
            }
            for(int i = 0; i < json.length(); i++){
                if(json.charAt(i) == '{' || json.charAt(i) == '}' || json.charAt(i) == ']'
                        || json.charAt(i) == '['){
                    stripped = json.replace(json.charAt(i)+"", "");
                    json = stripped;
                }
            }
            String[]arr = stripped.split(",");
            for(int i = 0; i < arr.length; i++){
                if(arr[i].contains("videoId=")){
                    array.add(arr[i].split("=")[1]);
                }
            }
    }catch(Exception e){
         out.println();
    }
    
%>
            
<div id="video">
    <h1>Tips on Motor Crate</h1>
    <p>Follow our videos on unboxing our crate box</p>
    <div translate="jsp" class="smallvideo">
        <iframe src="https://www.youtube.com/embed/<%=array.size()>=1? array.get(0) : ""%>"
                frameborder="0" allowfullscreen></iframe>
        <iframe src="https://www.youtube.com/embed/<%=array.size()>=2? array.get(1) : "" %>"
                frameborder="0" allowfullscreen></iframe>
        <iframe src="https://www.youtube.com/embed/<%=array.size()>=3? array.get(2) : "" %>"
                frameborder="0" allowfullscreen></iframe>
        <iframe src="https://www.youtube.com/embed/<%=array.size()>=4? array.get(3) : "" %>"
                frameborder="0" allowfullscreen style="margin-right:0px;"></iframe>
    </div>
    <div class="cb"></div>
    <div class="bigvideo">
        <iframe src="https://www.youtube.com/embed/<%=array.size()>=5? array.get(4) : "" %>"
                frameborder="0" allowfullscreen></iframe>
    </div>
</div>
                
                