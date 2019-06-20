package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.*;

import java.io.IOException;
import java.net.URL;

public class MainClient extends Application {
    private static Stage mainStage;

    public static Connect mainConnect;

    public static void setScene(URL url) {
        Parent root;
        try {
            root = FXMLLoader.load(url);
            mainStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setScene(Parent root) {
        mainStage.setScene(new Scene(root));
    }

    static void minimize(){
        mainStage.setIconified(true);
    }

    static boolean isMaximized(){
        return mainStage.isMaximized();
    }

    static void setMaximized(boolean maximized){
        mainStage.setMaximized(maximized);
    }

    public static Connect connect() {
        try {
            return new Connect(Strings.IP, Integers.port);
        } catch (IOException e) {
            return Alerts.connection();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainConnect = connect();
        mainStage = primaryStage;
        Parent root;
        mainStage.getIcons().add(new Image("icons/icon.png"));
        mainStage.setTitle("Telegram");
        mainStage.initStyle(StageStyle.UNDECORATED);
        if (Setting.getInstance().initialize()) {
            root = FXMLLoader.load(getClass().getResource("../view/MainPane.fxml"));
        } else {
            Setting.getInstance().setBackgroundPath("files/wallpaper/3.jpg");
            root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        }
        setScene(root);
        MainClient.mainStage.show();
    }

    @Override
    public void stop() throws Exception {
        Setting.getInstance().serialize();
        System.out.println("Exit");
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
