package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import util.Fields;
import util.Setting;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerChangePassword extends Controller {
    @FXML
    private BorderPane pane;
    @FXML
    public TextField password;

    @FXML
    void backClicked(ActionEvent event) {
        MainClient.setScene(getClass().getResource("../view/Setting.fxml"));
    }

    @FXML
    void changeClicked(ActionEvent event) {
        if (Fields.notEmpty(password)) {
            Setting.getInstance().getThisUser().resetPassword(password.getText());
            MainClient.setScene(getClass().getResource("../view/MainPane.fxml"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTitle(pane,false);
    }
}
