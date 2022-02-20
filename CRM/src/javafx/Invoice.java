/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.google.gson.Gson;
import java.awt.Desktop;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Generator;
import models.InvoiceData;
import models.UrlEntities;

/**
 *
 * @author user1
 */
public class Invoice extends Stage{
    BorderPane root = new BorderPane();
    Label title = new Label();
    HBox box = new HBox();
    Button open = new Button("Open Folder");
    Button close = new Button("Abort And Exit Window");
    TextField location = new TextField();
    InvoiceData[]invoice = null;
    Generator gen;
    String website = "";
    Invoice(javafx.stage.Window window){
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(window);
        this.initStyle(StageStyle.UNDECORATED);
        Scene scene =new Scene(root,900,400);
        scene.getStylesheets().add("css/invoice.css");
        this.setScene(scene);
        root.setId("root");
        location.setDisable(true);
        open.setDisable(true);
        box.getChildren().addAll(location,open);
        box.setAlignment(Pos.CENTER);
        title.setWrapText(true);
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setCenter(new Group(box));
        gen = new Generator();
        this.printAll();
        root.setBottom(close);
        close.setOnAction(e->{this.close();});
    }
    public void printAll(){
        try{
            website = Login.prop.getProperty("url").split("//")[1].replace("/webresources", "");
            String url = Login.prop.getProperty("url")+"/invoice/view";
            InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
            invoice = new Gson().fromJson( reader, InvoiceData[].class );
            if(invoice.length == 0){
                new MessageBox().message(this, "No Active Subscriber can't generate Invoice", 1);
            }else{
                //call method to do task
                this.start();
            }
        }catch(Exception e){
            new MessageBox().message(this, "error fetching invoice data", 0);
        }
    }
    public void start(){
        Runnable task = () -> runTask();
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
    public void runTask(){
        for(int i = 0; i < invoice.length; i++){
            try{
                String status = "processing invoice "+(i+1)+" of "+invoice.length;
                Platform.runLater(()->{
                    title.setText(status);
                });
                //run task now
                gen.header(website,UrlEntities.decode(invoice[i].getAddress()), 
                        UrlEntities.decode(invoice[i].getTelephone()), 
                        UrlEntities.decode(invoice[i].getEmail()), 
                        UrlEntities.decode(invoice[i].getFacebook()), 
                        UrlEntities.decode(invoice[i].getTwitter()), 
                        UrlEntities.decode(invoice[i].getInstagram()));
                gen.body(invoice[i].getShippingName(), invoice[i].getStreet(), invoice[i].getCity(), 
                        invoice[i].getState(), invoice[i].getCountry(), invoice[i].getPostal(),(i+1));
                gen.footer(invoice[i].getPlan(), invoice[i].getPrice(), invoice[i].getDiscount(), 
                        invoice[i].getShippingCost(), invoice[i].getTotal());
                Thread.sleep(1);
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        location.setText(gen.closeFile());
        open.setDisable(false);
        open.setOnAction(e->{
            if(Desktop.isDesktopSupported()){
                try{
                     Desktop desktop = Desktop.getDesktop();
                     desktop.open(new File(location.getText()));
                }catch(Exception ex){
                  new MessageBox().message(this, ex.getMessage(), 0);
                }
            }else{
                new MessageBox().message(this, "file link: "+location.getText(), 1);
            }
            
        });
        
    }
}
