package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import module.*;
import util.Request;
import util.Setting;
import util.Strings;

import java.io.IOException;

public class ControllerChatManager {
    private Chat chat;

    public void setChat(Chat chat) {
        this.chat = chat;
        image.setImage(chat.getImage().getImage());
        name.setText(chat.getName());
        if (chat instanceof PublicChat) {
            caption.setText("");
        } else if (chat instanceof PrivateChat) {
            PrivateChat privateChat = (PrivateChat) chat;
            User targetUser = privateChat.getTargetUser();
            if (targetUser.isWatchable())
                caption.setText(Strings.handleLastSeen(targetUser.getLastOnline()));
            else
                caption.setText("");
        }
    }

    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label caption;
    @FXML
    private VBox vBox;
    @FXML
    private BorderPane pane;

    public void setSender() {

    }

}
