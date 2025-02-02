import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* Implementation of the chat server that manages client connections
* and broadcasts messages to all connected users.
*/
public class ChatServer extends UnicastRemoteObject implements InterfaceChatServer {

    // Map to store connected clients (pseudo -> remote client reference)
    private final Map<String, InterfaceChatClient> clients;

    protected ChatServer() throws RemoteException {
        super();
        clients = new ConcurrentHashMap<>(); // Allows secure concurrent access
    }

    // Method to get the current time from the server
    @Override
    public String getServerHour() {
        Calendar calendar = new GregorianCalendar();
        return String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    @Override
    public void connect(String pseudo, String clientUrl) throws RemoteException {
        try {
            InterfaceChatClient client = (InterfaceChatClient) Naming.lookup(clientUrl);
            clients.put(pseudo, client);

            // Notify all clients about the new connection
            Message joinMsg = new Message("SERVER", pseudo + " has joined the chat", getServerHour(), getServerHour());
            broadcastMessage(joinMsg);

            System.out.println(pseudo + " connected from " + clientUrl);
        } catch (Exception e) {
            System.err.println("Connection error for " + pseudo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect(String pseudo) throws RemoteException {
        clients.remove(pseudo);

        // Notify all clients about the disconnection
        Message leaveMsg = new Message("SERVER", pseudo + " has left the chat", getServerHour(), getServerHour());
        broadcastMessage(leaveMsg);

        System.out.println(pseudo + " disconnected.");
    }

    @Override
    public void broadcastMessage(Message msg) throws RemoteException {
        for (InterfaceChatClient client : clients.values()) {
            try {
                client.diffuseMessage(msg);
            } catch (RemoteException e) {
                System.err.println("Failed to send message to " + msg.getPseudo() + ": " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServer();
            Naming.rebind("rmi://localhost:1099/ChatServer", server);

            System.out.println("Chat Server is running...");
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
