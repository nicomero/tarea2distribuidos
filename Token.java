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

        int next = this.colaQ.poll();
        return  next;
    }

    public void agregarQ(int id){

        this.colaQ.add(id);

        return;
    }

    public void setLN(int id, int num){

        this.arrLN[id] = num;
        return;
    }

    public void updateQ(int[] rn){
        int k=0;
        for (int i : rn){
            if (i == this.arrLN[k]+1){
                if (!this.colaQ.contains(i)){
                    this.colaQ.add(i);
                }
            }
            k +=1;
        }
        return;
    }

}
