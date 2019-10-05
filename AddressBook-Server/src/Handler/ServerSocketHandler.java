package Handler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketHandler {

    // Declaring variables and objects
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private DataOutputStream steamOut;
    private Boolean isRunning = true;

    // Adding new server socket
    public void addNewServerSocket(int port) {

            try {
                // Opening connection towards client
                serverSocket = new ServerSocket(port);
                System.out.println("Waiting for client....");
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                steamOut = new DataOutputStream(clientSocket.getOutputStream());
                ProgramLoop();
            } catch (IOException e) {
                System.out.println("This port is already in use");
            }
    }

    // The main loop of the program
    private void ProgramLoop() throws IOException {

        // Adding while loop to be able to run this multiple times
        while (isRunning) {
            WelcomeText();
            AddressBookChoises();
            String choice = in.readLine();
            System.out.println(choice);
            switch (choice){
                case "1":
                    out.println("Address List");
                    out.println("Marcus Wilen, Smallroad 14, 14390");
                    out.println("Martin Bolvin, Hellroad 9, 66666");
                    out.println("Viktor Olsson, Endroad 90, 0");
                break;
                case "2":
                    out.println("case 2");
                    break;
                case "3":
                    serverSocket.close();
                    clientSocket.close();
                    break;
            }
        }
    }

    // Welcome text for the client
    private void WelcomeText(){
        out.println("Welcome to the hell of doom! Please make a Choice or meet your faith");
    }

    // Choices for the client
    private void AddressBookChoises(){
        out.println("1. List all addresses");
        out.println("2. Search for an address");
        out.println("3. Quit");
    }
}
