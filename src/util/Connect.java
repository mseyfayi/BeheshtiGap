package util;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import module.Message;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

/**
 * Created by M.Seyfayi
 * Connect is a class to resolve connection to the server with a socket
 * and work with the streams of the socket
 */
public class Connect implements AutoCloseable {
    /**
     * socket is a way to connecting to the server
     */
    private Socket socket;

    /**
     * in is an ObjectInputStream for receiving a {@link Request} object
     */
    private ObjectInputStream in;

    /**
     * out is an ObjectOutputStream for sending a {@link Request} object
     */
    private ObjectOutputStream out;

    /**
     * Initializes a {@code Connect} object with initialized host IP and port
     * The given IP and port is using for creating a new {@link Socket} to connecting
     *
     * @param host The given IP
     * @param port The given port
     * @throws IOException Throws when getting input or output stream failed
     */
    public Connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        System.out.println("connected");
    }

    /**
     * Gets in
     *
     * @return in
     */
    public ObjectInputStream getIn() {
        if (in == null) {
            try {
                in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            } catch (IOException e) {
                Alerts.connection();
            }
        }
        return in;
    }

    /**
     * Gets out
     *
     * @return out
     */
    public ObjectOutputStream getOut() {
        if (out == null) {
            try {
                out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    /**
     * To sending a request into server
     *
     * @param request given request to send
     * @throws IOException Throws when writing object failed
     */
    public void send(Request request) throws IOException {
        getOut().writeObject(request);
        getOut().flush();
        System.out.println("sent " + request);
    }

    /**
     * To receiving a message from server
     *
     * @return The received message
     * @throws IOException            Throws when reading object failed
     * @throws ClassNotFoundException Throws when casting failed
     */
    public Request receive() throws IOException, ClassNotFoundException {
        Request result;
        result = (Request) getIn().readObject();
        System.out.println("received " + result);
        return result;
    }

    /**
     * Downloads a files from server
     * @param file files to download
     * @param download download button
     * @param open open button
     * @param cancel cancel button
     * @param progress shows progress
     * @return the thread
     */
    public static Task<Void> download(Message.Content file, ImageView download, ImageView open, ImageView cancel, ProgressIndicator progress) {
        return new Task<>() {
            @Override
            protected void succeeded() {
                download.setVisible(false);
                cancel.setVisible(false);
                open.setVisible(true);
            }

            @Override
            protected void failed() {
                progress.progressProperty().unbind();
                progress.setProgress(0);
                download.setVisible(true);
                cancel.setVisible(false);
                open.setVisible(false);
            }

            @Override
            protected void scheduled() {
                download.setVisible(false);
                cancel.setVisible(true);
                open.setVisible(false);
            }

            @Override
            protected Void call() throws Exception {
                Connect connectionToDownload = new Connect(Strings.IP, Integers.port);
                byte[] buffer;
                connectionToDownload.send(new Request("download", file));
                File fileToSave = Paths.get("files/" + file.getName()).toAbsolutePath().toFile();
                OutputStream out1 = new FileOutputStream(fileToSave);
                Request receive;
                int totalDownloaded = 0, read;
                long totalSize = file.getSize();
                do {
                    receive = connectionToDownload.receive();
                    read = Integer.parseInt(receive.getRequest());
                    totalDownloaded += read;
                    buffer = (byte[]) receive.getSerializable();
                    out1.write(buffer, 0, read);
                    updateProgress(totalDownloaded, totalSize);
                } while (!receive.getRequest().equals("finished"));
                out1.close();
                return null;
            }
        };
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }
}