package util;

import controller.MainClient;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Alerts is used to declaring some method for creating alerts
 */
public class Alerts {
    /**
     * The connection creates an error alert when connection to server failed
     * @return An error alert("Connection failed!")
     */
    public static Connect connection() {
        ButtonType retry = new ButtonType("Retry");
        Alert alert = new Alert(Alert.AlertType.ERROR, "Connection failed!", retry);
        if (alert.showAndWait().get() == retry) {
            return MainClient.connect();
        } else {
            System.exit(-1);
        }
        return null;
    }

    public static WritableImage imageChooser() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image for the channel");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.ico", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        System.out.println(selectedFile.getAbsolutePath());
        BufferedImage bufferedImage = ImageIO.read(selectedFile);
        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
        return (writableImage);
    }
}