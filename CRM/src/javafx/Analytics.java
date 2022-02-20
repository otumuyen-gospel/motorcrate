/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import models.ActiveSubscriberChartModel;
import models.CustomerChartModel;

/**
 *
 * @author Gospel system
 */
public class Analytics extends Stage{
    Scene scene;
    BorderPane root = new BorderPane();
    public Analytics(Window owner){
        this.setTitle("Simple Motor Crate Website Analytics For The Current Year");
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        scene = new Scene(root, 1200, 550);
        this.setScene(scene);
        root.setId("root");
        this.setMinHeight(550);
        this.setMaxHeight(550);
        this.setMinWidth(1200);
        this.setMaxWidth(1200);
        Charts chart = new Charts(owner);
        chart.setAlignment(Pos.CENTER);
        StackPane pane = new StackPane(chart);
        ScrollPane scroll = new  ScrollPane(pane);
        pane.minWidthProperty().bind(Bindings.createDoubleBinding(()-> 
        scroll.getViewportBounds().getWidth(),scroll.viewportBoundsProperty()));
        root.setCenter(scroll);
        HBox bottom = new HBox();
        Label author = new Label("Motor Crate Gift Box - AmbiSmart Enterprises");
        author.setId("author");
        bottom.setId("bottom");
        bottom.getChildren().add(author);
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
    }
    
    
    class Charts extends GridPane{
        
        public Charts(Window owner){
            PieChart chart1 = new PieChart();               
            chart1.setTitle("No Of Registered Customers in "+LocalDate.now().getYear());               
            // Place the legend on the left side               
            chart1.setLegendSide(Side.LEFT);               
            // Set the data for the chart               
            ObservableList<PieChart.Data> chartData = new CustomerChartModel().getChartData();
            if(chartData.size() == 0){
                new MessageBox().message(owner, "no data to analyse", 1);
            }
            chart1.setData(chartData);               
            // Add a Tooltip to all pie slices              
            this.addSliceTooltip(chart1);               
            StackPane chartPane1 = new StackPane(chart1);
            
            this.setVgap(10);
            this.add(chartPane1, 0, 0);
            
            PieChart chart2 = new PieChart();               
            chart2.setTitle("No Of Active Subscribers in "+LocalDate.now().getYear());               
            // Place the legend on the left side               
            chart2.setLegendSide(Side.LEFT);               
            // Set the data for the chart               
            ObservableList<PieChart.Data> chartData2 = new ActiveSubscriberChartModel().getChartData();               
            chart2.setData(chartData2);   

            if(chartData2.size() == 0){
                new MessageBox().message(owner, "no data to analyse", 1);
            }            
            // Add a Tooltip to all pie slices              
            this.addSliceTooltip(chart2);               
            StackPane chartPane2 = new StackPane(chart2);
            
            this.add(chartPane2, 0, 1);
            
        }
         private void addSliceTooltip(PieChart chart) {               
            // Compute the total pie value               
            double totalPieValue = 0.0;               
            for (PieChart.Data d : chart.getData()) {                       
                totalPieValue += d.getPieValue();
                
            }  
           // Add a tooltip to all pie slices               
            for (PieChart.Data d : chart.getData()) {                       
                Node sliceNode = d.getNode();
                double pieValue = d.getPieValue();                       
                double percentPieValue = (pieValue / totalPieValue) * 100;                       
                // Create and install a Tooltip for the slice                       
                String msg = d.getName() + "=" + pieValue +  
                        " (" + String.format("%.2f", percentPieValue) + "%)";                       
                Tooltip tt = new Tooltip(msg);                       
                tt.setStyle("-fx-background-color: Yellow;" +                                   
                        "-fx-text-fill: #000;-fx-font-size:20;");                       
                Tooltip.install(sliceNode, tt);              
            }
         }
    }
}
