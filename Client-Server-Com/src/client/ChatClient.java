import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
* Implementation of the chat client that connects to the server,
* sends messages and receives messages from other users.
*/
public class ChatClient extends UnicastRemoteObject implements InterfaceChatClient {

    private String pseudo;
    private InterfaceChatServer server;

    protected ChatClient(String pseudo, InterfaceChatServer server) throws RemoteException {
        super();
        this.pseudo = pseudo;
        this.server = server;
    }

    // Clear the terminal
    private static void clearScreen() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("[!] Unable to clear terminal.");
        }
    }

    // Get the client's local time
    private String getClientHour() {
        Calendar calendar = new GregorianCalendar();
        return String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    // Method called by the server to receive messages
    @Override
    public void diffuseMessage(Message msg) throws RemoteException {
        System.out.println(msg);
    }

    // Initialize the client
    public static void main(String[] args) {
        try {
            clearScreen();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String pseudo = scanner.nextLine();

            // Connect to the server
            InterfaceChatServer server = (InterfaceChatServer) Naming.lookup("rmi://localhost:1099/ChatServer");

            // Register the client in the RMI Registry
            ChatClient client = new ChatClient(pseudo, server);
            Naming.rebind("rmi://localhost:1099/Client_" + pseudo, client);

            // Connect to the server
            server.connect(pseudo, "rmi://localhost:1099/Client_" + pseudo);

            // Loop to send messages
            System.out.println("Welcome to the Chat, " + pseudo + "! Type your messages below:");
            while (true) {
                String msgContent = scanner.nextLine();
                Message msg = new Message(pseudo, msgContent, server.getServerHour(), client.getClientHour());
                server.broadcastMessage(msg);
            }

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
