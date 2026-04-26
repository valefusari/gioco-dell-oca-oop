package oca.service;

/**
 * Eccezione che indica un errore durante la creazione del file di log.
 */
public class ErroreCreazioneLog extends Exception {
    /**
     * Indica che si è verificato un errore durante la creazione del file di log e stampa un messaggio di errore.
     */
    public ErroreCreazioneLog(String message) {
        System.out.println(message);
    }
}
