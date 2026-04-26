package oca.datamodel;

/**
 * Classe che rappresenta un giocatore bot.
 */
public class GiocatoreBot extends Giocatore {
    /**
     * Costruttore di default che inizializza il giocatore con il nome passato come parametro.
     * @param gioco riferimento al gioco
     * @param nome nome del giocatore
     */
    public GiocatoreBot(Gioco gioco, String nome) {
        super(gioco, nome, 0, false);
    }

}

