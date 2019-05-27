package tp.pr3.logic.multigames;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Scanner;

import tp.pr3.Board;
import tp.pr3.Direction;
import tp.pr3.GameState;
import tp.pr3.GameStateStack;
import tp.pr3.MoveResults;
import tp.pr3.Position;
import tp.pr3.exceptions.ExecutionException;
import tp.pr3.exceptions.GameOverException;
import tp.pr3.exceptions.InvalidNumberException;
import tp.pr3.util.GameType;

public class Game {
	
	private Board _board; // tablero
	private int _size; // Dimension del tablero
	private int _initCells; //Numero de baldosas no nulas iniciales
	private Random _myRandom; //Comportamiento aleatorio del juego
	private int score; //puntuacion total
	private int highest; //valor de la cell de valor mas alto
	private GameStateStack undoStack = new GameStateStack();
	private GameStateStack redoStack = new GameStateStack();
	private GameType gt;
	private final static int DEFAULT_SIZE = 4;
	private final static int DEFAULT_CELLS = 2;
	
	public Game(int _size, int _initCells, Random _myRandom, GameType gt2) {
		this._size = _size;
		this._initCells = _initCells;
		this._myRandom = _myRandom;
		this._board = new Board(this._size);
		this.score = 0;
		this.highest = 0;
		this.gt = gt2;
		
		reset();
	}
	
	//Getter para el controller saber si acabar la partida o no (llegado a 2048)
	
	public int getHighest() {
		
		return this.highest;
		
	}
	
	/*Guarda el tablero llamando al metodo de board, deja una linea en blanco y guarda los valores pedidos y el tipo de juego*/
	
	public void store(BufferedWriter out) throws IOException {
		
		this._board.save(out);
		out.newLine();
		out.write(this._initCells + " " + this.score + " " + this.gt.externalise());
		
	}
	
	/*Carga un juego, llamando al board para cargar lo correspondiente y comprobando los valores para modificar el juego.
	 * Si falla, volvera al estado previo con backup guardado previamente
	 * Limpia tambien las pilas de undo y redo*/
	
	public GameType load(BufferedReader buffer) throws IOException, ExecutionException {
		GameState backup = this.getState();
		int sizeBackup = this._size;
		
		int min = 2048, max = 0, initial = 0, score = 0;
		try {
			this._board.loadBoard(buffer);
			String[] words = buffer.readLine().split("\\s+");
			
			try {
				initial = Integer.parseInt(words[0]);
				score = Integer.parseInt(words[1]);
				
				max = this._board.getMaxValue();
				min = this._board.getMinValue();

				this.gt = GameType.parse(words[2]);
				if(gt == GameType.INV)	
					this.highest = min;
				else if (gt == GameType.FIB || gt == GameType.ORIG)
					this.highest = max;
				else {
					this._size = sizeBackup;
					this._board = new Board(this._size);
					this.setState(backup);
					throw new ExecutionException("Not valid game type\n");
				}
				this._initCells = initial;
				this.score = score;
				this.redoStack.cleanStack();
				this.undoStack.cleanStack();
				
			} catch (NumberFormatException e) {
				throw new ExecutionException("Init cells and/or score are/is not a number\n");

			}
			
		}
		catch (ExecutionException e) {
			this._size = sizeBackup;
			this._board = new Board(this._size);
			this.setState(backup);
			throw new ExecutionException(e.getMessage());
		}
		
		return gt;

	}
	
	public boolean play(GameType gameType, Scanner in) throws ExecutionException {
		
		int size = -1,init = -1; 
		long seed = -1;
		String[] words;
		String line;
		Scanner s = in;
		
		if(gameType == null)
			throw new ExecutionException("Play must be followed by a game type: original, fib, inverse\n");

		

		//SIZE: mientras no metamos un size valido, sigue pidiendolo; si damos enter, usa el valor por defecto
		
		System.out.println("*** You have chosen to play the game: " + gameType.toString() + " ***");
		
		do {
			System.out.print("Please enter the size of the board: ");
			line = s.nextLine().trim().replaceAll("\\s{2,}"," ");
			words = line.split(" ");
		
			if(words.length == 1) {
			
				try {
					
					if(words[0].equals("")) {
						System.out.println("  Using default size of the board: " + DEFAULT_SIZE);
						size = DEFAULT_SIZE;
					}
					
					else if(2 <= Integer.parseInt(words[0]))
						
							size = Integer.parseInt(words[0]);
					
					else {
						try{
							if ( 2 > Integer.parseInt(words[0])){
								throw new InvalidNumberException("  The size of the board must be positive...");
							}
						} catch (InvalidNumberException e) {
							System.out.println(e.getMessage());
							size = -1;
						}
					}
					
					
				} catch (NumberFormatException e) {
					System.out.println("  The size of the board must be a number...");
					size = -1;
				}
			}	
			
			else
				System.out.println("  Please, provide a single positive integer or press return.");
			
		}while(size == -1);
			
		// END SIZE
		
		//INIT CELLS: pedimos un valor de init cells hasta que se de uno valido o se de enter, en cuyo caso usara el valor por defecto
		
		do {
			System.out.print("Please enter the number of initial cells: ");
			line = s.nextLine().trim().replaceAll("\\s{2,}"," ");
			words = line.split(" ");
			
			if(words.length == 1) {
			
				try {
					if(words[0].equals("")) {
						System.out.println("  Using default number of initial cells: " + DEFAULT_CELLS);
						init = DEFAULT_CELLS;
					}
					
					else if (1 <= Integer.parseInt(words[0])){
						init = Integer.parseInt(words[0]);
					}
					
					else {
						try{
							if ( 1 > Integer.parseInt(words[0])){
								throw new InvalidNumberException("  The number of initial cells must be greater than zero");
							}
						} catch (InvalidNumberException e) {
							System.out.println(e.getMessage());
							init = -1;
						}
					}
						
				} catch (NumberFormatException e) {
					System.out.println("  The number of initial cells must be a number");
					init = -1;
				}
			}	
			
			else
				System.out.println("  Please, provide a single positive integer or press return.");
			
		}while(init == -1);
		
		//END INIT CELLS
		
		//PSEUDO-RANDOM NUMBER: pedimos un valor para la semilla; en caso de no proporcionarlo, usa una aleatoria de [0, 1000)
		
		do {
			System.out.print("Please enter the seed for the pseudo-random number generator: ");
			line = s.nextLine().trim().replaceAll("\\s{2,}"," ");
			words = line.split(" ");
			
			if(words.length == 1) {
			
				try {
					if(words[0].equals("")) {
						seed = new Random().nextInt(1000);
						System.out.println("  Using default size for the pseudo-random number generator: " + seed);
						
					}
					
					else if ((0 <= Integer.parseInt(words[0].replaceAll("[\\D]", ""))) && (1000 > Integer.parseInt(words[0].replaceAll("[\\D]", "")))){
						
						seed = (Integer.parseInt(words[0]));
						
					}
					
					else {
						try{
							if ( 0 > Integer.parseInt(words[0])){
								throw new InvalidNumberException("  Please provide a single positive integer or press return\r\n");
							}
						} catch (InvalidNumberException e) {
							System.out.println(e.getMessage());
							seed = -1;
						}
					}
						
				} catch (NumberFormatException e) {
					System.out.println("  The seed for the pseudo-random number generator must be a number.");
					seed = -1;
				}
			}	
			
		}while(seed == -1);
		
		try {
			if(init > size*size)
				throw new InvalidNumberException("The number of initial cells must be less than the number of cells on the board.\n");
			
			hardReset(gameType, size, init, seed);
				
		} catch(InvalidNumberException e) {
			throw new ExecutionException(e.getMessage());
		}
		
		return true;
	}
	
	
	/*Si se ha ganado el juego o perdido, devuelve true para que acabe el juego en controller*/
	
	public int gameEnded() throws GameOverException{
		
		GameRules currentRules = gt.getRules();
		
		int gameOver = 0;
		boolean win = currentRules.win(this._board);
		boolean lose = currentRules.lose(this._board);
		
		if(win) {
			throw new GameOverException("You won!");
		}
		
		else if (lose) {
			throw new GameOverException("You lose: GAME OVER");
		}
		
		return gameOver;
		
	}
	
	//Ejecuta un movimiento en el board, crea una nueva cell si se ha movido y actualiza puntuaciones
	
	public void move(Direction dir) throws ExecutionException {
		MoveResults results = null;
		GameRules currentRules = gt.getRules();
		//Guardamos el estado antes de mover por si queremos deshacer el movimiento y volver al estado anterior
		undoStack.push(new GameState(this._board.getState(), this.highest, this.score));
		results = this._board.executeMove(dir, currentRules);
		this.score += results.getPoints();
		if(results.isMoved()) {
			currentRules.addNewCell(this._board, this._myRandom);
			this.highest = currentRules.getWinValue(this._board);
			redoStack.cleanStack();//hay que limpiar la pila de redo cada vez que mueves
		}
		else
			throw new ExecutionException("Nothing moved! Try another direction\n");
	}
	
	//Reset casilla a casilla del tablero
	//Reset de las puntuaciones
	//Genera un tablero nuevo acorde a las reglas
	
	public void reset() {
		GameRules currentRules = this.gt.getRules();
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
		
		GameRules currentRules = gt.getRules();
		
		this.redoStack.cleanStack();
		this.undoStack.cleanStack();
		this.score = 0;
		this.highest = 0;
		this._size = size;
		this._initCells = init;
		this._board = currentRules.createBoard(this._size);
		this._myRandom = new Random(seed);
		this.gt = gt;
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

	public void redo() throws ExecutionException {
		
		try{
			GameState gs = redoStack.pop();
			undoStack.push(getState());
			setState(gs);
		}
		
		catch (EmptyStackException e) {
			throw new ExecutionException("Nothing to Redo\n");
		}
	}

	/*deshace un movimiento y lo guarda en la pila redo por si lo rehacemos de nuevo*/
	
	public void undo() throws ExecutionException {
		try {
			GameState gs = undoStack.pop();
			redoStack.push(getState());
			setState(gs);
		}
		catch (EmptyStackException e) {
	
			throw new ExecutionException("Undo is not available\n");
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
