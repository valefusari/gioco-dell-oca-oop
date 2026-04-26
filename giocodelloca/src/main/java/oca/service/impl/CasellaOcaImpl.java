package oca.service.impl;

import oca.datamodel.Casella;
import oca.datamodel.CasellaOca;
import oca.datamodel.Giocatore;
import oca.datamodel.Gioco;
import oca.service.CasellaService;
import oca.service.GiocatoreService;
import oca.service.ValoreOltreUltimaCasellaException;
import oca.service.ValoreOltrePrimaCasellaException;

/**
 * Classe che implementa la casella Oca
 */
public class CasellaOcaImpl extends CasellaServiceAbstractImpl<CasellaOca> {
    /**
     * Metodo che implementa l'effetto della casella Oca
     * @param g giocatore che ha effettuato la mossa
     */
    @Override
    public void effetto(Giocatore g) {
        int valore = CasellaOca.getRaddoppioOca();
        System.out.println("Il giocatore " + g.getNome() + " puo' avanzare due volte il valore del lancio del dado che ha appena effettuato!");
        Gioco gioco = g.getGioco();
        GiocatoreService<? extends Giocatore> giocatoreService;
        if (g.getIsUmano()) giocatoreService = new GiocatoreRealeServiceImpl();            
        else giocatoreService = new GiocatoreBotServiceImpl();
        try {
            giocatoreService.muoviGiocatore(g, valore); 
            Casella arrivo = gioco.getCaselle().get(g.getPosizione());
            System.out.println("Il giocatore si trova nella casella "+arrivo.getNumber());
            CasellaService<? extends Casella> cs = CasellaServiceFactory.getCasellaService(arrivo.getClass());   
            cs.effetto(g);
            Log.getInstance().info("Il giocatore "+g.getNome()+" e' saltato alla casella "+arrivo.getNumber());
        } catch (ValoreOltreUltimaCasellaException e) {
            System.out.println("Il giocatore ha oltrepassato il numero di caselle totali!");
            int diff = (g.getPosizione() + valore) + 1 - gioco.getCaselle().size();
            System.out.println("Verra' retrocesso di " + diff + " caselle rispetto all'uitima casella.");
            int indexArrivo = gioco.getCaselle().size() - (diff + 1);
            gioco.getCaselle().get(g.getPosizione()).removeGiocatore(g);
            gioco.getCaselle().get(indexArrivo).addGiocatore(g);
            g.setPosizione(indexArrivo);
            Casella arrivo = gioco.getCaselle().get(g.getPosizione());
            System.out.println("Il giocatore si trova nella casella " + arrivo.getNumber());
            CasellaService<? extends Casella> cs = CasellaServiceFactory.getCasellaService(arrivo.getClass());   
            cs.effetto(g);
            Log.getInstance().info("Il giocatore "+g.getNome()+" è saltato alla casella "+arrivo.getNumber());
        } catch (ValoreOltrePrimaCasellaException e) {
            System.out.println("Il giocatore e' stato retrocesso più indietro della casella 0!");
            System.out.println("Questo non puo' accadere, verrà posizionato nella casella 0.");
            gioco.getCaselle().get(g.getPosizione()).removeGiocatore(g);
            gioco.getCaselle().get(0).addGiocatore(g);
            g.setPosizione(0);
        }     
    }
}
