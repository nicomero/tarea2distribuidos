import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.net.*;
import java.util.*;



public class Servidor extends UnicastRemoteObject implements Interfaz{

    public Servidor() throws RemoteException {}

    public void request(int id, int req){
        try{
            byte[] buf = new byte[256];

            buf = "afsdfdasdfas".getBytes();

            InetAddress group = InetAddress.getByName("230.0.0.1");
            DatagramSocket socket = new DatagramSocket();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 5555);

            socket.send(packet);
        }catch(IOException e) {
        			System.out.println("request");
                    e.printStackTrace();
                }

    }


}