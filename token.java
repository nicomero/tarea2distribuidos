import java.util.LinkedList;
import java.util.Queue;
import java.io.Serializable;

public class token implements Serializable{


    int[] arrLN; //request mas exitoso reciente del proceso j en que el token se dio
    Queue<Integer> colaQ; //IDs de procesos que esprean el token

}
