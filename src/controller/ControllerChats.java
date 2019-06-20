package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import module.Channel;
import module.Group;
import module.PrivateChat;
import util.Setting;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ControllerChats implements Initializable {
    @FXML
    private VBox messageList;

    private Set<PrivateChat> privateChats=Setting.getInstance().getThisUser().getPrivateChats();
    private Set<Channel> channels=Setting.getInstance().getThisUser().getChannels();
    private Set<Group> groups=Setting.getInstance().getThisUser().getGroups();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
