/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

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
public class MessageBox {
    
        void message(javafx.stage.Window owner, String message, int severity){
            Stage stage = new Stage();
            Scene scene;
            BorderPane root = new BorderPane();
            Button close = new Button("Close");
            close.setId("button");
            Label output = new Label(message);
            output.setWrapText(true);
            if(severity == 0){
                output.setStyle("-fx-text-fill:red");
            }else if(severity == 1){
                output.setStyle("-fx-text-fill:#444");
            }
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
            questionBox.getChildren().add(output);
            questionBox.setAlignment(Pos.CENTER);
            root.setTop(questionBox);

            HBox buttonBox = new HBox(20);
            buttonBox.setId("box2");
            buttonBox.getChildren().addAll(close);
            buttonBox.setAlignment(Pos.CENTER);
            root.setCenter(buttonBox);

            close.setOnAction(e->{stage.close();});
            
            stage.showAndWait();
            
        }
}

