import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

// Implementation of the remote interface InterfaceHourServer
public class HourServer extends UnicastRemoteObject implements InterfaceHourServer {

    protected HourServer() throws RemoteException {
        super();
    }

    // Remote method that returns the current server time in HH:mm format
    @Override
    public String getHour() throws RemoteException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(calendar.getTime());
    }

    // Main method to start the time server
    public static void main(String[] args) {
        try {
            // Creating the time server instance
            HourServer hourServer = new HourServer();

            // Creating the RMI record on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Time server registration in RMI registry
            registry.rebind("HourServer", hourServer);

            System.out.println("HourServer is running and ready to provide the current time...");
        } catch (RemoteException e) {
            System.err.println("HourServer encountered an error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
