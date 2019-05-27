package tp.pr2;

import tp.pr2.logic.multigames.GameRules;

public class Cell {
	
	private int value;
	
	public Cell(int value) {  //constructora de celdas con valor
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean isEmpty() { //comprueba si la celda esta vacia
		
		return this.value == 0;
	}
	
	public int doMerge(Cell neighbour, GameRules rules) { //compueba el valor de la celda y su vecina para poder fusionarse
		//Usando las reglas especificas del juego
		return rules.merge(this, neighbour);
		
	}

}
