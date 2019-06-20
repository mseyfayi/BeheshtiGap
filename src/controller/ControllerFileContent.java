package controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import module.Message;
import util.Connect;
import util.Strings;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ControllerFileContent {
    private Message.Content file;

    public void setFile(Message.Content file) {
        this.file = file;
        name.setText(file.getName());
        size.setText(Strings.readableFileSize(file.getSize()));
        if (file.isDownloaded()) {
            setVisible(download);
        } else{
            setVisible(open);
        }
    }

    @FXML
    private ProgressIndicator progress;
    @FXML
    private ImageView download;
    @FXML
    private ImageView open;
    @FXML
    private ImageView cancel;
    @FXML
    private Label name;
    @FXML
    private Label size;

    private Thread threadTask;

    @FXML
    private void downloadClicked(){
        Task<Void> task=Connect.download(file,download,open,cancel, progress);
        progress.progressProperty().bind(task.progressProperty());
        threadTask =new Thread(task);
        threadTask.start();
    }

    @FXML
    private void openClicked(){
        File file=Paths.get("files/"+name.getText()).toAbsolutePath().toFile();
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            setVisible(download);
        }
    }

    public void cancelClicked(MouseEvent mouseEvent) {
        setVisible(download);
        threadTask.interrupt();
    }

    private void setVisible(ImageView imageView) {
        download.setVisible(false);
        open.setVisible(false);
        cancel.setVisible(false);
        imageView.setVisible(true);
    }
}