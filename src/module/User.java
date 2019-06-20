package module;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by M.Seyfayi
 * !!!Shared in client and server
 * <p>
 * To modeling account of a person
 */
public class User implements Serializable,Mappable {
    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 2L;

    /**
     * The username is used for keeping username of the account
     */
    private String username;

    /**
     * The password is used for keeping password of the account
     * the password is readable only
     */
    private String password;

    /**
     * The ID is specific number for each user
     * to be find
     */
    private long ID;

    /**
     * The bio is the biography of this user
     */
    private String bio;

    /**
     * The image is icon or icons of the user
     */
    private Image image;

    /**
     * The lastOnline keeps date of the last time that this account was online
     */
    private Date lastOnline;

    private boolean isWatchable;

    /**
     * The privateChats keeps all private chats of the user
     */
    private Set<PrivateChat> privateChats=Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * The groups keeps all group chats of the user
     */
    private Set<Group> groups=Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * The channels keeps all channels that the user is member of it
     */
    private Set<Channel> channels=Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static String coding(String password) {
        if (password == null)
            throw new NullPointerException("password is null");
        return "" + password.hashCode() * 13 + (char) (password.charAt(0) + 5) + (char) (password.charAt(0) + 13);
    }

    /**
     * Initializes a newly created {@link User} object
     * with given {@code username} and {@code Password}
     *
     * @param username The initial value of the user's username
     * @param password The initial value of the user's password
     * @param coding if true the user will create with coded password
     */
    public User(String username, String password, boolean coding) {
        this.username = username;
        if (coding)
            this.password = coding(password);
        else
            this.password = password;
    }

    /**
     * Gets the username of the user
     *
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user
     *
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Checks that the a string is matched with the password or not
     *
     * @param password The string to checking
     * @return {@code true} if the given string was matched with the password
     */
    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }

    /**
     * Resets the password of the user
     *
     * @param password The new value of the password
     */
    public void resetPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the password of the user
     *
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    //todo javadoc
    public void setPassword(String password) {
        this.password = password;
    }

    //todo javadoc
    public long getID() {
        return ID;
    }

    //todo javadoc
    public void setID(long ID) {
        this.ID = ID;
    }

    //todo javadoc
    public Date getLastOnline() {
        return lastOnline;
    }

    //todo javadoc
    public void setPrivateChats(Set<PrivateChat> privateChats) {
        this.privateChats = privateChats;
    }

    //todo javadoc
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    //todo javadoc
    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    /**
     * Checks that this user is matched with the given object or not
     *
     * @param o The given object to check
     * @return {@code true} if the given object and this user was equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID==user.ID;
    }

    /**
     * Hashes this user to a number
     *
     * @return The hash code of this user
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    /**
     * Gets difference of last time that this user was online and now
     *
     * @return The difference of  now and last seen of this user
     */
    public Date getLastSeen() {
        Long longDate = Calendar.getInstance().getTime().getTime() - lastOnline.getTime();
        return new Date(longDate);
    }

    /**
     * Sets lastOnline of this user to given date
     *
     * @param lastOnline The given date to be set
     */
    public void setLastOnline(Date lastOnline) {
        this.lastOnline = lastOnline;
    }

    /**
     * Sets lastOnline to now
     */
    public void setLastOnlineToNow() {
        setLastOnline(new Date());
    }

    /**
     * Gets the image of the user
     *
     * @return The image of the user
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the image of the user
     *
     * @param image given image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Gets the bio of the user
     *
     * @return bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the bio of the user
     *
     * @param bio given bio to set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Gets all of the private chats that are visible for the user
     *
     * @return Visible private chats
     */
    public Set<PrivateChat> getPrivateChats() {
        return privateChats;
    }

    /**
     * Gets all of the groups that the user is member of them
     *
     * @return The groups
     */
    public Set<Group> getGroups() {
        return groups;
    }

    /**
     * Gets all of the channel that the user followed them
     *
     * @return The channel
     */
    public Set<Channel> getChannels() {
        return channels;
    }

    /**
     * Adds a private chats to the user
     *
     * @param privateChat The given private chat to add
     */
    public void addPrivateChats(PrivateChat privateChat) {
        privateChats.add(privateChat);
    }

    /**
     * Adds a group to the user
     *
     * @param group The given group to add
     */
    public void addGroups(Group group) {
        groups.add(group);
    }

    /**
     * Adds a channel to the user
     *
     * @param channel The given channel to add
     */
    public void addChannels(Channel channel) {
        channels.add(channel);
    }

    /**
     * removes a private chats to the user
     *
     * @param privateChat The given private chat to remove
     */
    public void removePrivateChats(PrivateChat privateChat) {
        privateChats.remove(privateChat);
    }

    /**
     * removes a group to the user
     *
     * @param group The given group to remove
     */
    public void removeGroups(Group group) {
        groups.remove(group);
    }

    /**
     * removes a channel to the user
     *
     * @param channel The given channel to remove
     */
    public void removeChannels(Channel channel) {
        channels.remove(channel);
    }

    /**
     * An overriding for toString
     * @return username + password
     */
    @Override
    public String toString() {
        return
                username +
                        " : {" + password + '}';
    }

    public boolean isWatchable() {
        return isWatchable;
    }

    public void setWatchable(boolean watchable) {
        isWatchable = watchable;
    }
}
