// Ref https://www.youtube.com/watch?v=grVaGVHYnEw&list=PLWZB9ryZXz1Psb8R2TmOgn16xc0ixhTCn&index=1
import java.net.*;
import java.io.*;

pulic class Server {
private ServerSocket serverSocket;
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

private void waitForConnection() throws IOException {
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
 * 4. If the client sends a message, the server will send a message back to the client
 * 5. If client sends a empty message, the server will close the connection
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
        output.writeUTF("SRV: Hello from the server"); // write the object to the output stream

        // [OBS]: The methos getInput or Output deals with binary data, so I need to use 
        // Other schema for read and write strucutred data. OnjectInputStream and ObjectOutputStream 
        // is one of them that facilitate this service
        // [OBS]: I need to handle the exception because the socket might be closed (better handling) 
        String msg = input.readUTF();               // read the object from the input stream
        System.out.println("CLT: " + msg);          // print the message to the console
        output.writeUTF("SRV: " + msg);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {                                   // close the connection when the client sends an empty message
        // close the input and output stream
        input.close();
        output.close();        
        closeConnection(socket);
    }            
}

public static void main(String[] args) {
    Server server = new Server();               // create a new server object
    try {                                       // handle the exception here in main
        server.newServerSocket(3000);           // start the server on port 3000
        server.waitForConnection();             // wait for a client to connect
        server.ConexionHandler(socket);         // handle the connection
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}