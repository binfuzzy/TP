package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.control.commands.Command;
import tp.pr3.control.commands.CommandParser;
import tp.pr3.exceptions.ExecutionException;
import tp.pr3.exceptions.GameOverException;
import tp.pr3.exceptions.InvalidNumberException;
import tp.pr3.exceptions.ParsingException;
import tp.pr3.logic.multigames.Game;

public class Controller {
	
	public static final String commandErrorMsg = "Unknown command. Use ’help’ to see the available commands";
	private Game game; 
	private Scanner in; 
	
	public Controller(Game game, Scanner in) {
		this.game = game;
		this.in = in;
	}

	//Ejecuta el juego, pidiendo al usuario un comando hasta que se acabe el juego o meta uno correcto, y si 
	//es válido lo ejecuta
	
	public void run() throws InvalidNumberException{
		
		String[] words;
		String line;
		int gameOver = 0;
		
		System.out.println(game);//muestra el tablero, se hace tras move, play o reset
		
		//Chequear el caso en que el tablero se haya creado lleno
		
		try {
			game.gameEnded();
		} catch (GameOverException e) {
			System.out.println(e.getMessage());
			gameOver = -1;
		}
		
		
		while(gameOver == 0) {
			//trim elimina los espacios previos y posteriores, 
			//replaceAll es para evitar varios espacios intermedios
			System.out.print("Command > ");
			line = in.nextLine().trim().replaceAll("\\s{2,}"," ");
			words = line.split(" ");
			
			try { //intenta parsear el comando
				
				Command command = CommandParser.parseCommand(words);
				
				try { //intenta ejecutar el comando
					if(command.execute(game))
						System.out.println(game);
					
					try { //comprueba si acaba el juego solo si has intentado mover
						if(words[0].equals("move"))
							game.gameEnded();
					} catch (GameOverException e) {
						System.out.println(e.getMessage());
						gameOver = -1;
					}
					
				} catch (ExecutionException e) {
					System.out.println(e.getMessage());
				}
				
			} catch (ParsingException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		in.close();
	}
	
	
	
}
