package oca.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.fusesource.jansi.Ansi;

import oca.service.GiocatoreService;
import oca.service.ModeEnumeration;
import oca.datamodel.GiocatoreReale;

public class GiocatoreRealeServiceImpl implements GiocatoreService<GiocatoreReale>{

    /**
     * Metodo per lanciare un dado con un numero di facce preso da un file di configurazione.
     * @return il numero ottenuto dal lancio del dado
     */
    @Override
    public int lanciaDado() {
        /*      Path path = Path.of("giocodelloca","src","main","resources","application.properties");
        InputStream resourceAsStream = GiocoServiceImpl.class.getClassLoader().getResourceAsStream(path.toString());
         */
        // Crea un oggetto Properties per leggere le configurazioni
        Properties properties = new Properties();
        
        // Prova a caricare il file di configurazione
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (resourceAsStream == null) {
                // Se il file non viene trovato, genera un'eccezione
                throw new FileNotFoundException("File di configurazione non trovato");
            }
    
            // Carica le proprietà dal file
            properties.load(resourceAsStream);
    
            // Legge il numero di facce del dado dal file di configurazione
            int facceDado = Integer.parseInt(properties.getProperty("dado.facce"));
    
            // Richiede all'utente di inserire un numero valido tra 1 e facceDado
            System.out.println("Inserisci un numero tra 1 e " + facceDado + ": ");
            int numero = CommandLineSingleton.getInstance().readInteger();
    
            // Continua a chiedere finché il numero non rientra nell'intervallo valido
            while (!(numero >= 1 && numero <= facceDado)) {
                Log.getInstance().warning("Numero ottenuto fuori dal range 1-6. Input: "+numero);
                System.out.println("Inserisci un numero tra 1 e " + facceDado + ": ");
                numero = CommandLineSingleton.getInstance().readInteger();
            }
    
            // Restituisce il numero scelto dall'utente
            return numero;
    
        } catch (FileNotFoundException e) {
            // Gestisce l'errore se il file di configurazione non viene trovato
            System.out.println("Errore: il file di configurazione non e' stato trovato");
            Log.getInstance().error("Errore: il file di configurazione non è stato trovato");
    
        } catch (IOException e) {
            // Gestisce gli errori di lettura del file di configurazione
            System.out.println("Errore nel caricamento del file di configurazione");
            Log.getInstance().error("Errore nel caricamento del file di configurazione");
    
        } catch (NumberFormatException e) {
            // Gestisce l'errore se il valore delle facce del dado non è un numero valido
            System.out.println("Errore nel formato del numero di facce del dado");
            Log.getInstance().error("Errore nel formato del numero di facce del dado");
        }
    
        // Se si verifica un errore, restituisce -1 come valore di default
        return -1;
    }
    

    /**
     * Metodo per chiedere all'utente di scegliere una mossa per il tris.
     * @return la mossa scelta dall'utente
     */
    @Override
    public int sceltaTris(){
        System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a("Inserisci la tua mossa (1-9): ").reset().toString());
        return CommandLineSingleton.getInstance().readInteger();
    }

    /**
     * Metodo per chiedere all'utente di inserire il numero di dita e la previsione per il gioco della Morra.
     * @return un ArrayList contenente il numero di dita e la previsione inseriti dall'utente
     */
    @Override
    public ArrayList<Integer> roundMorra() {
        int ditaUtente, previsioneUtente;
        do {
            System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a( "Inserire numero: ").reset().toString());
            ditaUtente = CommandLineSingleton.getInstance().readInteger();
            if (ditaUtente > 0 && ditaUtente <= 5) {                    
                System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a( "Inserire previsione: ").reset().toString());
                previsioneUtente = CommandLineSingleton.getInstance().readInteger();
                if (previsioneUtente > ditaUtente && previsioneUtente < ditaUtente + 6) break;
            }
            System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Inserire un valore idoneo.").reset().toString());
            Log.getInstance().warning("Inserire un valore idoneo.");
        } while (true);
        ArrayList<Integer> mano = new ArrayList<>();
        mano.add(ditaUtente);
        mano.add(previsioneUtente);
        return mano;
    }
    

    /**
     * Metodo per chiedere all'utente di scegliere una modalità di gioco.
     * @return la modalità scelta dall'utente
     */
    @Override
	public ModeEnumeration getMode() {
        do {            
            System.out.println("Selezionare una delle seguanti modalita':");
            System.out.println("\t1 gioca");
            System.out.println("\t2 salva");
            System.out.println("\t3 esci");
            System.out.println("\t4 abbandona");
            Integer scelta = CommandLineSingleton.getInstance().readIntegerUntilPossibleValue(new Integer[] { 1, 2, 3, 4 });
            if (scelta == 1){
                Log.getInstance().info("Modalità selezionata: gioca");
                return ModeEnumeration.GIOCA;
            }
            if (scelta == 2) {
                Log.getInstance().info("Modalità selezionata: salva");
                return ModeEnumeration.SALVA;
            }
            if (scelta == 3) {
                Log.getInstance().info("Modalità selezionata: esci");
                return ModeEnumeration.ESCI;
            }
            if(scelta == 4){
                Log.getInstance().info("Modalità selezionata: abbandona");
                return ModeEnumeration.ABBANDONA;
            }
        } while (true);
	}
}
