package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAddChat extends Controller{
    @FXML
    private BorderPane pane;

    @FXML
    void channelClicked(ActionEvent event) {
        MainClient.setScene(getClass().getResource("../view/AddChannel.fxml"));
    }

    @FXML
    void groupClicked(ActionEvent event) {

    }

    @FXML
    void privateChatClicked(ActionEvent event) {
        //todo
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTitle(pane,false);
    }

    public void backClicked(ActionEvent actionEvent) {
        MainClient.setScene(getClass().getResource("../view/MainPane.fxml"));
    }
}
