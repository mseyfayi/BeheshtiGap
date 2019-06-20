package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import module.User;
import util.Fields;
import util.Request;
import util.Setting;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerLogin extends Controller {
    @FXML
    private Label alert;
    @FXML
    private JFXTextField fldUsername;
    @FXML
    private JFXPasswordField fldPassword;
    @FXML
    private BorderPane pane;

    @FXML
    void loginClicked(ActionEvent event) throws IOException, ClassNotFoundException {
        if (Fields.notEmpty(fldUsername, fldPassword)) {
            ArrayList<String> usernameAndPassword=new ArrayList<>();
            usernameAndPassword.add(fldUsername.getText());
            usernameAndPassword.add(User.coding(fldPassword.getText()));
            MainClient.mainConnect.send(new Request("login",usernameAndPassword));

            Request backed=MainClient.mainConnect.receive();
            switch (backed.getRequest()) {
                case "succeed":
                    Setting.getInstance().setThisUser((User) backed.getSerializable());
                    MainClient.setScene(getClass().getResource("../view/MainPane.fxml"));
                    break;
                case "username or password is wrong":
                    alert.setText("username or password is wrong");
                    Fields.makeAlertField(fldPassword,fldUsername);
                    break;
            }
        }
    }

    @FXML
    void registerClicked(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/Sign up.fxml"));
        MainClient.setScene(parent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTitle(pane,false);
        FadeTransition transition= new FadeTransition(Duration.millis(1500),pane);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }
}