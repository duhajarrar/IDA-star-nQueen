package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class notController {

    @FXML
    private AnchorPane ptry;

    @FXML
    private Button tryB;

    @FXML
    private Label lb;

    @FXML
    private ImageView img;

    @FXML
    void tryBTN(ActionEvent event) throws IOException {
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("show.fxml"));
    	ptry.getChildren().setAll(pane);
    }

}