package tp.pr1;

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
	
	public boolean doMerge(Cell neighbour) { //compueba el valor de la celda y su vecina para poder fusionarse
		
		boolean fusion = false;
		
		if(this.value == neighbour.value) {
			neighbour.value += this.value;
			this.value = 0;
			fusion = true;
		}
		
		return fusion;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
