package util;

import controller.MainClient;
import module.User;

import java.io.*;
import java.util.ArrayList;

//todo
public class Setting implements Serializable {
    private transient static Setting ourInstance = new Setting();
    private transient User thisUser;
    private String username;
    private String password;
    private String backgroundPath;

    public boolean initialize() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("setting.ser"))) {
            ourInstance = (Setting) input.readObject();

            if (ourInstance.username != null && ourInstance.password != null) {
                ArrayList<String> usernameAndPassword = new ArrayList<>();
                usernameAndPassword.add(ourInstance.username);
                usernameAndPassword.add(ourInstance.password);
                MainClient.mainConnect.send(new Request("login", usernameAndPassword));

                Request backed = MainClient.mainConnect.receive();
                switch (backed.getRequest()) {
                    case "succeed":
                        Setting.getInstance().setThisUser((User) backed.getSerializable());
                        return true;
                    case "username or password is wrong":
                        return false;
                }
            }
            return false;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public void logout() {
        thisUser = null;
        serialize();
    }

    public void serialize() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("setting.ser"))) {
            output.writeObject(ourInstance);
        } catch (IOException e) {
            System.out.println("Retry for serialize");
            serialize();
        }
    }

    public static Setting getInstance() {
        return ourInstance;
    }

    private Setting() {
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
        username = thisUser.getUsername();
        password = thisUser.getPassword();
    }
}
