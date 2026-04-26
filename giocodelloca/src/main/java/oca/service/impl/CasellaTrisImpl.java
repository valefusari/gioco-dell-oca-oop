package oca.service.impl;

import java.util.ArrayList;

import oca.datamodel.Giocatore;
import oca.service.GiocatoreService;
import oca.service.SimboloTris;

import org.fusesource.jansi.Ansi;

/**
 * Classe che implementa la casella Tris
 */
public class CasellaTrisImpl extends MiniGameImpl {
    /**
     * Metodo per gestire una partita di Tris tra due giocatori.
     * @param g1 il primo giocatore
     * @param g2 il secondo giocatore
     */
    @Override
    protected void partita(Giocatore g1, Giocatore g2) {
        ArrayList<SimboloTris> board = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            board.add(SimboloTris.V);
        }
        Giocatore currentPlayer = g1;
        SimboloTris currentSymbol = SimboloTris.X;
        boolean gameWon = false;
        GiocatoreService<? extends Giocatore> gs1 = (g1.getIsUmano()) ? new GiocatoreRealeServiceImpl() : new GiocatoreBotServiceImpl();       
        GiocatoreService<? extends Giocatore> gs2 = (g2.getIsUmano()) ? new GiocatoreRealeServiceImpl() : new GiocatoreBotServiceImpl();       

        System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a( "Sei arrivato in una casella Tris!").reset().toString()); 
        System.out.println(Ansi.ansi().cursorToColumn(0).fg(Ansi.Color.CYAN).a( "Si sfideranno i giocatori " + g1.getNome() + " e " + g2.getNome()).reset().toString());  
        printBoard(board);
        for (int turn = 0; turn < 9 && !gameWon; turn++) {
            int move;

            System.out.println("E' il turno del giocatore " + currentPlayer.getNome() + "!");
            do {
                move = (currentPlayer == g1) ? gs1.sceltaTris() : gs2.sceltaTris();
            } while (!isMoveValid(move, board));                  
            System.out.println("Il giocatore " + currentPlayer.getNome() + " ha scelto la mossa: " + move);
            board.set(move - 1, currentSymbol);

            gameWon = checkWin(board, currentSymbol);
            if (gameWon) {
                setVincitore(currentPlayer);
                Log.getInstance().info("Risultato partita Tris: " + currentPlayer.getNome() + " ha vinto");
            }
            
            printBoard(board);
            // Passa al giocatore successivo
            currentPlayer = (currentPlayer == g1) ? g2 : g1;
            currentSymbol = (currentSymbol == SimboloTris.X) ? SimboloTris.O : SimboloTris.X;
        }

        if (!gameWon) {
            vincitore = null;
            printBoard(board);
            Log.getInstance().info("Risultato partita Tris: pareggio");
        }
    }

    /**
     * Metodo per stampare la griglia del Tris.
     * @param board la griglia da stampare
     */
    private void printBoard(ArrayList<SimboloTris> board) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a(
            " " + board.get(0) + " | " + board.get(1) + " | " + board.get(2) + "\n" +
            "---+---+---\n" +
            " " + board.get(3) + " | " + board.get(4) + " | " + board.get(5) + "\n" +
            "---+---+---\n" +
            " " + board.get(6) + " | " + board.get(7) + " | " + board.get(8) + "\n"
        ).reset().toString());
    }

    /**
     * Metodo per verificare se una mossa è valida.
     * @param move la mossa da verificare
     * @param board la griglia in cui verificare la mossa
     * @return `true` se la mossa è valida, altrimenti `false`
     */
    private boolean isMoveValid(int move, ArrayList<SimboloTris> board) {
        return board.get(move - 1) == SimboloTris.V;
    }

    /**
     * Metodo per verificare se un giocatore ha vinto.
     * @param board la griglia da controllare
     * @param symbol il simbolo del giocatore da controllare
     * @return `true` se il giocatore ha vinto, altrimenti `false`
     */
    private boolean checkWin(ArrayList<SimboloTris> board, SimboloTris symbol) {
        return (board.get(0) == symbol && board.get(1) == symbol && board.get(2) == symbol) ||
            (board.get(3) == symbol && board.get(4) == symbol && board.get(5) == symbol) ||
            (board.get(6) == symbol && board.get(7) == symbol && board.get(8) == symbol) ||
            (board.get(0) == symbol && board.get(3) == symbol && board.get(6) == symbol) ||
            (board.get(1) == symbol && board.get(4) == symbol && board.get(7) == symbol) ||
            (board.get(2) == symbol && board.get(5) == symbol && board.get(8) == symbol) ||
            (board.get(0) == symbol && board.get(4) == symbol && board.get(8) == symbol) ||
            (board.get(2) == symbol && board.get(4) == symbol && board.get(6) == symbol);
    }
}
