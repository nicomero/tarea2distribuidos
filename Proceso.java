

public class Proceso {

    int id;
    static int n;
    int initialDelay;
    boolean bearer;
    static boolean hayBearer = false;
    int[] arrRN; //ultimo request recibido por el proceso j

    public Proceso(int id, int n, int initialDelay, boolean bearer){

        this.id = id ;
        Proceso.n = n ;
        this.arrRN = new int[n] ;
        this.initialDelay = initialDelay ;
        if (Proceso.hayBearer){
            this.bearer = false;
        }
        else{
            this.bearer = bearer;
            Proceso.hayBearer = bearer;
        }
    }

}
