package paiement.socketClient.sockets;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;


    public Client(String hostname, int port) {
        try {
            socket = new Socket(InetAddress.getByName(hostname), port);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }


}
