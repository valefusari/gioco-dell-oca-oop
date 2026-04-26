package oca.service.impl;

import oca.datamodel.*;
import oca.service.CasellaService;

/**
 * Classe che implementa la casella Ponte
 */
public class CasellaPonteImpl extends CasellaServiceAbstractImpl<CasellaPonte> {
    /**
     * Metodo che implementa l'effetto della casella Ponte, cioè portare il giocatore alla casella 12
     * @param g giocatore che ha effettuato la mossa
     */    
    @Override 
    public void effetto(Giocatore g) {

        Log.getInstance().info("Inizio effetto casella Ponte");
        System.out.println("Effetto casella Ponte");
        // Applica la penitenza specifica della casella ponte
        applicaPenitenza(g);
        // Implementazione specifica per la casella ponte
        System.out.println("Il giocatore " + g.getNome() + " e' stato mandato alla casella 12.");
        int posIniziale = g.getPosizione();
        g.getGioco().getCaselle().get(g.getPosizione()).removeGiocatore(g);
        g.getGioco().getCaselle().get(12).addGiocatore(g);
        g.setPosizione(12);
        Casella arrivo = g.getGioco().getCaselle().get(12);
        CasellaService<? extends Casella> cs = CasellaServiceFactory.getCasellaService(arrivo.getClass());
        // Se la casella 12 è una casella oca e la casella ponte si trova in posizione (12 + raddoppio),
        // non chiamare l'effetto, si genererebbe un loop infinito con conseguente stack overflow
        if (!(posIniziale == 12+CasellaOca.getRaddoppioOca() && arrivo.getClass() == CasellaOca.class )) cs.effetto(g);
        Log.getInstance().info("Il giocatore "+g.getNome()+" è stato mandato alla casella 12");
    }
}
