/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author user1
 */
public class LargePost {
    
    String webpage;
    String parameter;
    public LargePost(String webpage, String parameter){
        this.webpage = webpage;
        this.parameter = parameter;
        
    }
    public String Connect(){
        String status;
        try {
            // TODO code application logic here
            URL url = new URL(webpage);//this the url
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setChunkedStreamingMode(0);
            http.setRequestProperty("User-Agent", "Mozilla/5.0");
            http.setDoOutput(true);
            OutputStream os = http.getOutputStream();
            os.write(parameter.getBytes()); // the request parameter here
            os.flush();
            os.close();
           int responseCode =  http.getResponseCode();
           if(responseCode == HttpURLConnection.HTTP_OK){
               status = "posted";
           }else{
               status = "error";
           }
        } catch (Exception e) {
           status = e.getMessage();
        }
        return status;
    }
    
}
