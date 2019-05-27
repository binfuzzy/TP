package tp.pr3.control.commands;

import tp.pr3.logic.multigames.Game;

public class HelpCommand extends NoParamsCommands{

	private static String helpInfo = commandHelp();
	private static String commandInfo = "Help";
	
	public HelpCommand() {
		super(commandInfo, helpInfo);
	}

	@Override
	//Ejecuta el commandHelp del parser que ejecutara todos los help de los comandos
	public boolean execute(Game game) {
		String text=null;
		text = CommandParser.commandHelp();
		System.out.println(text);
		
		return false;
		
	}

	private static String commandHelp() {

		return "print this help message\r\n";
		
	}

}
