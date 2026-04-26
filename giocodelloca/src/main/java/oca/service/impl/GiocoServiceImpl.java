package oca.service.impl;
import java.io.*;
import java.util.*;

import oca.datamodel.*;
import oca.service.*;

public class GiocoServiceImpl implements GiocoService {
    /**
     * Carica una partita da un file
     * @param nomeFile il nome del file da cui caricare la partita
     * @return la partita caricata
     * @throws IOException se si verifica un errore durante la lettura del file
     * @throws ClassNotFoundException se si verifica un errore durante la deserializzazione dell'oggetto
     * @throws ErroreCaricamentoPartita se si verifica un errore durante il caricamento della partita
     */

    @Override
    public Gioco caricaPartita(String nomeFile) throws IOException, ClassNotFoundException, ErroreCaricamentoPartita {
        System.out.println("Tentativo di apertura del file: " + nomeFile);
        try (FileInputStream fis = new FileInputStream(nomeFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Gioco g = (Gioco) ois.readObject();
    
            // Controlla se la partita è già terminata
            if (isFinita(g)) {
                System.out.println(g);
                System.out.println("La partita e' gia' terminata e non puo' essere rigiocata.");
                Log.getInstance().info("Tentativo di caricare una partita già terminata.");
                //throw new ErroreCaricamentoPartita();
                do {
                    System.out.println("Vuoi giocare una nuova partita? (si/no): ");
                    String risposta = CommandLineSingleton.getInstance().readString();
                    if (risposta.equalsIgnoreCase("si")) {
                        try {
                            return creaPartita();
                        } catch (ErroreCreazionePartita e) {
                            System.out.println("Errore durante la creazione della nuova partita.");
                            Log.getInstance().error("Errore durante la creazione della nuova partita: " + e.getMessage());
                            return null;
                        }
                    } else if (risposta.equalsIgnoreCase("no")) {
                        System.out.println("Uscita dal gioco...");
                        Log.getInstance().info("Uscita dal gioco");
                        return null;
                    }
                    System.out.println("Input non valido. Per favore rispondi con 'si' o 'no'.");
                } while(true);
            }    
            Log.getInstance().info("Partita caricata da " + nomeFile);
            return g;
        } catch (FileNotFoundException e) {
            System.out.println("Errore: il file " + nomeFile + " non è stato trovato nella directory corrente.");
            Log.getInstance().error("Errore: il file " + nomeFile + " non è stato trovato");
            throw new ErroreCaricamentoPartita();
        } catch (IOException e) {
            System.out.println("Errore di I/O durante il caricamento del file " + nomeFile);
            Log.getInstance().error("Errore di I/O durante il caricamento del file " + nomeFile);
            throw new ErroreCaricamentoPartita();
        } catch (ClassNotFoundException e) {
            System.out.println("Errore di deserializzazione durante il caricamento del file " + nomeFile);
            Log.getInstance().error("Errore di deserializzazione durante il caricamento del file " + nomeFile);
            throw new ErroreCaricamentoPartita();
        }
    }

    /**
     * Gioca una partita, facendo scegliere la modalità di gioco all'utente.
     * Se la modalità è GIOCA, il giocatore lancia il dado e muove il proprio pedone.
     * Se la modalità è SALVA, salva la partita in un file.
     * Se la modalità è ESCI, esce dal gioco.
     * Se la modalità è ABBANDONA, il giocatore abbandona la partita permettendo agli altri di andare avanti.
     * @param gioco la partita da giocare
     */
    @Override
    public void gioca(Gioco gioco) {
        if (gioco == null) return;
        while(true) {
            Giocatore g = gioco.getGiocatoreDiTurno();
            System.out.println("\nE' il turno di "+g.getNome());
            System.out.println(gioco);
            GiocatoreService<? extends Giocatore> giocatoreService;
            if (g.getIsUmano()) giocatoreService = new GiocatoreRealeServiceImpl();            
            else giocatoreService = new GiocatoreBotServiceImpl();
            ModeEnumeration mode = giocatoreService.getMode();
            if (mode == ModeEnumeration.GIOCA) {
                // Controlla se il giocatore deve saltare il turno
                if (g.getTurniDaSaltare() != 0) {
                    System.out.println(g.getNome() + " deve saltare il turno per " + g.getTurniDaSaltare() + " turni.");
                    g.setTurniDaSaltare(g.getTurniDaSaltare() - 1);
                    gioco.avanzaTurno();
                    continue; // Passa al prossimo giocatore
                }
                // Controlla se il giocatore è in prigione
                if (g.getIsInPrigione()) {
                    System.out.println(g.getNome() + " e' in prigione e non puo' giocare.");
                    gioco.avanzaTurno();
                    continue; // Passa al prossimo giocatore
                }
                int valore = giocatoreService.lanciaDado();
                CasellaOca.setRaddoppioOca(valore);
                Log.getInstance().info("Il lancio del dado ha restituito il valore: " + valore);
                try {
                    giocatoreService.muoviGiocatore(g, valore);
                    Casella arrivo = gioco.getCaselle().get(g.getPosizione());
                    System.out.println("Il giocatore si trova nella casella " + arrivo.getNumber());
                    CasellaService<? extends Casella> cs = CasellaServiceFactory.getCasellaService(arrivo.getClass());  
                    cs.effetto(g);
                    if (!isFinita(gioco)) gioco.avanzaTurno();
                    else {
                        stampaFinePartita(gioco, g);
                        return;
                    }
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
                    gioco.avanzaTurno();
                } catch (ValoreOltrePrimaCasellaException e) {
                    System.out.println("Il giocatore e' stato retrocesso più indietro della casella 0!");
                    System.out.println("Questo non puo' accadere, verrà posizionato nella casella 0.");
                    gioco.getCaselle().get(g.getPosizione()).removeGiocatore(g);
                    gioco.getCaselle().get(0).addGiocatore(g);
                    g.setPosizione(0);
                    gioco.avanzaTurno();
                    //La casella iniziale non ha mai effetti per costruzione, quindi non serve chiamare il metodo effetto
                }
            }
            if(mode == ModeEnumeration.SALVA){
                System.out.println("Inserisci il nome del file per salvare la partita: ");
                String nomeFile = CommandLineSingleton.getInstance().readString();
                try {
                    salvaPartita(gioco, nomeFile);
                    System.out.println("Partita salvata con successo nel file: " + nomeFile);
                    mode = ModeEnumeration.ESCI;
                } catch (ErroreSalvataggioPartita | IOException e) {
                    System.out.println("Errore durante il salvataggio della partita");
                }
            }
            
            if(mode == ModeEnumeration.ESCI){
                System.out.println("Sei sicuro di voler uscire? (si/no): ");
                String risposta = CommandLineSingleton.getInstance().readString();
                if (risposta.equalsIgnoreCase("si")) {
                    System.out.println("Uscita dal gioco...");
                    Log.getInstance().info("Uscita dal gioco");
                    return; // Esce dal metodo gioca, terminando il ciclo di gioco
                }
                if(!risposta.equalsIgnoreCase("no")){
                    Log.getInstance().warning("Input fornito errato. Input: "+risposta);
                }
            }

           /*  if(mode == ModeEnumeration.ABBANDONA){
                System.out.println("Sei sicuro di voler abbandonare la partita? (si/no): ");
                String risposta = CommandLineSingleton.getInstance().readString();
                if(risposta.equalsIgnoreCase("si")){
                    System.out.println("Il giocatore "+g.getNome()+" ha abbandonato la partita.");
                    Log.getInstance().info("Il giocatore "+g.getNome()+" ha abbandonato la partita.");
                    int c = g.getPosizione();
                    gioco.getCaselle().get(c).removeGiocatore(g);
                    int indexGiocatoreAbbandono = gioco.getGiocatori().indexOf(g);
                    if (gioco.getGiocatori().size() == 2) {
                        gioco.removeGiocatore(indexGiocatoreAbbandono);
                        stampaFinePartita(gioco, gioco.getGiocatori().get(0));
                        return;
                    } 
                    gioco.avanzaTurno();
                    gioco.removeGiocatore(indexGiocatoreAbbandono);
                    /* 
                       si rimuove il giocatore che ha abbandonato dalla casella,
                       si salva il suo indice nella lista dei giocatori,
                       si controlla se tolto il giocatore rimangono meno di 2 giocatori (se si la partita è finita),
                       si avanza il turno e dopo si rimuove il giocatore dalla partita,
                       per evitare conflitti con il metodo avanzaTurno()
                    */
                /* }
                if(!risposta.equalsIgnoreCase("no")){
                    Log.getInstance().warning("Input fornito errato. Input: "+risposta);
                }
            }*/
            if (mode == ModeEnumeration.ABBANDONA) {
                System.out.println("Sei sicuro di voler abbandonare la partita? (si/no): ");
                String risposta = CommandLineSingleton.getInstance().readString();
                if (risposta.equalsIgnoreCase("si")) {
                    System.out.println("Il giocatore " + g.getNome() + " ha abbandonato la partita.");
                    Log.getInstance().info("Il giocatore " + g.getNome() + " ha abbandonato la partita.");
    
                    // Rimuovi il giocatore dalla casella corrente
                    int posizione = g.getPosizione();
                    gioco.getCaselle().get(posizione).removeGiocatore(g);
    
                    // Rimuovi il giocatore dalla lista dei giocatori
                    int indexGiocatoreAbbandono = gioco.getGiocatori().indexOf(g);
                    gioco.removeGiocatore(indexGiocatoreAbbandono);

                    // Controlla se ci sono abbastanza giocatori per continuare
                    if (gioco.getGiocatori().size() < 2) {
                        System.out.println("Non ci sono abbastanza giocatori per continuare la partita.");
                        Log.getInstance().info("Partita terminata per mancanza di giocatori.");
                        stampaFinePartita(gioco, gioco.getGiocatori().get(0));
                        return; // Termina il gioco
                    }
    
                    /*
                        * Se ci sono solo giocatori bot, chiedi all'utente se vuole continuare la partita
                        * e vedere chi vince, oppure se vuole terminare la partita
                    */
                    boolean soloBot = gioco.getGiocatori().stream().allMatch(giocatore -> !giocatore.getIsUmano());
                    if (soloBot) {
                        do {
                            System.out.println("Ci sono solo giocatori bot, vuoi continuare la partita e vedere chi vince? (si/no): ");
                            risposta = CommandLineSingleton.getInstance().readString();
                            if (risposta.equalsIgnoreCase("no")) {
                                System.out.println("Partita terminata.");
                                Log.getInstance().info("Partita terminata.");
                                return; // Termina il gioco
                            } else if (risposta.equalsIgnoreCase("si")) {
                                break; // Continua il gioco
                            } else {
                                Log.getInstance().warning("Input fornito errato. Input: " + risposta);
                                System.out.println("Per favore rispondi con 'si' o 'no'.");
                            }
                        } while(true);                        
                    }
    
                    // Avanza il turno al prossimo giocatore
                    gioco.avanzaTurno();
                    continue;
                } else if (!risposta.equalsIgnoreCase("no")) {
                    Log.getInstance().warning("Input fornito errato. Input: " + risposta);
                }
            }
        }
    }

    

    /**
     * Metodo che serve a effettuare una stampa a fine partita
     * @param gioco la partita da stampare
     * @param g il giocatore che ha vinto
     */
    private void stampaFinePartita(Gioco gioco, Giocatore g){
        System.out.println("Il giocatore " + g.getNome() + " ha vinto!");
        System.out.println("Congratulazioni!");
        System.out.println("Tabellone finale: ");
        System.out.println(gioco);
        Log.getInstance().info("Il giocatore " + g.getNome() + " ha vinto");
        do {
            try {
                // Chiedi all'utente se vuole salvare la partita
                System.out.println("vuoi salvare la partita? (si/no): ");
                String risposta = CommandLineSingleton.getInstance().readString();
                if (risposta.equalsIgnoreCase("si")) {
                    System.out.println("Inserisci il nome del file per salvare la partita: ");
                    String nomeFile = CommandLineSingleton.getInstance().readString();
                    salvaPartita(gioco, nomeFile);
                    System.out.println("Partita salvata con successo nel file: " + nomeFile);
                    return; // Esce dopo il salvataggio
                } else if (risposta.equalsIgnoreCase("no")) {
                    System.out.println("La partita non e' stata salvata.");
                    return; // Esce se l'utente non vuole salvare
                } else {
                    System.out.println("Input non valido. Per favore rispondi con 'si' o 'no'.");
                    Log.getInstance().warning("Input fornito errato. Input: " + risposta);
                }
            } catch (ErroreSalvataggioPartita | IOException e) {
                System.out.println("Errore durante il salvataggio della partita: " + e.getMessage());
                Log.getInstance().error("Errore durante il salvataggio della partita: " + e.getMessage());
                return;
            }
        } while(true);
    }
   
    /**
     * Crea una nuova partita
     * @return la partita creata
     * @throws ErroreCreazionePartita se si verifica un errore durante la creazione della partita
     */

     @Override
    public Gioco creaPartita() throws ErroreCreazionePartita, ErroreCaricamentoPartita {
        Gioco gioco = null;
        //boolean sessioneDiGioco = true;
        int modo = 0;
        while (modo != 1 && modo != 2) {
        System.out.println("Selezionare (1) nuova partita o (2) per caricare una partita esistente:");
        modo = CommandLineSingleton.getInstance().readInteger();
        } 
        if (modo == 1) {
            try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
                if (resourceAsStream == null) {
                    System.out.println("Errore: il file di configurazione non e' stato trovato.");
                    Log.getInstance().error("Il file di configurazione non è stato trovato.");
                    throw new ErroreCreazionePartita();
                }
                Properties properties = new Properties();
                properties.load(resourceAsStream);
                int facceDado = Integer.parseInt(properties.getProperty("dado.facce"));
                int numeroCaselle = Integer.parseInt(properties.getProperty("tabellone.caselle"));
    
                // Chiedi all'utente di inserire il numero di giocatori
                int numeroGiocatoriReali;
                int numeroGiocatoriBot;
                int numeroGiocatori;
                do {
                    System.out.println("Inserisci il numero di giocatori reali: ");
                    numeroGiocatoriReali = CommandLineSingleton.getInstance().readInteger();
                    System.out.println("Inserisci il numero di giocatori bot: ");
                    numeroGiocatoriBot = CommandLineSingleton.getInstance().readInteger();
                    numeroGiocatori = numeroGiocatoriReali + numeroGiocatoriBot;
                    if (numeroGiocatori >= 2 && numeroGiocatori <= 6) {
                        Log.getInstance().info("Numero di giocatori valido. Input: " + numeroGiocatori);
                        break;
                    }
                    Log.getInstance().warning("Numero di giocatori errato. Input: " + numeroGiocatori);
                    System.out.println("Reinserire i valori\n");
                } while (true);
    
                gioco = new Gioco(numeroGiocatori, numeroCaselle, facceDado, null);
                 // Set per tenere traccia dei nomi già usati
                Set<String> nomiGiocatori = new HashSet<>();

                for (int i = 0; i < numeroGiocatoriReali; i++) {
                    String nome;
                    // Chiedi all'utente di inserire il nome del giocatore
                    do {
                        System.out.println("Inserire nome " + (i + 1) + "o giocatore: ");
                        nome = CommandLineSingleton.getInstance().readString();
                        // Verifica che il nome non sia già stato usato
                        if (nomiGiocatori.contains(nome)) {
                            System.out.println("Il nome '" + nome + "' e' già stato usato. Inserire un nome diverso.");
                            Log.getInstance().warning("Nome duplicato inserito: " + nome);
                        } else {
                            break;
                        }
                    } while (true);

                    Giocatore g = new GiocatoreReale(gioco, nome);
                    gioco.addGiocatore(g);
                    nomiGiocatori.add(nome); // Aggiungi il nome al Set
                }
                
                Log.getInstance().info("Giocatori reali aggiunti");

                for (int i = 0; i < numeroGiocatoriBot; i++) {
                    Giocatore g = new GiocatoreBot(gioco, "Cpu" + (i + 1));
                    gioco.addGiocatore(g);
                }

                Log.getInstance().info("Giocatori bot aggiunti");

                ArrayList<Casella> caselle = generaTabellone(numeroCaselle, gioco);
                gioco.setCaselle(caselle);
                
    
                // Verifica che le caselle siano state create correttamente
                Log.getInstance().info("Numero di caselle create: " + caselle.size());
                for (int i = 0; i < caselle.size(); i++) {
                    Log.getInstance().info("Casella " + i + ": " + caselle.get(i).getClass().getSimpleName());
                }
    
                // Imposta il primo giocatore come giocatore di turno
                gioco.setGiocatoreDiTurno(gioco.getGiocatori().get(0));
                Log.getInstance().info("Partita creata con successo");
    
                return gioco;
    
            } catch (IOException e) {
                throw new ErroreCreazionePartita();
            } catch (InputMismatchException e){
                String s = CommandLineSingleton.getInstance().readString();
                Log.getInstance().warning("Input fornito errato. Input: "+s);
            }
        }
        else if (modo == 2) {
            System.out.println("Inserisci il nome del file da caricare: ");
            String nomeFile = CommandLineSingleton.getInstance().readString();
            try {
                gioco = caricaPartita(nomeFile);
                gioca(gioco);
            } catch (ErroreCaricamentoPartita | IOException | ClassNotFoundException e) {
                System.out.println("Errore nel caricamento del file");
            }
        }
        return null;
    } 

    @SuppressWarnings("unused")
    private boolean partitaSalvataConSuccesso = false;

    /**
     * Salva una partita in un file
     * @param gioco la partita da salvare
     * @param nomeFile il nome del file in cui salvare la partita
     * @throws ErroreSalvataggioPartita se si verifica un errore durante il salvataggio della partita
     * @throws IOException se si verifica un errore durante la scrittura del file
     */
    @Override
    public void salvaPartita(Gioco gioco, String nomeFile) throws ErroreSalvataggioPartita, IOException {
        try {
            // Logga un messaggio informativo per indicare che il tentativo di salvataggio è iniziato
            Log.getInstance().info("Tentativo di salvataggio della partita in " + nomeFile);

            // Crea un FileOutputStream per scrivere i byte nel file specificato
            FileOutputStream fos = new FileOutputStream(nomeFile);

            // Crea un ObjectOutputStream per scrivere l'oggetto Gioco in forma serializzata nel FileOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Scrive l'oggetto Gioco nel ObjectOutputStream
            oos.writeObject(gioco);

            // Chiude l'ObjectOutputStream per assicurarsi che tutti i dati siano scritti e le risorse siano rilasciate
            oos.close();

            // Logga un messaggio informativo per indicare che la partita è stata salvata con successo
            Log.getInstance().info("Partita salvata in " + nomeFile);

            // Imposta il flag a true per indicare che la partita è stata salvata con successo
            partitaSalvataConSuccesso = true;
        } catch (FileNotFoundException e) {
            // Logga un messaggio di errore se il file non è stato trovato
            Log.getInstance().error("File non trovato: " + e.getMessage());

            // Lancia un'eccezione personalizzata per segnalare che si è verificato un errore durante il salvataggio della partita
            throw new ErroreSalvataggioPartita();
        } catch (IOException e) {
            // Logga un messaggio di errore se si è verificato un errore durante il salvataggio della partita
            Log.getInstance().error("Errore durante il salvataggio della partita: " + e.getMessage());

            // Lancia un'eccezione personalizzata per segnalare che si è verificato un errore durante il salvataggio della partita
            throw new ErroreSalvataggioPartita();
        }
    }


    /**
     * Verifica se la partita è finita
     * @param gioco la partita da verificare
     * @return true se la partita è finita, false altrimenti
     */
    @Override
    public boolean isFinita(Gioco gioco) {
        // Se c'è un solo giocatore rimasto, la partita è finita
        ArrayList<Giocatore> giocatori = gioco.getGiocatori();
        if(giocatori.size() == 1) return true;

        // Se l'ultima casella contiene il giocatore di turno, la partita è finita
        ArrayList<Casella> caselle = gioco.getCaselle();
        Casella ultima = caselle.get(caselle.size()-1);
        return ultima.containsGiocatore(gioco.getGiocatoreDiTurno());
    }

    /**
     * Genera un tabellone di gioco
     * @param numeroCaselle il numero di caselle del tabellone
     * @param gioco il gioco a cui appartiene il tabellone
     * @return la lista delle caselle del tabellone
     */
    @Override
    public ArrayList<Casella> generaTabellone(int numeroCaselle, Gioco gioco) {
        
        Random random = new Random();
        ArrayList<Casella> caselle = new ArrayList<>(numeroCaselle);

        int numeroCaselleSpeciali = (int) (numeroCaselle * 0.40);
        int numeroCaselleNormali = (numeroCaselle - 2) - numeroCaselleSpeciali;

        // Aggiungi almeno due caselle di tipo Tris e Morra
        caselle.add(new CasellaTris());
        caselle.add(new CasellaTris());
        caselle.add(new CasellaMorra());
        caselle.add(new CasellaMorra());

        // Aggiungi almeno una casella speciale per ogni altro tipo
        caselle.add(new CasellaOca());
        caselle.add(new CasellaPonte());
        caselle.add(new CasellaPrigione());
        caselle.add(new CasellaScheletro());
        caselle.add(new CasellaLocanda());
        caselle.add(new CasellaLabirinto());

        // Aggiungi le restanti caselle speciali in modo casuale
        int caselleSpecialiAggiunte = 10; // 2 Tris + 2 Morra + 6 altri tipi
        while (caselleSpecialiAggiunte < numeroCaselleSpeciali) {
            int tipoCasella = random.nextInt(8);
            switch (tipoCasella) {
                case 0: caselle.add(new CasellaOca()); break;
                case 1: caselle.add(new CasellaTris()); break;
                case 2: caselle.add(new CasellaPonte()); break;
                case 3: caselle.add(new CasellaPrigione()); break;
                case 4: caselle.add(new CasellaMorra()); break;
                case 5: caselle.add(new CasellaScheletro()); break;
                case 6: caselle.add(new CasellaLocanda()); break;
                case 7: caselle.add(new CasellaLabirinto()); break;
            }
            caselleSpecialiAggiunte++;
        }
        Log.getInstance().info("Caselle speciali aggiunte "+caselleSpecialiAggiunte+" su "+numeroCaselle+" caselle totali");

        // Aggiungi caselle normali
        for (int i = 0; i < numeroCaselleNormali; i++) {
            caselle.add(new Casella());
        }

        // Mescola le caselle per distribuirle in modo casuale
        Collections.shuffle(caselle);

        //Prima e ultima casella normali, per evitare che siano speciali
        caselle.add(0, new Casella());
        caselle.add(new Casella());
        
        // Verifica che la lista delle caselle non sia vuota
        if (caselle.size() < numeroCaselle) {
            Log.getInstance().error("La lista delle caselle è incompleta!");
            throw new RuntimeException("La lista delle caselle e' incompleta!");
        }

        // Assegna un numero ad ogni casella
        int i = 0;
            for(Casella c : caselle){
                c.setNumber(i);
                i++;
            }
        
        // Aggiungi i giocatori alla prima casella
        for(Giocatore g : gioco.getGiocatori())
            caselle.get(0).addGiocatore(g);

        Log.getInstance().info("Tabellone creato con successo"); 
        return caselle;
    } 
  
}
