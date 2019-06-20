package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import module.Mappable;
import util.Setting;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSetting extends Controller{
    @FXML
    private BorderPane pane;
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    void changeImage(ActionEvent event) {
        MainClient.setScene(getClass().getResource("../view/ChangeImage.fxml"));
    }

    @FXML
    void changePass(ActionEvent event) {
        MainClient.setScene(getClass().getResource("../view/ChangePassword.fxml"));
    }

    @FXML
    void changeUser(ActionEvent event) throws IOException {
        MainClient.setScene(getClass().getResource("../view/ChangeUsername.fxml"));
    }

    @FXML
    void deleteImage(ActionEvent event) {
        Setting.getInstance().getThisUser().setImage(new Image("icons/default.jpg"));
    }

    @FXML
    void logout(ActionEvent event) {
        Setting.getInstance().logout();
        MainClient.setScene(getClass().getResource("../view/Login.fxml"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTitle(pane, false);
        image.setImage(Setting.getInstance().getThisUser().getImage());
        name.setText(Setting.getInstance().getThisUser().getUsername());
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        MainClient.setScene(getClass().getResource("../view/MainPane.fxml"));
    }
}
