package oca.service;

import oca.service.impl.Log;

/**
 * Eccezione che indica un errore durante la creazione della partita.
 */
public class ErroreCreazionePartita extends Exception {
    /**
     * Indica che si è verificato un errore durante la creazione della partita e registra l'evento nel file di log.
     */
    public ErroreCreazionePartita() {
        Log.getInstance().error("Errore nella creazione della partita.");
    }
}
