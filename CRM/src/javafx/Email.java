/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import models.AddLinkToEditor;
import models.LargePost;
import models.UrlEntities;

/**
 *
 * @author Gospel system
 */
public class Email extends Stage{
    BorderPane root = new BorderPane();
    Scene scene;
    Button send = new Button("Send");
    Button all = new Button("Send To All");
    Button settings = new Button("Email Server Settings");
    TextField subject = new TextField();
    TextField recipient = new TextField();
    HTMLEditor message = new HTMLEditor();
    
    public Email(Window owner){
        subject.setText("subject");
        subject.setStyle("-fx-text-fill:#aaa;");
        subject.setOnMouseClicked(e->{subject.clear();});
        recipient.setText("recipient Email Address");
        recipient.setStyle("-fx-text-fill:#aaa;");
        recipient.setOnMouseClicked(e->{recipient.clear();});
        this.setTitle("Simple Emailing Tool");
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        scene = new Scene(root, 1000,500);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        root.setId("root");
        this.centerOnScreen();
        this.setMinHeight(500);
        this.setMaxHeight(500);
        this.setMinWidth(1000);
        this.setMaxWidth(1000);
        
        GridPane p = new GridPane();
        p.setVgap(10);
        p.setAlignment(Pos.CENTER);
        message.setPrefHeight(400);
        p.add(subject, 0, 0);
        p.add(recipient, 0, 1);
        p.add(message, 0, 2);
        root.setCenter(p);
        new AddLinkToEditor(message);
        HBox b =new HBox(10);
        b.setAlignment(Pos.CENTER);
        send.setId("button");
        all.setId("button");
        settings.setId("button");
        b.getChildren().addAll(send, all,settings);
        root.setBottom(b);
        settings.setOnAction(e->{new Settings(this).showAndWait();});
        send.setOnAction(e->{this.send(this, recipient.getText());});
        all.setOnAction(e->{this.send(this, "all");});
    }
    public void send(Window owner, String recipient){
            try{
                
                String url = Login.prop.getProperty("url")+"/Messenger/"+
                        UrlEntities.encode(recipient)+"/"+
                        UrlEntities.encode(subject.getText())+"/"+
                        UrlEntities.encode(UrlEntities.getHtmlBodyContent(message.getHtmlText()));
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
                TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                new MessageBox().message(owner, message.getMessage(), 1);
                
                }catch(Exception e){
                    e.printStackTrace();
                    new MessageBox().message(owner, e.getMessage(), 0);
                }
            
        }
    
}

class Settings extends Stage{
    GridPane root = new GridPane();
    TextField server = new TextField();
    TextField user = new TextField();
    TextField port = new TextField();
    PasswordField password = new PasswordField();
    Button update = new Button("Update Settings");
    public Settings(Window owner){
        
        root.setAlignment(Pos.CENTER);
        root.setVgap(10);
        root.setHgap(10);
        root.add(new Label("Email Server:"), 0, 0);
        root.add(server, 1, 0);
        root.add(new Label("User Name:"), 0, 1);
        root.add(user, 1, 1);
        root.add(new Label("Password"), 0, 2);
        root.add(password, 1, 2);
        root.add(new Label("Port"), 0, 3);
        root.add(port, 1, 3);
        root.add(update, 1, 4);
      
        this.setTitle("Email Server Settings");
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.UNIFIED);
        Scene scene = new Scene(root, 500,500);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        root.setId("root");
        this.fetch(owner);
        update.setId("button");
        update.setOnAction(e->{
             String webpage = Login.prop.getProperty("url").replace("/webresources", "/jsp/SetEmailSettings.jsp");
             String parameter = "server="+server.getText()+"&user="+user.getText()+"&password="+
                     password.getText()+"&port="+port.getText();
             LargePost postTerms = new LargePost(webpage,parameter);
             if(postTerms.Connect().equalsIgnoreCase("posted")){
                 new MessageBox().message(owner, "Email settings updated accordingly", 1);
             }else{
                 new MessageBox().message(owner, "Error ocurred trying to update email settings", 0);
             }

        });
    }
        public void fetch(Window owner){
            try{
                
                    String url = Login.prop.getProperty("url")+"/Messenger/fetch";
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
  
                    server.setText(message.getMessage()==null ? "":message.getMessage().split("/")[0]);
                    user.setText(message.getMessage()==null ? "":message.getMessage().split("/")[1]);
                    password.setText(message.getMessage()==null ? "":message.getMessage().split("/")[2]);
                    port.setText(message.getMessage()==null ? "":message.getMessage().split("/")[3]);
                    
                }catch(Exception ex){
                    new MessageBox().message(owner, ex.getMessage(), 0);
                }
        }
}