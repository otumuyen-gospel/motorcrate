/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Gospel system
 */
public class Window extends Stage{
    BorderPane root = new BorderPane();
    public Window(){
        root.setId("root");
        this.setTitle("Customer Relationship Management System");
        Scene scene = new Scene(root, 1400,700);
        scene.getStylesheets().add("css/window.css");
        this.setScene(scene);
        Rectangle2D fullWindow = Screen.getPrimary().getVisualBounds();
        this.setX(fullWindow.getMinX());
        this.setY(fullWindow.getMinY());
        this.setWidth(fullWindow.getWidth());
        this.setHeight(fullWindow.getHeight());
        
        this.setMinHeight(fullWindow.getHeight());
        this.setMinWidth(fullWindow.getWidth());
        
        HBox top = new HBox(5);
        ImageView logo = new ImageView();
        logo.setId("logo");
        top.getChildren().addAll(logo);
        top.setAlignment(Pos.CENTER);
        top.setId("top");
        root.setTop(top);
        
        widgets(this);
        
        HBox bottom = new HBox();
        Label author = new Label("Motor Crate Gift Box - AmbiSmart Enterprises");
        author.setStyle("-fx-font-weight:bolder;");
        bottom.setPadding(new Insets(10));
        author.setId("author");
        bottom.setId("bottom");
        bottom.getChildren().add(author);
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
        
    }
    void widgets(Stage stage){
        GridPane p = new GridPane();
        root.setCenter(p);
        p.setAlignment(Pos.CENTER);
        ImageView account = new ImageView();
        account.setOnMouseClicked(e->{new Account(stage).showAndWait();});
        
        ImageView reviews = new ImageView();
        reviews.setOnMouseClicked(e->{new Review(stage).showAndWait();});
        
        ImageView analytics = new ImageView();
        analytics.setOnMouseClicked(e->{new Analytics(stage).showAndWait();});
        
        ImageView settings = new ImageView();
        settings.setOnMouseClicked(e->{new Admin(stage).showAndWait();});
        
        ImageView faq = new ImageView();
        faq.setOnMouseClicked(e->{new Faq(stage).showAndWait();});
        
        ImageView schedule = new ImageView();
        schedule.setOnMouseClicked(e->{new Schedule(stage).showAndWait();});
        
        
        ImageView email = new ImageView();
        email.setOnMouseClicked(e->{new Email(stage).showAndWait();});
        
        ImageView editor = new ImageView();
        editor.setOnMouseClicked(e->{new Editor(stage).showAndWait();});
        
        
        p.setVgap(30);
        p.setHgap(70);
        p.add(account, 0, 0);
        p.add(analytics, 1, 0);
        p.add(reviews, 2, 0);
        p.add(schedule, 0, 1);
        p.add(faq, 1, 1);
        p.add(settings,2,1);
        p.add(editor, 0, 2);
        p.add(email,1,2);
        
        account.setId("account");
        analytics.setId("analytics");
        reviews.setId("reviews");
        faq.setId("faq");
        settings.setId("settings");
        schedule.setId("schedule");
        editor.setId("editor");
        email.setId("email");
    }
    
}
