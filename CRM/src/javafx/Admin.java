/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import models.Plan;
import models.Profile;
import models.UrlEntities;

/**
 *
 * @author Gospel system
 */
public class Admin extends Stage{
    Scene scene;
    TabPane pane = new TabPane();
    BorderPane root = new BorderPane();
    public Admin(Window owner){
        this.setTitle("Admin Operations");
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.DECORATED);
        scene = new Scene(root, 1000, 500);
        scene.getStylesheets().add("css/profile.css");
        this.setScene(scene);
        root.setId("root");
        this.setMinHeight(500);
        this.setMaxHeight(500);
        this.setMinWidth(1000);
        this.setMaxWidth(1000);
        pane.getTabs().addAll(new StripeKeys(owner),new CreatePlan(owner),new ViewPlan(owner), new View(this),
                new CreateCoupon(owner));
        root.setTop(pane);
        HBox bottom = new HBox();
        Label author = new Label("Motor Crate Gift Box - AmbiSmart Enterprises");
        author.setId("author");
        bottom.setId("bottom");
        bottom.getChildren().add(author);
        bottom.setAlignment(Pos.CENTER);
        root.setBottom(bottom);
    }
    
    
    class View extends Tab{
        GridPane p = new GridPane();
        TextField fName = new TextField("");
        TextField LName = new TextField("");
        TextField email = new TextField("");
        PasswordField pass = new PasswordField();
        Label fLabel = new Label("First Name");
        Label LLabel = new Label("Last Name");
        Label emailLabel = new Label("Email");
        Label pLabel = new Label("Password");
        Button update = new Button("Update");
        int id;
        public View(Window owner){
            pass.setText("");
            this.setText("Your Administrator Profile");
            this.setContent(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(30);
            
            p.add(fLabel, 0, 0);
            p.add(fName, 1, 0);
            p.add(LLabel, 0, 1);
            p.add(LName, 1, 1);
            p.add(emailLabel, 0, 2);
            p.add(email, 1, 2);
            p.add(pLabel, 0, 3);
            p.add(pass, 1, 3);
            p.add(update, 1, 4);
            update.setId("button");
            
            this.showProfile(owner);
            update.setOnAction(e->{this.updateProfile(owner);});
        }
        
        public void updateProfile(Window owner){
            try{
                
                String url = Login.prop.getProperty("url")+"/profile/"+this.id+"/"+fName.getText()+"/"+LName.getText()+"/"+
                        email.getText()+"/"+pass.getText();
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                new MessageBox().message(owner, message.getMessage(), 1);
                }catch(Exception e){
                    new MessageBox().message(owner, "Error ocurred trying to update profile", 0);
                }
        }
        
        public void showProfile(Window owner){
            try{
                
                String url = Login.prop.getProperty("url")+"/profile/fetch";
                InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                Profile[]profile = new Gson().fromJson( reader, Profile[].class );
                Profile prof = profile[0];
                this.id = prof.getId();
                fName.setText(prof.getFirstName());
                LName.setText(prof.getLastName());
                email.setText(prof.getEmail());
                pass.setText(prof.getPassword());
                
                }catch(Exception e){
                    
                    Label l = new Label("Error trying to Fetch profile");
                    l.setStyle("-fx-text-fill:red;");
                    p.add(l, 0, 5,2,1);
                    
                }
        }
        
    }
    
    class CreateCoupon extends Tab{
        GridPane p = new GridPane();
        TextField code = new TextField();
        TextField discount = new TextField();
        Button b = new Button("Save");
        Button a = new Button("Generate");
        Button c = new Button("activate");
        Button d = new Button("deactivate");
        String Id;
        public CreateCoupon(Window owner){
            this.setText("Coupon Code/Costing");
            this.setContent(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(10);
            
            p.add(new Label("Coupon Code"), 0, 0);
            p.add(code, 1, 0);
            p.add(new Label("Discount Price"), 0, 1);
            p.add(discount, 1, 1);
            a.setPrefWidth(130);
            a.setOnAction(e->{code.setText(this.generate());});
            p.add(a, 1, 2);
            b.setPrefWidth(130);
            p.add(b, 1, 3);
            c.setPrefWidth(130);
            p.add(c, 1, 4);
            d.setPrefWidth(130);
            p.add(d, 1, 5);
            a.setId("button");
            b.setId("button");
            c.setId("button");
            d.setId("button");
            Label guide = new Label("click 'generate button' to generate new"
                    + " coupon code and click 'save button' to update "
                    + "code and cost data. \nClick 'activate' or 'deactivate' to start"
                    + " or stop a coupon section respectively");
            guide.setWrapText(true);
            p.add(guide, 0, 6, 2,1);
            this.fetch(owner);
            b.setOnAction(e->{
                  try{
                
                    String url = Login.prop.getProperty("url")+"/coupon/"+Id+"/"+code.getText()+
                            "/"+discount.getText();
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                    new MessageBox().message(owner, message.getMessage(), 1);
                }catch(Exception ex){
                    new MessageBox().message(owner, "Error ocurred trying to update table", 0);
                    ex.printStackTrace();
                }
                
            });
            c.setOnAction(e->{
                  try{
                
                    String url = Login.prop.getProperty("url")+"/activate/activated";
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                    new MessageBox().message(owner, message.getMessage(), 1);
                }catch(Exception ex){
                    new MessageBox().message(owner, "Error ocurred trying to activate coupon", 0);
                    ex.printStackTrace();
                }
                
            });
            d.setOnAction(e->{
                  try{
                
                    String url = Login.prop.getProperty("url")+"/activate/deactivated";
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                    new MessageBox().message(owner, message.getMessage(), 1);
                }catch(Exception ex){
                    new MessageBox().message(owner, "Error ocurred trying to deactivate coupon", 0);
                    ex.printStackTrace();
                }
                
            });
        }
        public String generate(){
            String values = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwsyz";
            String codes = "";
            int range = values.length();
            Random rand = new Random();
            for(int i = 1; i < 16; i++){
                codes += values.charAt(rand.nextInt(range));
            }
            return codes;
            
        }
        public void fetch(Window owner){
            try{
                
                    String url = Login.prop.getProperty("url")+"/coupon/fetch";
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
   
                    Id = message.getMessage()==null ? "0":message.getMessage().split("/")[0];
                    code.setText(message.getMessage()==null ? "0":message.getMessage().split("/")[1]);
                    discount.setText(message.getMessage()==null ? "0":message.getMessage().split("/")[2]);
                    
                }catch(Exception ex){
                    new MessageBox().message(owner, "Error ocurred trying to fetch table", 0);
                }
        }
        
    }
    
    
    class StripeKeys extends Tab{
        GridPane p = new GridPane();
        PasswordField secret = new PasswordField();
        PasswordField publishable = new PasswordField();
        Button b = new Button("Save");
        String Id;
        public StripeKeys(Window owner){
            this.setText("Stripe Api Payment Keys");
            this.setContent(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(30);
            
            p.add(new Label("Secret Key"), 0, 0);
            p.add(secret, 1, 0);
            secret.setPrefSize(200, 40);
            p.add(new Label("Publishable Key"), 0, 1);
            publishable.setPrefSize(200, 40);
            p.add(publishable, 1, 1);
            
            p.add(b, 1, 2);
            b.setPrefHeight(40);
            b.setId("button");
            
            this.fetch(owner);
            b.setOnAction(e->{
                  try{
                
                    String url = Login.prop.getProperty("url")+"/stripeKey/"+Id+"/"+
                            UrlEntities.encode(secret.getText())+
                            "/"+UrlEntities.encode(publishable.getText());
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                    new MessageBox().message(owner, message.getMessage(), 1);
                }catch(Exception ex){
                    new MessageBox().message(owner, "Error ocurred trying to update table", 0);
                    ex.printStackTrace();
                }
                
            });
        }
        
        public void fetch(Window owner){
            try{
                
                    String url = Login.prop.getProperty("url")+"/stripeKey/fetch";
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
   
                    Id = message.getMessage()==null ? "":message.getMessage().split("/")[0];
                    secret.setText(message.getMessage()==null ? "":UrlEntities.decode(message.getMessage().split("/")[1]));
                    publishable.setText(message.getMessage()==null ? "":UrlEntities.decode(message.getMessage().split("/")[2]));
                    
                }catch(Exception ex){
                    new MessageBox().message(owner, "Error ocurred trying to fetch table", 0);
                }
        }
        
    }
    
    class CreatePlan extends Tab{
        GridPane p = new GridPane();
        ComboBox product = new ComboBox();
        ComboBox plan = new ComboBox();
        TextField cost = new TextField();
        TextField shipping = new TextField();
        Button b = new Button("Create Plan");
        String Id;
        public CreatePlan(Window owner){
            this.setText("Create Plan");
            this.setContent(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(20);
            p.setVgap(10);
            
            plan.getItems().addAll("1","3","6","9","12");
            plan.setValue("1");;
            product.getItems().addAll("Motor Crate Gift Box");
            product.setValue("Motor Crate Gift Box");
            p.add(new Label("Product Category"), 0, 0);
            product.setPrefSize(300, 30);
            p.add(product, 1, 0);
            p.add(new Label("Plan(Monthly)"), 0, 1);
            plan.setPrefSize(300, 30);
            p.add(plan, 1, 1);
            p.add(new Label("Plan(Cost)"), 0, 2);
            cost.setPrefSize(300, 30);
            p.add(cost, 1, 2);
            p.add(new Label("Plan(Shipping Cost)"), 0, 3);
            shipping.setPrefSize(300, 30);
            p.add(shipping, 1, 3);
            
            p.add(b, 1, 4);
            b.setId("button");
            b.setOnAction(e->{
                  try{
                
                    String url = Login.prop.getProperty("url")+"/createPlan/"+
                            UrlEntities.encode(product.getValue().toString())+"/"+
                            plan.getValue()+"/"+cost.getText()+"/"+shipping.getText();
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                    new MessageBox().message(owner, message.getMessage(), 1);
                }catch(Exception ex){
                    new MessageBox().message(owner, "Error ocurred trying to create", 0);
                    ex.printStackTrace();
                }
                
            });
        }
        
        
    }
    
    class ViewPlan extends Tab{
        GridPane p = new GridPane();
        public ViewPlan(Window owner){
            this.setText("View All Plan");
            this.setContent(p);
            p.setAlignment(Pos.CENTER);
            p.setId("create");
            p.setHgap(10);
            p.setVgap(10);
            this.load(this,owner);
        }
        public void load(Tab pane, Window owner){
            pane.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event t) {
                    if (pane.isSelected()) {
                        generateView(owner);
                    }
                }
            });
            
        }
        
        public void generateView(Window owner){
            try{
                    p.getChildren().clear();
                    String url = Login.prop.getProperty("url")+"/createPlan";
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    Plan[] plan = new Gson().fromJson( reader, Plan[].class );
                    if(plan.length > 0){
                        int row = 0;
                        for(int i = 0; i < plan.length; i++){
                            Label id = new Label(String.valueOf(plan[i].getId()));
                            id.setId("viewLabel");
                            Label productName = new Label(UrlEntities.decode(plan[i].getProductName()));
                            productName.setId("viewLabel");
                            Label productId = new Label(plan[i].getProductId());
                            productId.setId("viewLabel");
                            Label planName = new Label(plan[i].getPlanName());
                            planName.setId("viewLabel");
                            Label planId = new Label(plan[i].getPlanId());
                            planId.setId("viewLabel");
                            Label cost = new Label("$"+String.valueOf(plan[i].getCost()));
                            cost.setId("viewLabel");
                            Label shipping = new Label("$"+String.valueOf(plan[i].getShipping()));
                            shipping.setId("viewLabel");
                            Button remove = new Button("Remove Plan");
                            remove.setId("button");
                            int ids  = plan[i].getId();
                            String prod_id = plan[i].getProductId();
                            String plan_id = plan[i].getPlanId();
                            remove.setOnAction(e->{
                                this.destroy(owner, ids,prod_id,plan_id);
                            });

                            p.add(id, 0, row);
                            p.add(productName, 1, row);
                            p.add(productId, 2, row);
                            p.add(planName, 3, row);
                            p.add(planId, 4, row);
                            p.add(cost, 5, row);
                            p.add(shipping, 6, row);
                            p.add(remove, 7, row);
                            row++;
                        }
                    }else{
                        Label placeholder = new Label("No Plan To Display");
                        placeholder.setId("holder");
                        
                        p.add(placeholder, 0, 0);
                    }
                    
                }catch(Exception ex){
                    new MessageBox().message(owner, "Unable to generate view for plan", 0);
                }
        }
        
        public void destroy(Window owner, int id,String prod_id,String plan_id){
            try{
                
                    String url = Login.prop.getProperty("url")+"/createPlan/"+id+"/"+prod_id+"/"+
                            plan_id;
                    InputStreamReader reader = new InputStreamReader( new URL( url ).openStream() );

                    TextMessage message = new Gson().fromJson( reader, TextMessage.class );
                    new MessageBox().message(owner, message.getMessage(), 1);
                    this.generateView(owner);
                }catch(Exception ex){
                    new MessageBox().message(owner, "Error ocurred trying to create", 0);
                    ex.printStackTrace();
                }
        }
        
    }
    
    
    
    
}
