package controller;

import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import util.Alerts;
import util.Setting;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerChangeImage extends Controller {
    @FXML
    private BorderPane pane;
    @FXML
    private ImageView image;
    @FXML
    private JFXButton attachment;
    @FXML
    void attachmentClicked(ActionEvent event) throws IOException {
        WritableImage writableImage=Alerts.imageChooser();
        image.setImage(writableImage);
    }

    @FXML
    void backClicked(ActionEvent event) {
        MainClient.setScene(getClass().getResource("../view/Setting.fxml"));
    }

    @FXML
    void changeClicked(ActionEvent event) {
        Setting.getInstance().getThisUser().setImage(image.getImage());
        MainClient.setScene(getClass().getResource("../view/Setting.fxml"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTitle(pane,false);
        ImageView attachmentIcon=new ImageView("icons/attachment.png");
        attachmentIcon.setFitHeight(30);
        attachmentIcon.setFitWidth(30);

        attachment.setGraphic(attachmentIcon);
        image.setImage(Setting.getInstance().getThisUser().getImage());
    }
}
