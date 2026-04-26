package oca.service.impl;

import oca.service.CasellaService;
import oca.service.Penitenze;

import java.util.Random;
import java.util.List;

import oca.datamodel.*;

/**
 * Classe astratta che implementa i metodi comuni a tutte le caselle.
 * @param <T> il tipo di casella
 */
public abstract class CasellaServiceAbstractImpl<T extends Casella> implements CasellaService<T>{

    /**
     * Metodo per gestire l'effetto della casella.
     * @param g il giocatore che ha attivato l'effetto della casella
     */
    @Override
    public void effetto(Giocatore g) {}

    /**
     * Metodo per applicare una penitenza al giocatore.
     * @param g il giocatore a cui applicare la penitenza
     */
    protected void applicaPenitenza(Giocatore g) {
        Penitenze p = generaPenitenzaCasuale();
        System.out.println("Il giocatore " + g.getNome() + " e' costretto a: " + p);
        Log.getInstance().info("Il giocatore "+g.getNome()+" ha subito la penitenza: "+p);
    }

    /**
     * Metodo per generare una penitenza casuale.
     * @return la penitenza generata
     */
    private Penitenze generaPenitenzaCasuale() {
        // Ottiene tutte le possibili penitenze dall'enumerazione Penitenze
        List<Penitenze> penitenze = List.of(Penitenze.values());
        
        // Crea un'istanza della classe Random per generare numeri casuali
        Random random = new Random();
        
        // Seleziona una penitenza casuale dall' array delle penitenze
        return penitenze.get(random.nextInt(penitenze.size()));
    }
}


