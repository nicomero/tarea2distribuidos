

public class proceso {

    int id;
    static int n;
    int initialDelay;
    boolean bearer;
    static boolean hayBearer = false;
    int[] arrRN; //ultimo request recibido por el proceso j

    public proceso(int id, int n, int initialDelay, boolean bearer){

        this.id = id ;
        proceso.n = n ;
        this.arrRN = new int[n] ;
        this.initialDelay = initialDelay ;
        if (proceso.hayBearer){
            this.bearer = false;
        }
        else{
            this.bearer = bearer;
            proceso.hayBearer = bearer;
        }
    }

}
