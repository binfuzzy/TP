package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.ExecutionException;
import tp.pr3.exceptions.InvalidNumberException;
import tp.pr3.exceptions.ParsingException;
import tp.pr3.logic.multigames.Game;

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
	
	public abstract boolean execute(Game game) throws ExecutionException, InvalidNumberException;
	public abstract Command parse(String[] commandWords, Scanner in) throws ParsingException;
	public String helpText(){return "	" + commandText + ": " + helpText;}

}