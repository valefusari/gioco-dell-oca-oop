package oca.service;

import oca.service.impl.Log;

/**
 * Eccezione che indica che il giocatore è arrivato oltre l'ultima casella.
 */
public class ValoreOltreUltimaCasellaException extends Exception {
    /**
     * Indica che il giocatore è arrivato oltre l'ultima casella e registra l'evento nel file di log.
     */
    public ValoreOltreUltimaCasellaException() {
        Log.getInstance().error("Il giocatore è arrivato oltre l'ultima casella");
    }
}