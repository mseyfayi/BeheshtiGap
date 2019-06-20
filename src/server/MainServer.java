package server;

public class MainServer {
    public static void main(String[] args) {
        System.out.println("Users : "+Database.getInstance().getUsers());
        System.out.println("Public Chat : " + Database.getInstance().getPublicChats());
        RequestServer server=new RequestServer();
        server.startServer();
    }
}
