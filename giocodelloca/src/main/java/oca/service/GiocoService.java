package oca.service;


import java.io.IOException;
import java.util.*;

import oca.datamodel.Casella;
import oca.datamodel.Gioco;

/**
 * Interfaccia per il servizio associato al gioco
 */
public interface GiocoService {
    /**
     * Carica una partita da file
     * @param nomeFile nome del file
     * @return partita caricata
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws ErroreCaricamentoPartita
     */
    public Gioco caricaPartita(String nomeFile) throws IOException, ClassNotFoundException, ErroreCaricamentoPartita;

    /**
     * Salva una partita su file
     * @param gioco partita da salvare
     * @param nomeFile nome del file
     * @throws ErroreSalvataggioPartita
     * @throws IOException
     */
    public void salvaPartita(Gioco gioco, String nomeFile) throws ErroreSalvataggioPartita, IOException;

    /**
     * Metodo per giocare una partita
     * @param gioco partita da giocare
     */
    public void gioca(Gioco gioco);

    /**
     * Metodo per controllare se la partita è finita
     * @param gioco partita da controllare
     * @return true se la partita è finita, false altrimenti
     */
    public boolean isFinita(Gioco gioco);

    /**
     * Metodo per generare il tabellone
     * @param numeroCaselle numero di caselle
     * @param gioco partita a cui associare il tabellone
     * @return tabellone generato come ArrayList di caselle
     */
    public ArrayList<Casella> generaTabellone(int numeroCaselle, Gioco gioco);

    /**
     * Metodo per creare una partita
     * @return partita creata
     * @throws ErroreCreazionePartita
     * @throws ErroreCaricamentoPartita
     */
    public Gioco creaPartita() throws ErroreCreazionePartita, ErroreCaricamentoPartita;
}
