package module;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by M.Seyfayi
 * !!!Shared in client and server
 * <p>
 * An empty Interface to implementing same classes for modelling chats
 */
public abstract class Chat implements Serializable,Mappable {
    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 10L;

    /**
     * name of the chat
     */
    private String name;

    /**
     * The ID is specific number for each user
     * to be find
     */
    private long ID;

    /**
     * image is icon of the chat
     */
    private transient ImageView image;

    /**
     * The message is a collection to keeping sent messages in chat
     */
    private Vector<Message> messages = new Vector<>();

    Chat(String name) {
        this.name = name;
        image = new ImageView("icons/default.jpg");
    }


    /**
     * Gets the image of the chat
     *
     * @return image
     */
    public ImageView getImage() {
        if(image==null)
            image=new ImageView("icons/default.jpg");
        return image;
    }

    /**
     * Sets the image of the chat
     *
     * @param image given image to set
     */
    public void setImage(ImageView image) {
        this.image = image;
    }

    /**
     * Gets the name of the chat
     *
     * @return name of the chat
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the chat
     *
     * @param name given name of the chat
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets messages of chat
     *
     * @return messages
     */
    public Vector<Message> getMessages() {
        return messages;
    }

    /**
     * Sets messages of chat
     *
     * @param messages given messages to set
     */
    public void setMessages(Vector<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "name='" + name + '\'' +
                '}';
    }
}
