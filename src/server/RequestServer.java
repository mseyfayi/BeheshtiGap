package server;

import module.*;
import util.Integers;
import util.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//todo javadoc
public class RequestServer {
    private ExecutorService threadPool = Executors.newFixedThreadPool(Integers.nThreads);

    void startServer() {
        try (ServerSocket server = new ServerSocket(Integers.port)) {
            while (true) {
                Socket newSocket = server.accept();
                threadPool.submit(new ClientThread(newSocket, this));
                System.out.println("a client connect");
            }
        } catch (IOException e) {
//            System.out.println("restarting the server!");
//            startServer();
            e.printStackTrace();
        }
    }
}

class ClientThread implements Runnable {
    private Socket socket;
    private RequestServer server;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    ClientThread(Socket socket, RequestServer server) throws IOException {
        this.server = server;
        this.socket = socket;
        input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    private void send(Request request) throws IOException {
        output.writeObject(request);
        output.flush();
        System.out.println("sent " + request);
    }

    @Override
    public void run() {
        Request request;
        try {
            Database database = Database.getInstance();
            while (true) {
                request = (Request) input.readObject();
                System.out.println("received " + request);
                switch (request.getRequest()) {
                    case "login":
                        ArrayList<String> userAndPass = (ArrayList<String>) request.getSerializable();
                        String user = userAndPass.get(0);
                        String pass = userAndPass.get(1);
                        if (database.findUser(user, pass) != null)
                            send(new Request("succeed", database.findUser(user, pass)));
                        else
                            send(new Request("username or password is wrong", null));
                        break;
                    case "sign up":
                        userAndPass = (ArrayList<String>) request.getSerializable();
                        user = userAndPass.get(0);
                        pass = userAndPass.get(1);
                        if (database.findUser(user) == null) {
                            User newUser = new User(user, pass, false);
                            database.getUsers().add(newUser);
                            send(new Request("succeed", newUser));
                        } else
                            send(new Request("username is repetitive!", null));
                        break;
                    case "download":
                        Message.Content file = (Message.Content) request.getSerializable();
                        upload(file.getName());
                        break;
                    case "message":
                        Message message = (Message) request.getSerializable();
                        Chat chat = database.findChat(message.getChat().getName());
                        chat.getMessages().add(message);
                        break;
                    case "new channel":
                        Channel channel = (Channel) request.getSerializable();
                        String name = channel.getName();
                        if (database.findChat(name) != null) {
                            send(new Request("this name is repetitive!", null));
                        } else {
                            send(new Request("succeed", null));
                            final User[] user1 = new User[1];
                            channel.getMembers().keySet().forEach(a -> user1[0] = a);
                            database.getPublicChats().add(channel);
                            database.findUser(user1[0].getUsername()).getChannels().add(channel);
                        }
                        break;
                    case "search":
                        String username = (String) request.getSerializable();
                        ArrayList<Mappable> mappable=database.getMappable(username);
                        if (mappable == null) {
                            send(new Request("failed",null));
                        }else {
                            send(new Request("succeed", mappable));
                        }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected");
            Database.getInstance().serialize();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void upload(String name) throws IOException, InterruptedException {
        File file = Paths.get("files/" + name).toAbsolutePath().toFile();
        if (!file.exists()) {
            send(new Request("finish", null));
            return;
        }
        InputStream input = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = input.read(buffer)) > 0) {
            Thread.sleep(1); ///todo Remove This Later!!!
            send(new Request(read + "", buffer));
        }
    }
}
