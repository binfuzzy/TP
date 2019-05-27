package tp.pr2;

/*Clase para representar un estado del juego*/

public class GameState {

	private int[][] boardState; // estado del tablero
	private int score;
	private int highest;
	
	public GameState(int[][] boardState, int highest, int score) {
		
		this.boardState = boardState;
		this.score = score;
		this.highest = highest;
		
	}

	public int[][] getBoardState() {
		return boardState;
	}

	public int getScore() {
		return score;
	}

	public int getHighest() {
		return highest;
	}
	
}
