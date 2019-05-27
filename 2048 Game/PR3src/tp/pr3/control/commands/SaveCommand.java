package tp.pr3.control.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import tp.pr3.exceptions.ExecutionException;
import tp.pr3.exceptions.ParsingException;
import tp.pr3.logic.multigames.Game;
import tp.pr3.util.*;

public class SaveCommand extends Command {

	private static String helpInfo = commandHelp();
	private static String commandInfo = "Save <filename>";
	private String fich;
	private final static String FirstLine = "This file stores a saved 2048 game";
	
	public SaveCommand() {
		super(commandInfo, helpInfo);
	}
	
	public SaveCommand(String fich) {
		super(commandInfo, helpInfo);
		this.fich = fich;
		
	}
	
	private static String commandHelp() {

		return "saves a game's complete state into a file\r\n";
		
	}

	@Override
	
	/*Guarda un fichero con la partida actual, con el nombre indicado; los posibles fallos se contemplan en parseo*/
	public boolean execute(Game game) throws ExecutionException{
		
		try(BufferedWriter out = new BufferedWriter(new FileWriter(fich))){
			out.write(FirstLine);
			out.newLine();
			out.newLine();
			game.store(out);
			System.out.println("Game successfully saved to file; use load command to reload it\n");
		} catch (IOException e) {
			throw new ExecutionException("Fail saving the game");
		}
		
		return false;
	}
	
	public String getLoadName(String filenameString, Scanner in) throws ParsingException {
		
		String newFilename = null;
		String fnsArray[] = null;
		String fns;
		boolean yesOrNo = false;
		
		while (!yesOrNo) {
			String filenameInUseMsg = "This file already exists; do you want to overwrite it? (Y/N)";
			System.out.print(filenameInUseMsg + ": ");
			String[] responseYorN =
					in.nextLine().toLowerCase().trim().split("\\s+");
			if (responseYorN.length == 1) {
				switch (responseYorN[0]) {
					case "y": yesOrNo = true;//sobrescribe el fich
						newFilename = filenameString;
						break;
					case "n": yesOrNo = true; //pide otro nombre
						System.out.print("Please, enter another filename: ");
						fnsArray = in.nextLine().trim().split("\\s+");
						if(fnsArray.length==1) { //pide confirmar si es valido tambien este
							fns = fnsArray[0];
							newFilename = confirmFileNameStringForWrite(fns, in);
							
						}
						else {
							throw new ParsingException("Invalid filename: the filename contains spaces");
						}
						break;
					default: //si no respondemos con y n pide que lo hagamos
						System.out.print("Please answer ’Y’ or ’N’\n");
						break;
				}
			} else {
				System.out.println("Invalid filename: the filename contains spaces");
				yesOrNo = true;
			}
		}
		
		return newFilename;
	}

	private String confirmFileNameStringForWrite(String filenameString, Scanner in) throws ParsingException {
		String loadName = filenameString;
		boolean filename_confirmed = false;
		
		while (!filename_confirmed) {
			if (MyStringUtils.validFileName(loadName)) {
				File file = new File(loadName);
				if (!file.exists())
					filename_confirmed = true;
				else {
					loadName = getLoadName(filenameString, in);
					filename_confirmed = true;
				}
			} else {
				filename_confirmed = true;
				loadName = null;
				throw new ParsingException("Invalid filename: the filename contains invalid characters");
			}
		}
		return loadName;
	}
	
	@Override
	public Command parse(String[] commandWords, Scanner in) throws ParsingException{
		Command command = null;
		
		if(commandWords[0].equalsIgnoreCase("save") && commandWords.length == 2) {
			String filenameString = commandWords[1];
			
			try {
				String filename = confirmFileNameStringForWrite(filenameString, in);
				command = new SaveCommand(filename);
				
			} catch (ParsingException e){
				System.out.println(e.getMessage());
			}	
		}
		
		else if (commandWords[0].equalsIgnoreCase("save") && commandWords.length == 1) {
			
			throw new ParsingException("Save must be followed by a filename\n");
			
		}
		
		else if (commandWords[0].equalsIgnoreCase("save") && commandWords.length > 2) {
			
			throw new ParsingException("Invalid filename: the filename contains spaces\n");
			
		}
			
		return command;
	}

}
