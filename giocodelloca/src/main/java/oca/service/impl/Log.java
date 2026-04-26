package oca.service.impl;

import oca.service.ErroreCreazioneLog;
import java.util.logging.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe per la gestione del file di log.
 */
public class Log {
    /**
     * Logger per la scrittura del file di log
     */
    private static final Logger logger = Logger.getLogger(Log.class.getName());

    /**
     * Istanza del logger
     */
    private static Log instance;

    /**
     * Costruttore privato per la creazione del file di log.
     * @throws ErroreCreazioneLog se si verifica un errore nella creazione del file di log
     */
    private Log() throws ErroreCreazioneLog {
        try{
            FileHandler fh = new FileHandler("logFile.log", true);
            fh.setFormatter(new SimpleFormatter(){
                @Override
                public String format(LogRecord record){
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    return now.format(formatter) + " - " + record.getLevel() + ": " + record.getMessage() + "\n";
                }
            });
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
        } catch (IOException e){
            throw new ErroreCreazioneLog("Errore nella creazione del file di log");
        }
    }

    /**
     * Metodo per ottenere l'istanza del logger
     * @return l'istanza del logger
     */
    public static Log getInstance(){
        if(instance == null){
            try {
                instance = new Log();
            } catch(ErroreCreazioneLog e){
                System.out.println("Errore nella creazione del file di log");
            }
        }
        return instance;
    }

    /**
     * Metodo per scrivere un messaggio di log di tipo info
     * @param msg il messaggio da scrivere
     */
    public void info(String msg){
        logger.info(msg);
    }

    /**
     * Metodo per scrivere un messaggio di log di tipo warning
     * @param msg il messaggio da scrivere
     */
    public void warning(String msg){
        logger.warning(msg);
    }

    /**
     * Metodo per scrivere un messaggio di log di tipo error
     * @param msg il messaggio da scrivere
     */
    public void error(String msg){
        logger.severe(msg);
    }
}
