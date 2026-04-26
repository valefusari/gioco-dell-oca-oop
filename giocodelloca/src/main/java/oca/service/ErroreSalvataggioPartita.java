package oca.service;

import oca.service.impl.Log;

/**
 * Eccezione che indica un errore durante il salvataggio della partita.
 */
public class ErroreSalvataggioPartita extends Exception {
    /**
     * Indica che si è verificato un errore durante il salvataggio della partita e registra l'evento nel file di log.
     */
    public ErroreSalvataggioPartita() {
        Log.getInstance().error("Errore nel salvataggio della partita.");
    }
}