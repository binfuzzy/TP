package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.ExecutionException;
import tp.pr3.exceptions.InvalidNumberException;
import tp.pr3.logic.multigames.Game;
import tp.pr3.util.GameType;

public class PlayCommand extends Command {

	protected GameType gameType;
	private static String helpInfo = commandHelp();
	private static String commandInfo = "Play <game>";
	private Scanner scan;
	
	public PlayCommand() {
		super(commandInfo, helpInfo);
	}
	
	public PlayCommand(GameType gt, Scanner in) {
		super(commandInfo, helpInfo);
		this.gameType = gt;
		this.scan = in;
		
	}

	@Override
	
	/*Pedimos los parametros para el nuevo juego, hacemos un hardReset y el juego continua con un nuevo tablero*/
	public boolean execute(Game game) throws ExecutionException, InvalidNumberException {
		boolean ex;	
		
		if(gameType == GameType.FAIL)
			throw new ExecutionException("Invalid game type\n");
		ex = game.play(gameType, this.scan);
		
		return ex;
		
	}

	@Override
	
	public Command parse(String[] commandWords, Scanner in) {
		Command c = null;
		if(commandWords[0].equalsIgnoreCase("play") && commandWords.length == 2) {
			
			if(commandWords[1].equalsIgnoreCase("original")) {
				c = new PlayCommand(GameType.ORIG, in);
				
			}
			
			else if(commandWords[1].equalsIgnoreCase("inverse")) {
				c = new PlayCommand(GameType.INV, in);
				
			}
			
			else if(commandWords[1].equalsIgnoreCase("fib")) {
				c = new PlayCommand(GameType.FIB, in);
				
			}
			
			else
				c = new PlayCommand(GameType.FAIL, in); //definido para que de fallo en execute y cazar la excepcion con el mensaje adecuado
			
		}
		
		else if (commandWords[0].equalsIgnoreCase("play") && commandWords.length == 1)
			c = new PlayCommand(null, in);
		
		else if (commandWords[0].equalsIgnoreCase("play") && commandWords.length > 2)
			c = new PlayCommand(GameType.FAIL, in);
		
		return c;
	}
	
	public static String commandHelp() {

		return "start a new game of one of the game types: original, fib or inverse\r\n";
		
	}

}
