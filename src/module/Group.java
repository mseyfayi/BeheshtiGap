package module;

import java.util.Map;

/**
 * Created by M.Seyfayi
 * !!!Shared in client and server
 * <p>
 * The group is a module for multi-member gaps
 */
public class Group extends PublicChat {
    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 6L;

    public Group(Map<User, Boolean> members, String name) {
        super(members, name);
    }
}
