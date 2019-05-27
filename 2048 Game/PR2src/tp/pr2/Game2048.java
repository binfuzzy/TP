package tp.pr2;

import java.util.Random;
import java.util.Scanner;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.*;

public class Game2048 {

	public static void main(String[] args) {
		
		long seed = 0;
		
		if(args.length == 2) {
			seed = new Random().nextInt(1000); // Si solo hay 2 argumentos
			 // Para crear la misma secuencia de numeros aleatorios
		}
		
		else if(args.length == 3)
			seed = Long.parseLong(args[2]); // Si hay un tercer argumento
		
		Random rand = new Random(seed);
		//Por defecto el juego es el 2048 original
		GameRules currentRules = new Rules2048();
		Game g = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]), rand, currentRules);
		Scanner s = new Scanner(System.in);
		Controller controller = new Controller(g, s);
		controller.run();
		//inicializa la partida
		
	}

}
