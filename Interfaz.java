import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interfaz extends Remote {
    void request(int id, int req) throws RemoteException;
    void waitToken() throws RemoteException;
    void takeToken() throws RemoteException;
    void kill() throws RemoteException;
}
