package oca.service.impl;

import oca.service.CasellaService;
import oca.datamodel.Casella;
import oca.datamodel.CasellaLabirinto;
import oca.datamodel.CasellaLocanda;
import oca.datamodel.CasellaMorra;
import oca.datamodel.CasellaOca;
import oca.datamodel.CasellaPonte;
import oca.datamodel.CasellaPrigione;
import oca.datamodel.CasellaScheletro;
import oca.datamodel.CasellaTris;

/**
 * Classe factory per ottenere il servizio associato alla casella.
 */
public class CasellaServiceFactory {

	/**
	 * Metodo per ottenere il servizio associato alla casella passata come parametro.
	 * @param clazz classe della casella
	 * @return il servizio associato alla casella
	 * @param <T> tipo della casella
	 */
    public static <T extends Casella> CasellaService<? extends Casella> getCasellaService(Class<T> clazz){
			
		if (clazz == CasellaLabirinto.class)
			return new CasellaLabirintoImpl();
		if (clazz == CasellaLocanda.class)
			return new CasellaLocandaImpl();
		if (clazz == CasellaPonte.class)
			return new CasellaPonteImpl();
		if (clazz == CasellaPrigione.class)
			return new CasellaPrigioneImpl();
		if (clazz == CasellaOca.class)
			return new CasellaOcaImpl();
		if (clazz == CasellaTris.class)
			return new CasellaTrisImpl();
        if (clazz == CasellaMorra.class)
			return new CasellaMorraImpl();
        if (clazz == CasellaScheletro.class)
            return new CasellaScheletroImpl();
        if (clazz == Casella.class)
            return new CasellaImpl();

		Log.getInstance().error("Classe "+clazz.getName()+" non trovata");

		return null;
    }
}
