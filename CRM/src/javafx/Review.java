/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import models.ReviewModel;
import models.Reviews;
import models.UrlEntities;

/**
 *
 * @author Gospel system
 */
public class Review extends Stage{
    Scene scene;
    TabPane pane = new TabPane();
    BorderPane root = new BorderPane();
    public Review(Window owner){
        this.setTitle("Customer Testimonial / Reviews");
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        scene = new Scene(root, 1200, 600);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        root.setId("root");
        this.centerOnScreen();
        this.setMinHeight(600);
        this.setMaxHeight(600);
        this.setMinWidth(1200);
        this.setMaxWidth(1200);
        pane.getTabs().addAll(new Create(this),new View(this));
        root.setTop(pane);
        HBox bottom = new HBox();
        Label author = new Label("Motor Crate Gift Box - AmbiSmart Enterprises");
        author.setId("author");
        bottom.setId("bottom");
        bottom.getChildren().add(author);
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
    }
    
    class Create extends Tab{
        GridPane p = new GridPane();
        TextField name = new TextField();
        ComboBox rating = new ComboBox();
        TextArea review = new TextArea();
        Button b = new Button("Add New Review");
        public Create(Window owner){
            this.setText("Add New Review");
            this.setContent(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(20);
            
            p.add(new Label("Customer"), 0, 0);
            p.add(name, 1, 0);
            p.add(new Label("Review Rating"), 0, 1);
            p.add(rating, 1, 1);
            rating.getItems().addAll("1 star","2 star", "3 star", "4 star", "5 star");
            rating.setValue("rate this review");
            p.add(new Label("Customer Review"), 0, 2);
            p.add(review, 1, 2);
            b.setId("button");
            p.add(b, 1, 3);
            
            b.setOnAction(e->{
                  this.createReview(owner);
            });
        }
        
        public void createReview(Window owner){
            try{
                String parameter1 = UrlEntities.encode(name.getText());
                String parameter2 = UrlEntities.encode(rating.getValue().toString());
                String parameter3 = UrlEntities.encode(review.getText());
                String url = Login.prop.getProperty("url")+"/review/"+parameter1+"/"+parameter2+
                        "/"+parameter3;
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
                
                TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                new MessageBox().message(owner, message.getMessage(), 1);
            }catch(Exception e){
                new MessageBox().message(owner, "Error ocurred trying to create Review", 0);
                e.printStackTrace();
            }
        }
    
        
        
    }
    
    class View extends Tab{
        VBox pane = new VBox();
        TableView<ReviewModel> table = new TableView<>();
        HBox buttons = new HBox(5);
        Button update = new Button("Update");
        Button delete = new Button("Delete");
        Button refresh = new Button("Refresh");
        public View(Window owner){
            this.setText("View All Customer Reviews");
            this.setContent(pane);
            update.setId("button");
            delete.setId("button");
            refresh.setId("button");
            buttons.getChildren().addAll(update, delete,refresh);
            buttons.setPadding(new Insets(30,0,0,0));
            buttons.setAlignment(Pos.CENTER);
            table.setTableMenuButtonVisible(true);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            pane.getChildren().addAll(table,buttons);
            this.review(owner);
            
            delete.setOnAction(e->{this.delete(owner);});
            update.setOnAction(e->{this.updateReview(owner);});
            refresh.setOnAction(e->{this.populateTable(owner);});
        }
        public TableView<ReviewModel> review(Window owner){
            TableColumn<ReviewModel, String>  idColumn = new TableColumn<>("Id"); 
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            TableColumn<ReviewModel, String>  nameColumn = new TableColumn<>("Name"); 
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            TableColumn<ReviewModel, String>  ratingColumn = new TableColumn<>("Rating"); 
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
            
            TableColumn<ReviewModel, String>  reviewColumn = new TableColumn<>("Customer Review"); 
            reviewColumn.setCellValueFactory(new PropertyValueFactory<>("review"));
            
            table.getColumns().addAll(idColumn,nameColumn,ratingColumn,reviewColumn);
            
            this.populateTable(owner);
            
            return table;
            
        }
        
        void populateTable(Window owner){
            try{
                //clear table first before populating
                table.getItems().clear();
                String url = Login.prop.getProperty("url")+"/review/fetch";
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
                Reviews[] f = new Gson().fromJson( reader, Reviews[].class );
                for(int i = 0; i < f.length; i++){
                    String id = String.valueOf(f[i].getId());
                    String name = UrlEntities.decode(f[i].getName());
                    String rating = UrlEntities.decode(f[i].getRating());
                    String review = UrlEntities.decode(f[i].getReview());
                    ReviewModel model = new ReviewModel(id,name,rating,review);
                    table.getItems().add(model);
                }
            }catch(Exception e){
                new MessageBox().message(owner, "Error ocurred trying to fetch reviews", 0);
            }
            
        }
        
        void delete(Window owner){
            TableSelectionModel<ReviewModel> tm = table.getSelectionModel();
            if(tm.isEmpty()){
                new MessageBox().message(owner, "you have not selected any row", 1);
                return;
            }
            ObservableList<ReviewModel> selected = tm.getSelectedItems();
            
            
            //get id and carry out delete operation
            String Id = selected.get(0).getId();
            String databaseTable = "review";
            new Delete().dialog(Id, owner, databaseTable);
             
            //repopulate or refresh table from server
            this.populateTable(owner);
            
        }
        
        void updateReview(Window owner){
            Stage stage = new Stage();
            Scene scene;
            BorderPane root = new BorderPane();
            stage.setTitle("Update Review");
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            scene = new Scene(root, 1000, 500);
            scene.getStylesheets().add("css/profile.css");
            stage.setScene(scene);
            root.setId("root");
            stage.setResizable(false);
            GridPane p = new GridPane();
            root.setCenter(p);
            TextField name = new TextField();
            ComboBox rating = new ComboBox();
            TextArea review = new TextArea();
            Button change = new Button("Commit Changes");
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(40);
            
            p.add(new Label("Customer"), 0, 0);
            p.add(name, 1, 0);
            p.add(new Label("Review Rating"), 0, 1);
            rating.getItems().addAll("1 star","2 star", "3 star", "4 star", "5 star");
            p.add(rating, 1, 1);
            p.add(new Label("Customer Review"), 0, 2);
            p.add(review, 1, 2);
            p.add(change, 1, 3);
            
            TableSelectionModel<ReviewModel> tm = table.getSelectionModel();
            if(tm.isEmpty()){
                new MessageBox().message(owner, "you have not selected any row", 1);
                return;
            }
            ObservableList<ReviewModel> selected = tm.getSelectedItems();
            
            String Id = selected.get(0).getId();
            
            name.setText(selected.get(0).getName());
            
            rating.setValue(selected.get(0).getRating());
            
            review.setText(selected.get(0).getReview());
            change.setOnAction(e->{
                try{
                    String parameter1 = UrlEntities.encode(name.getText());
                    String parameter2 = UrlEntities.encode(rating.getValue().toString());
                    String parameter3 = UrlEntities.encode(review.getText());
                    String url = Login.prop.getProperty("url")+"/review/"+Id+"/"+parameter1+"/"+parameter2+
                            "/"+parameter3;
                    
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                    stage.close();
                    new MessageBox().message(owner, message.getMessage(), 1);
                    this.populateTable(owner);
                }catch(Exception ex){
                    stage.close();
                    new MessageBox().message(owner, "Error ocurred trying to update review", 0);
                    ex.printStackTrace();
                }
            });
            stage.showAndWait();
        }
        
       
    }
    
}
