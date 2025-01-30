import java.rmi.*;

public interface InterfaceHourServer extends Remote {
    // Método remoto que retorna a data e hora
    public String getHour() throws RemoteException;
}
