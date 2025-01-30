import java.io.*;
import java.net.*;

public class Client {
public static void main(String[] args) throws IOException {
    final int port = 3000;
    Socket client = null;
    BufferedReader input = null;
    PrintWriter output = null;
    try {
        client = new Socket("CLT: 127.0.0.1", port);
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(),true);
    }
    catch(UnknownHostException e) {
        System.out.println("CLT: Unknown Host ! ");
        System.exit(1);
    }
    catch(IOException ioe) {
        System.out.println("CLT: Cannot connect to the server");
        System.exit(1);
    }

    System.out.println("CLT: Connection successful to the server:" + client.getRemoteSocketAddress());
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    String userInput;
    
    System.out.print("Path: Please, insert one path in Linux");
    userInput = stdin.readLine();
    output.println(userInput);
    String res = input.readLine();
    if (res == "-1"){
        System.out.println("SRV: error");
    } else{
        System.out.println(res);
    }
    
    stdin.close();
    output.close();
    input.close();
    client.close();
    }
}