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
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import module.*;
import util.Request;
import util.Setting;
import util.Strings;

import java.io.File;
import java.io.IOException;

public class ControllerChatListItem {
    private Chat chat;
    private User user;
    private ControllerMainPane pane2;
    private BorderPane chatPane;

    public void set(Chat chat, ControllerMainPane pane2, BorderPane chatPane) {
        this.chat = chat;
        name.setText(chat.getName());
        if (chat.getMessages().size() != 0)
            lastMessage.setText(chat.getMessages().get(chat.getMessages().size() - 1).toString());
        image.setImage(chat.getImage().getImage());
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
        image.setImage(chat.getImage().getImage());
        pane2.name.setText(chat.getName());
        if (chat instanceof PublicChat) {
            pane2.caption.setText("");
        } else if (chat instanceof PrivateChat) {
            PrivateChat privateChat = (PrivateChat) chat;
            User targetUser = privateChat.getTargetUser();
            if (targetUser.isWatchable())
                pane2.caption.setText(Strings.handleLastSeen(targetUser.getLastOnline()));
            else
                pane2.caption.setText("");
        }

        if (chat instanceof Channel) {
            Boolean admin = ((Channel) chat).getMembers().get(Setting.getInstance().getThisUser());
            if (admin == null)//not followed
            {
                JFXButton button = new JFXButton("Follow");
                button.setOnAction(a -> {
                    ((Channel) chat).getMembers().put(Setting.getInstance().getThisUser(), false);
                    chatPane.setBottom(null);
                });
                chatPane.setBottom(button);
            } else if (admin) {
                makeSender();
            }
        } else {
            makeSender();
        }
        for (Message message : chat.getMessages()) {
            FXMLLoader loader;
            Parent root;
            ControllerMessage controllerMessage;
            loader = new FXMLLoader(getClass().getResource("../view/Message.fxml"));
            root = loader.load();
            controllerMessage = loader.getController();
            controllerMessage.setMessage(message);
            pane2.messageList.getChildren().add(root);
            pane2.scrollPaneChat.setVvalue(1.0);
        }
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
