package tp.pr3.control.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;

import tp.pr3.exceptions.ExecutionException;
import tp.pr3.exceptions.ParsingException;
import tp.pr3.logic.multigames.Game;
import tp.pr3.util.GameType;
import tp.pr3.util.MyStringUtils;

public class LoadCommand extends Command {

	private static String helpInfo = commandHelp();
	private static String commandInfo = "Load <filename>";
	private String fich; //nombre del fichero para la constructora
	private final static String FIRSTLINE = "This file stores a saved 2048 game"; //linea de comienzo de fich
	
	public LoadCommand() {
		super(commandInfo, helpInfo);
		// TODO Auto-generated constructor stub
	}
	
	public LoadCommand(String fich) {
		super(commandInfo, helpInfo);
		this.fich = fich;
		
	}
	
	private static String commandHelp() {

		return "load from a file the complete state of a previous saved game\r\n";
		
	}

	@Override
	//Carga una partida del fichero indicado llamando al metodo de game para ello con un buffer que corresponde a la lectura del fich
	
	public boolean execute(Game game) throws ExecutionException {
		
		boolean show = true;
		GameType gt = null;
		
		try (BufferedReader buffer = new BufferedReader(new FileReader(fich))) {
			
			if (!buffer.readLine().equals(FIRSTLINE))
				throw new ExecutionException("This file is not valid, it doesn't contain the first line\n");
			
			buffer.readLine();
			gt = game.load(buffer);
			System.out.println("Game successfully loaded from file: " + gt.toString() + "\n");

		} catch (IOException | IndexOutOfBoundsException e) {
			throw new ExecutionException("Load failed: invalid file format or not existing\n"); // el fichero no existia
		}catch (NumberFormatException | ExecutionException e) {
			System.out.println(e.getMessage()); //si era invalido alguna parte del fichero, no lo carga e imprime el mensaje adecuado
			show = false; //si han saltado excepciones no tiene que imprimir el tablero
		}
		
		return show;
	}

	@Override
	public Command parse(String[] commandWords, Scanner in) throws ParsingException {
		Command command = null;
		
		if(commandWords[0].equalsIgnoreCase("load") && commandWords.length == 2) {
			String filenameString = commandWords[1];
			
			try {
				if(MyStringUtils.validFileName(filenameString))
				command = new LoadCommand(filenameString);
				
			} catch (Exception e){
				System.out.println(e.getMessage());
			}	
		}
		
		else if (commandWords[0].equalsIgnoreCase("load") && commandWords.length == 1) {
			
			throw new ParsingException("Load must be followed by a filename\n");
			
		}
		
		else if (commandWords[0].equalsIgnoreCase("load") && commandWords.length > 2) {
			
			throw new ParsingException("Invalid filename: the filename contains spaces\n");
			
		}
			
		return command;
	}

}
