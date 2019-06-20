package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import module.*;
import util.Request;
import util.Setting;
import util.Strings;

import java.io.File;
import java.io.IOException;

public class ControllerChatListItemUser {
    private User user;
    private Chat chat;
    private ControllerMainPane pane2;
    private BorderPane chatPane;

    public void set(User user, ControllerMainPane pane2, BorderPane chatPane) {
        this.user=user;
        name.setText(user.getUsername());
        image.setImage(user.getImage());
        this.pane2 = pane2;
        this.chatPane = chatPane;
    }

    private void makeSender() {
        ImageView sendIcon = new ImageView("icons/send.png");
        ImageView attachmentIcon = new ImageView("icons/attachment.png");
        sendIcon.setFitHeight(35);
        sendIcon.setFitWidth(35);
        attachmentIcon.setFitWidth(35);
        attachmentIcon.setFitHeight(35);

        JFXButton send = new JFXButton("", sendIcon);
        JFXButton attachment = new JFXButton("", attachmentIcon);

        send.setPadding(new Insets(5, 5, 0, 5));
        attachment.setPadding(new Insets(5, 5, 0, 5));

        JFXTextArea text = new JFXTextArea();
        text.setPromptText("Write a message");
        text.getStyleClass().add("un-focus-JFX");

        send.getStyleClass().add("uncolored-background");
        attachment.getStyleClass().add("uncolored-background");

        send.setOnAction(a -> {
            Message message;
            FXMLLoader loader;
            Parent root;
            ControllerMessage controllerMessage;
            try {
                message = new Message(Setting.getInstance().getThisUser(), Message.MessageType.Text, chat, new Message.Content(text.getText()));
                loader = new FXMLLoader(getClass().getResource("../view/Message.fxml"));
                root = loader.load();
                controllerMessage = loader.getController();
                controllerMessage.setMessage(message);
                pane2.messageList.getChildren().add(root);
                pane2.scrollPaneChat.setVvalue(1.0);
                MainClient.mainConnect.send(new Request("message", message));
                text.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        attachment.setOnAction(a -> {
            FileChooser fileChooser=new FileChooser();
            fileChooser.setTitle("Choose a file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All File","*.*")
            );
            File selectedFile=fileChooser.showOpenDialog(null);
        });

        BorderPane tempChatManager = new BorderPane(text, null, send, null, attachment);
        tempChatManager.setPrefHeight(50);
        tempChatManager.setPrefWidth(50);
        tempChatManager.setStyle("-fx-background-color: #fff");
        chatPane.setBottom(tempChatManager);
    }

    @FXML
    private Pane pane;
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label lastMessage;

    @FXML
    void leftClicked(MouseEvent event) throws IOException {
        image.setImage(user.getImage());
        pane2.name.setText(user.getUsername());
        chat=new PrivateChat(user);
        makeSender();
    }

    @FXML
    void mouseEntered(MouseEvent event) {
        pane.setStyle("-fx-background-color: #23cff9");
    }

    @FXML
    void rightClicked(ContextMenuEvent event) {
        //todo
    }

    public void mouseExited(MouseEvent mouseEvent) {
        pane.setStyle("-fx-background-color: transparent");
    }
}
