package tp.pr2.control.commands;

import tp.pr2.control.Controller;

public class CommandParser{
	
	private static Command[] availableCommands = 
		{new HelpCommand(), new ResetCommand(),
		 new ExitCommand(), new MoveCommand(),
		 new RedoCommand(), new UndoCommand(),
		 new PlayCommand()};
	
	
	public static String commandHelp() {
		
		String text = "Available commands are:\n ";
		for(Command com : availableCommands) 
			text += com.helpText();
		
		return text;
		
	}
	
	public static Command parseCommand(String[] commandWords, Controller controller) {	
		
		Command c = null;
		
		/*Para recorrer todos los comandos, devolvera null si no es ninguno*/
		
		for(Command com : availableCommands) {
			if(c==null)
				c = com.parse(commandWords, controller);
		}

		return c;
		
		
	}

}
