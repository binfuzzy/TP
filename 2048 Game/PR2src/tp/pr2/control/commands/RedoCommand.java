package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

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
	public void execute(Game game, Controller controller) {
		/*Comprobamos si el tablero era igual antes al de ahora copiandolo como String; 
		 * Para evitar mostrarlo si no se ha hecho nada en el tablero aun usando el comando*/
		String g = game.toString();
		game.redo();
		
		if(!g.equals(game.toString()))
			System.out.println(game);
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		
		if(commandWords[0].equalsIgnoreCase("redo") && commandWords.length == 1)
			c = new RedoCommand();
		
		return c;
	}

}
