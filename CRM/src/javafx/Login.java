/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Properties;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Gospel system
 */
public class Login extends Stage{
    Button login = new Button("Login");
    TextField email = new TextField();
    PasswordField password = new PasswordField();
    String webservice_url = "";
    TextField url = new TextField();
    TextField port = new TextField();
    Label errorLabel = new Label("");
    BorderPane root = new BorderPane();
    public static Properties prop = new Properties();
    GridPane pane;
    Scene scene;
    public Login(){
        root.setId("root");
        this.setTitle("Customer Relationship Management System");
        scene = new Scene(root, 390,490);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        this.initStyle(StageStyle.UTILITY);
        this.show();
        this.setResizable(false);
        errorLabel.setText("Domain Name: https://www.motorcrategiftbox.com");
        errorLabel.setStyle("-fx-text-fill:#333;");
        
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(20);
        pane.setHgap(20);
        pane.add(new Label("Email:"), 0, 0);
        pane.add(email, 1, 0);
        pane.add(new Label("Password:"), 0, 1);
        pane.add(password, 1, 1);
        pane.add(new Label("Domain Name"), 0, 2);
        pane.add(url, 1, 2);
        login.setId("button");
        pane.add(login, 1, 3);
        pane.add(errorLabel,0,4,2,1);
        
        root.setCenter(pane);
        
        HBox bottom = new HBox();
        Label author = new Label("Motor Crate Gift Box - AmbiSmart Enterprises");
        author.setId("author");
        bottom.setId("bottom");
        bottom.getChildren().add(author);
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
        
        
        login.setOnAction(e->{ authenticate(); });
    }
    void authenticate(){
        if(!email.getText().isEmpty()){

            if(!password.getText().isEmpty()){
                
                if(!url.getText().trim().isEmpty()){
                    //online
                    webservice_url = url.getText().trim()+"/webresources";
                }else{
                    //locally on port 8080 context path '/web'
                    webservice_url = "http://localhost:8080/web/webresources";
                    
                }
                prop.put("url", webservice_url);
                try{
                    webservice_url += "/motor/"+email.getText()+"/"+password.getText();
                    InputStreamReader reader = new InputStreamReader( new URL( webservice_url ).openStream() );

                    TextMessage status = new Gson().fromJson( reader, TextMessage.class );
                    if(status.getMessage().equalsIgnoreCase("authenticated")){
                        errorLabel.setText("please wait");
                        errorLabel.setStyle("-fx-text-fill:red;");
                        new Window().show();
                        this.close();
                    }else{
                        errorLabel.setText(status.getMessage());
                        errorLabel.setStyle("-fx-text-fill:red;");
                    }
                }catch(Exception e){
                    errorLabel.setText("error: Unable to connect to the server hosting domain");
                    errorLabel.setStyle("-fx-text-fill:red;");
                    e.printStackTrace();
                    
                }
                
            }else{
                errorLabel.setText("please you must fill in the password field above ");
                errorLabel.setStyle("-fx-text-fill:red;");
            }
            
        }else{
            errorLabel.setText("please you must fill in the email field above ");
            errorLabel.setStyle("-fx-text-fill:red;");
            
        }
    }
    
}

class TextMessage{
    String message;
    public String getMessage(){
        return message;
    }
    public void setMessage(String value){
        this.message = value;
    }
    
}