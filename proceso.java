

public class proceso {

    int id;
    static int n = 0;
    int initialDelay;
    boolean bearer;
    static boolean hayBearer = false;

    public proceso(int id, int n, int initialDelay, boolean bearer){

        this.id = id ;
        this.n = n ;
        this.initialDelay = initialDelay ;
        if (this.hayBearer){
            this.bearer = false;
        }
        else{
            this.bearer = bearer;
            this.hayBearer = bearer;
        }
    }

}
