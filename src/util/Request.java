package util;

import java.io.Serializable;

/**
 * Created by M.Seyfayi
 * !!!Shared in client and server
 * <p>
 * Request is an object that will be serialized and transmitted between client and server
 * It has a String to transmitting a request
 * a Serializable to transmitting a files or a object
 * an enum named RequestType to showing type of the request
 * and a boolean to sho that this request is waiting for a respond or not
 */
public class Request implements Serializable {
    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 1L;

    /**
     * The request is an explanation of the request
     */
    private String request;

    /**
     * The object or the objects that will transmit
     */
    private Serializable serializable;


    /**
     * Initializes a new {@code Request} object
     *
     * @param request      The explanation of request or
     *                     a string we need to send with the request
     * @param serializable The object or objects to send and receive
     */
    public Request(String request, Serializable serializable) {
        this.request = request;
        this.serializable = serializable;
    }

    /**
     * Gets the request of the {@code Request}
     *
     * @return The request of this
     */
    public String getRequest() {
        return request;
    }

    /**
     * Sets the request of this by a given value
     *
     * @param request The given value
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * Gets the object of the request
     *
     * @return serialized objects that received or sent
     */
    public Serializable getSerializable() {
        return serializable;
    }

    /**
     * Changes the serializable of the object
     *
     * @param serializable The serializable of the object
     */
    public void setSerializable(Serializable serializable) {
        this.serializable = serializable;
    }

    @Override
    public String toString() {
        return "{" +
                "request='" + request + '\'' +
                ", serializable=" + serializable+
                '}';
    }
}
