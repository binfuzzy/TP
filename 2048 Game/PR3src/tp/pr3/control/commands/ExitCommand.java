package tp.pr3.control.commands;

import tp.pr3.logic.multigames.Game;

public class ExitCommand extends NoParamsCommands{

	private static String helpInfo = commandHelp();
	private static String commandInfo = "Exit";
	
	public ExitCommand() {
		super(commandInfo, helpInfo);
	}

	@Override
	//Si quiere salir del juego utilizamos directamente la salida del sistema
	public boolean execute(Game game) {
		
		System.out.println("Game Over...");
		System.exit(1);
		
		return false;
		
	}
	
	public static String commandHelp() {

		return "terminate the program\n";
		
	}

}
