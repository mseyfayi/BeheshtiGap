package module;

import controller.ControllerFileContent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

/**
 * Created by M.Seyfayi
 * !!!Shared in client and server
 * <p>
 * Message is a module for the messages that users sends in the chats
 */
public class Message implements Serializable {
    public static class Content implements Serializable {
        private String name;
        private long size;
        private String caption;

        /**
         * if the content was a files such as files, image, video and voice
         *
         * @param name    name of the files
         * @param size    size of the files
         * @param caption caption of the content
         */
        public Content(String name, long size, String caption) {
            this.name = name;
            this.size = size;
            this.caption = caption;
        }

        /**
         * if the content was text
         *
         * @param caption the text
         */
        public Content(String caption) {
            this.name = null;
            this.size = 0;
            this.caption = caption;
        }

        public boolean isDownloaded() {
            File file = Paths.get("files/" + name).toAbsolutePath().toFile();
            return file.exists();
        }

        public File getFile() {
            return Paths.get("files/" + name).toAbsolutePath().toFile();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Content that = (Content) o;
            return size == that.size &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(caption, that.caption);
        }

        @Override
        public int hashCode() {

            return Objects.hash(name, size, caption);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }
    }

    public enum MessageType {Text, File, Image, Voice, Video}


    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 7L;

    private User sender;

    private Date date;

    private MessageType type;

    private Chat chat;

    private Content content;

    private transient Pane pane;

    public Message(User sender, MessageType type, Chat chat, Content content) throws IOException {
        this.sender = sender;
        this.type = type;
        this.chat = chat;
        this.content = content;
        this.date = new Date();

        setPane();
    }

    public void setPane() throws IOException {
        pane=new StackPane();
        switch (type) {
            case Text:
                Label text = new Label(content.caption);
                pane.getChildren().add(text);
                break;
            case File:
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/FileContent.fxml"));
                Parent parent = fxmlLoader.load();
                ControllerFileContent fileContent = fxmlLoader.getController();
                fileContent.setFile(content);
                pane.getChildren().add(parent);
                break;
            case Image:
                break;
            case Voice:
                break;
            case Video:
                break;
        }
    }


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Pane getPane() {
        return pane;
    }

    @Override
    public String toString() {
        if (type == MessageType.Text)
            return content.caption;
        return "A " + type.toString().toLowerCase();
    }
}
