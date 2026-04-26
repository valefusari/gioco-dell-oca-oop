package oca.service.impl;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Singleton per la lettura da tastiera
 */
public class CommandLineSingleton implements Closeable{
	/**
	 * Singleton per la lettura da tastiera
	 */
	private final Scanner scanner = new Scanner(System.in);
	private static final CommandLineSingleton cls = new CommandLineSingleton();

	/**
	 * Costruttore privato
	 */

	private CommandLineSingleton() {}

	/**
	 * Metodo per ottenere l'istanza del singleton
	 * @return l'istanza del singleton
	 */
	public static CommandLineSingleton getInstance() {
		return cls;
	}

	/**
	 * Metodo per ottenere lo scanner
	 * @return lo scanner
	 */

	public Scanner getScanner() {
		return scanner;
	}

	/**
	 * Metodo per chiudere lo scanner
	 */
	public void disposeScanner() {
		scanner.close();
	}

	/**
	 * Metodo per chiudere lo scanner
	 */
	@Override
	public void close() throws IOException {
		scanner.close();	
	}
	
	/**
	* Metodo per leggere un intero da tastiera
	* @return l'intero letto da tastiera
	*/
	public int readInteger() {
		while (true) {
			try {
				return scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Input non valido. Per favore, inserisci un numero intero.");
				scanner.next(); // Consuma l'input non valido
			}
		}
	}
	
	
	/**
	 * Metodo per leggere un intero da tastiera, finché non viene inserito un valore valido
	 * @param possibleValues valori validi che l'utente può inserire
	 * @return l'intero letto da tastiera
	 */
	public Integer readIntegerUntilPossibleValue(Integer[] possibleValues){
		while (true) {
			try {
				Integer scelta = CommandLineSingleton.getInstance().readInteger();
				if (Arrays.asList(possibleValues).contains(scelta)){
					return scelta;
				}

			} catch (InputMismatchException e) {
				String s = CommandLineSingleton.getInstance().readString();
				System.out.println("Scelta inammissibile. Devi inserire un valore idoneo: 1, 2");
				Log.getInstance().warning("Input fornito non valido. Input: "+s);
				scanner.nextLine();
			}
		}
		
	}

	public String readString(){
		return scanner.next();
	}
	
}
