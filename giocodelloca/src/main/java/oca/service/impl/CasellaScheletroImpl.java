package oca.service.impl;

import oca.datamodel.CasellaScheletro;
import oca.datamodel.Giocatore;

/**
 * Classe che implementa la casella Scheletro
 */
public class CasellaScheletroImpl extends CasellaServiceAbstractImpl<CasellaScheletro>{

    /**
     * Metodo per gestire l'effetto della casella.
     * @param g il giocatore che ha attivato l'effetto della casella
     */
    @Override
    public void effetto(Giocatore g) {        
        System.out.println("Effetto casella Scheletro");
        applicaPenitenza(g);
        // Ricominciare dalla casella di partenza
        int posizioneIniziale = 0;
        g.getGioco().getCaselle().get(g.getPosizione()).removeGiocatore(g); // Rimuove il giocatore dalla posizione attuale
        g.setPosizione(posizioneIniziale); // Imposta la nuova posizione del giocatore
        g.getGioco().getCaselle().get(posizioneIniziale).addGiocatore(g); // Aggiunge il giocatore alla nuova posizione
        System.out.println(g.getNome() + " e' stato riportato alla casella di partenza.");
        /* La prima casella non ha effetti per costruzione, quindi non è necessario chiamare il metodo effetto*/
        Log.getInstance().info("Il giocatore "+g.getNome()+" è stato riportato alla casella di partenza");
    }
}
