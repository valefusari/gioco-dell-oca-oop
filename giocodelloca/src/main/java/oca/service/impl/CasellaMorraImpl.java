package oca.service.impl;

import oca.datamodel.Giocatore;
import oca.service.GiocatoreService;

import org.fusesource.jansi.Ansi;

import java.util.ArrayList;

/**
 * Classe che implementa la casella Morra
 */
public class CasellaMorraImpl extends MiniGameImpl {
    /**
     * Metodo per gestire una partita di morra tra due giocatori.
     * @param g1 il primo giocatore
     * @param g2 il secondo giocatore
     */
    @Override
    protected void partita(Giocatore g1, Giocatore g2) {
        int punteggioG1 = 0;
        int punteggioG2 = 0;
        
        ArrayList<Integer> manoGiocatore1, manoGiocatore2;
        GiocatoreService<? extends Giocatore> gs1 = (g1.getIsUmano()) ? new GiocatoreRealeServiceImpl() : new GiocatoreBotServiceImpl();       
        GiocatoreService<? extends Giocatore> gs2 = (g2.getIsUmano()) ? new GiocatoreRealeServiceImpl() : new GiocatoreBotServiceImpl(); 
        // in questo modo il giocatore umano sarà sempre il primo a giocare, senza conoscerne la mano del bot
        if (!g1.getIsUmano() && g2.getIsUmano()) {
            Giocatore temp = g1;
            g1 = g2;
            g2 = temp;
            GiocatoreService<? extends Giocatore> tempGs = gs1;
            gs1 = gs2;
            gs2 = tempGs;
        }
        
        System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a( "Sei arrivato in una casella Morra!").reset().toString());
        System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a( "Si sfideranno i giocatori " + g1.getNome() + " e " + g2.getNome()).reset().toString());      
        
        for(int i = 0; i<3; i++) {
            System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a("Round "+ (i+1)).reset().toString());  
            System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a(g1.getNome() + ": " ).reset().toString()); 
            manoGiocatore1 = gs1.roundMorra();
            System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a(g2.getNome() + ": " ).reset().toString()); 
            manoGiocatore2 = gs2.roundMorra();        
            int somma = manoGiocatore1.get(0) + manoGiocatore2.get(0);
            if (manoGiocatore1.get(1) != somma && manoGiocatore2.get(1) == somma) {
                System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("Risultato "+(i+1)+"o turno: "+g2.getNome() + " ha vinto").reset().toString());
                punteggioG2++;
                Log.getInstance().info("Risultato "+(i+1)+"° turno: "+g2.getNome() + " ha vinto");
            }
            else if (manoGiocatore1.get(1) == somma && manoGiocatore2.get(1) != somma) {
                System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("Risultato "+(i+1)+"o turno: " + g1.getNome() + " ha vinto").reset().toString());
                punteggioG1++;
                Log.getInstance().info("Risultato "+(i+1)+"° turno: " + g1.getNome() + " ha vinto");
            } else {
                System.out.println("Risultato "+(i+1)+"o turno: pareggio");
                Log.getInstance().info("Risultato "+(i+1)+"° turno: pareggio");
            }
        }
        if (punteggioG1 > punteggioG2) {
            setVincitore(g1);
            Log.getInstance().info("Risultato duello morra: " + g1.getNome() + " ha vinto");
        } else if (punteggioG2 > punteggioG1) {
            setVincitore(g2);
            Log.getInstance().info("Risultato duello morra: "+g2.getNome()+" ha vinto");
        }else{
            Log.getInstance().info("Risultato duello morra: pareggio");
        }
        System.out.println("Punteggio "+g1.getNome()+": "+punteggioG1);
        System.out.println("Punteggio "+g2.getNome()+": "+punteggioG2);
    }
}

