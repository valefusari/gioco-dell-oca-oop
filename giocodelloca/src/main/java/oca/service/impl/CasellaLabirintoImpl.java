package oca.service.impl;

import oca.datamodel.Casella;
import oca.datamodel.CasellaLabirinto;
import oca.datamodel.CasellaOca;
import oca.datamodel.Giocatore;
import oca.service.GiocatoreService;
import oca.service.CasellaService;
import oca.service.ValoreOltrePrimaCasellaException;
import oca.service.ValoreOltreUltimaCasellaException;

/**
 * Classe che implementa la casella Labirinto
 */
public class CasellaLabirintoImpl extends CasellaServiceAbstractImpl<CasellaLabirinto>{
    /**
     * Metodo che implementa l'effetto della casella Labirinto
     * @param g giocatore che ha effettuato la mossa
     */
    @Override
    public void effetto(Giocatore g) {
        Log.getInstance().info("Inizio effetto casella Labirinto!");
        System.out.println("Effetto casella Labirinto");
        applicaPenitenza(g);
        // Il giocatore retrocede di 4 caselle dietro la casella labirinto
        try {
            GiocatoreService<? extends Giocatore> giocatoreService= g.getIsUmano() ? new GiocatoreRealeServiceImpl() : new GiocatoreBotServiceImpl();
            giocatoreService.muoviGiocatore(g, -4);
            System.out.println("Il giocatore " + g.getNome() + " retrocede alla casella " + g.getPosizione());
            Casella arrivo = g.getGioco().getCaselle().get(g.getPosizione());
            CasellaService<? extends Casella> cs = CasellaServiceFactory.getCasellaService(arrivo.getClass());
            // Se la casella di arrivo è un'oca e il raddoppio è 4, non chiamare l'effetto, si generebbe un loop infinito
            // con conseguente stack overflow
            if (!(arrivo.getClass() == CasellaOca.class && CasellaOca.getRaddoppioOca() == 4)) cs.effetto(g);
            Log.getInstance().info("Il giocatore "+g.getNome()+" è stato retrocesso alla casella "+arrivo.getNumber());
        } catch (ValoreOltrePrimaCasellaException e) {
            // Gestione dell'eccezione, riportare il giocatore alla posizione iniziale
            g.setPosizione(0);
            g.getGioco().getCaselle().get(0).addGiocatore(g);
            System.out.println("Il giocatore " + g.getNome() + " e' stato riportato alla casella iniziale.");
            //La prima casella non ha effetti per costruzione, quindi non è necessario chiamare il metodo effetto
            Log.getInstance().info("Il giocatore "+g.getNome()+" è ritornato alla prima casella");
        } catch (ValoreOltreUltimaCasellaException e) {
            Log.getInstance().error("Eccezione ValoreOltreUltimaCasellaException scaturita senza motivo.\n"+e.getMessage());
        }
    }

}
