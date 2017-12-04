/*
 * Main
 *
 * 1.0.0
 *
 * 2017, Diciembre 3
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class Main {

    private static class Rn {
        public int[] arrRN;
    }

    public static void main(String[] args) throws IOException {

        int id = Integer.parseInt(args[0]); //id del proceso
        int n = Integer.parseInt(args[1]);  //cantidad de procesos totales
        int initialDelay = Integer.parseInt(args[2]); //tiempo de espera para CS
        boolean bearer = Boolean.valueOf(args[3]);// si tiene el token al principio

        final Rn arreglo = new Rn();

        arreglo.arrRN = new int[n] ; //ultimo request recibido por el proceso j
        Token toquen = null; //token

        detectarMulti(id, arreglo.arrRN);//detecta mensaje en multicast

        crear(id, arreglo.arrRN, "VERDE");

        try{  //Da tiempo para crear los otros procesos
            Thread.sleep(10000);
        }catch( InterruptedException e ){
            System.out.println("sleep");
            e.printStackTrace();
        }

        System.out.println("Semaforo en VERDE");

        if(bearer){ //si parte con toquen o no
            toquen = new  Token(n);
            System.out.println("main: Creando Token");

        }

        try{
            Interfaz funciones = (Interfaz) Naming.lookup("/HelloServer");//aca se obtienen las funciones

            if(!bearer){    //requesting critical section
                System.out.println("Semaforo en AMARILLO");
                arreglo.arrRN[id] += 1;
                funciones.request(id, arreglo.arrRN[id]);
                System.out.println("request enviada");
                crear(id, arreglo.arrRN, "VERDE");
                toquen = funciones.waitToken(id);
            }

            try{  //esperar antes de la seccion critica
                Thread.sleep(initialDelay);
            }catch( InterruptedException e ){
                System.out.println("sleep");
                e.printStackTrace();
            }

            System.out.println("Semaforo en ROJO");

            escribir(id, arreglo.arrRN, toquen, "ROJO");

            System.out.println("arrRN del proceso" + Arrays.toString(arreglo.arrRN));

            /**releasing the cs**/
            System.out.println("main: soltando Token");
            toquen.printDatos();
            toquen.setLN(id, arreglo.arrRN[id]); //indicar al toquen que se hizo la CS
            toquen.updateQ(arreglo.arrRN); //agregar a la cola los procesos que piden la CS
            toquen.printDatos();
            escribir(id, arreglo.arrRN, toquen, "VERDE");
            System.out.println("Semaforo en VERDE");

            /** Mientras no todos los procesos
            terminen su seccion critica, el algoritmo
            continua **/
            if (toquen.listos() != n-1){
                while(toquen.check()){ //si no hay nada en la cola, esperar otros mensajes
                    toquen.updateQ(arreglo.arrRN);
                }
                funciones.takeToken(toquen);  //enviar el toquen
            }
            else{
                funciones.kill(); //terminar el algoritmo y los procesos
            }
            System.out.println("Este proceso ya hizo su parte");

       }catch (Exception e){
           System.out.println("HelloClient exception: " + e.getMessage());
           e.printStackTrace();
       }
    }

    /**
    *ESte metodo permita ejecutar un Thread que escucha las peticiones de los otros procesos por el token
    */
    public static void detectarMulti(int id, int[] rn) { //receiving a request

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

                        System.out.println("Desde " + sublista[0] + " se recibio " + sublista[1]);

                        if (recibID == -1){ //si el agoritmo ya finalizo
                            break;
                        }

                        if (recibID != id){ //si el mensaje recibido no es el propio

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


    /**
    *ESte metodo permita escribir en un archivo ya existente
    */
    public static void escribir(int id, int[] rn, Token toquen, String color){

        FileWriter fichero = null;
        PrintWriter pw = null;
        java.util.Date date = new java.util.Date();
        String archivo = "log" + Integer.toString(id) + ".txt";
        try
        {
            fichero = new FileWriter(archivo, true);
            pw = new PrintWriter(fichero);


            pw.println("************** " + date + " PROCESO " + id +"*******************");
            pw.println("Color semaforo: +" + color);
            pw.println("Arreglo rn del proceso:" + Arrays.toString(rn));
            pw.println("El arreglo LN del toquen" + toquen.getLN());
            pw.println("La cola del toquen" + toquen.getQ());
            pw.println("_________________________________________________________");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }

    }

    /**
    *ESte metodo permita escribir en un archivo que no existe
    */
    public static void crear(int id, int[] rn, String color){

        FileWriter fichero = null;
        PrintWriter pw = null;
        java.util.Date date = new java.util.Date();
        String archivo = "log" + Integer.toString(id) + ".txt";
        try
        {
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);


            pw.println("************** " + date + " PROCESO " + id +"*******************");
            pw.println("Color semaforo: +" + color);
            pw.println("Arreglo rn del proceso:" + Arrays.toString(rn));
            pw.println("_________________________________________________________");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }

    }


}
