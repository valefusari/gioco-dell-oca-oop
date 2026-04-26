package oca.service.impl;

import oca.datamodel.CasellaLocanda;
import oca.datamodel.Giocatore;

/**
 * Classe che implementa la casella Locanda
 */
public class CasellaLocandaImpl extends CasellaServiceAbstractImpl<CasellaLocanda> {
    /**
     * Metodo per gestire l'effetto della casella.
     * @param g il giocatore che ha attivato l'effetto della casella
     */
    @Override
    public void effetto(Giocatore g){
        System.out.println("Effetto casella Locanda");
        // Applica la penitenza specifica della casella locanda
        applicaPenitenza(g);
        // Il giocatore rimane fermo per un turno
        g.setTurniDaSaltare(g.getTurniDaSaltare() + 1);
        System.out.println(g.getNome() + " deve rimanere fermo per un turno.");
    }
}
