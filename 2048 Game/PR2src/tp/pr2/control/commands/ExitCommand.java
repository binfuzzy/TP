package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

public class ExitCommand extends NoParamsCommands{

	private static String helpInfo = commandHelp();
	private static String commandInfo = "Exit";
	
	public ExitCommand() {
		super(commandInfo, helpInfo);
	}

	@Override
	//Si quiere salir del juego utilizamos directamente la salida del sistema
	public void execute(Game game, Controller controller) {
		
		System.out.println("Game Over...");
		System.exit(1);
		
	}
	
	public static String commandHelp() {

		return "terminate the program\n";
		
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		
		if(commandWords[0].equalsIgnoreCase("exit") && commandWords.length == 1)
			c = new ExitCommand();
		
		return c;
	}

}
