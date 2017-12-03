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


        System.out.println("Semaforo en VERDE");

        if(bearer){ //si parte con toquen o no
            toquen = new  Token(n);
            System.out.println("main: Creando Token");

        }

        detectarMulti(id, arrRN);//detecta mensaje en multicast

        try{
            Interfaz funciones = (Interfaz) Naming.lookup("/HelloServer");//aca se obtienen las funciones

            if(!bearer){    //requesting critical section
                System.out.println("Semaforo en AMARILLO");
                arrRN[id] += 1;
                funciones.request(id, arrRN[id]);
                System.out.println("request enviada");
                toquen = funciones.waitToken(id);
            }
            try{
                Thread.sleep(initialDelay);
            }catch( InterruptedException e ){
                System.out.println("sleep");
                e.printStackTrace();
            }

            System.out.println("Semaforo en ROJO");
            System.out.println("arrRN del proceso" + Arrays.toString(arrRN));

            //releasing the cs
            System.out.println("main: soltando Token");
            toquen.setLN(id, arrRN[id]);
            toquen.updateQ(arrRN);
            toquen.printDatos();
            System.out.println("Semaforo en VERDE");
            funciones.takeToken(toquen);

       }catch (Exception e){
           System.out.println("HelloClient exception: " + e.getMessage());
           e.printStackTrace();
       }

    }

    public static void detectarMulti(int id, int[] rn){ //receiving a request
    		Thread t = new Thread(new Runnable(){
    			public void run(){

                    try{
                        String[] sublista;
                        MulticastSocket socket = new MulticastSocket(5555);

                        byte[] buf = new byte[256];
                        InetAddress address = InetAddress.getByName("230.0.0.1");
                        socket.joinGroup(address);
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        				while(true){
                            socket.receive(packet);
                            String received = new String(packet.getData(), 0, packet.getLength());
                            sublista = received.split(",");

                            int recibID = Integer.parseInt(sublista[0]);
                            int recibSEQ = Integer.parseInt(sublista[1]);

                            if (recibID != id){
                                System.out.println("Desde " + sublista[0] + " se recibio " + sublista[1]);

                                if (recibSEQ > rn[recibID]){
                                    rn[recibID] = recibSEQ;
                                }
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
