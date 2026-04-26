package oca.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import org.fusesource.jansi.Ansi;

import oca.service.GiocatoreService;
import oca.service.ModeEnumeration;
import oca.datamodel.GiocatoreBot;

/**
 * Classe che implementa il servizio per il giocatore bot.
 */
public class GiocatoreBotServiceImpl implements GiocatoreService<GiocatoreBot>{

    /**
     * Metodo per lanciare un dado con un numero di facce preso da un file di configurazione.
     * @return il numero ottenuto dal lancio del dado
     */
    @Override
    public int lanciaDado() {
        /*Path path = Path.of("giocodelloca","src","main","resources","application.properties");
        InputStream resourceAsStream = GiocoServiceImpl.class.getClassLoader().getResourceAsStream(path.toString());
         */
        Properties properties = new Properties();
        try (InputStream input = GiocatoreBotServiceImpl.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                Log.getInstance().error("File di configurazione non trovato");
                throw new FileNotFoundException("File di configurazione non trovato");
            }
            properties.load(input);
            Log.getInstance().info("File di configurazione caricato correttamente");
            int facceDado = Integer.parseInt(properties.getProperty("dado.facce"));
            Random random = new Random();
            int numero = random.nextInt(facceDado) + 1; // Genera un numero casuale tra 1 e facceDado
            System.out.println("Il bot ha lanciato il dado e ha ottenuto: " + numero);
            return numero;
        } catch (FileNotFoundException e) {
            System.out.println("Errore: il file di configurazione non è stato trovato");
            Log.getInstance().error("Errore: il file di configurazione non è stato trovato");
        } catch (IOException e) {
            System.out.println("Errore nel caricamento del file di configurazione");
            Log.getInstance().error("Errore nel caricamento del file di configurazione");
        } catch (NumberFormatException e) {
            System.out.println("Errore nel formato del numero di facce del dado");
            Log.getInstance().error("Errore nel formato del numero di facce del dado");
        }
        return -1;
    }

    /**
     * Metodo per scegliere una casella in cui muoversi.
     * @return la casella scelta
     */
    @Override
    public int sceltaTris() {
        Random random = new Random();
        int move = random.nextInt(9) + 1; 
        return move;
    }

    /**
     * Metodo per giocare una mano di Morra.
     * @return la mano giocata
     */
    @Override
    public ArrayList<Integer> roundMorra(){
        Random random = new Random();
        System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a( "Inserire numero: ").reset().toString());
        int ditaCpu = random.nextInt(1,6);
        System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a(ditaCpu).reset().toString());
        System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a( "Inserire previsione: ").reset().toString());
        int previsioneCpu = random.nextInt(ditaCpu+1,ditaCpu+6);
        System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a(previsioneCpu).reset().toString());
        ArrayList<Integer> mano = new ArrayList<>();
        mano.add(ditaCpu);
        mano.add(previsioneCpu);
        return mano;
    }

    /**
     * Il computer può solo giocare
     */
    @Override
	public ModeEnumeration getMode() {
		return ModeEnumeration.GIOCA;
	}
}
