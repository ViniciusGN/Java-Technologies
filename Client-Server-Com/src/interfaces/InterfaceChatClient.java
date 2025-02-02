import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* Remote interface that defines the method for the chat client.
* Allows the server to send messages to the client.
*/
public interface InterfaceChatClient extends Remote {
    /**
    * Remote method called by the server to broadcast a message to the client.
    * @param msg Message object containing the message content and sender information.
    * @throws RemoteException In case of RMI communication failure.
    */
    void diffuseMessage(Message msg) throws RemoteException;
}