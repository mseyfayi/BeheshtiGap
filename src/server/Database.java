package server;

import javafx.util.Pair;
import module.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by M.Seyfayi
 * <p>
 * The Database is used for keeping information of the server in the hard
 * Database is singleton
 */
public class Database implements Serializable {
    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 1234556778L;

    /**
     * The Database is singleton
     * The instance is the single object of the Database
     */
    private transient static Database instance;

    /**
     * The users is used for saving all of the users signed up in server
     */
    private Set<User> users;

    /**
     * The publicChats is used for saving all of the public chats that created in the server
     */
    private Set<PublicChat> publicChats;

    static {
        //Initializing database from the fileInputStream
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("database.ser"))) {
            instance = (Database) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            instance = new Database();
            instance.users = Collections.newSetFromMap(new ConcurrentHashMap<>());
            instance.publicChats = Collections.newSetFromMap(new ConcurrentHashMap<>());
        }
    }

    /**
     * Serialized the instance
     */
    public void serialize() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("database.ser"))) {
            outputStream.writeObject(instance);
            System.out.println(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The Database is singleton
     * This method gets the instance of the database
     *
     * @return instance
     */
    public static Database getInstance() {
        return instance;
    }

    /**
     * Gets the users of the server
     *
     * @return users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Gets the public chats of the server
     *
     * @return publicChats
     */
    public Set<PublicChat> getPublicChats() {
        return publicChats;
    }

    /**
     * Finds and returns a user with the given user and pass
     *
     * @param username given username to search
     * @param password given password to search
     * @return found user and null if couldn't find
     */
    public User findUser(String username, String password) {
        Map<Pair<String, String>, User> userMap = getUserMapUP();
        for (Pair<String, String> usernameAndPassword : userMap.keySet()) {
            if (usernameAndPassword.getKey().equals(username)
                    && usernameAndPassword.getValue().equals(password))
                return userMap.get(usernameAndPassword);
        }
        return null;
    }

    /**
     * Finds and returns a user with the given ID
     *
     * @param ID given ID to search
     * @return found user and null if couldn't find
     */
    public User findUSer(long ID) {
        Map<Long, User> userMap = getUserMapID();
        for (Long ID2 : userMap.keySet()) {
            if (ID2 == ID)
                return userMap.get(ID2);
        }
        return null;
    }

    /**
     * Finds and returns a user with the given username
     *
     * @param username given ID to search
     * @return found user and null if couldn't find
     */
    public User findUser(String username) {
        Map<String, User> userMap = getUserMapU();
        for (String username2 : userMap.keySet()) {
            if (username2.equals(username))
                return userMap.get(username2);
        }
        return null;
    }

    public ArrayList<Mappable> getMappable(String name) {
        ArrayList<Mappable> arrayList = new ArrayList<>();
        boolean isFound = false;
        for (String nameOfChat : getChatMapN().keySet()) {
            if (nameOfChat.matches(".*" + name + ".*")) {
                arrayList.add(getChatMapN().get(nameOfChat));
                isFound = true;
            }
        }
        for (User user : users) {
            if (user.getUsername().matches(".*" + name + ".*")) {
                arrayList.add(user);
                isFound = true;
            }
        }
        if (isFound)
            return arrayList;
        else
            return null;
    }

    public Chat findChat(String name) {
        Map<String, Chat> map = getChatMapN();
        for (String publicChatName : map.keySet()) {
            if (publicChatName.equals(name))
                return map.get(name);
        }
        return null;
    }

    private Map<String, Chat> getChatMapN() {
        Map<String, Chat> map = new ConcurrentHashMap<>();
        for (Chat publicChat : publicChats) {
            map.put(publicChat.getName(), publicChat);
        }
        Set<Chat> chats = Collections.newSetFromMap(new ConcurrentHashMap<>());
        for (User user : users) {
            chats.addAll(user.getPrivateChats());
        }
        for (Chat chat : chats) {
            map.put(chat.getName(), chat);
        }
        return map;
    }

    private Map<String, User> getUserMapU() {
        Map<String, User> userMap = new ConcurrentHashMap<>();
        for (User user : users) {
            userMap.put(user.getUsername(), user);
        }
        return userMap;
    }

    private Map<Pair<String, String>, User> getUserMapUP() {
        Map<Pair<String, String>, User> userMap = new ConcurrentHashMap<>();
        for (User user : users) {
            Pair<String, String> usernameAndPassword = new Pair<>(user.getUsername(), user.getPassword());
            userMap.put(usernameAndPassword, user);
        }
        return userMap;
    }

    private Map<Long, User> getUserMapID() {
        Map<Long, User> userMap = new ConcurrentHashMap<>();
        for (User user : users) {
            userMap.put(user.getID(), user);
        }
        return userMap;
    }

    private Set<Message> getMessages() {
        Map<Message.Content, File> transmissibleFileMap = new ConcurrentHashMap<>();
        Set<Message> messages = Collections.newSetFromMap(new ConcurrentHashMap<>());
        for (PublicChat publicChat : publicChats) {
            messages.addAll(publicChat.getMessages());
        }
        for (User user : users) {
            for (PrivateChat privateChat : user.getPrivateChats()) {
                messages.addAll(privateChat.getMessages());
            }
        }
        return messages;
    }

    private Database() {
    }
}
