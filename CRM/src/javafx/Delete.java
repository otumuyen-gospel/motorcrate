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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Gospel system
 */
public class Delete {
    
    void dialog(String Id, javafx.stage.Window owner, String databaseTable){
            Stage stage = new Stage();
            Scene scene;
            BorderPane root = new BorderPane();
            Button yes = new Button("Yes");
            Button no = new Button("No");
            yes.setId("button");
            no.setId("button");
            
            Label question = new Label("Are You Sure You Want To Delete Item From Database ?");
            stage.setTitle("Dialog");
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            scene = new Scene(root, 900, 300);
            scene.getStylesheets().add("css/profile.css");
            stage.setScene(scene);
            root.setId("root");
            stage.setResizable(false);
        
            HBox questionBox = new HBox();
            questionBox.setId("box");
            questionBox.getChildren().add(question);
            questionBox.setAlignment(Pos.CENTER);
            root.setTop(questionBox);

            HBox buttonBox = new HBox(20);
            buttonBox.setId("box2");
            buttonBox.getChildren().addAll(yes, no);
            buttonBox.setAlignment(Pos.CENTER);
            root.setCenter(buttonBox);

            no.setOnAction(e->{stage.close();});
            yes.setOnAction(e->{
                //perform delete operation here close dialog and show status of operation
                this.delete(owner, Id, databaseTable, stage);
            });
            
            stage.showAndWait();
        }
    
    public void delete(javafx.stage.Window owner, String Id, String table, Stage stage){
        try{
                
            String url = Login.prop.getProperty("url")+"/delete/"+Integer.parseInt(Id)+"/"+table;
            InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

            TextMessage message = new Gson().fromJson( reader, TextMessage.class );
            stage.close();
            new MessageBox().message(owner, message.getMessage(), 1);
        }catch(Exception e){
            stage.close();
            new MessageBox().message(owner, "Error ocurred trying to carry out delete operation", 0);
        }
    }
    
}
