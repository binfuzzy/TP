package tp.pr3.control.commands;

import tp.pr3.logic.multigames.Game;

public class ResetCommand extends NoParamsCommands{

	private static String helpInfo = commandHelp();
	private static String commandInfo = "Reset";
	
	public ResetCommand() {
		super(commandInfo, helpInfo);
	}

	@Override
	public boolean execute(Game game) {
		game.reset();
		return true;
	}
	
	public static String commandHelp() {

		return "start a new game\r\n";

	}

}
