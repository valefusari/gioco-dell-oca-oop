package oca.datamodel;

import java.io.Serializable;
import java.util.*;

/**
 * La classe Casella rappresenta una casella del gioco dell'oca.
 * Ogni casella ha un numero identificativo e può contenere uno o più giocatori.
 * Implementa l'interfaccia Serializable per permettere la serializzazione degli oggetti Casella.
 */
public class Casella implements Serializable{

    /**
     * Numero identificativo della casella.
     */
    private int numCasella;

    /**
     * Lista dei giocatori presenti sulla casella.
     */
    private ArrayList<Giocatore> giocatori;
    
    /**
     * Costruttore di default che inizializza la casella con numero 0 e una lista vuota di giocatori.
     */
    public Casella() {
        this.numCasella = 0;
        this.giocatori = new ArrayList<Giocatore>();
    }

    /**
     * Restituisce il numero della casella.
     * 
     * @return il numero della casella.
     */
    public int getNumber() {
        return numCasella;
    }

    /**
     * Imposta il numero della casella.
     * 
     * @param number il nuovo numero della casella.
     */
    public void setNumber(int number) {
        this.numCasella = number;
    }

    /**
     * Restituisce la lista dei giocatori presenti sulla casella.
     * 
     * @return la lista dei giocatori presenti sulla casella.
     */
    public ArrayList<Giocatore> getGiocatori() {
        return giocatori;
    }

    /**
     * Aggiunge un giocatore alla casella se non è già presente.
     * 
     * @param g il giocatore da aggiungere.
     */
    public void addGiocatore(Giocatore g) {
        if (!giocatori.contains(g))
            giocatori.add(g);
    }

    /**
     * Rimuove un giocatore dalla casella.
     * 
     * @param g il giocatore da rimuovere.
     */
    public void removeGiocatore(Giocatore g) {
        giocatori.remove(g);
    }

    /**
     * Verifica se un giocatore è presente sulla casella.
     * 
     * @param g il giocatore da verificare.
     * @return true se il giocatore è presente, false altrimenti.
     */
    public boolean containsGiocatore(Giocatore g) {
        return giocatori.contains(g);
    }

    /**
     * Restituisce una rappresentazione in formato stringa della casella.
     * 
     * @return una stringa che rappresenta la casella.
     */
    @Override
    public String toString() {
        return "Casella{" + "Tipo: " + getClass().getSimpleName() + ", number=" + numCasella + " giocatori: " + giocatori + '}';
    }


    /**
     * Due caselle sono uguali se hanno lo stesso numero.
     * @param obj casella da confrontare
     * @return true se le caselle sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Casella other)) {
            return false;
        }
        return this.numCasella == other.numCasella;
    }
}
