import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interfaz extends Remote {
    void request(int id, int req) throws RemoteException;
    //void waitToken() throws RemoteException;
    //void takeToken(Token token) throws RemoteException;
    //void kill() throws RemoteException;
}
