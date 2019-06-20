package module;

import java.util.Map;

/**
 * Created by M.Seyfayi
 * !!!Shared in client and server
 * An abstract class to modelling chats(Groups and Channels) in the code
 */
public abstract class PublicChat extends Chat{
    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 3L;

    /**
     * members is a map to keeping members of the chat as keys
     * if a member was admin(has special permissions) his or her value will be true
     */
    private Map<User, Boolean> members;

    /**
     * ID is specific number of the chat
     */
    private String ID;

    /**
     * bio is an explanation for the chat
     */
    private String bio;

    public PublicChat(Map<User, Boolean> members, String name) {
        super(name);
        this.members = members;
    }

    /**
     * Gets the members of the chat
     *
     * @return members
     */
    public Map<User, Boolean> getMembers() {
        return members;
    }

    /**
     * Adds an user to the chat
     *
     * @param member  The user to add
     * @param isAdmin if true gives the member special permissions(makes it an admin)
     * @throws Exception if this member is exist in the chat
     */
    public void addMember(User member, boolean isAdmin) throws Exception {
        if (members.containsKey(member))
            throw new Exception("This member is exist");

        members.put(member, isAdmin);
    }

    /**
     * Removes a member from the chat
     *
     * @param member The member to remove
     */
    public void removeMember(User member) {
        members.remove(member);
    }

    /**
     * Makes a member admin
     *
     * @param member The given member to be admin
     * @throws Exception if the given member is not exist in the chat
     */
    public void makeAdmin(User member) throws Exception {
        changePermission(member, true);
    }

    /**
     * Takes permissions from a member
     *
     * @param member The given member to be normal
     * @throws Exception if the given member is not exist in the chat
     */
    public void makeMember(User member) throws Exception {
        changePermission(member, false);
    }

    private void changePermission(User member, boolean isAdmin) throws Exception {
        if (!members.containsKey(member))
            throw new Exception("This member is not exist");

        members.put(member, isAdmin);
    }

    /**
     * Gets the ID of the chat
     *
     * @return ID of the chat
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the chat
     *
     * @param ID given ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Checks that ID of the chat is equals of given ID or not
     *
     * @param ID given ID to check
     * @return true if ID of the chat was equals to given ID
     */
    public boolean equalsID(String ID) {
        return ID.equals(this.ID);
    }

    /**
     * Gets the bio
     *
     * @return bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the bio
     *
     * @param bio given bio to set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }
}