package tp.pr2;

import tp.pr2.logic.multigames.GameRules;
import tp.pr2.util.MyStringUtils;

public class Board {
	
	private Cell [ ][ ] _board; //array bidimensional de celdas
	private int _boardSize;//tamaño del tablero (dimension)
	private static final String NEWLINE = System.getProperty("line.separator");//pone el salto de linea necesario para cada sistema operativo

	public Board(int _boardSize) {//constructora de board
		this._boardSize = _boardSize;
		this._board = new Cell[_boardSize][_boardSize];
		for(int i = 0; i < this._boardSize; i++) {
			for(int j = 0; j < this._boardSize; j++)
				this._board[i][j] = new Cell(0);
		}
	}
	
	//setCell de board que llama al setValue de una Cell

	public void setCell(Position pos, int value) {
		
		this._board[pos.getRow()][pos.getCol()].setValue(value);
		
	}
	
	public int getCell(Position pos) {
	
		return this._board[pos.getRow()][pos.getCol()].getValue();
	
	}
	
	public int getSize() {
		
		return this._boardSize;
	}
	
	public boolean isEmpty(Position pos) { //comprueba si la celda esta vacia
		
		int i = pos.getRow();
		int j = pos.getCol();
		return this._board[i][j].isEmpty();
		
	}

	
	//Ejecuta un movimiento y devuelve en un MoveResults los resultados de este; 
	//MoveRight es llamado con rules para que ejecute el movimiento mediante la interfaz sabiendo las reglas a usar
	
	public MoveResults executeMove(Direction dir, GameRules rules) {
		
		MoveResults results = null;
		
		if(dir == Direction.RIGHT) {
			
			results = moveRight(rules);
		}
		
		if(dir == Direction.DOWN) {

			transpose();
			results = moveRight(rules);			
			transpose();
		}
		
		if(dir == Direction.LEFT) {
			
			reflect();
			results = moveRight(rules);
			reflect();
		}
		
		if(dir == Direction.UP) {	

			transpose();
			reflect();
			results = moveRight(rules);
			reflect();
			transpose();
		}
		
		return results;
		
	}
	
	//Ejecuta el movimiento completo a la direccion derecha
			
	private MoveResults moveRight(GameRules rules) {

		boolean merged = false;
		boolean turn = false, fill = false, moved = false;
		int lap = 0, points = 0;
		
		for(int i = 0; i < this._boardSize; i++) {
			for(int j = this._boardSize - 1; j >= 0; j--) {
				if(this._board[i][j].getValue() != 0) {
					int k = j;
					while(k < this._boardSize - 1) {
						if (this._board[i][k + 1].isEmpty()) { 
							this._board[i][k + 1].setValue(this._board[i][k].getValue());
							this._board[i][k].setValue(0);
							moved = true;
						}
						if(!merged) {
							//doMerge usa ahora también rules para mediante la interfaz usar las reglas adecuadas al juego
							if(this._board[i][k].doMerge(this._board[i][k + 1], rules) != 0) {
								turn = true;
								merged = true;
								lap = 0;
								moved = true;
								//usando el calculo de puntos mediante la interfaz
								points += rules.calculatePoints(this._board[i][k + 1].getValue());
								k = this._boardSize - 2;
							}
						}
						
						if((this._board[i][k].getValue() !=0) && (this._board[i][k + 1].getValue() !=0)) {
							k = this._boardSize - 2;
						}//si no se puede fusionar y ya esta colocada, pasa al final para comprobar las siguientes y no estar recorriendo para nada
							
						k++;
					}
					fill = true;
				}
				if(turn && lap == 1 && fill) {
					merged = false;
					turn = false;
				}
				if(fill)
					lap++;//si hay ceros no debe sumar el lap, (solo sumar si distinto de 0)
				fill = false;
			}	
		}
		
		MoveResults mr = new MoveResults(moved, points);
		return mr;
	}
	
	//Transpone la matriz del tablero
	
	private void transpose() {
		
		int aux;
		
		for(int i = 0; i < this._boardSize - 1; i++) {
			for(int j = i + 1; j < this._boardSize; j++) {
				aux = this._board[i][j].getValue();
				this._board[i][j].setValue(this._board[j][i].getValue());
				this._board[j][i].setValue(aux);
				
			}
		}
	}
	
	//Refleja la matriz del tablero
	
	private void reflect() {
		
		int aux;
		
		for(int i = 0; i < this._boardSize; i++) {
			for(int j = 0; j < this._boardSize/2; j++) {
				aux = this._board[i][j].getValue();
				this._board[i][j].setValue(this._board[i][this._boardSize - j - 1].getValue());
				this._board[i][this._boardSize - j - 1].setValue(aux);
			}
		}
	}
	
	

	//el metodo tostring dibuja el tablero
	
	public String toString() {
		
		int cellSize = 7;
		String space = " ";
		String vDelimiter = "|";
		String hDelimiter = "-";
		String board = space;
		int i, j, k;
		
		for(k = 0; k < this._boardSize; k++) {
			board += MyStringUtils.repeat(hDelimiter, cellSize);
			if(k < this._boardSize - 1)
				board += hDelimiter;
			else
				board += NEWLINE;
		}
		
		for(i = 0; i < this._boardSize; i++) {
			board += vDelimiter;
			for(j = 0; j < this._boardSize; j++) {
				if(this._board[i][j].isEmpty())
					board += MyStringUtils.repeat(space, cellSize);
				
				else
					board += MyStringUtils.centre(Integer.toString(_board[i][j].getValue()), cellSize);
				
				board += vDelimiter;
			}
			board += NEWLINE;
			board += space;
			for(k = 0; k < this._boardSize; k++) {
				board += MyStringUtils.repeat(hDelimiter, cellSize);
				if(k < this._boardSize - 1)
					board += hDelimiter;
				else
					board += NEWLINE;
			}
		}
		
		return board;
	}
	
	
	/*PR2 methods*/
	
	
	
	public int[ ][ ] getState(){ // Devuelve el estado actual del tablero
		
		int[][] intBoard = new int[_boardSize][ _boardSize];
		
		for(int i = 0; i < this._boardSize; i++)
			for(int j = 0; j < this._boardSize; j++)
				intBoard[i][j] = this._board[i][j].getValue();
		
		return intBoard;

	}
	
	
	public void setState(int[ ][ ] aState) { // Pone el tablero en el estado definido por aState
		
		for(int i = 0; i < this._boardSize; i++)
			for(int j = 0; j < this._boardSize; j++)
				this._board[i][j].setValue(aState[i][j]);
	
	}
	/*Recorre todas las casillas para encontrar el max de todo el tablero*/
	public int getMaxValue() {
		int max=0;
		
		for(int i = 0; i < this._boardSize; i++) {
			for(int j = 0; j < this._boardSize; j++) {
				int aux = this._board[i][j].getValue();
				if(max < aux)
					max = aux;
			}
		}
		
		return max;
	}
	
	/*Recorre primero el tablero inicializando un minimo distinto de cero (el primero que encuentra); 
	 * Recorre de nuevo buscando si hay alguno mas pequeño que ese*/
	public int getMinValue() {
		
		int min = 0;
		int i = 0, j=0;
		
		//Minimo inicializado a un valor del tablero != 0
		while ((i<this._boardSize) && (min == 0)) {
			while((j< this._boardSize) && (min == 0)) {
				min = this._board[i][j].getValue();
				j++;
			}
			j = 0;
			i++;
		}
		
		//Busqueda del minimo absoluto
		
		for(i = 0; i < this._boardSize; i++) {
			for(j = 0; j < this._boardSize; j++) {
				int aux = this._board[i][j].getValue();
				if((min > aux )&& (aux != 0))
					min = aux;
			}
		}
		
		return min;
	}
	
	
	
	

}
