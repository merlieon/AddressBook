package Main;

import Handler.ServerSocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        ServerSocketHandler serverSocketHandler = new ServerSocketHandler();
        serverSocketHandler.addNewServerSocket(9999);
    }
}
