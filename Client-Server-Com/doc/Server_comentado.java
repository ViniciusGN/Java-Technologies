// Ref https://www.youtube.com/watch?v=grVaGVHYnEw&list=PLWZB9ryZXz1Psb8R2TmOgn16xc0ixhTCn&index=1
import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends UnicastRemoteObject implements InterfaceHourServer{
private ServerSocket serverSocket;

public Server() throws RemoteException {
    super();
}

private void newServerSocket(int port) throws IOException {
    // [1]: Create a new ServerSocket object
    // and bind it to the specified port number
    // [2]: I need to handle the exception because the port number might be already in use
    // or the port number is not available, every time that I deal with
    // network programming, I need to handle the exception

    // [OBS]: When I use throws IOException, I don't need to handle the exception
    // I leave this trailling to the caller of the method
    serverSocket = new ServerSocket(port) ; 
    System.out.println("Server started on port " + port);
    }

private Socket waitForConnection() throws IOException {
    // [1]: Wait for a client to connect to the server
    // [2]: When a client connects, the server will return a new Socket object
    // [3]: Same question for exception handling
    Socket socket = serverSocket.accept();      // blocking call that return a new socket object
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

/*  PROTOCOL
 * 1. The server will wait for a client to connect
 * 2. When a client connects, the server will create a new thread to handle the connection
 * 3. The server will wait for the client to send a message
 * 4. If the client sends a message, the server will list all files in path informed
 * 5. If client sends a invalid path, the server will close the connection and return -1
 */

private void ConexionHandler(Socket socket){    // Handling  the conexion for each protocol
    // [1]: Create a new thread to handle the connection
    // [2]: Pass the socket object to the thread
    // [3]: Start the thread
    // [4]: The thread will handle the connection
    
    // [OBS]: Here I need to create the input and output stream
    // and traitement of the conversation between the client and the server

    // get the input or output stream from the socket -> socket.getOutputStream();
    try {
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        output.writeUTF("SRV: Connection successful"); // write the object to the output stream

        // [OBS]: The methods getInput or Output deals with binary data, so I need to use 
        // Other schema for read and write strucutred data. OnjectInputStream and ObjectOutputStream 
        // is one of them that facilitate this service
        // [OBS]: I need to handle the exception because the socket might be closed (better handling) 
        String msg = input.readUTF();               // read the object from the input stream
        System.out.println("CLT: " + msg);          // print the message to the console
        output.writeUTF("SRV: " + msg);

        // TODO: Explain this protocol actions >
        Process process = Runtime.getRuntime().exec("ls " + msg);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while((line = reader.readLine()) != null){
            output.writeUTF(line = reader.readLine());
        }

        // close the input and output stream
        output.writeUTF("Connection terminated - Bye");
        input.close();
        output.close();
        reader.close();

        // < TODO: Explain this protocol actions
    } catch (IOException e) {
        System.out.println("-1");
        System.exit(1);
    } finally {
        closeConnection(socket);
    }            
}

// Implementação do método remoto getHour() que retorna a data e hora
@Override
public String getHour() throws RemoteException {
    // Obtém a data e hora atuais
    Calendar calendar = new GregorianCalendar();
    return calendar.getTime().toString(); // Retorna a data e hora no formato padrão
}

public static void main(String[] args) {
    try {                                       // handle the exception here in main
        Server server = new Server();               // create a new server object
        Server RMIserver = new Server();
        Naming.rebind("rmi://localhost:1099/HeureServeur", RMIserver);
        System.out.println("RMI Server is running...");


        server.newServerSocket(3000);      // start the server on port 3000
        Socket socket = server.waitForConnection();    // wait for a client to connect
        server.ConexionHandler(socket);         // handle the connection
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}