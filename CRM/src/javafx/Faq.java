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
import models.FaqModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import models.AddLinkToEditor;
import models.Faqs;
import models.UrlEntities;

/**
 *
 * @author Gospel system
 */
public class Faq extends Stage{
    Scene scene;
    TabPane pane = new TabPane();
    BorderPane root = new BorderPane();
    public Faq(Window owner){
        this.setTitle("FAQ");
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
        TextField question = new TextField();
        HTMLEditor answer = new HTMLEditor();
        Button b = new Button("Create");
        public Create(Window owner){
            this.setText("Create New FAQ");
            this.setContent(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(40);
            
            p.add(new Label("Question"), 0, 0);
            p.add(question, 1, 0);
            answer.setPrefHeight(250);
            p.add(new Label("Answer"), 0, 1);
            p.add(answer, 1, 1);
            b.setId("button");
            p.add(b, 1, 2);
            new AddLinkToEditor(answer);
            b.setOnAction(e->{
                  this.createFaq(owner);
            });
        }
        
        public void createFaq(Window owner){
            try{
                String parameter1 = UrlEntities.encode(question.getText());
                String parameter2 = UrlEntities.encode(UrlEntities.getHtmlBodyContent(answer.getHtmlText()));
                String url = Login.prop.getProperty("url")+"/faq/"+parameter1+"/"+parameter2;
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                new MessageBox().message(owner, message.getMessage(), 1);
            }catch(Exception e){
                new MessageBox().message(owner, "Error ocurred trying to create new faq", 0);
            }
        }
    
        
    }
    
    class View extends Tab{
        VBox pane = new VBox();
        TableView<FaqModel> table = new TableView<>();
        HBox buttons = new HBox(5);
        Button update = new Button("Update");
        Button delete = new Button("Delete");
        Button refresh = new Button("Refresh");
        public View(Window owner){
            this.setText("View All Frequently Asked Question");
            this.setContent(pane);
            update.setId("button");
            delete.setId("button");
            refresh.setId("button");
            buttons.getChildren().addAll(update, delete, refresh);
            buttons.setPadding(new Insets(30,0,0,0));
            buttons.setAlignment(Pos.CENTER);
            table.setTableMenuButtonVisible(true);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            pane.getChildren().addAll(table,buttons);
            this.faq(owner);
            
            delete.setOnAction(e->{this.delete(owner);});
            update.setOnAction(e->{this.updateFaq(owner);});
            refresh.setOnAction(e->{this.populateTable(owner);});
        }
        public TableView<FaqModel> faq(Window owner){
            TableColumn<FaqModel, String>  idColumn = new TableColumn<>("Id"); 
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            TableColumn<FaqModel, String>  questionColumn = new TableColumn<>("Question"); 
            questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
            
            TableColumn<FaqModel, String>  answerColumn = new TableColumn<>("Answer"); 
            answerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));
            
            table.getColumns().addAll(idColumn,questionColumn,answerColumn);
            
            this.populateTable(owner);
            
            return table;
            
        }
        void populateTable(Window owner){
            try{
                //clear table first before populating
                table.getItems().clear();
                String url = Login.prop.getProperty("url")+"/faq/fetch";
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
                Faqs[] f = new Gson().fromJson( reader, Faqs[].class );
                for(int i = 0; i < f.length; i++){
                    String id = String.valueOf(f[i].getId());
                    String ques = UrlEntities.decode(f[i].getQuestion());
                    String ans = UrlEntities.decode(f[i].getAnswer());
                    FaqModel faqmodel = new FaqModel(id,ques,ans);
                    table.getItems().add(faqmodel);
                }
            }catch(Exception e){
                new MessageBox().message(owner, "Error ocurred trying to fetch faqs", 0);
                e.printStackTrace();
            }
            
            
        }
        
        void delete(Window owner){
            TableSelectionModel<FaqModel> tm = table.getSelectionModel();
            if(tm.isEmpty()){
                new MessageBox().message(owner, "you have not selected any row", 1);
                return;
            }
            ObservableList<FaqModel> selected = tm.getSelectedItems();
            
            
            //get id and carry out delete operation
            String Id = selected.get(0).getId();
            String databaseTable = "faq";
            new Delete().dialog(Id, owner, databaseTable);
             
            //repopulate or refresh table from server
            this.populateTable(owner);
            
        }
        
        void updateFaq(Window owner){
            Stage stage = new Stage();
            Scene scene;
            BorderPane root = new BorderPane();
            stage.setTitle("Update FAQ");
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
            TextField question = new TextField();
            HTMLEditor answer = new HTMLEditor();
            answer.setPrefHeight(250);
            Button change = new Button("Commit Changes");
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(40);
            
            p.add(new Label("Question"), 0, 0);
            p.add(question, 1, 0);
            p.add(new Label("Answer"), 0, 1);
            p.add(answer, 1, 1);
            p.add(change, 1, 2);
            new AddLinkToEditor(answer);
            TableSelectionModel<FaqModel> tm = table.getSelectionModel();
            if(tm.isEmpty()){
                new MessageBox().message(owner, "you have not selected any row", 1);
                return;
            }
            ObservableList<FaqModel> selected = tm.getSelectedItems();
            
            String Id = selected.get(0).getId();
            
            answer.setHtmlText(selected.get(0).getAnswer());
            
            question.setText(selected.get(0).getQuestion());
            change.setOnAction(e->{
                try{
                    String parameter1 = UrlEntities.encode(question.getText());
                    String parameter2 = UrlEntities.encode(UrlEntities.getHtmlBodyContent(answer.getHtmlText()));
                    
                    String url = Login.prop.getProperty("url")+"/faq/"+Id+"/"+parameter1+
                                    "/"+parameter2;
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                    stage.close();
                    new MessageBox().message(owner, message.getMessage(), 1);
                    this.populateTable(owner);
                }catch(Exception ex){
                    stage.close();
                    new MessageBox().message(owner, "Error ocurred trying to update faq", 0);
                }
                
               
            });
            stage.showAndWait();
        }
        
       
    }
    
}
