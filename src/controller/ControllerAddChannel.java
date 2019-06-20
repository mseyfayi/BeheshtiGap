package controller;

import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import module.Channel;
import module.User;
import util.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerAddChannel extends Controller {
    @FXML
    private TextField name;
    @FXML
    private ImageView image;
    @FXML
    private BorderPane pane;
    @FXML
    private JFXButton attachment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTitle(pane, false);
        ImageView attachmentIcon = new ImageView("icons/attachment.png");
        attachmentIcon.setFitHeight(20);
        attachmentIcon.setFitWidth(20);
        attachment.setGraphic(attachmentIcon);
    }

    public void attachmentClicked(ActionEvent actionEvent) throws IOException {
        WritableImage writableImage=Alerts.imageChooser();
        image.setImage(writableImage);
    }

    public void createClicked(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Connect connect = MainClient.mainConnect;
        Map<User, Boolean> map = new ConcurrentHashMap<>();
        map.put(Setting.getInstance().getThisUser(), Boolean.TRUE);
        if (Fields.notEmpty(name)) {
            Channel channel = new Channel(map, name.getText());
            connect.send(new Request("new channel", channel));
            Request request = connect.receive();
            if (!request.getRequest().equals("succeed")) {
                Fields.makeAlertField(name);
            } else {
                Setting.getInstance().getThisUser().getChannels().add(channel);
                MainClient.setScene(getClass().getResource("../view/MainPane.fxml"));
            }
        }
    }

    public void back(ActionEvent actionEvent) {
        MainClient.setScene(getClass().getResource("../view/Setting.fxml"));
    }
}
