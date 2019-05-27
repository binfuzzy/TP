package tp.pr1;

import java.util.Scanner;

public class Controller {
	
	private Game game; 
	private Scanner in; 
	
	
	public Controller(Game game, Scanner in) {
		this.game = game;
		this.in = in;
	}

	//Ejecuta el juego, pidiendo al usuario un comando hasta que se acabe el juego o meta uno correcto, y si 
	//es válido lo ejecuta
	
	public void run() {
		
		String[] words;
		String line;
		boolean play = true, moves = true;
		
		System.out.println(game);//muestra el tablero, se hace tras move o reset
		
		do {
			//trim elimina los espacios previos y posteriores, 
			//replaceAll es para evitar varios espacios intermedios
			System.out.print("Command > ");
			line = in.nextLine().trim().replaceAll("\\s{2,}"," ");
			words = line.split(" ");
			
			if (words.length == 1) {
				if (words[0].equalsIgnoreCase("exit")) {
					play = false;
					System.out.println("Game Over");
				}
				else if (words[0].equalsIgnoreCase("move"))
					System.out.println("Move must be followed by a direction: up, down, left or right\n");

				else if (words[0].equalsIgnoreCase("reset")) {
					game.reset();
					System.out.println(game);
					
				}
				else if (words[0].equalsIgnoreCase("help")) {
					System.out.println(
							"Move <direction>: execute a move in one of the four directions, up, down, left, right\r\n"
									+ "Reset: start a new game\r\n" + "Help: print this help message\r\n"
									+ "Exit: terminate the program\n");
				}
		
				
				else if (words[0].equalsIgnoreCase("a")) {
					game.move(Direction.LEFT);
					System.out.println(game);
				}
				else if (words[0].equalsIgnoreCase("w")) {
					game.move(Direction.UP);
					System.out.println(game);
				}
				else if (words[0].equalsIgnoreCase("s")) {
					game.move(Direction.DOWN);
					System.out.println(game);
				}
				else if (words[0].equalsIgnoreCase("d")) {
					game.move(Direction.RIGHT);
					System.out.println(game);
				}
				
				
				else
					System.out.println("Unknown command\n");
			} 
			
			//Para las longitudes 2 los unicos validos son los movimientos, chequeamos si es alguno
			
			else if ((words.length == 2) && (words[0].equalsIgnoreCase("move"))) {
				
				if(words[1].equalsIgnoreCase("right")) {
					
					game.move(Direction.RIGHT);
					System.out.println(game);
					
				}
				
				else if(words[1].equalsIgnoreCase("left")) {
					
					game.move(Direction.LEFT);
					System.out.println(game);
					
				}
				
				else if(words[1].equalsIgnoreCase("up")) {
					
					game.move(Direction.UP);
					System.out.println(game);
					
				}
				
				else if(words[1].equalsIgnoreCase("down")) {
					
					game.move(Direction.DOWN);
					System.out.println(game);
					
				}
				
				else
					System.out.println("Unknown direction for move command\n");
				
			} 
			
			
			//Si no es ninguno de los casos anteriores, directamente pedimos otro comando
			
			else {
				System.out.println("Not a valid command. Check using help the game possible commands\n");
			}
			
			//Ganamos el juego si llegamos a la puntuacion 
			
			if(game.gameEnded()) {
				
				play = false;
				
			}
			
		moves = game.emptyCells();
		if(!moves) {
			moves = game.checkMoves();
			if(moves)
				System.out.println("Complete board, but there are moves");
			else {
				System.out.println("Game Over");
			}
			
		}
			
		//Se ejecuta en bucle mientras se pueda seguir jugando, no hayamos ganado o no demos a exit

		}while((play) && (moves));
		
		in.close();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(game);
	}
}
