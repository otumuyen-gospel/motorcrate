/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 * @author user1
 */
public class UrlEntities {
    
    public static String getHtmlBodyContent(String s){
        String top = s.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
        s = top.replace("</body></html>", "");
        top = s.replace("background-color: rgb(255, 255, 255);", "");
        return top.trim();
    }
    
    public static String EncodePercent(String s){
        return s.replace("%", "-5");
    }
    public static String DecodePercent(String s){
        return s.replace("-5", "%");
    }
    public static String encode(String s){
        String string = "";
        try{
            string = UrlEntities.EncodePercent(URLEncoder.encode(s,"UTF-8"));
            
        }catch(Exception e){
            
        }
        return string;
    }
    
    public static String decode(String s){
        String string = "";
        try{
            string = URLDecoder.decode(UrlEntities.DecodePercent(s),"UTF-8");
            
        }catch(Exception e){
            
        }
        return string;
    }
    
}
