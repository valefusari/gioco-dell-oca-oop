package oca;

import oca.datamodel.Gioco;
import oca.service.ErroreCaricamentoPartita;
import oca.service.ErroreCreazionePartita;
import oca.service.GiocoService;
import oca.service.impl.GiocoServiceImpl;

/**
 * Classe principale per l'avvio del gioco.
 */
public class Runner {
	public static void main(String[] args) {
		try {
			GiocoService gs = new GiocoServiceImpl();
			Gioco g = gs.creaPartita();
			gs.gioca(g);
		} catch (ErroreCreazionePartita e) {
			System.out.println("Errore nella creazione della partita");
		} catch (ErroreCaricamentoPartita e) {
			System.out.println("Errore nel caricamento della partita");
		}
	}
}
