package Handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketHandler {

    public void addNewServerSocket(int port) {

            try {
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Waiting for client....");
                Socket clientSocket = serverSocket.accept();

            } catch (IOException e) {
                System.out.println("This port is already in use");
            }
    }





}
