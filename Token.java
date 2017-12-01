import java.util.LinkedList;
import java.util.Queue;
import java.io.Serializable;

public class Token implements Serializable{


    int[] arrLN; //request mas exitoso reciente del proceso j en que el token se dio
    Queue<Integer> colaQ = new LinkedList<Integer>(); //IDs de procesos que esprean el token

    public Token(int n){
        arrLN = new int[n] ;

    }

    public int siguienteQ(){

        int next = colaQ.poll();
        return  next;
    }

    public void agregarQ(int id){

        colaQ.add(id);

        return;
    }

}
