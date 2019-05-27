package tp.pr1;

import java.util.Random;

import tp.pr1.util.ArrayAsList;

public class Game {
	
	private Board _board; // tablero
	private int _size; // Dimension del tablero
	private int _initCells; //Numero de baldosas no nulas iniciales
	private Random _myRandom; //Comportamiento aleatorio del juego
	private int score; //puntuacion total
	private int highest; //valor de la cell de valor mas alto
	private static final int END = 2048;
	
	public Game(int _size, int _initCells, Random _myRandom) {
		int i = 0, value = 0;
		this._size = _size;
		this._initCells = _initCells;
		this._myRandom = _myRandom;
		this._board = new Board(this._size);
		this.score = 0;
		this.highest = 0;
		//Inicializa el tablero
		while(i < this._initCells) {
			value = fillEmpty();
			if(value > this.highest) {
				this.highest = value;//valor de la baldosa mas alta rellenada
			}
			i++;
		}
	}
	
	//Getter para el controller saber si acabar la partida o no (llegado a 2048)
	
	public int getHighest() {
		
		return this.highest;
		
	}
	
	public boolean gameEnded() {
		
		if(this.highest == END)
			System.out.println("You reached " + END + ", you won!");
		
		return this.highest == END;
		
	}
	
	//Ejecuta un movimiento en el board, crea una nueva cell si se ha movido y actualiza puntuaciones
	
	public void move(Direction dir) {
		MoveResults results = null;
		results = this._board.executeMove(dir);
		this.score += results.getPoints();
		if(this.highest < results.getMaxToken())
		this.highest = results.getMaxToken();
		if(results.isMoved())
			fillEmpty();
		else
			System.out.println("Nothing moved! Try another direction");
	}
	
	//Reset casilla a casilla del tablero
	//Reset de las puntuaciones
	//Genera un tablero nuevo
	
	public void reset() {
		
		int i = 0, value = 0, j = 0, k = 0;
		
		for(j = 0; j < this._size; j++) {
			for(k = 0; k < this._size; k++) {
				Position pos = new Position(i, j);
				this._board.setCell(pos, 0);
			}
		}
		this.score = 0;
		this.highest = 0;
		while(i < this._initCells) {
			value = fillEmpty();
			if(value > this.highest) {
				this.highest = value;//valor de la baldosa mas alta rellenada
			}
			i++;
		}
		
	}
	
	//Metodo que rellena una celda libre del tablero:
	//Primero genera un arraylist de posiciones libres y coge una random para hacerlo inicializandola
	
	private int fillEmpty() {
		
		Position pos = null;
		ArrayAsList list = new ArrayAsList(this._size * this._size);
		int counter = 0;
		Random r = this._myRandom;
		
		for(int i = 0; i < this._size; i++) {
			for(int j = 0; j < this._size; j++) {
				pos = new Position(i, j);
				if(this._board.isEmpty(pos)) {
					list.set(pos, counter);
					counter++;
				}		
			}
		}
		
		int value = r.nextInt(counter);
		pos = (Position) list.get(value);
		
		value = initialCell();
		this._board.setCell(pos, value);;
		
		return value;
	}

	//Inicializador de celdas con valores 2 (90%) o 4(10%)
	
	private int initialCell() {
		
		Random r = this._myRandom;
		int value = r.nextInt(10);
		if(value != 4)
			value = 256;
		
		return value;
	}
	
	//Para chequear desde controller si el tablero tiene alguna vacia; si encuentra una sale del for directamente
	
	public boolean emptyCells() {
		
		for(int i = 0; i < this._size; i++) {
			for(int j = 0; j < this._size; j++) {
				Position pos = new Position(i, j);
				if(this._board.isEmpty(pos))
					return true;
			}
		}
		return false;
	}
	
	//Para chequear desde controller que existen movs aun estando el tablero sin celdas libres
	
	public boolean checkMoves() {
		
		Position pos = null, posNeig = null, posNeig2 = null;
		
		for(int i = 0; i < this._size; i++) {
			for(int j = 0; j < this._size; j++) {
				pos = new Position(i, j);
				posNeig = new Position(i, j+1);
				posNeig2 = new Position(i+1, j);
				
				if((j != this._size - 1) && (i != this._size - 1)) {
					if((this._board.getCell(pos) == this._board.getCell(posNeig)) ||
						(this._board.getCell(pos) == this._board.getCell(posNeig2)))
							return true;
				}
				else if((j == this._size - 1) && (i != this._size - 1)) {
					if(this._board.getCell(pos) == this._board.getCell(posNeig2))
						return true;
				}
				else if	((j != this._size - 1) && (i == this._size - 1)) {
					if(this._board.getCell(pos) == this._board.getCell(posNeig))
						return true;
				}
			}
		}
		return false;
	}
	
	public String toString() {
		
		String gamePoints;
		
		gamePoints = this._board.toString();
		String patron = " Highest: %d          Score: %d\n";
		gamePoints += String.format(patron, this.highest, this.score);
		
		return gamePoints;
	}

	public static void main(String[] args) {
		
		/*Game g = new Game(6,4, new Random(0));
		System.out.println(g);*/

	}

}
