package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import module.User;
import util.Fields;
import util.Request;
import util.Setting;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerSignUp extends Controller{
    @FXML
    private Label alert;
    @FXML
    private VBox box;
    @FXML
    private JFXTextField fldUsername;
    @FXML
    private JFXPasswordField fldPassword;
    @FXML
    private JFXButton btnSignup;
    @FXML
    private ImageView imgIcon;
    @FXML
    private BorderPane pane;

    @FXML
    void signupClicked(ActionEvent event) throws IOException, ClassNotFoundException {
        if(Fields.notEmpty(fldUsername,fldPassword)){
            ArrayList<String> usernameAndPassword= new ArrayList<>();
            usernameAndPassword.add(fldUsername.getText());
            usernameAndPassword.add(User.coding(fldPassword.getText()));
            MainClient.mainConnect.send(new Request("sign up",usernameAndPassword));

            Request backedUser=MainClient.mainConnect.receive();
            switch (backedUser.getRequest()) {
                case "succeed":
                    Setting.getInstance().setThisUser((User) backedUser.getSerializable());
                    MainClient.setScene(getClass().getResource("../view/MainPane.fxml"));
                    break;
                case "username is repetitive!":
                    alert.setText("this username is exist");
                    Fields.makeAlertField(fldUsername,fldPassword);
                    break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTitle(pane,false);
        Duration duration = Duration.millis(1500);
        ScaleTransition[] transitions=new ScaleTransition[4];
        transitions[0]=new ScaleTransition(duration,btnSignup);
        transitions[1]=new ScaleTransition(duration,fldUsername);
        transitions[2]=new ScaleTransition(duration,fldPassword);
        transitions[3]=new ScaleTransition(duration,imgIcon);
        for (ScaleTransition transition : transitions) {
            transition.setFromX(0);
            transition.setFromY(0);
            transition.setToX(1);
            transition.setToY(1);
            transition.play();
        }
    }
}
