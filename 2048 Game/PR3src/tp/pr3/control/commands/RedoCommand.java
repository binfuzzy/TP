package tp.pr3.control.commands;

import tp.pr3.exceptions.ExecutionException;
import tp.pr3.logic.multigames.Game;

public class RedoCommand extends NoParamsCommands {
	
	private static String helpInfo = commandHelp();
	private static String commandInfo = "Redo";

	public RedoCommand() {
		super(commandInfo, helpInfo);
		
	}

	private static String commandHelp() {

		return "redo the last undone command\r\n";
		
	}

	@Override
	public boolean execute(Game game) throws ExecutionException {
		boolean print = false;
		/*Comprobamos si el tablero era igual antes al de ahora copiandolo como String; 
		 * Para evitar mostrarlo si no se ha hecho nada en el tablero aun usando el comando*/
		String g = game.toString();
		game.redo();
		System.out.println("Redoing one move...\n");
		
		if(!g.equals(game.toString())) {
			print = true;
		}
		return print;
	}

}
