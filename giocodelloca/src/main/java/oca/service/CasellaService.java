package oca.service;

import oca.datamodel.Casella;
import oca.datamodel.*;

/**
 * Interfaccia per il servizio associato alla casella
 * @param <T> tipo della casella
 */
public interface CasellaService <T extends Casella> {
    public void effetto(Giocatore g);
}