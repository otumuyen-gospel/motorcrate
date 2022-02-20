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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import models.Contacts;
import models.LargePost;
import models.UrlEntities;

/**
 *
 * @author Gospel system
 */
public class Editor extends Stage{
    Scene scene;
    TabPane pane = new TabPane();
    BorderPane root = new BorderPane();
    public Editor(Window owner){
        this.setTitle("Admin Operations");
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        scene = new Scene(root, 550, 550);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        root.setId("root");
        this.setMinHeight(550);
        this.setMaxHeight(550);
        this.setMinWidth(550);
        this.setMaxWidth(550);
        pane.getTabs().addAll(new Contact(this), new Other(this));
        root.setTop(pane);
        HBox bottom = new HBox();
        Label author = new Label("Motor Crate Gift Box - AmbiSmart Enterprises");
        author.setId("author");
        bottom.setId("bottom");
        bottom.getChildren().add(author);
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
    }
    
    
    class Contact extends Tab{
        GridPane p = new GridPane();
        TextField email = new TextField();
        TextField facebook = new TextField();
        TextField instagram = new TextField();
        TextField twitter = new TextField();
        PasswordField youtube = new PasswordField();
        PasswordField key = new PasswordField();
        TextField contact = new TextField();
        TextField telephone = new TextField();
        Button update = new Button("Update Contact");
        public Contact(Window owner){
            this.setText("Edit All Contacts");
            this.setContent(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(10);
            p.add(new Label("Email Address"), 0, 0);
            p.add(email, 1, 0);
            p.add(new Label("Facebook Address"), 0, 1);
            p.add(facebook, 1, 1);
            p.add(new Label("Instagram Address"), 0, 2);
            p.add(instagram, 1, 2);
            p.add(new Label("Twitter Address"), 0, 3);
            p.add(twitter, 1, 3);
            p.add(new Label("Youtube Channel Id"), 0, 4);
            p.add(youtube, 1, 4);
            p.add(new Label("Youtube Data Api Key"), 0, 5);
            p.add(key, 1, 5);
            p.add(new Label("Contact Address"), 0, 6);
            p.add(contact, 1, 6);
            p.add(new Label("Telephone"), 0, 7);
            p.add(telephone, 1, 7);
            update.setId("button");
            p.add(update, 1, 8);
            this.showContacts();
            update.setOnAction(e->{this.updateContacts(owner);});
        }
        
        public void updateContacts(Window owner){
            try{
                String para1 = UrlEntities.encode(email.getText());
                String para2 = UrlEntities.encode(facebook.getText());
                String para3 = UrlEntities.encode(instagram.getText());
                String para4 = UrlEntities.encode(twitter.getText());
                String para5 = UrlEntities.encode(youtube.getText());
                String para6 = UrlEntities.encode(key.getText());
                String para7 = UrlEntities.encode(contact.getText());
                String para8 = UrlEntities.encode(telephone.getText());
                
                
                String url = Login.prop.getProperty("url")+"/editor/"+para1+"/"+para2+"/"+para3+"/"+para4+
                        "/"+para5+"/"+para6+"/"+para7+"/"+para8;
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
                TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                new MessageBox().message(owner, message.getMessage(), 1);
                
                }catch(Exception e){
                    new MessageBox().message(owner, "Error ocurred trying to update contacts", 0);
                }
            
        }
        public void showContacts(){
            try{
                String url = Login.prop.getProperty("url")+"/editor/fetch";
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
                Contacts contacts = new Gson().fromJson( reader, Contacts.class );
                facebook.setText(UrlEntities.decode(contacts.getFacebook()));
                email.setText(UrlEntities.decode(contacts.getEmail()));
                instagram.setText(UrlEntities.decode(contacts.getInstagram()));
                youtube.setText(UrlEntities.decode(contacts.getYoutube()));
                key.setText(UrlEntities.decode(contacts.getKey()));
                twitter.setText(UrlEntities.decode(contacts.getTwitter()));
                contact.setText(UrlEntities.decode(contacts.getAddress()));
                telephone.setText(UrlEntities.decode(contacts.getTelephone()));
                }catch(Exception e){
                    Label l = new Label("Error trying to Fetch contact data");
                    l.setWrapText(true);
                    l.setStyle("-fx-text-fill:red;");
                    p.add(l, 0, 8);
                    
                }
        }
        
        
    }
    
    class Other extends Tab{
        GridPane p = new GridPane();
        Button terms = new Button("Edit Terms");
        Button privacy = new Button("Edit Privacy");
        Button more = new Button("Edit Learn More");
        public Other(Window owner){
            this.setText("Edit other website contents");
            this.setContent(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(20);
            privacy.setOnAction(e->{new Privacy(owner).showAndWait();});
            more.setOnAction(e->{new More(owner).showAndWait();});
            terms.setOnAction(e->{new Terms(owner).showAndWait();});
            terms.setId("button");
            more.setId("button");
            privacy.setId("button");
            terms.setPrefWidth(250);
            privacy.setPrefWidth(250);
            more.setPrefWidth(250);
            p.add(terms, 0, 0);
            p.add(more, 0, 1);
            p.add(privacy, 0, 2);
            
        }
        
        
    }
    
    
}

class Terms extends Stage{
    GridPane p = new GridPane();
    Button b = new Button("Update Terms");
    HTMLEditor editor = new HTMLEditor();
    public Terms(Window owner){
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(p, 1200, 600);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        p.setId("root");
        this.setMinHeight(600);
        this.setMaxHeight(600);
        this.setMinWidth(1200);
        this.setMaxWidth(1200);
        p.setAlignment(Pos.CENTER);
        p.add(b, 0, 0);
        b.setId("button");
        p.add(editor, 0, 1);
        new AddLinkToEditor(editor);
        editor.prefWidthProperty().bind(this.widthProperty());
        this.showTerms(owner);
        b.setOnAction(e->{this.updateTerms(owner);});
    }
    
    
    public void updateTerms(Window owner){
        String webpage = Login.prop.getProperty("url").replace("/webresources", "/jsp/LargePost.jsp");
         String value = UrlEntities.encode(UrlEntities.getHtmlBodyContent(editor.getHtmlText()));
         String parameter = "type=terms&value="+value;
         LargePost postTerms = new LargePost(webpage,parameter);
         if(postTerms.Connect().equalsIgnoreCase("posted")){
             new MessageBox().message(owner, "Terms & Conditions updated Successfully", 1);
         }else{
             new MessageBox().message(owner, "Error ocurred trying to update Terms & Conditions", 0);
         }
       
    }
    public void showTerms(Window owner){
        try{
            String url = Login.prop.getProperty("url")+"/terms/fetch";
            InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
            TextMessage message = new Gson().fromJson( reader, TextMessage.class );
            editor.setHtmlText(UrlEntities.decode(message.getMessage()));

            }catch(Exception e){
                 new MessageBox().message(owner, "Error ocurred trying fetch Terms & Conditions", 0);

            }
        
        
    }
        
        
    
}

class More extends Stage{
    GridPane p = new GridPane();
    Button b = new Button("Update Learn More");
    HTMLEditor editor = new HTMLEditor();
    public More(Window owner){
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(p, 1200, 600);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        p.setId("root");
        this.setMinHeight(600);
        this.setMaxHeight(600);
        this.setMinWidth(1200);
        this.setMaxWidth(1200);
        p.setAlignment(Pos.CENTER);
        p.add(b, 0, 0);
        b.setId("button");
           
        p.add(editor, 0, 1);
        new AddLinkToEditor(editor);
        editor.prefWidthProperty().bind(this.widthProperty());
        this.showMore(owner);
        b.setOnAction(e->{this.updateMore(owner);});
        
    }
    public void updateMore(Window owner){
        String webpage = Login.prop.getProperty("url").replace("/webresources", "/jsp/LargePost.jsp");
         String value = UrlEntities.encode(UrlEntities.getHtmlBodyContent(editor.getHtmlText()));
         String parameter = "type=more&value="+value;
         LargePost postTerms = new LargePost(webpage,parameter);
         if(postTerms.Connect().equalsIgnoreCase("posted")){
             new MessageBox().message(owner, "Learn more updated Successfully", 1);
         }else{
             new MessageBox().message(owner, "Error ocurred trying to update Learn more", 0);
         }
       
    }
    public void showMore(Window owner){
        try{
            String url = Login.prop.getProperty("url")+"/more/fetch";
            InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
            TextMessage message = new Gson().fromJson( reader, TextMessage.class );
            editor.setHtmlText(UrlEntities.decode(message.getMessage()));

            }catch(Exception e){
                 new MessageBox().message(owner, "Error ocurred trying fetch Learn More Text", 0);

            }
        
        
    }
    
}

class Privacy extends Stage{
    GridPane p = new GridPane();
    Button b = new Button("Update Privacy");
    HTMLEditor editor = new HTMLEditor();
    public Privacy(Window owner){
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(p, 1200, 600);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        p.setId("root");
        this.setMinHeight(600);
        this.setMaxHeight(600);
        this.setMinWidth(1200);
        this.setMaxWidth(1200);
        p.setAlignment(Pos.CENTER);
        p.add(b, 0, 0);
        b.setId("button");
         
        p.add(editor, 0, 1);
        new AddLinkToEditor(editor);
        editor.prefWidthProperty().bind(this.widthProperty());
        this.showPrivacy(owner);
        b.setOnAction(e->{this.updatePrivacy(owner);});
        
    }
    public void updatePrivacy(Window owner){
        String webpage = Login.prop.getProperty("url").replace("/webresources", "/jsp/LargePost.jsp");
         String value = UrlEntities.encode(UrlEntities.getHtmlBodyContent(editor.getHtmlText()));
         String parameter = "type=privacy&value="+value;
         LargePost postTerms = new LargePost(webpage,parameter);
         if(postTerms.Connect().equalsIgnoreCase("posted")){
             new MessageBox().message(owner, "Privacy policy updated Successfully", 1);
         }else{
             new MessageBox().message(owner, "Error ocurred trying to update privacy policy", 0);
         }
       
    }
    public void showPrivacy(Window owner){
        try{
            String url = Login.prop.getProperty("url")+"/privacy/fetch";
            InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );
            TextMessage message = new Gson().fromJson( reader, TextMessage.class );
            editor.setHtmlText(UrlEntities.decode(message.getMessage()));

            }catch(Exception e){
                 new MessageBox().message(owner, "Error ocurred trying fetch Privacy", 0);

            }
        
        
    }
}