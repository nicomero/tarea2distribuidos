import java.io.*;
import java.net.*;
import java.util.*;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class main {

    public static void main(String[] args) throws IOException {

        int id = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int initialDelay = Integer.parseInt(args[2]);
        boolean bearer = Boolean.valueOf(args[3]);
        int[] arrRN = new int[n] ; //ultimo request recibido por el proceso j

        detectarMulti();

        try{
          Interfaz obj = (Interfaz) Naming.lookup("/HelloServer");         //objectname in registry

          obj.request(id, 2);

       }catch (Exception e){
          System.out.println("HelloClient exception: " + e.getMessage());
          e.printStackTrace();
       }

    }

    public static void detectarMulti(){
    		Thread t = new Thread(new Runnable(){
    			public void run(){

                    try{
                        MulticastSocket socket = new MulticastSocket(5555);

                        byte[] buf = new byte[256];
                        InetAddress address = InetAddress.getByName("230.0.0.1");
                        socket.joinGroup(address);
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        				while(true){
                            socket.receive(packet);
                            String received = new String(packet.getData(), 0, packet.getLength());
                            System.out.println(received);
        				}
                    }catch(IOException e){
                        System.out.println("detectarMulti");
                        e.printStackTrace();
                    }

                }
    		});
    		t.start();
    	}//end detectarMulti


}
