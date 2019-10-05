package Handler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerSocketHandler {

    // Declaring variables and objects
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Boolean isRunning = true;
    private List<String> addressList = new ArrayList<String>();

    // Adding new server socket
    public void addNewServerSocket(int port) {

            try {
                // Opening connection towards client
                serverSocket = new ServerSocket(port);
                System.out.println("Waiting for client....");
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ProgramLoop();
            } catch (IOException e) {
                System.out.println("This port is already in use");
            }
    }

    // The main loop of the program
    private void ProgramLoop() throws IOException {
        // Adding items to address list
        addressList.add("Marcus Wilen, Smallroad 14, 14390");
        addressList.add("Martin Bolvin, Hellroad 9, 66666");
        addressList.add("Viktor Olsson, Endroad 90, 0");
        Boolean isFound = false;
        // Adding while loop to be able to run this multiple times
        while (isRunning) {
            WelcomeText();
            AddressBookChoises();
            String choice = in.readLine();
            System.out.println(choice);
            switch (choice){
                // Printing all item to client
                case "1":
                    for (String list : addressList) {
                        out.println("");
                        out.println(list + "\n");
                    }
                break;
                // Printing search item to client
                case "2":
                    String search = in.readLine();
                    for (int i = 0; i < addressList.size(); i++) {
                        if (addressList.get(i).contains(search)){
                            out.println("");
                            out.println(addressList.get(i) + "\n");
                            break;
                        } else if (addressList.size() == i + 1){
                            out.println("");
                            out.println("last item \n");
                        }
                    }
                    
                    break;
                // Closing client
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
