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
        Token toquen = null;
        DatagramSocket socket = new DatagramSocket(id+4000);

        if(bearer){ //si parte con toquen o no
            toquen = new  Token(n);
        }

        detectarMulti(id);//detecta mensaje en multicast

        try{
            Interfaz funciones = (Interfaz) Naming.lookup("/HelloServer");//aca se obtienen las funciones

            if(!bearer){    //requesting critical section
                arrRN[id] += 1;
                funciones.request(id, 2);
                toquen = funciones.waitToken(socket);
            }
            Thread.sleep(id);//pausar

            //releasing the cs
            toquen.setLN(id, arrRN[id]);
            toquen.updateQ(arrRN);
            funciones.takeToken(toquen);

       }catch (Exception e){
           System.out.println("HelloClient exception: " + e.getMessage());
           e.printStackTrace();
       }

    }

    public static void detectarMulti(int id){ //receiving a request
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

                            int recibID = Integer.parseInt(received);
                            if (recibID != id){
                                System.out.println(received);
                            }
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
