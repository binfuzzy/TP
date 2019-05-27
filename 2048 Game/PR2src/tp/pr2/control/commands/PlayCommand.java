package tp.pr2.control.commands;

import java.util.Random;
import java.util.Scanner;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;
import tp.pr2.util.GameType;

public class PlayCommand extends Command {

	protected int randomSeed, boardSize, initialCells;
	protected GameType gameType;
	private static String helpInfo = commandHelp();
	private static String commandInfo = "Play <game>";
	private final static int DEFAULT_SIZE = 4;
	private final static int DEFAULT_CELLS = 2;
	
	public PlayCommand() {
		super(commandInfo, helpInfo);
	}
	
	public PlayCommand(GameType gt) {
		super(commandInfo, helpInfo);
		this.gameType = gt;
		
	}

	@Override
	/*Pedimos los parametros para el nuevo juego, hacemos un hardReset y el juego continua con un nuevo tablero*/
	public void execute(Game game, Controller controller) {
		
		int size = -1,init = -1; 
		long seed = -1;
		String[] words;
		String line;
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		
		//SIZE: mientras no metamos un size valido, sigue pidiendolo; si damos enter, usa el valor por defecto
		
		do {
			System.out.print("Please enter the size of the board: ");
			line = s.nextLine().trim().replaceAll("\\s{2,}"," ");
			words = line.split(" ");
			
			if(words.length == 1) {
			
				
				if(words[0].equals("")) {
					System.out.println("  Using default size of the board: " + DEFAULT_SIZE);
					size = DEFAULT_SIZE;
				}
				
				else if(2 <= Integer.parseInt(words[0].replaceAll("[\\D]", "")))
					
						size = Integer.parseInt(words[0].replaceAll("[\\D]", ""));
				
				else{
					size = Integer.parseInt(words[0].replaceAll("[\\D]", ""));
					if(size < 2) {
						System.out.println("  The size of the board must be positive...");
						size = -1;
					}
				}
				
			}	
			
			else
				System.out.println("  Please, provide a single positive integer or press return.");
			
		}while(size == -1);
			
		// END SIZE
		
		//INIT CELLS: pedimos un valor de init cells hasta que se de uno valido o se de enter, en cuyo caso usara el valor por defecto
		
		do {
			System.out.print("Please enter the number of initial cells: ");
			line = s.nextLine().trim().replaceAll("\\s{2,}"," ");
			words = line.split(" ");
			
			if(words.length == 1) {
			
				if(words[0].equals("")) {
					System.out.println("  Using default number of initial cells: " + DEFAULT_CELLS);
					init = DEFAULT_CELLS;
				}
				
				else if (1 <= Integer.parseInt(words[0].replaceAll("[\\D]", ""))){
					
					init = Integer.parseInt(words[0]);
					
				}
				
				else{
					init = Integer.parseInt(words[0].replaceAll("[\\D]", ""));
					if(init < 1) {
						System.out.println("  The number of initial cells must be greater than zero");
						init = -1;
					}
				}
			}	
			
		}while(init == -1);
		
		//END INIT CELLS
		
		//PSEUDO-RANDOM NUMBER: pedimos un valor para la semilla; en caso de no proporcionarlo, usa una aleatoria de [0, 1000)
		
		do {
			System.out.print("Please enter the seed for the pseudo-random number generator: ");
			line = s.nextLine().trim().replaceAll("\\s{2,}"," ");
			words = line.split(" ");
			
			if(words.length == 1) {
			
				if(words[0].equals("")) {
					seed = new Random().nextInt(1000);
					System.out.println("  Using default size for the pseudo-random number generator: " + seed);
					
				}
				
				else if ((0 <= Integer.parseInt(words[0].replaceAll("[\\D]", ""))) && (1000 > Integer.parseInt(words[0].replaceAll("[\\D]", "")))){
					
					seed = (Integer.parseInt(words[0]));
					
				}
				
				else{
					seed = Integer.parseInt(words[0].replaceAll("[\\D]", ""));
					if(seed < 0) {
						System.out.println("  The size of the board must be positive...");
						seed = -1;
					}
				}
			}	
			
		}while(seed == -1);
		
		if(init > size*size)
			System.out.println("The number of initial cells must be less than the number of cells on the board.\n");
		else {
			game.hardReset(this.gameType, size, init, seed);
			System.out.println(game);
		}
		
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		if(commandWords[0].equalsIgnoreCase("play") && commandWords.length == 2) {
			
			if(commandWords[1].equalsIgnoreCase("original")) {
				c = new PlayCommand(GameType.ORIG);
				
			}
			
			else if(commandWords[1].equalsIgnoreCase("inverse")) {
				c = new PlayCommand(GameType.INV);
				
			}
			
			else if(commandWords[1].equalsIgnoreCase("fib")) {
				c = new PlayCommand(GameType.FIB);
				
			}
			
			else
				System.out.println("Unknown game type for play command\n");
		}
		
		else if (commandWords[0].equalsIgnoreCase("play") && commandWords.length > 2){
			
			System.out.println("Unknown game type for play command\n");
			
		}
		
		else if (commandWords[0].equalsIgnoreCase("play") && commandWords.length == 1) {
			
			System.out.println("Play must be followed by a game type: original, fib, inverse\n");
			
		}
		
		return c;
	}
	
	public static String commandHelp() {

		return "start a new game of one of the game types: original, fib or inverse\r\n";
		
	}

}
