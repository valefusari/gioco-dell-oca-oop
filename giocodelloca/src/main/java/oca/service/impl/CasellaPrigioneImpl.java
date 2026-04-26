package oca.service.impl;

import oca.datamodel.CasellaPrigione;
import oca.datamodel.Giocatore;

/**
 * Classe che implementa la casella Prigione
 */
public class CasellaPrigioneImpl extends CasellaServiceAbstractImpl<CasellaPrigione> {
    /**
     * Metodo per gestire l'effetto della casella.
     * @param g il giocatore che ha attivato l'effetto della casella
     */
    @Override
    public void effetto(Giocatore g) {
        // Implementazione specifica per la casella prigione
        mandaInPrigione(g);
        System.out.println("Il giocatore " + g.getNome() + " e' stato mandato in prigione.");
        Log.getInstance().info("Il giocatore "+g.getNome()+" è stato mandato in prigione");
    }

    /**
     * Metodo per mandare un giocatore in prigione.
     * @param g il giocatore da mandare in prigione
     */
    static private void mandaInPrigione(Giocatore g) {
   
        int posizione = g.getPosizione();
        CasellaPrigione casellaPrigione = (CasellaPrigione) g.getGioco().getCaselle().get(posizione);
        
        if (casellaPrigione.isOccupata()) {
            Giocatore giocatoreInPrigione = casellaPrigione.getGiocatoreInPrigione();
            giocatoreInPrigione.setIsInPrigione(false);
            System.out.println("Il giocatore " + giocatoreInPrigione.getNome() + " e' stato liberato.");
            Log.getInstance().info("Il giocatore " + giocatoreInPrigione.getNome() + " e' stato liberato");
        }
        
        g.setIsInPrigione(true);
        casellaPrigione.setGiocatoreInPrigione(g);
    }    
}
