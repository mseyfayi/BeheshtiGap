package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import module.*;
import util.Fields;
import util.Request;
import util.Setting;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerMainPane extends Controller {
    public Label name;
    public Label caption;
    public VBox messageList;
    public ScrollPane scrollPaneChat;
    public JFXButton btnSearch;
    @FXML
    private JFXButton add;
    @FXML
    private TextField search;
    @FXML
    private JFXButton setting;
    @FXML
    private BorderPane pane;
    public VBox chatList;
    @FXML
    private BorderPane chatPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTitle(pane, true);

        ImageView settingIcon = new ImageView("icons/menu.png");
        settingIcon.setFitWidth(40);
        settingIcon.setFitHeight(40);
        setting.setText("");
        setting.setGraphic(settingIcon);

        ImageView addIcon = new ImageView("icons/add.png");
        addIcon.setFitWidth(40);
        addIcon.setFitHeight(40);
        add.setText("");
        add.setGraphic(addIcon);

        chatPane.setStyle("-fx-background-image: url(" + Setting.getInstance().getBackgroundPath() + ")");
        addChats();
    }

    private void addChats(){
        chatList.getChildren().clear();
        User user = Setting.getInstance().getThisUser();
        Set<Chat> chats = Collections.newSetFromMap(new ConcurrentHashMap<>());
        chats.addAll(user.getGroups());
        chats.addAll(user.getChannels());
        chats.addAll(user.getPrivateChats());
        for (Chat chat : chats) {
            FXMLLoader loader;
            Parent root;
            ControllerChatListItem controllerChatListItem;
            try {
                loader = new FXMLLoader(getClass().getResource("../view/ChatListItem.fxml"));
                root = loader.load();
                controllerChatListItem = loader.getController();
                controllerChatListItem.set(chat, this, chatPane);
                chatList.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void settingClicked(ActionEvent actionEvent) {
        MainClient.setScene(getClass().getResource("../view/Setting.fxml"));
    }

    public void addClicked(ActionEvent actionEvent) {
        MainClient.setScene(getClass().getResource("../view/AddChat.fxml"));
    }

    private PrivateChat findPV(String name) {
        Map<String, PrivateChat> map = new ConcurrentHashMap<>();
        for (PrivateChat privateChat : Setting.getInstance().getThisUser().getPrivateChats()) {
            map.put(privateChat.getName(), privateChat);
        }
        for (String PVname : map.keySet()) {
            if (PVname.matches(".*"+name+".*"))
                return map.get(name);
        }
        return null;
    }

    @FXML
    public void searchClicked(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (Fields.notEmpty(search)) {
            chatList.getChildren().clear();
            String username = search.getText();

                MainClient.mainConnect.send(new Request("search", username));
                Request request = MainClient.mainConnect.receive();
                if (request.getRequest().equals("succeed")) {
                    ArrayList<Mappable> userOrChats= (ArrayList<Mappable>) request.getSerializable();
                    for (Mappable s : userOrChats) {
                        FXMLLoader loader;
                        Parent root;
                        if (s instanceof User) {
                            ControllerChatListItemUser controllerChatListItem;
                            try {
                                loader = new FXMLLoader(getClass().getResource("../view/ChatListItemUser.fxml"));
                                root = loader.load();
                                controllerChatListItem = loader.getController();
                                controllerChatListItem.set((User) s, this, chatPane);
                                chatList.getChildren().add(root);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (s instanceof Chat) {
                            ControllerChatListItem controllerChatListItem;
                            try {
                                loader = new FXMLLoader(getClass().getResource("../view/ChatListItem.fxml"));
                                root = loader.load();
                                controllerChatListItem = loader.getController();
                                controllerChatListItem.set((Chat) s, this, chatPane);
                                chatList.getChildren().add(root);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
        }else
            addChats();
    }
}