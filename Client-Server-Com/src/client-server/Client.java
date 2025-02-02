import java.io.*;
import java.net.*;

public class Client {
    private static final int PORT = 3000;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket client = connectToServer();
             BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter output = new PrintWriter(client.getOutputStream(), true);
             BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("CLT: Connection successful to the server: " + client.getRemoteSocketAddress());

            String directoryPath = getUserInput(stdin);
            sendDirectoryPath(output, directoryPath);
            receiveAndDisplayServerResponse(input);

        } catch (UnknownHostException e) {
            System.out.println("CLT: Unknown Host!");
        } catch (IOException ioe) {
            System.out.println("CLT: Cannot connect to the server.");
        }
    }

    // Function to connect to the server
    private static Socket connectToServer() throws IOException {
        return new Socket(SERVER_IP, PORT);
    }

    // Function to obtain the direction of use
    private static String getUserInput(BufferedReader stdin) throws IOException {
        System.out.print("Path: Please, insert one path in Linux: ");
        return stdin.readLine();
    }

    // Function to enviar the route to the direction of the service
    private static void sendDirectoryPath(PrintWriter output, String path) {
        output.println(path);
    }

    // Function to receive and deliver a service response
    private static void receiveAndDisplayServerResponse(BufferedReader input) throws IOException {
        String response = input.readLine();

        if (response.equals("-1")) {
            System.out.println("SRV: Error accessing the directory.");
        } else {
            int fileCount = Integer.parseInt(response);
            System.out.println("SRV: Number of files: " + fileCount);

            for (int i = 0; i < fileCount; i++) {
                String fileName = input.readLine();
                System.out.println("File " + (i + 1) + ": " + fileName);
            }
        }
    }
}