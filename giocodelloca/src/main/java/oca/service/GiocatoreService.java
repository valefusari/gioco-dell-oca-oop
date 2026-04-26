package oca.service;

import java.util.ArrayList;

import oca.datamodel.Giocatore;
import oca.datamodel.Gioco;

/**
 * Interfaccia per la gestione dei giocatori
 */
public interface GiocatoreService <T extends Giocatore> {
    
    /**
     * Legge il numero di facce del dado (es. M) dal file di property e genera un numero casuale tra 1 e M
     */ 
    public int lanciaDado();
    public int sceltaTris();
    public ArrayList<Integer> roundMorra();
    public ModeEnumeration getMode();
    
    /**
     * Sposta il Giocatore g di n posizioni dalla propria casella di partenza
     * @param g giocatore di turno
     * @param n numero di caselle di cui avanza g
     */
    default void muoviGiocatore(Giocatore g, int n) throws ValoreOltreUltimaCasellaException, ValoreOltrePrimaCasellaException {
        int start = g.getPosizione();
        Gioco gioco = g.getGioco();
        if (start + n >= gioco.getCaselle().size()) {
            throw new ValoreOltreUltimaCasellaException();
        }
        if (start + n < 0) {
            throw new ValoreOltrePrimaCasellaException();
        }
        g.setPosizione(start + n);
        gioco.getCaselle().get(start).removeGiocatore(g);
        gioco.getCaselle().get(start + n).addGiocatore(g);
    }
}
