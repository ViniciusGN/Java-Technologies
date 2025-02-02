import java.io.Serializable;

/**
* The Message class represents a message sent in a chat system.
* It stores the content of the message (msg), the name of the user who sent it (pseudo),
* the server time (serverHour) and the local time of the client (clientHour).
*
* This class implements Serializable to allow Message objects
* to be transmitted over the network using RMI (Remote Method Invocation).
*/

public class Message implements Serializable {
    private String msg;
    private String pseudo;
    private String serverHour;
    private String clientHour;

    public Message(String pseudo, String msg, String serverHour, String clientHour) {
        this.pseudo = pseudo;
        this.msg = msg;
        this.serverHour = serverHour;
        this.clientHour = clientHour;
    }

    public String getMessage() {
        return msg;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getServerHour() {
        return serverHour;
    }

    public String getClientHour() {
        return clientHour;
    }

    @Override
    public String toString() {
        return String.format("[USR: %s - ServerHour: %s ClientHour: %s]> %s", 
                             pseudo, serverHour, clientHour, msg);
    }
}