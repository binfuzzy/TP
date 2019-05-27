package tp.pr3;

public class Position {

	private int row;
	private int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	@Override
	public String toString() {
		return "Position [row=" + row + ", col=" + col + "]";
	}
	
	//Para saber si una posicion esta dentro del tablero
	
	public boolean inOut(int _boardSize) {

		boolean inOut = false;
		
		if((this.row < _boardSize) && (this.col < _boardSize))
			inOut = true;
		
		return inOut;
	}
	
	//Obtiene la pos del vecino en una direccion dada
	
	public Position getNeighbour(Direction dir, int _boardSize) {

		Position p = null;
		
		if(dir == Direction.UP) {
			if(this.row == 0)   //si estoy arriba del todo nada
				return null;
			else 
				p = new Position(this.row - 1, this.col);
		}
		
		else if(dir == Direction.DOWN) {
			if(this.row == _boardSize - 1)   //si estoy abajo del todo nada
				return null;
			else
				p = new Position(this.row + 1, this.col);
			
		}
		
		else if(dir == Direction.LEFT) {
			if(this.col == 0)	//si estoy izquierda del todo nada
				return null;
			else
				p = new Position(this.row, this.col - 1);
		}
		
		else if(dir == Direction.RIGHT) {
			if(this.col == _boardSize - 1)   //si estoy derecha del todo nada
				return null;
			else
				p = new Position(this.row, this.col + 1);
		}

		return p;
	}
	
}
