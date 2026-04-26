package oca.datamodel;

/**
 * Classe che rappresenta un giocatore reale.
 */
public class GiocatoreReale extends Giocatore {
    /**
     * Costruttore di default che inizializza il giocatore con il nome passato come parametro.
     * @param gioco partita a cui il giocatore partecipa
     * @param nome nome del giocatore
     */
    public GiocatoreReale(Gioco gioco, String nome) {
        super(gioco, nome, 0, true);
    }

}
