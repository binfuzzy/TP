package tp.pr2.control;

import java.util.Scanner;
import tp.pr2.control.commands.Command;
import tp.pr2.control.commands.CommandParser;
import tp.pr2.logic.multigames.Game;

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
		
		System.out.println(game);//muestra el tablero, se hace tras move, play o reset
		
		do {
			//trim elimina los espacios previos y posteriores, 
			//replaceAll es para evitar varios espacios intermedios
			System.out.print("Command > ");
			line = in.nextLine().trim().replaceAll("\\s{2,}"," ");
			words = line.split(" ");
			Command command = CommandParser.parseCommand(words, this);
			
			if (command != null)
				command.execute(game, this);
			else { 
				//Move y Play ya tienen sus mensajes de error, este es para el resto de comandos
				if (!(words[0].equals("move")) && !(words[0].equals("play")))
					System.out.println("Unknown command. Use ’help’ to see the available commands\n");
			}

		} while(!game.gameEnded());
		
		in.close();
	}
	
	
	
}
