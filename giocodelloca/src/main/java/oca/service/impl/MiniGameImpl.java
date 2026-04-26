package oca.service.impl;

import java.util.ArrayList;

import org.fusesource.jansi.Ansi;

import oca.datamodel.Casella;
import oca.datamodel.Gioco;
import oca.datamodel.Giocatore;

/**
 * Classe astratta che implementa i metodi comuni a tutti i mini giochi.
 */
public abstract class MiniGameImpl extends CasellaServiceAbstractImpl<Casella> {
    protected Giocatore vincitore;

    /**
     * Metodo per gestire l'effetto della casella.
     * @param g il giocatore che ha attivato l'effetto della casella
     */
    @Override
    public void effetto(Giocatore g) {
        miniGioco(g.getGioco(), g);
    }

    /**
     * Metodo per scambiare due giocatori di posto sul tabellone.
     * @param g1 il primo giocatore
     * @param g2 il secondo giocatore
     */
    protected void swapGiocatori (Giocatore g1, Giocatore g2) {
        int posGiocatore1 = g1.getPosizione(), posGiocatore2 = g2.getPosizione();
        int turniDaSaltare1 = g1.getTurniDaSaltare(), turniDaSaltare2 = g2.getTurniDaSaltare();
        g1.getGioco().getCaselle().get(posGiocatore2).addGiocatore(g1);
        g1.getGioco().getCaselle().get(posGiocatore1).removeGiocatore(g1);
        g1.setPosizione(posGiocatore2);
        g2.getGioco().getCaselle().get(posGiocatore1).addGiocatore(g2);
        g2.getGioco().getCaselle().get(posGiocatore2).removeGiocatore(g2);
        g2.setPosizione(posGiocatore1);
        // scambio dei turni da saltare se almeno uno dei due giocatori è in una casella locanda
        g1.setTurniDaSaltare(turniDaSaltare2);
        g2.setTurniDaSaltare(turniDaSaltare1);
        // scambio del flag isInPrigione se uno dei due giocatori è in prigione
        if (g1.getIsInPrigione()) {
            g1.setIsInPrigione(false);
            g2.setIsInPrigione(true);
        }
        else if (g2.getIsInPrigione()) {
            g2.setIsInPrigione(false);
            g1.setIsInPrigione(true);
        }
        
        System.out.println("I giocatori coinvolti nella sfida sono stati scambiati di posto!");
    }

    /**
     * Metodo per impostare il vincitore della partita.
     * @param g il giocatore vincitore
     */
    protected void setVincitore(Giocatore g) {
        vincitore = g;
    }

    abstract protected void partita(Giocatore g1, Giocatore g2);

    /**
     * Metodo per gestire il mini gioco tra due giocatori.
     * @param gioco il gioco in cui si svolge il mini gioco
     * @param g il giocatore che ha attivato il mini gioco
     */
    protected void miniGioco(Gioco gioco, Giocatore g){
        ArrayList<Giocatore> giocatori = gioco.getGiocatori();
        Giocatore ultimo = g;
        Giocatore primo = g;
        boolean swap = false;
        // un solo for each per acquisire il primo e l'ultimo giocatore sul tabellone
        for(Giocatore giocatore: giocatori){
            if(giocatore.getPosizione() < ultimo.getPosizione()){
                ultimo = giocatore;
            }
            if(giocatore.getPosizione() > primo.getPosizione()){
                primo = giocatore;
            }
        }
        // g si sfida col primo se si trova più indietro di tutti gli altri, altrimenti con l'ultimo
        if (ultimo == g) {
            partita(g, primo);
            if (g.equals(vincitore)) {
                swapGiocatori(g, primo); 
                swap = true;
            }
        }      
        else {
            partita(g, ultimo);
            if (ultimo.equals(vincitore)) {
                swapGiocatori(ultimo, g); 
                swap = true;
            }
        }
        if (vincitore != null) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a(vincitore.getNome() + " ha vinto la sfida!").reset().toString());
            if (swap) System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("I giocatori sono stati scambiati").reset().toString());
        }
        else System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("La sfida e' finita in pareggio!").reset().toString());
    }
}


