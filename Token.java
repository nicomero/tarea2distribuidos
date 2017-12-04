/*
 * Token
 *
 * 1.0.0
 *
 * 2017, Diciembre 3
 *
 */

import java.util.LinkedList;
import java.util.Queue;
import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class Token implements Serializable{


    int[] arrLN; //request mas exitoso reciente del proceso j en que el token se dio
    Queue<Integer> colaQ = new LinkedList<Integer>(); //IDs de procesos que esperean el token

    /**
    *Constructor del token
    */
    public Token(int n){

        arrLN = new int[n] ;
    }

    /**
    *Metodo siguienteQ permite acceder al siguiente valor en la cola, eliminandolo
    */
    public int siguienteQ(){

        int next = this.colaQ.poll();
        return  next;
    }

    /**
    *Metodo agregarQ permite agregar mas procesos a la cola
    */
    public void agregarQ(int id){

        this.colaQ.add(id);
        return;
    }

    /**
    *Metodo detLN permite actualizar el arreglo LN
    */
    public void setLN(int id, int num){
        System.out.println("SE SETEARA A " + id + " el valor " + num);
        this.arrLN[id] = num;
        return;
    }

    /**
    *Metodo updateQ permite agregar a la cola procesos que solicitaron el token
    */
    public void updateQ(int[] rn){

        int k=0;

        for (int i : rn){
            if (i == this.arrLN[k]+1){
                if (!this.colaQ.contains(k)){
                    this.colaQ.add(k);
                }
            }
            k +=1;
        }
        return;
    }

    /**
    *Metodo printDatos permita mostrar en pantalla la informacion del token
    */
    public void printDatos(){

        System.out.println("la cola del toquen " + this.colaQ);
        System.out.println("arrLN del toquen" + Arrays.toString(this.arrLN));
        return;
    }

    /**
    *Metodo listos permite contar cuantos procesos ya ejecutaron su SC
    */
    public int listos(){
        int sum = 0;
        for(int i : this.arrLN) {
            sum += i;
        }
        return sum;
    }

    /**
    *Metodo check permite ver si la cola esta vacia
    */
    public boolean check(){
        boolean esIgual;
        esIgual = this.colaQ.peek() == null;

        return esIgual;
    }

    public Queue<Integer> getQ(){

        return this.colaQ;
    }

    public String getLN(){

        return Arrays.toString(this.arrLN);
    }

}
