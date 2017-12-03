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
            String mensaje = Integer.toString(id)+ "," + Integer.toString(req);
            buf = mensaje.getBytes();

            InetAddress group = InetAddress.getByName("230.0.0.1");
            DatagramSocket socket = new DatagramSocket();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 5555);
            System.out.println("main: request realizada");

            socket.send(packet);
        }catch(IOException e) {
        			System.out.println("request");
                    e.printStackTrace();
                }

    }

    public Token waitToken(int id){

        Token serverResponse;
        System.out.println("iniciando espera");
        try{


            byte[] buf = new byte[256];
            DatagramSocket socket = new DatagramSocket(id+4000);
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);


            try{
                ByteArrayInputStream serializado = new ByteArrayInputStream(buf);
                ObjectInputStream is = new ObjectInputStream(serializado);
                serverResponse = (Token)is.readObject();
                is.close();
                System.out.println("main: token obtenido");
                serverResponse.printDatos();
                return serverResponse;
            }catch(ClassNotFoundException e){
                System.out.println("waitToken class not found");
                e.printStackTrace();
            }
        }catch(IOException e){
            System.out.println("waitToken");
            e.printStackTrace();
        }
        return null;
    }



    public void takeToken(Token toquen){

        try{
            toquen.printDatos();
            int proximo = toquen.siguienteQ();
            System.out.println("el siguiente es: "+proximo);

            ByteArrayOutputStream serial = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(serial);
            os.writeObject(toquen);
            os.close();

            byte[] bufMsg = serial.toByteArray();
            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packetRequest = new DatagramPacket(bufMsg, bufMsg.length, address, proximo+4000);
            socket.send(packetRequest);
            System.out.println("main: token enviado");
        }catch(IOException e){
            System.out.println("takeToken");
            e.printStackTrace();
        }
    }


    public static void main(String args[])
    {
        try
        {
            Servidor obj = new Servidor();
            // Bind this object instance to the name "HelloServer"
            Naming.rebind("HelloServer", obj);
            System.out.println("Servidor: funciones en registry");
        }
        catch (Exception e)
        {
            System.out.println("HelloImpl err: " + e.getMessage());
            e.printStackTrace();
        }
    }



}
