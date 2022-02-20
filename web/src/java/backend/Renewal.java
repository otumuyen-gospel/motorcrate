/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.time.LocalDate;

/**
 *
 * @author user1
 */
public class Renewal {
    
    public static String date(String duration){
        long monthToAdd = 0;
         if(duration.equalsIgnoreCase("1 month")){
             monthToAdd = 1;
         }else if(duration.equalsIgnoreCase("3 month")){
             monthToAdd = 3;
         }else if(duration.equalsIgnoreCase("6 month")){
             monthToAdd = 6;
         }
         return LocalDate.now().plusMonths(monthToAdd).toString().replace("-", "/");
         
    }
}

