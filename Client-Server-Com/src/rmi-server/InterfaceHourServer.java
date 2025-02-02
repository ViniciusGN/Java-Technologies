import java.rmi.*;

public interface InterfaceHourServer extends Remote {
    // Remote method that returns the date and time
    public String getHour() throws RemoteException;
}
