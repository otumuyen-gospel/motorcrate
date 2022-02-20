/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Operation;

/**
 *
 * @author user1
 */
public class Action extends Stage{
    BorderPane root = new BorderPane();
    GridPane pane = new GridPane();
    public Action(javafx.stage.Window owner){
        this.setTitle("View Customers Actions");
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root, 1300,550);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        root.setId("root");
        pane.setVgap(15);
        this.complains(owner);
        
    }
    public void complains(javafx.stage.Window owner) {
        try{
        String url = Login.prop.getProperty("url")+"/operation";
            InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
            Operation []p = new Gson().fromJson( reader, Operation[].class );
            if(p.length == 0){
                Label notice = new Label("Hurray!!! No Complains From www.isabouttogo.online");
                notice.setStyle("-fx-font-size:24px;");
                root.setCenter(notice);
                return;
            }
            
            ScrollPane scroll = new ScrollPane(new StackPane(pane));
            scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            root.setCenter(scroll);
            
            for(int i = 0; i < p.length; i++){
                VBox box = new VBox(5);
                box.setPrefWidth(1290);
                box.setStyle("-fx-background-color:#fff; -fx-padding:20px;");
                Label id = new Label();
                id.setId("id");
                if(i == p.length-1){
                    id.setText("Latest");
                }else{
                    id.setText(""+(i+1));
                }
                Label act = new Label("ACTION: "+ p[i].getAction());
                act.setId("action");
                Label why = new Label("REASON: "+p[i].getWhy());
                why.setWrapText(true);
                why.setId("why");
                Label email = new Label(p[i].getEmail());
                email.setWrapText(true);
                email.setId("email");
                box.getChildren().addAll(id,act,why,email);
                pane.add(box, 0, i);
            }
        }catch(Exception e){
            new MessageBox().message(owner, "Error ocurred trying to fetch Customers Complain", 0);

        }
        
    }
}
