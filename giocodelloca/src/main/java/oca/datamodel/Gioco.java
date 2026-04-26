package oca.datamodel;

import oca.service.impl.Log;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import static org.fusesource.jansi.Ansi.ansi;

/** La classe Gioco rappresenta un gioco dell'oca con un numero variabile di giocatori e caselle.
 * Implementa l'interfaccia Serializable per consentire la serializzazione degli oggetti di questa classe.
 */

public class Gioco implements Serializable {
    /**
     * Attributi:
     * - numeroCaselle: il numero di caselle del gioco.
     * - numeroGiocatori: il numero di giocatori partecipanti.
     * - facceDado: il numero di facce del dado utilizzato nel gioco.
     * - giocatoreDiTurno: il giocatore che ha il turno corrente.
     * - caselle: la lista delle caselle del gioco.
     * - giocatori: la lista dei giocatori partecipanti.
     * - colori: la lista dei colori associati ai giocatori.
     */
    private int numeroCaselle;
    private int numeroGiocatori;
    private int facceDado;
    private Giocatore giocatoreDiTurno;
    private ArrayList<Casella> caselle;
    private ArrayList<Giocatore> giocatori;
    private final ArrayList<Ansi.Color> colori;
    

    /**
     * Costruttore della classe Gioco, inizializza un nuovo gioco con i parametri specificati.
     * @param numeroGiocatori numero di giocatori del gioco
     * @param numeroCaselle numero di caselle del gioco
     * @param facceDado numero di facce del dado utilizzato
     * @param giocatoreDiTurno giocatore di turno iniziale
     */
        public Gioco(int numeroGiocatori, int numeroCaselle, int facceDado, Giocatore giocatoreDiTurno) {
            this.numeroCaselle = numeroCaselle;
            this.numeroGiocatori = numeroGiocatori;
            this.facceDado = facceDado;
            this.giocatoreDiTurno = giocatoreDiTurno;
            this.caselle = new ArrayList<Casella>();
            this.giocatori = new ArrayList<Giocatore>();
            this.colori = new ArrayList<>();
            colori.add(Ansi.Color.GREEN);
            colori.add(Ansi.Color.YELLOW);
            colori.add(Ansi.Color.RED);
            colori.add(Ansi.Color.BLUE);
            colori.add(Ansi.Color.MAGENTA);
            colori.add(Ansi.Color.WHITE);
        }
    /**
     * Setta il numero di giocatori del gioco.
     * @param numeroGiocatori numero di giocatori
     */
    public void setNumeroGiocatori(int numeroGiocatori) {
        this.numeroGiocatori = numeroGiocatori;
    }

    /**
     * Setta il giocatore di turno del gioco.
     * @param giocatoreDiTurno giocatore di turno
     */
    public void setGiocatoreDiTurno(Giocatore giocatoreDiTurno) {
        this.giocatoreDiTurno = giocatoreDiTurno;
    }

    /**
     * Setta le caselle del gioco.
     * @param caselle caselle
     */
    public void setCaselle(ArrayList<Casella> caselle) {
        this.caselle = caselle;
    }

    /**
     * Setta i giocatori del gioco.
     * @param giocatori giocatori
     */
    public void setGiocatori(ArrayList<Giocatore> giocatori) {
        this.giocatori = giocatori;
    }

    /**
     * Setta il numero di caselle del gioco.
     * @param numeroCaselle numero di caselle
     */
    public void setNumeroCaselle(int numeroCaselle) {
        this.numeroCaselle = numeroCaselle;
    }

    /**
     * Setta il numero di facce del dado del gioco.
     * @param facceDado numero di facce del dado
     */
    public void setFacceDado(int facceDado) {
        this.facceDado = facceDado;
    }

    /**
     * @return il numero di caselle del gioco.
     */
    public int getNumeroCaselle() {
        return numeroCaselle;
    }

    /**
     * @return il numero di giocatori del gioco.
     */
    public int getNumeroGiocatori() {
        return numeroGiocatori;
    }

    /**
     * @return il giocatore di turno del gioco.
     */
    public Giocatore getGiocatoreDiTurno() {
        return giocatoreDiTurno;
    }

    /**
     * @return le caselle del gioco.
     */
    public ArrayList<Casella> getCaselle() {
        return caselle;
    }

    /**
     * @return i giocatori del gioco.
     */
    public ArrayList<Giocatore> getGiocatori() {
        return giocatori;
    }

    /**
     * @return il numero di facce del dado del gioco.
     */
    public int getFacceDado() {
        return facceDado;
    }

    /**
     * Verifica se una casella è presente nel gioco.
     * @param casella casella da verificare
     * @return true se la casella è presente, false altrimenti.
     */
    public boolean contains(Casella casella) {
        return caselle.contains(casella);
    }

    /**
     * Aggiunge un giocatore alla lista dei giocatori partecipanti al gioco.
     * @param giocatore giocatore da aggiungere
    */
    public void addGiocatore(Giocatore giocatore){
        giocatori.add(giocatore);
        numeroGiocatori++;
    }

    /**
     * Rimuove il giocatore passato come parametro dal gioco.
     * @param giocatore giocatore da rimuovere
     */
    public void removeGiocatore(Giocatore giocatore){
        giocatori.remove(giocatore);
        numeroGiocatori--;
    }

    /**
     * Rimuove il giocatore a indice index dal gioco.
     * @param index indice del giocatore in giocatori
     */
    public void removeGiocatore(int index){
        giocatori.remove(index);
        numeroGiocatori--;
    }

    /**
     * Il metodo fa passare il turno al prossimo giocatore in lista.
     */
    public void avanzaTurno() {
        int currentIndex = giocatori.indexOf(giocatoreDiTurno);
        int nextIndex = (currentIndex + 1) % giocatori.size();
        giocatoreDiTurno = giocatori.get(nextIndex);
        Log.getInstance().info("Turno passato a: "+giocatoreDiTurno.getNome());
    }


    /**
     * Il metodo stampa su terminale un riga iniziale informativa del gioco corrente, e appena dopo
     * il tabellone suddiviso in 10 colonne.
     * I giocatori sono rappresentati attraverso il propio nome, e per
     * indicare la loro posizione si indica la sigla accanto il numero della casella.
     */  
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Aggiunge una riga iniziale informativa sullo stato del gioco
        sb.append(String.format("\nNumeroCaselle = %d , numeroGiocatori = %d , facceDado = %d", numeroCaselle, numeroGiocatori, facceDado));
        sb.append("\n\n");

        // Definisce il numero di colonne e calcola il numero di righe necessarie
        int numRighe = 5;
        int numColonne = (int) Math.ceil((double) numeroCaselle / numRighe);

        ArrayList<ArrayList<String>> tabellone = new ArrayList<>();

        // Inizializza le righe del tabellone con stringhe vuote
        for (int i = 0; i < numRighe; i++) {
            tabellone.add(new ArrayList<>(Collections.nCopies(numColonne, "   ")));
        }

        // Popola il tabellone con i numeri delle caselle in modo spirale
        int top = 0, bottom = numRighe - 1, left = 0, right = numColonne - 1;
        int numCasella = 0;
        while (numCasella < numeroCaselle) {
            // Riempie la riga superiore da sinistra a destra
            for (int i = left; i <= right && numCasella < numeroCaselle; i++) {
                tabellone.get(top).set(i, ansi().fg(Ansi.Color.CYAN).a(String.format("%2d", numCasella)).reset().toString());
                numCasella++;
            }
            top++;
            // Riempie la colonna destra dall'alto verso il basso
            for (int i = top; i <= bottom && numCasella < numeroCaselle; i++) {
                tabellone.get(i).set(right, ansi().fg(Ansi.Color.CYAN).a(String.format("%2d", numCasella)).reset().toString());
                numCasella++;
            }
            right--;
            // Riempie la riga inferiore da destra a sinistra
            for (int i = right; i >= left && numCasella < numeroCaselle; i--) {
                tabellone.get(bottom).set(i, ansi().fg(Ansi.Color.CYAN).a(String.format("%2d", numCasella)).reset().toString());
                numCasella++;
            }
            bottom--;
            // Riempie la colonna sinistra dal basso verso l'alto
            for (int i = bottom; i >= top && numCasella < numeroCaselle; i--) {
                tabellone.get(i).set(left, ansi().fg(Ansi.Color.CYAN).a(String.format("%2d", numCasella)).reset().toString());
                numCasella++;
            }
            left++;
        }

        stampaLegenda();

        // Aggiunge i giocatori al tabellone
        for (Giocatore g : giocatori) {
            int pos = g.getPosizione();
            top = 0; 
            bottom = numRighe - 1; 
            left = 0; 
            right = numColonne - 1;
            numCasella = 0;
            int i;
            // Scorre la prima riga da sinistra a destra
            for (i = left; i < right && numCasella < pos; i++) {
                numCasella++;
            }
            if (numCasella == pos) {
                stampaGiocatoreSuTabellone(g, tabellone, top, i);
                continue;
            }

            while (numCasella < pos) {
                top++;
                // Scorre la colonna destra dall'alto verso il basso
                for (i = top-1; i < bottom && numCasella < pos; i++) {
                    numCasella++;
                }
                if (numCasella == pos) {
                    stampaGiocatoreSuTabellone(g, tabellone, i, right);
                    break;
                }
                right--;
                // Scorre la riga inferiore da destra a sinistra
                for (i = right+1; i > left && numCasella < pos; i--) {
                    numCasella++;
                }
                if (numCasella == pos) {
                    stampaGiocatoreSuTabellone(g, tabellone, bottom, i);
                    break;
                }
                bottom--;
                // Scorre la colonna sinistra dal basso verso l'alto
                for (i = bottom+1; i > top && numCasella < pos; i--) {
                    numCasella++;
                }
                if (numCasella == pos) {
                    stampaGiocatoreSuTabellone(g, tabellone, i, left);
                    break;
                }
                left++;
                // Scorre la riga superiore da sinistra a destra
                for (i = left-1; i < right && numCasella < pos; i++) {
                    numCasella++;
                }
                if (numCasella == pos) {
                    stampaGiocatoreSuTabellone(g, tabellone, top, i);
                    break;
                }
            }
        }

        // Aggiunge il tabellone al StringBuilder
        sb.append("Tabellone:\n");
        for (ArrayList<String> r : tabellone) {
            for (String c : r) {
                sb.append(c).append("   ");
            }
            sb.append("\n\n");
        }

        // Restituisce la rappresentazione del gioco come stringa
        return sb.toString();
    }

    /**
     * Il metodo prende in base all'indice di g in giocatori il relativo colore Ansi.Color in colori e stampa il nome del giocatore sul tabellone con il colore corrispondente.
     * @param g giocatore da stampare
     * @param t tabellone
     * @param riferimento indice di riga
     * @param indiceTabellone indice di colonna
     */
    private void stampaGiocatoreSuTabellone(Giocatore g, ArrayList<ArrayList<String>> t, int riferimento, int indiceTabellone) {
        String s;
        if (giocatori.contains(g)) {
            int i = giocatori.indexOf(g);
            Ansi.Color colore = colori.get(i);
            if (this.caselle.get(g.getPosizione()).getGiocatori().size() > 1) {
                s = ansi().bg(Ansi.Color.CYAN).a("**").reset().toString();
            } else {
                String nomeGiocatore = g.getNome();
            /* 
                - Viene verificato se il nome del giocatore è lungo almeno 2 caratteri. 
                - Se sì, viene creata un'abbreviazione prendendo i primi due caratteri del nome e formattandoli con il colore del giocatore.
                - Se il nome del giocatore è più corto di 2 caratteri, viene utilizzato il nome completo e formattato con il colore del giocatore.
            */
                if (nomeGiocatore.length() >= 2) {
                    s = ansi().bg(colore).a(nomeGiocatore.substring(0, 2)).reset().toString();
                } else {
                    s = ansi().bg(colore).a(nomeGiocatore).reset().toString(); // Usa il nome completo se è più corto di 2 caratteri
                }
            }
            t.get(riferimento).set(indiceTabellone, s);
        } else {
            Log.getInstance().warning("Il giocatore non è presente in lista. Giocatore: " + g.getNome());
        }
    }

    /**
     * Il metodo stampa la legenda dei giocatori con il colore di sfondo corrispondente.
     */
    private void stampaLegenda() {
        System.out.println("\nLegenda giocatori:");
        for (Giocatore g : giocatori) {
            int i = giocatori.indexOf(g);
            Ansi.Color colore = colori.get(i);
            String nomeGiocatore = g.getNome();
            String abbreviazioneNome;
    
            // Controlla se il nome del giocatore è abbastanza lungo per prendere una sottostringa di 2 caratteri
            if (nomeGiocatore.length() >= 2) {
                abbreviazioneNome = nomeGiocatore.substring(0, 2);
            } else {
                abbreviazioneNome = nomeGiocatore; // Usa il nome completo se è più corto di 2 caratteri
            }
    
            String s = ansi().bg(colore).a("Giocatore " + ++i + ": " + abbreviazioneNome + " = ").reset().toString();
            String info = ansi().bg(colore).a(g.getNome() + ", si trova in posizione " + g.getPosizione()).reset().toString();
            System.out.println(s + info);
        }
        System.out.println();
    }
}