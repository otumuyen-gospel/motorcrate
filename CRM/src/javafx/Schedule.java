/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.google.gson.Gson;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import models.Schedules;

/**
 *
 * @author Gospel system
 */
public class Schedule extends Stage{
    Scene scene;
    BorderPane root = new BorderPane();
    public Schedule(Window owner){
        this.setTitle("Scheduling Shipping Date");
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        scene = new Scene(root, 1000, 500);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        root.setId("root");
        this.centerOnScreen();
        this.setMinHeight(500);
        this.setMaxHeight(500);
        this.setMinWidth(1000);
        this.setMaxWidth(1000);
        Create create = new Create(this);
        StackPane pane = new StackPane(create);
        create.setAlignment(Pos.CENTER);
        root.setCenter(pane);
        HBox bottom = new HBox();
        Label author = new Label("Motor Crate Gift Box - AmbiSmart Enterprises");
        author.setId("author");
        bottom.setId("bottom");
        bottom.getChildren().add(author);
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
    }
    
    class Create extends GridPane{
        Button b = new Button("Save All Schedule");
        int id;
        public Create(Window owner){
            this.setId("create");
            this.setHgap(20);
            this.setVgap(20);
            DatePicker month = new DatePicker(LocalDate.now());
            DatePickerSkin monthly = new DatePickerSkin(month);
            Node monthPicker = monthly.getPopupContent();
            
            DatePicker threeMonth = new DatePicker(LocalDate.now());
            DatePickerSkin threeMonthly = new DatePickerSkin(threeMonth);
            Node threeMonthPicker = threeMonthly.getPopupContent();
            
            DatePicker sixMonth = new DatePicker(LocalDate.now());
            DatePickerSkin sixMonthly = new DatePickerSkin(sixMonth);
            Node sixMonthPicker = sixMonthly.getPopupContent();
            
            DatePicker year = new DatePicker(LocalDate.now());
            DatePickerSkin yearly = new DatePickerSkin(year);
            Node yearPicker = yearly.getPopupContent();
            VBox box1 = new VBox(5);
            box1.getChildren().addAll(new Label("Schedule Next Shipping Date\n(1 Month Plan)"),monthPicker);
            VBox box2 = new VBox(5);
            box2.getChildren().addAll(new Label("Schedule Next Shipping Date\n(3 Month Plan)"),
                    threeMonthPicker);
            VBox box3 = new VBox(5);
            box3.getChildren().addAll(new Label("Schedule Next Shipping Date\n(6 Month Plan)"),
                    sixMonthPicker);
            VBox box4 = new VBox(5);
            box4.getChildren().addAll(new Label("Schedule Next Shipping Date\n(Annual(year) Plan)"),
                    yearPicker);
            
            this.add(box1, 0, 0);
            this.add(box2, 1,0);
            this.add(box3, 2, 0);
            this.add(box4, 3, 0);
            b.setId("button");
            this.add(b, 1, 1);
            
            this.showSchedule(owner, month, threeMonth, sixMonth, year);
            
            b.setOnAction(e->{
                  this.updateProfile(owner, month, threeMonth, sixMonth, year);
            });
        }
        
        public void updateProfile(Window owner,DatePicker month,DatePicker threeMonth,DatePicker sixMonth,
                DatePicker year){
            try{
                
                String url = Login.prop.getProperty("url")+"/schedule/"+this.id+"/"+month.getValue()+"/"+threeMonth.getValue()+"/"+
                        sixMonth.getValue()+"/"+year.getValue();
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                new MessageBox().message(owner, message.getMessage(), 1);
                }catch(Exception e){
                    new MessageBox().message(owner, "Error ocurred trying to update schedule", 0);
                }
        }
        
        public void showSchedule(Window owner,DatePicker month,DatePicker threeMonth,DatePicker sixMonth,
                DatePicker year){
            try{
                
                String url = Login.prop.getProperty("url")+"/schedule/fetch";
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                Schedules sche = new Gson().fromJson( reader, Schedules.class );
                this.id = sche.getId();
                String one = sche.getOneMonthly() == null ? LocalDate.now().toString() : sche.getOneMonthly();
                String three = sche.getThreeMonthly()== null ? LocalDate.now().toString() : sche.getThreeMonthly();
                String six = sche.getSixMonthly() == null ? LocalDate.now().toString() : sche.getSixMonthly();
                String yea = sche.getYearly() == null ? LocalDate.now().toString() : sche.getYearly();
                month.setValue(LocalDate.parse(one));
                threeMonth.setValue(LocalDate.parse(three));
                sixMonth.setValue(LocalDate.parse(six));
                year.setValue(LocalDate.parse(yea));
                }catch(Exception e){
                    e.printStackTrace();
                    new MessageBox().message(owner, e.getMessage(), 0);
                }
        }
        
    }
    
    
}
