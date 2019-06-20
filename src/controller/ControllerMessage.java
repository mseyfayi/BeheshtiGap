package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import module.Message;
import util.Strings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMessage implements Initializable {
    private Message message;
    @FXML
    BorderPane pane;
    @FXML
    private Label user;
    @FXML
    private Label date;

    public void setMessage(Message message) throws IOException {
        this.message = message;
        user.setText(message.getSender().getUsername());
        date.setText(Strings.makeTimeSimple(message.getDate()));
        message.setPane();
        pane.setCenter(message.getPane());
    }

    public void userClicked(MouseEvent mouseEvent) {
        //todo loading personal pane
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
