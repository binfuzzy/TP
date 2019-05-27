package tp.pr3.control.commands;

import java.util.Scanner;

public abstract class NoParamsCommands extends Command{

	public NoParamsCommands(String commandInfo, String helpInfo) {
		super(commandInfo, helpInfo);
	}
	
	public Command parse(String[] commandWords, Scanner in) {
		Command c = null;
		
		if(commandWords[0].equalsIgnoreCase(commandName) && commandWords.length == 1)
			c = this;
		
		return c;
	}

}
