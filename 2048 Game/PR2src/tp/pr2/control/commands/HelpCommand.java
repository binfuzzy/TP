package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

public class HelpCommand extends NoParamsCommands{

	private static String helpInfo = commandHelp();
	private static String commandInfo = "Help";
	
	public HelpCommand() {
		super(commandInfo, helpInfo);
	}

	@Override
	//Ejecuta el commandHelp del parser que ejecutara todos los help de los comandos
	public void execute(Game game, Controller controller) {
		String text=null;
		text = CommandParser.commandHelp();
		System.out.println(text);
		
	}

	private static String commandHelp() {

		return "print this help message\r\n";
		
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		
		if(commandWords[0].equalsIgnoreCase("help") && commandWords.length == 1)
			c = new HelpCommand();
		
		return c;
	}

}
