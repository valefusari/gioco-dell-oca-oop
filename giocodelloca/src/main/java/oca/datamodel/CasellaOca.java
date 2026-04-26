package oca.datamodel;

/**
 * La classe CasellaOca rappresenta una casella del gioco dell'oca con effetto Oca.
 * Ogni casella ha un numero identificativo e può contenere uno o più giocatori.
 * Implementa l'interfaccia Serializable per permettere la serializzazione degli oggetti CasellaOca.
 */
public class CasellaOca extends Casella {
    /**
     * Attributo statico che memorizza il valore dell'ultimo lancio del dado,
     * che viene raddoppiato dall'effetto di queste caselle quando il giocatore vi transita.
     */
    private static int raddoppio;

    public CasellaOca(){
        super();
    }

    /**
     * Restituisce il valore del raddoppio effettuato dalla casella Oca.
     * 
     * @return il valore del raddoppio effettuato dalla casella Oca.
     */
    public static int getRaddoppioOca() {
        return raddoppio;
    }

    /**
     * Imposta il valore del raddoppio effettuato dalla casella Oca.
     * 
     * @param val il nuovo valore del raddoppio effettuato dalla casella Oca.
     */
    public static void setRaddoppioOca(int val) {
        raddoppio = val;
    }
}
