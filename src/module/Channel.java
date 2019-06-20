package module;

import java.util.Map;

/**
 * Created by M.Seyfayi
 * !!!Shared in client and server
 * <p>
 * The channel is a module for broadcast chats in the program
 */
public class Channel extends PublicChat {
    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 5L;

    public Channel(Map<User, Boolean> members, String name) {
        super(members, name);
    }
}
