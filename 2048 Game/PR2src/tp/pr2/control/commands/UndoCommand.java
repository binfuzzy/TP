package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;


public class UndoCommand extends NoParamsCommands {

	private static String helpInfo = commandHelp();
	private static String commandInfo = "Undo";
	
	public UndoCommand() {
		super(commandInfo, helpInfo);
	}

	private static String commandHelp() {
		
		return "undo the last command\r\n";
	}

	@Override
	public void execute(Game game, Controller controller) {
		/*Comprobamos si el tablero era igual antes al de ahora copiandolo como String; 
		 * Para evitar mostrarlo si no se ha hecho nada en el tablero aun usando el comando*/
		String g = game.toString();
		game.undo();
		
		if(!g.equals(game.toString()))
			System.out.println(game);
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		
		if(commandWords[0].equalsIgnoreCase("undo") && commandWords.length == 1)
			c = new UndoCommand();
		
		return c;
	}

}
