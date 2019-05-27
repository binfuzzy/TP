package tp.pr3.logic.multigames;

import java.util.Random;

import tp.pr3.Board;
import tp.pr3.Cell;
import tp.pr3.Position;
import tp.pr3.util.*;

public interface GameRules {
	
	
	int calculatePoints(int value); //Para hacer un calculo de puntos acorde a las reglas de cada juego
	
	boolean checkMoves(Board board); //Los chequeos de movimientos son distintos para cada juego segun sus reglas

	void addNewCellAt(Board board, Position pos, Random rand);//Incorpora una nueva celda con valor aleatorio en la posicion pos del tablero board
	
	int merge(Cell self, Cell other); // fusiona dos celdas y devuelve el numero de puntos obtenidos
	
	default boolean canMergeNeighbours(Cell cell1, Cell cell2) { //Este método comprueba si dos celdas pueden fusionarse
	
		if(cell1.getValue() == cell2.getValue())
			return true;
		else 
			return false;
	
	
	}
	
	int getWinValue(Board board); // Devuelve el mejor valor de tablero segun las reglas del juego, comprobando si es un valor ganador y si se ha ganado el juego,
	
	boolean win(Board board); // Devuelve si el juego se ha ganado o no
	
	default boolean lose(Board board) {

		boolean moves = board.canMerge(this);
		boolean full = board.isFull();
		
		if(moves && full)
			System.out.println("Board is full, but there are moves\n");
		
		return full && !moves;


	}// Devuelve si el juego se ha perdido o no
	
	default Board createBoard(int size) {
		
		return new Board(size);
		
	} // Crea y devuelve un tablero size * size
	
	default void addNewCell(Board board, Random rand) {
		Position pos = null;
		int sizeBoard = board.getSize();
		ArrayAsList list = new ArrayAsList(sizeBoard * sizeBoard);
		int counter = 0;
		Random r = rand;
		
		for(int i = 0; i < sizeBoard; i++) {
			for(int j = 0; j < sizeBoard; j++) {
				pos = new Position(i, j);
				if(board.isEmpty(pos)) {
					list.set(pos, counter);
					counter++;
				}		
			}
		}
		
		int value = r.nextInt(counter);
		pos = (Position) list.get(value);
		addNewCellAt(board, pos, rand);
	} // Elige una posicion libre de board e invoca el metodo addNewCellAt() para añadir una celda en esa posicion,
	
	default void initBoard(Board board, int numCells, Random rand) {
		
		for(int i = 0; i < numCells; i++) {
			addNewCell(board, rand);
		}
	}// Inicializa board eligiendo numCells posiciones libres, e invoca el metodo addNewCellAt() para añadir nuevas celdas en esas posiciones.

}
