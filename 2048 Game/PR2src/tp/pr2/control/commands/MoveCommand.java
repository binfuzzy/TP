package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.Direction;
import tp.pr2.logic.multigames.Game;

public class MoveCommand extends Command{
	
	private Direction dir;
	private static String helpInfo = commandHelp();
	private static String commandInfo = "Move <direction>";

	public MoveCommand() {
		super(commandInfo, helpInfo);
	}

	public MoveCommand(Direction dir2) {
		super(commandInfo, helpInfo);
		this.dir = dir2;
		
	}

	@Override
	/*Ejecuta el move de game en una dir parseada*/
	public void execute(Game game, Controller controller) {
		
		game.move(dir);
		System.out.println(game);
	}
	
	public static String commandHelp() {

		return "execute a move in one of the four directions, up, down, left, right\r\n";
		
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		
		if(commandWords[0].equalsIgnoreCase("move") && commandWords.length == 2) {
				
				if(commandWords[1].equalsIgnoreCase("right")) {
					c = new MoveCommand(Direction.RIGHT);
					
				}
				
				else if(commandWords[1].equalsIgnoreCase("left")) {
					
					c = new MoveCommand(Direction.LEFT);
					
				}
				
				else if(commandWords[1].equalsIgnoreCase("up")) {
					
					c = new MoveCommand(Direction.UP);
					
				}
				
				else if(commandWords[1].equalsIgnoreCase("down")) {
					
					c = new MoveCommand(Direction.DOWN);
				}
				
				else
					System.out.println("Unknown direction for move command\n");
				
		}
		
		else if (commandWords[0].equalsIgnoreCase("move") && commandWords.length > 2){
			
			System.out.println("Unknown direction for move command\n");
			
		}
		
		else if (commandWords[0].equalsIgnoreCase("move") && commandWords.length == 1) {
			
			System.out.println("Move must be followed by a direction: up, down, left, right\n");
			
		}
		
		return c;
	}

}
