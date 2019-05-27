package tp.pr2;

public class MoveResults {
	
	private boolean moved;//identifica si ha habido movimiento o fusion de baldosas
	private int points;//almacena el numero de puntos obtenidos en el movimiento
	
	public MoveResults(boolean moved, int points) {
		this.moved = moved;
		this.points = points;

	}

	public boolean isMoved() {
		return this.moved;
	}

	public int getPoints() {
		return this.points;
	}

}
