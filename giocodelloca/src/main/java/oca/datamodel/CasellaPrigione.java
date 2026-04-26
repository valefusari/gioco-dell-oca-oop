package oca.datamodel;

/**
 * La classe CasellaPrigione rappresenta una casella del gioco dell'oca con effetto Prigione.
 * Ogni casella ha un numero identificativo e può contenere uno o più giocatori.
 * Implementa l'interfaccia Serializable per permettere la serializzazione degli oggetti CasellaPrigione.
 */
public class CasellaPrigione extends Casella{
    /**
     * Attributo che rappresenta il giocatore in prigione
     */
    private Giocatore giocatoreInPrigione = null;

    /**
     * Costruttore della casella prigione
     */
    public CasellaPrigione(){
        super();
    }
    
    /**
     * @return il giocatore in prigione
     */
    public Giocatore getGiocatoreInPrigione() {
        return giocatoreInPrigione;
    }

    /**
     * Setter del giocatore in prigione
     * @param giocatoreInPrigione il giocatore da mandare in prigione
     */
    public void setGiocatoreInPrigione(Giocatore giocatoreInPrigione) {
        this.giocatoreInPrigione = giocatoreInPrigione;
    }

    /**
     * Metodo che verifica se la casella è occupata
     * @return true se la casella è occupata, false altrimenti
     */
    public boolean isOccupata(){
        return giocatoreInPrigione != null;
    }
   
}
