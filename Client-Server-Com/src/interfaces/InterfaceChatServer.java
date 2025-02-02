import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* Remote interface that defines methods for the chat server.
* Allows clients to connect, disconnect, and send messages
* to all connected users.
*/

public interface InterfaceChatServer extends Remote {
   /**
    * Connects a new client to the chat server.
    * @param pseudo Username (nickname).
    * @param clientUrl URL of the remote client object.
    * @throws RemoteException In case of RMI communication failure.
    */
    void connect(String pseudo, String clientUrl) throws RemoteException;

    /**
    * Disconnects a client from the chat server.
    * @param pseudo Name of the user who is disconnecting.
    * @throws RemoteException In case of RMI communication failure.
    */
    void disconnect(String pseudo) throws RemoteException;

    /**
    * Broadcasts a message to all connected clients.
    * @param msg Message object containing the message content and sender information.
    * @throws RemoteException In case of RMI communication failure.
    */
    void broadcastMessage(Message msg) throws RemoteException;
    String getServerHour() throws RemoteException;
}
