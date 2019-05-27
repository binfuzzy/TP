package tp.pr1;

import java.util.Random;
import java.util.Scanner;

public class Game2048 {

	public static void main(String[] args) {
		
		long seed = 0;
		
		if(args.length == 2) {
			seed = new Random().nextInt(1000); // Si solo hay 2 argumentos
			 // Para crear la misma secuencia de numeros aleatorios
		}
		
		else if(args.length == 3) {
			seed = Long.parseLong(args[2]); // Si hay un tercer argumento
		}
		
		Random rand = new Random(seed);

		Game g = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]), rand);
		Scanner s = new Scanner(System.in);
		Controller controller = new Controller(g, s);
		controller.run();//inicializa la partida
		
	}

}
