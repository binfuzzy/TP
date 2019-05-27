package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

public class ResetCommand extends NoParamsCommands{

	private static String helpInfo = commandHelp();
	private static String commandInfo = "Reset";
	
	public ResetCommand() {
		super(commandInfo, helpInfo);
	}

	@Override
	public void execute(Game game, Controller controller) {
		game.reset();
		System.out.println(game);	
	}
	
	public static String commandHelp() {

		return "start a new game\r\n";

	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		
		Command c = null;
		
		if(commandWords[0].equalsIgnoreCase("reset") && commandWords.length == 1)
			c = new ResetCommand();
		
		return c;
	}

}
