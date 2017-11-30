import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Servidor extends UnicastRemoteObject implements Interfaz{

    public void request(int id, int req){

        byte[] buf = new byte[256];

        InetAddress group = InetAddress.getByName("230.0.0.1");
        socket = new DatagramSocket();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);

    }


}
