package oca.service;

import oca.service.impl.Log;

/**
 * Eccezione che indica un errore durante il caricamento della partita.
 */
public class ErroreCaricamentoPartita extends Exception {
    /**
     * Indica che si è verificato un errore durante il caricamento della partita e registra l'evento nel file di log.
     */
    public ErroreCaricamentoPartita() {
        Log.getInstance().error("Errore nel caricamento della partita.");
    }
}
