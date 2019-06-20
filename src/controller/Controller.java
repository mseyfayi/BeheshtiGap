package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Created by M.Seyfayi
 *
 * The Controller is an abstract class that creates a toolbar with
 * icon ,title and... to add into the all pane to be decorated
 */
abstract class Controller implements Initializable {
    private double initialX,initialY;
    void addTitle(BorderPane pane,boolean haveMaximum) {
        //Icons
        ImageView exitIcon, iconifyIcon, telegramIcon;
        exitIcon = new ImageView("icons/close.png");
        iconifyIcon = new ImageView("icons/minimize.png");
        telegramIcon = new ImageView("icons/icon.png");
        telegramIcon.setFitWidth(20);
        telegramIcon.setFitHeight(20);
        //Buttons
        JFXButton exit, iconify;
        exit = new JFXButton("", exitIcon);
        iconify = new JFXButton("", iconifyIcon);
        exit.setOnAction(event -> {
            Platform.exit();
        });
        iconify.setOnAction(event -> {
            MainClient.minimize();
        });
        exit.getStyleClass().add("uncolored-background");
        iconify.getStyleClass().add("uncolored-background");
        //Label
        Label label = new Label("Telegram");
        label.setFont(new Font(14));
        //HBoxes
        HBox left, right;
        right = new HBox(5, iconify, exit);
        left = new HBox(5, telegramIcon, label);

        //Maximize and Restore
        if (haveMaximum) {
            JFXButton maximize_restore=new JFXButton("");
            ImageView maximize=new ImageView("icons/maximize.png");
            ImageView restore= new ImageView("icons/restore.png");
            maximize_restore.setGraphic(MainClient.isMaximized()?restore:maximize);
            maximize_restore.getStyleClass().add("uncolored-background");

            maximize_restore.setOnAction(event -> {
                MainClient.setMaximized(!MainClient.isMaximized());
                maximize_restore.setGraphic(MainClient.isMaximized()?restore:maximize);
            });

            right.getChildren().add(1, maximize_restore);
        }

        //Toolbar
        JFXToolbar title = new JFXToolbar();
        title.setPadding(new Insets(3,5,3,5));
        title.setRightItems(right);
        title.setLeftItems(left);

        title.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    initialX = me.getSceneX();
                    initialY = me.getSceneY();
                }
            }
        });

        title.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    title.getScene().getWindow().setX(me.getScreenX() - initialX);
                    title.getScene().getWindow().setY(me.getScreenY() - initialY);
                }
            }
        });

        pane.setTop(title);
    }
}
