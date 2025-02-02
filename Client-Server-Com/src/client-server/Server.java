import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;

    private void newServerSocket(int port) throws IOException {
        // [1]: Create a new ServerSocket object
        // and bind it to the specified port number
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    private Socket waitForConnection() throws IOException {
        // [1]: Wait for a client to connect to the server
        Socket socket = serverSocket.accept(); // blocking call that returns a new socket object
        System.out.println("Client connected: " + socket);
        return socket;
    }

    private void closeConnection(Socket socket) {
        try {
            socket.close();
            System.out.println("Connection closed: " + socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectionHandler(Socket socket) {
        // Handling the connection for each client in a new thread
        new Thread(() -> {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                // [OBS]: Reading the directory path from the client
                String directoryPath = input.readLine();
                System.out.println("CLT: Requested path - " + directoryPath);

                File dir = new File(directoryPath);
                if (dir.isDirectory()) {
                    String[] fileList = dir.list();
                    output.println(fileList.length); // Send number of files

                    for (String fileName : fileList) {
                        output.println(fileName); // Send each file name
                    }
                } else {
                    output.println("-1"); // Error case
                }

                input.close();
                output.close();
            } catch (IOException e) {
                System.out.println("-1");
            } finally {
                closeConnection(socket);
            }
        }).start();
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.newServerSocket(3000);

            while (true) { // Loop to handle multiple client connections
                Socket socket = server.waitForConnection();
                server.connectionHandler(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}