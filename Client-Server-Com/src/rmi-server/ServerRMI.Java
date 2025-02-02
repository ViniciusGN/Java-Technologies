import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ServerRMI extends UnicastRemoteObject implements InterfaceHourServer {

    // Construtor
    protected ServerRMI() throws RemoteException {
        super();
    }

    // Implementação do método remoto
    @Override
    public String getHour() throws RemoteException {
        Calendar calendar = new GregorianCalendar();
        return calendar.getTime().toString(); // Retorna a data e hora atuais
    }

    public static void main(String[] args) {
        try {
            // Criação da instância do servidor
            ServerRMI server = new ServerRMI();

            // Registro do serviço RMI no registro (Registry)
            Naming.rebind("rmi://localhost:1099/HourServer", server);

            System.out.println("RMI Server is running and ready to provide the current time.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
