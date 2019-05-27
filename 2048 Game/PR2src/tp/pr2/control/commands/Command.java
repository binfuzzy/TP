package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

public abstract class Command {
	
	private String helpText;
	private String commandText;
	protected final String commandName;
		
	public Command(String commandInfo, String helpInfo){
		
		commandText = commandInfo;
		helpText = helpInfo;
		String[] commandInfoWords = commandText.split("\\s+");
		commandName = commandInfoWords[0];
		
	}
	
	public abstract void execute(Game game, Controller controller);
	public abstract Command parse(String[] commandWords, Controller controller);
	public String helpText(){return "	" + commandText + ": " + helpText;}

}