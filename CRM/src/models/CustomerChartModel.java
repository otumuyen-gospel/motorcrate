/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Gospel system
 */
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class CustomerChartModel {
    
   public static ObservableList<PieChart.Data> getChartData() { 
       ObservableList<PieChart.Data> data = FXCollections. observableArrayList();
       try{
           String url = Login.prop.getProperty("url")+"/analytics/registration";
           InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
           Month month = new Gson().fromJson( reader, Month.class );
           if(month.getJanuary() != 0){
               data.add(new PieChart.Data("Jan", month.getJanuary())); 
           }
           if(month.getFebuary() != 0){
               data.add(new PieChart.Data("Feb", month.getFebuary()));
           }
           
           if(month.getMarch() != 0){
               data.add(new PieChart.Data("Mar", month.getMarch()));
           }
           if(month.getApril() != 0){
               data.add(new PieChart.Data("Apr", month.getApril()));
           }
           if(month.getMay() != 0){
               data.add(new PieChart.Data("May", month.getMay()));
           }
           if(month.getJune() != 0){
               data.add(new PieChart.Data("June", month.getJune()));
           }
           if(month.getJuly() != 0){
               data.add(new PieChart.Data("July", month.getJuly()));
           }
           if(month.getAugust() != 0){
               data.add(new PieChart.Data("Aug", month.getAugust()));
           }
           if(month.getSeptember() != 0){
               data.add(new PieChart.Data("Sep", month.getSeptember()));
           }
           if(month.getOctober() != 0){
               data.add(new PieChart.Data("Oct", month.getOctober()));
           }
           if(month.getNovember() != 0){
               data.add(new PieChart.Data("Nov", month.getNovember()));
           }
           if(month.getDecember() != 0){
               data.add(new PieChart.Data("Dec", month.getDecember()));
           }
       }catch(Exception e){
          
       }
        
       return data;        
   }
}
