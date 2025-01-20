import java.io.*;
import java.net.*;

public class Client {
public static void main(String[] args) throws IOException {
    final int port = 9092;
    Socket client = null;
    BufferedReader input = null;
    PrintWriter output = null;
    try {
        client = new Socket("127.0.0.1", port);
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(),true);
    }
    catch(UnknownHostException e) {
        System.out.println("Unknown Host ! ");
        System.exit(1);
    }
    catch(IOException ioe) {
        System.out.println("Cannot connect to the server");
        System.exit(1);
}