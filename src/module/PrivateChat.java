package module;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Created M.Seyfayi
 * !!!Shared in client and server
 * <p>
 * The PrivateChat is a module for chats between two user
 */
public class PrivateChat extends Chat {
    private User targetUser;

    public PrivateChat(User targetUser) {
        super(targetUser.getUsername());
        super.setImage(new ImageView(targetUser.getImage()));
        this.targetUser = targetUser;
    }
    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }
}