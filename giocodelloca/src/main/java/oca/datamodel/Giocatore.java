package oca.datamodel;

import java.io.Serializable;

/**
 * Questa classe rappresenta un giocatore all'interno del gioco.
 * Ogni giocatore ha un nome, una posizione, un ID univoco, un booleano che indica se è umano o meno, un booleano che indica se è in prigione, un intero che indica il numero di turni in prigione e un intero che indica il numero di turni da saltare
 */
public abstract class Giocatore implements Serializable {

    static private int contatoreGiocatori = 0;

    private String nome;
    private int turniInPrigione;
    private int turniDaSaltare=0;
    private final Gioco gioco;
    private int posizione;
    private final int IDGiocatore;
    private boolean isUmano;
    private boolean isInPrigione=false;

    /**
     * Costruttore della classe Giocatore
     * @param gioco partita a cui il giocatore partecipa
     * @param nome nome del giocatore
     * @param posizione posizione del giocatore
     * @param isUmano booleano che indica se il giocatore è umano
     */
    public Giocatore (Gioco gioco, String nome, int posizione, boolean isUmano) {
            this.gioco = gioco;
            this.nome = nome;
            this.posizione = posizione;
            this.isUmano = isUmano;
            this.IDGiocatore = ++contatoreGiocatori;
        }

    /**
     * Una volta creato il giocatore, viene assegnato a una partita e non può più essere cambiata
     * Non ci sarà quindi un metodo setGioco
     * @return partita a cui il giocatore partecipa
     */
    public Gioco getGioco() {
        return gioco;
    }

    /**
     * @return nome del giocatore
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Setta il nome del giocatore
     * @param nome nome del giocatore
     */
    public void setNome(String nome){
        this.nome=nome;
    }

    /**
     * Metodo che indica se il giocatore è umano o meno
     * @return true se il giocatore è umano, false altrimenti
     */
    public boolean getIsUmano(){
        return this.isUmano;
    }

    /**
     * Imposta se il giocatore è umano o meno.
     */
    public void setIsUmano(boolean isUmano){
        this.isUmano=isUmano;
    }

    /**
     * @return true se il giocatore è in prigione, false altrimenti.
     */
    public boolean getIsInPrigione() {
        return isInPrigione;
    }

    /**
     * Imposta se il giocatore è in prigione o meno.
     * @param isInPrigione booleano che indica se il giocatore è in prigione
     */
    public void setIsInPrigione(boolean isInPrigione) {
        this.isInPrigione = isInPrigione;
    } 

    /**
     * @return il numero di turni che il giocatore deve passare in prigione.
     */
    public int getTurniInPrigione() {
        return turniInPrigione;
    }

    /**
     * Imposta il numero di turni che il giocatore deve passare in prigione.
     * @param turniInPrigione numero di turni che il giocatore deve passare in prigione
     */
    public void setTurniInPrigione(int turniInPrigione) {
        this.turniInPrigione = turniInPrigione;
    }

    /**
     * @return restituisce la posizione del giocatore sul tabellone.
     */
    public int getPosizione() {
        return posizione;
    }

    /**
     * Imposta la posizione del giocatore sul tabellone.
     * @param posizione posizione del giocatore sul tabellone
     */
    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    /**
     * @return il numero di turni che il giocatore deve saltare.
     */
    public int getTurniDaSaltare() {
        return turniDaSaltare;
    }   

    /**
     * Imposta il numero di turni che il giocatore deve saltare.
     * @param turniDaSaltare numero di turni che il giocatore deve saltare
     */
    public void setTurniDaSaltare(int turniDaSaltare) {
        this.turniDaSaltare = turniDaSaltare;
    }

    /**
     * @return restituisce una stringa che rappresenta le informazioni del giocatore.
     */
    @Override
    public String toString() {
        return "Info giocatore: {" + "nome=" + nome + ", turniInPrigione=" + turniInPrigione + ", posizione=" + posizione + ", isUmano=" + isUmano + '}';
    }


    /**
     * Il metodo equals è stato ridefinito per confrontare due giocatori in base al loro ID
     * @param obj giocatore da confrontare
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Giocatore other)) {
            return false;
        }
        return this.IDGiocatore == other.IDGiocatore;
    }
       
}