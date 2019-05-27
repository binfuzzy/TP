package tp.pr3;

import java.util.EmptyStackException;

/*Para hacer una pila de CAPACITY estados del juego*/

public class GameStateStack {
	
	private static final int CAPACITY = 20;
	private GameState[] buffer = new GameState[CAPACITY+1];
	private int counter = 0;
	
	public static int getCapacity() {
		return CAPACITY;
	}
	public GameState[] getBuffer() {
		return buffer;
	}

	public GameState pop() throws EmptyStackException{// Devuelve el ultimo estado almacenado
		
		GameState gs = null;
		
		if (!isEmpty()){
			
			gs = buffer[counter - 1];
			counter--;
			
		}
		
		else throw new EmptyStackException();
		
		return gs;
		
	}

	public void push(GameState state) {  // Almacena un nuevo estado
		
		if(counter < CAPACITY) {
			this.buffer[counter] = state;
			counter++;
		}
		
		else {
			int i;
			for(i = 0; i < CAPACITY - 1; i++) {
				buffer[i] = buffer[i + 1];
			}
			
			buffer[CAPACITY - 1] = state;
		}
			
		
	}
	
	public boolean isEmpty() {// Devuelve true si la secuencia esta vacia
		
		return counter == 0 ? true : false;
		
	}
	
	/*Inicializando de nuevo el contador a 0, ya no tiene en cuenta los estados anteriores y los sobreescribe en la pila*/

	public void  cleanStack() {
		counter = 0;
	}
	

}
