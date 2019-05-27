package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.Board;
import tp.pr2.Direction;
import tp.pr2.GameState;
import tp.pr2.GameStateStack;
import tp.pr2.MoveResults;
import tp.pr2.Position;
import tp.pr2.util.GameType;

public class Game {
	
	private Board _board; // tablero
	private int _size; // Dimension del tablero
	private int _initCells; //Numero de baldosas no nulas iniciales
	private Random _myRandom; //Comportamiento aleatorio del juego
	private int score; //puntuacion total
	private int highest; //valor de la cell de valor mas alto
	private GameStateStack undoStack = new GameStateStack();
	private GameStateStack redoStack = new GameStateStack();
	private GameRules currentRules;
	
	public Game(int _size, int _initCells, Random _myRandom, GameRules currentRules) {
		this._size = _size;
		this._initCells = _initCells;
		this._myRandom = _myRandom;
		this._board = new Board(this._size);
		this.score = 0;
		this.highest = 0;
		this.currentRules = currentRules;

		reset();
	}
	
	//Getter para el controller saber si acabar la partida o no (llegado a 2048)
	
	public int getHighest() {
		
		return this.highest;
		
	}
	
	/*Si se ha ganado el juego o perdido, devuelve true para que acabe el juego en controller*/
	
	public boolean gameEnded() {
		
		boolean win = this.currentRules.win(this._board);
		boolean lose = this.currentRules.lose(this._board);
		
		if(win)
			System.out.println("You reached " + this.currentRules.getWinValue(this._board) + ", you won!");
		else if (lose)
			System.out.println("You lose: GAME OVER");
		
		return ((win) || (lose));
		
	}
	
	//Ejecuta un movimiento en el board, crea una nueva cell si se ha movido y actualiza puntuaciones
	
	public void move(Direction dir) {
		MoveResults results = null;
		//Guardamos el estado antes de mover por si queremos deshacer el movimiento y volver al estado anterior
		undoStack.push(new GameState(this._board.getState(), this.highest, this.score));
		results = this._board.executeMove(dir, this.currentRules);
		this.score += results.getPoints();
		if(results.isMoved()) {
			this.currentRules.addNewCell(this._board, this._myRandom);
			this.highest = this.currentRules.getWinValue(this._board);
			redoStack.cleanStack();//hay que limpiar la pila de redo cada vez que mueves
		}
		else
			System.out.println("Nothing moved! Try another direction\n");
	}
	
	//Reset casilla a casilla del tablero
	//Reset de las puntuaciones
	//Genera un tablero nuevo acorde a las reglas
	
	public void reset() {
		
		long seed = 0;
		seed = new Random().nextInt(1000);
		this._board = currentRules.createBoard(this._size);
		this.score = 0;
		this.highest = 0;
		this._myRandom = new Random(seed);
		
		currentRules.initBoard(this._board, this._initCells, this._myRandom);
		this.highest = currentRules.getWinValue(this._board);//valor de la baldosa mas alta rellenada
		
	}
	
	/*Ejecuta un reset del juego completo, incluidas las reglas y el tablero*/
	
	public void hardReset(GameType gt, int size, int init, long seed) {
		
		if(gt == GameType.FIB)
			this.currentRules = new RulesFib();
		if(gt == GameType.ORIG)
			this.currentRules = new Rules2048();
		if(gt == GameType.INV)
			this.currentRules = new RulesInverse();
		
		this.score = 0;
		this.highest = 0;
		this._size = size;
		this._initCells = init;
		this._board = currentRules.createBoard(this._size);
		this._myRandom = new Random(seed);
		currentRules.initBoard(this._board, this._initCells, this._myRandom);
		this.highest = currentRules.getWinValue(this._board);//valor de la baldosa mas alta rellenada

		
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
	
	
	
	public String toString() {
		
		String gamePoints;
		
		gamePoints = this._board.toString();
		String patron = " Best value: %d          Score: %d\n";
		gamePoints += String.format(patron, this.highest, this.score);
		
		return gamePoints;
	}
	
	
	/*PR2 methods*/
	
	/*Rehace un movimiento anterior guardado en pila y lo guarda en undo por si deshacemos despues*/

	public void redo() {
		
		GameState gs = redoStack.pop();
		if(gs == null)
			System.out.println("Nothing to Redo\n");
		else {
			undoStack.push(getState());
			setState(gs);
			System.out.println("Redoing one move...\n");
		}	
	}

	/*deshace un movimiento y lo guarda en la pila redo por si lo rehacemos de nuevo*/
	
	public void undo() {
		
		GameState gs = undoStack.pop();
		if(gs == null)
			System.out.println("Undo is not available\n");
		else {
			redoStack.push(getState());
			setState(gs);
			System.out.println("Undoing one move...\n");
		}
	}
	
	
	public GameState getState() {// Devuelve el estado actual del juego invocando el metodo getState de Board
		
		return new GameState(this._board.getState(), this.highest, this.score);
		
	}
	
	
	public void setState(GameState aState) { // Restablece el juego al estado aState e invocando el metodo setState de Board
		
		this._board.setState(aState.getBoardState());
		this.score = aState.getScore();
		this.highest = aState.getHighest();

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
