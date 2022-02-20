


import javafx.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {
    Login login = new Login();
    @Override
    public void start(Stage primaryStage) {
        login.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
