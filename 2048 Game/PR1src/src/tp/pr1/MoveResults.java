package tp.pr1;

public class MoveResults {
	
	private boolean moved;//identifica si ha habido movimiento o fusion de baldosas
	private int points;//almacena el numero de puntos obtenidos en el movimiento
	private int maxToken;//para llevar el valor mas alto tras el movimiento	

	public MoveResults(boolean moved, int points, int maxToken) {
		this.moved = moved;
		this.points = points;
		this.maxToken = maxToken;
	}

	public boolean isMoved() {
		return this.moved;
	}

	public int getPoints() {
		return this.points;
	}

	public int getMaxToken() {
		return this.maxToken;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
