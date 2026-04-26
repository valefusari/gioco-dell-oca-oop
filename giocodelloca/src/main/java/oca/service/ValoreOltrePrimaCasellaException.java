package oca.service;

import oca.service.impl.Log;

/**
 * Eccezione che indica che il giocatore ha oltrepassato la prima casella.
 */
public class ValoreOltrePrimaCasellaException extends Exception {
    /**
     * Indica che il giocatore ha oltrepassato la prima casella e registra l'evento nel file di log.
     */
    public ValoreOltrePrimaCasellaException() {
        Log.getInstance().error("Il giocatore ha oltrepassato la prima casella!");
    }
}
