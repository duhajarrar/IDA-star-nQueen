
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Project1 extends Application {
 Parent pStage;
    @Override
    public void start(Stage stage) throws Exception {
        Parent pStage = FXMLLoader.load(getClass().getResource("show.fxml")); 
        Scene scene = new Scene(pStage);
        
        stage.setScene(scene);
        stage.show();
    }

  
    public static void main(String[] args) {
        launch(args);
    }
    
}
