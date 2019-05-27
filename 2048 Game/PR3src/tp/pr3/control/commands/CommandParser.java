package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.ParsingException;


public class CommandParser{
	
	private static Command[] availableCommands = {
		 new HelpCommand(), new ResetCommand(),
		 new ExitCommand(), new MoveCommand(),
		 new RedoCommand(), new UndoCommand(),
		 new PlayCommand(), new LoadCommand(),
		 new SaveCommand()
	};
	
	private static Scanner in = new Scanner(System.in);
	
	public static String commandHelp() {
		
		String text = "Available commands are:\n ";
		for(Command com : availableCommands) 
			text += com.helpText();
		
		return text;
		
	}
	
	public static Command parseCommand(String[] commandWords) throws ParsingException {	
		
		Command c = null;
		
		/*Para recorrer todos los comandos, devolvera null si no es ninguno*/
		
		for(Command com : availableCommands) {
			if(c==null)
				c = com.parse(commandWords, in);
		}
		//Y si no es ninguno, lanzamos excepcion (parseo) de comando desconocido
		if(c == null)
			throw new ParsingException("Unknown command\n");
		

		return c;
		
		
	}

}
