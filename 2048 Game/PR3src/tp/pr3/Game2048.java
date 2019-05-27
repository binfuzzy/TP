package tp.pr3;

import java.io.EOFException;
import java.util.Random;
import java.util.Scanner;

import tp.pr3.control.Controller;
import tp.pr3.exceptions.InvalidNumberException;
import tp.pr3.logic.multigames.*;
import tp.pr3.util.GameType;

public class Game2048 {

	public static void main(String[] args) throws EOFException, InvalidNumberException {
		
		long seed = 0;
		Scanner s = new Scanner(System.in);
		if(args.length == 2) {
			seed = new Random().nextInt(1000); // Si solo hay 2 argumentos
			 // Para crear la misma secuencia de numeros aleatorios
		}
		
		else if(args.length == 3)
			seed = Long.parseLong(args[2]); // Si hay un tercer argumento
		
		Random rand = new Random(seed);
		GameType gt = GameType.ORIG;
		Game g = null;
		
		try {
			
			g = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]), rand, gt);
		
		}
		
		catch (NumberFormatException e ) {
			
			System.out.println("The command-line arguments must be numbers");
			System.exit(1);
		}
		
		Controller controller = new Controller(g, s);
		controller.run();
		//inicializa la partida
		
	}

}
