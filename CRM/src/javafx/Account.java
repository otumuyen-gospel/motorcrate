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
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import models.AccountModel;
import models.Accounts;

/**
 *
 * @author Gospel system
 */
public class Account extends Stage{
    Scene scene;
    TextField search = new TextField("enter a search keyword");
    BorderPane root = new BorderPane();
    Accounts []accounts;
    public Account(Window owner){
        this.setTitle("View Customers Details");
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        scene = new Scene(root);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        root.setId("root");
        search.prefWidthProperty().bind(scene.widthProperty().divide(2));
        HBox searchBox = new HBox();
        searchBox.setId("search");
        searchBox.getChildren().add(search);
        searchBox.setAlignment(Pos.CENTER);
        root.setTop(searchBox);
        View customersView = new View(this);
        customersView.fillWidthProperty();
        root.setCenter(customersView);
        search.setOnMouseClicked(e->{search.clear();});
        search.setOnKeyReleased(e->{customersView.search(search.getText(),owner);});
        Rectangle2D fullWindow = Screen.getPrimary().getVisualBounds();
        this.setMinHeight(700);
        this.setHeight(700);
        this.setMaxHeight(700);
        this.setMinWidth(fullWindow.getWidth());
        this.setMaxWidth(fullWindow.getWidth());
        HBox bottom = new HBox();
        Label author = new Label("Motor Crate Gift Box - AmbiSmart Enterprises");
        author.setId("author");
        bottom.setId("bottom");
        bottom.getChildren().add(author);
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
    }
    
    class View extends VBox{
        TableView<AccountModel> table = new TableView<>();
        HBox buttons = new HBox(5);
        Button refresh = new Button("Refresh Database");
        Button detail = new Button("View Selected Row");
        Button invoice = new Button("Generate Invoice");
        Button action = new Button("Customer Actions");
        Button close = new Button("Close User Account");
        public View(Window owner){
            Tooltip tool = new Tooltip("select a row and click button to see in detail");
            tool.setStyle("-fx-background-color:#eee;-fx-font-size:20px;-fx-text-fill:#333;");
            detail.setTooltip(tool);
            detail.setPrefWidth(250);
            Tooltip tip = new Tooltip("Generate Invoice As PDF");
            tip.setStyle("-fx-background-color:#eee;-fx-font-size:20px;-fx-text-fill:#333;");
            invoice.setTooltip(tip);
            invoice.setPrefWidth(250);
            refresh.setPrefWidth(250);
            action.setPrefWidth(250);
            close.setPrefWidth(250);
            detail.setId("button");
            invoice.setId("button");
            refresh.setId("button");
            action.setId("button");
            close.setId("close");
            buttons.getChildren().addAll(detail,refresh, invoice,action,close);
            buttons.setPadding(new Insets(30,0,0,0));
            buttons.setAlignment(Pos.CENTER);
            table.setTableMenuButtonVisible(true);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            this.setAlignment(Pos.CENTER);
            
            this.getChildren().addAll(table,buttons);
            this.plans(owner);
            invoice.setOnAction(e->{new Invoice(owner).showAndWait();});
            refresh.setOnAction(e->{this.populateTable(owner);});
            detail.setOnAction(e->{this.detail(owner);});
            close.setOnAction(e->{this.close(owner);});
            action.setOnAction(e->{new Action(owner).showAndWait();});
        }
        
        void search(String search, Window owner){
            try{
                //clear table first before populating
                table.getItems().clear();
                String url = Login.prop.getProperty("url")+"/search/"+search;
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
                accounts = new Gson().fromJson( reader, Accounts[].class );
                for(int i = 0; i < accounts.length; i++){
                   String id = String.valueOf(accounts[i].getId());
                   String firstname = accounts[i].getFirstName();
                   String lastname = accounts[i].getLastName();
                   String email = accounts[i].getEmail();
                   String sub = accounts[i].getSubscription();
                   String shippingname = accounts[i].getShippingName();
                   String country = accounts[i].getCountry();
                   String state = accounts[i].getState();
                   String city = accounts[i].getCity();
                   String zip = accounts[i].getZip();
                   String street = accounts[i].getStreet();
                   String landmark = accounts[i].getLandmark();
                   String duration = accounts[i].getDuration();
                   double cost = accounts[i].getTotalCost();
                   AccountModel model = new AccountModel(id,firstname,lastname,email,sub,shippingname,country,
                   state,city,zip,street,landmark,duration,cost);
                    table.getItems().add(model);
                   
                }
                
            }catch(Exception e){
                this.populateTable(owner);
            }
           
        }
        void detail(Window owner){
            Stage stage = new Stage();
            Scene scene;
            BorderPane root = new BorderPane();
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            root.setId("root");
            stage.setResizable(false);
            GridPane p = new GridPane();
            root.setCenter(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(15);
            p.setVgap(5);
            
            TableSelectionModel<AccountModel> tm = table.getSelectionModel();
            if(tm.isEmpty()){
                new MessageBox().message(owner, "you have not selected any row", 1);
                return;
            }
            ObservableList<AccountModel> selected = tm.getSelectedItems();
            String[]keys = {"Id","First Name","Last Name","Email","Subscription",
            "Shipping Name","Country","State","City","Zip","Street","Landmark","Duration","Cost"};
            
            String[]values = {
                selected.get(0).getId(),selected.get(0).getFirstName(),
                selected.get(0).getLastName(),selected.get(0).getEmail(),selected.get(0).getSubscription(),
                selected.get(0).getShippingName(),selected.get(0).getCountry(),
                selected.get(0).getState(),selected.get(0).getCity(),
                selected.get(0).getZip(),selected.get(0).getStreet(),
                selected.get(0).getLandmark(),selected.get(0).getDuration(),String.valueOf(selected.get(0).getCost()),
                
            };
            int row = 0;
            int col = 0;
            for(int i = 0; i < keys.length; i++){
                Label columnLabel1 = new Label(keys[i]+":");
                Label columnLabel2 = new Label(values[i]);
                columnLabel1.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");
                columnLabel2.setStyle("-fx-font-size:15px; -fx-font-weight:bold;");
                p.add(columnLabel1, col, row);
                col++;
                p.add(columnLabel2, col, row);
                col = 0;
                row++;
            }
            
            stage.showAndWait();
        }
        void close(Window owner){
            TableSelectionModel<AccountModel> tm = table.getSelectionModel();
            if(tm.isEmpty()){
                new MessageBox().message(owner, "you have not selected any row", 1);
                return;
            }
            ObservableList<AccountModel> selected = tm.getSelectedItems();
            
            //get email and carry out delete operation
            String email = selected.get(0).getEmail();
            try{
                String url = Login.prop.getProperty("url")+"/close/"+email;
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
                TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                new MessageBox().message(owner, message.getMessage(), 1);
                this.populateTable(owner);
            }catch(Exception e){
                new MessageBox().message(owner, "unknow error occured while processing script", 0);
            }
           
        }
        public TableView<AccountModel> plans(Window owner){
            TableColumn<AccountModel, String>  idColumn = new TableColumn<>("Id"); 
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            TableColumn<AccountModel, String>  firstNameColumn = new TableColumn<>("FirstName"); 
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            
            TableColumn<AccountModel, String>  lastNameColumn = new TableColumn<>("LastName"); 
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            
            TableColumn<AccountModel, String>  emailColumn = new TableColumn<>("Email"); 
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            
            TableColumn<AccountModel, String>  subColumn = new TableColumn<>("Subscription"); 
            subColumn.setCellValueFactory(new PropertyValueFactory<>("subscription"));
            
            TableColumn<AccountModel, String>  durationColumn = new TableColumn<>("Duration"); 
            durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
            
            TableColumn<AccountModel, Double>  costColumn = new TableColumn<>("TotalCost"); 
            costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
            
            TableColumn<AccountModel, String>  shippingNameColumn = new TableColumn<>("ShippingName"); 
            shippingNameColumn.setCellValueFactory(new PropertyValueFactory<>("shippingName"));
            TableColumn<AccountModel, String>  countryColumn = new TableColumn<>("Country"); 
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            TableColumn<AccountModel, String>  stateColumn = new TableColumn<>("State"); 
            stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
            TableColumn<AccountModel, String>  cityColumn = new TableColumn<>("City"); 
            cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
            TableColumn<AccountModel, String>  zipColumn = new TableColumn<>("Zip"); 
            zipColumn.setCellValueFactory(new PropertyValueFactory<>("Zip"));
            TableColumn<AccountModel, String>  streetColumn = new TableColumn<>("Street"); 
            streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
            TableColumn<AccountModel, String>  landmarkColumn = new TableColumn<>("Landmark"); 
            landmarkColumn.setCellValueFactory(new PropertyValueFactory<>("landmark"));
            
            
            
            table.getColumns().addAll(idColumn,firstNameColumn,lastNameColumn,emailColumn,subColumn,
                    shippingNameColumn,countryColumn,stateColumn,cityColumn,zipColumn,streetColumn,landmarkColumn,
                    durationColumn,costColumn);
            
            this.populateTable(owner);
            
            return table;
            
        }
  
        void populateTable(Window owner){
            try{
                //clear table first before populating
                table.getItems().clear();
                String url = Login.prop.getProperty("url")+"/account/view";
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
                accounts = new Gson().fromJson( reader, Accounts[].class );
                for(int i = 0; i < accounts.length; i++){
                   String id = String.valueOf(accounts[i].getId());
                   String firstname = accounts[i].getFirstName();
                   String lastname = accounts[i].getLastName();
                   String email = accounts[i].getEmail();
                   String sub = accounts[i].getSubscription();
                   String shippingname = accounts[i].getShippingName();
                   String country = accounts[i].getCountry();
                   String state = accounts[i].getState();
                   String city = accounts[i].getCity();
                   String zip = accounts[i].getZip();
                   String street = accounts[i].getStreet();
                   String landmark = accounts[i].getLandmark();
                   String duration = accounts[i].getDuration();
                   double cost = accounts[i].getTotalCost();
                   AccountModel model = new AccountModel(id,firstname,lastname,email,sub,shippingname,country,
                   state,city,zip,street,landmark,duration,cost);
                    table.getItems().add(model);
                    
                }
            }catch(Exception e){
                new MessageBox().message(owner, "Error ocurred trying to fetch accounts", 0);
            }
            
            
        }
        
        
    }
    
}
