package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.Direction;
import tp.pr3.exceptions.ExecutionException;
import tp.pr3.logic.multigames.Game;

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
	public boolean execute(Game game) throws ExecutionException{
		
		if(dir == Direction.INVALID)
			throw new ExecutionException("Unknown direction for move command\n");
		
		if(dir == Direction.ZERO)
			throw new ExecutionException("Move must be followed by a direction: up, down, left, right\n");
		
		game.move(dir);
		
		return true;
	}
	
	public static String commandHelp() {

		return "execute a move in one of the four directions, up, down, left, right\r\n";
		
	}

	@Override
	public Command parse(String[] commandWords, Scanner in) {
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
					c = new MoveCommand(Direction.INVALID);
				
		}
		
		else if (commandWords[0].equalsIgnoreCase("move") && commandWords.length > 2){
			
			c = new MoveCommand(Direction.INVALID); //tipo invalid definido para lanzar la excepcion adecuada
			
		}
		
		else if (commandWords[0].equalsIgnoreCase("move") && commandWords.length == 1) {
			c = new MoveCommand(Direction.ZERO);
			
		}
		
		return c;
	}

}
