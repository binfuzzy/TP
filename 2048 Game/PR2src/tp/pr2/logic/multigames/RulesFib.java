package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.Board;
import tp.pr2.Cell;
import tp.pr2.Position;
import tp.pr2.util.MyMathsUtil;

public class RulesFib implements GameRules {

	private static final int STOP_VALUE = 144;
	
	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		int value;
		value = initialCell(rand);
		board.setCell(pos, value);
	}

	@Override
	public int merge(Cell self, Cell other) {
		int merge = 0;
		
		if((self.getValue() == MyMathsUtil.nextFib(other.getValue()) )|| 
			(other.getValue() == 1 && 1 == self.getValue()) || //caso especial casillas 1
			( other.getValue() == MyMathsUtil.nextFib(self.getValue()) )) {
			merge = self.getValue() + other.getValue();
			other.setValue(merge);
			self.setValue(0);
		}
		
		return merge;
	}

	@Override
	public int getWinValue(Board board) {
		return board.getMaxValue();
	}

	@Override
	public boolean win(Board board) {
		return getWinValue(board) == STOP_VALUE;
	}
	
	private int initialCell(Random r) {

		int value = r.nextInt(10);
		if(value != 2)
			value = 1;
		
		return value;
	}
	
	/*Chequear los movimientos es distinto en un juego de fib; comprueba a los lados si hay su fib y el caso especial de los 1s*/
	
	public boolean checkMoves(Board board) {
		
		Position pos = null, posNeig = null, posNeig2 = null;
		int fib = 0, fibNeig = 0, fibNeig2 = 0;
		
		for(int i = 0; i < board.getSize(); i++) {
			for(int j = 0; j < board.getSize(); j++) {
				pos = new Position(i, j);
				posNeig = new Position(i, j+1);
				posNeig2 = new Position(i+1, j);
				fib = MyMathsUtil.nextFib(board.getCell(pos));
				fibNeig = MyMathsUtil.nextFib(board.getCell(posNeig));
				fibNeig2 = MyMathsUtil.nextFib(board.getCell(posNeig2));
				
				if((j != board.getSize() - 1) && (i != board.getSize() - 1)) {
					if((fib == board.getCell(posNeig)) || (fib == board.getCell(posNeig2))
						|| (fibNeig == board.getCell(pos)) || (fibNeig2 == board.getCell(pos))||
						(board.getCell(pos) == 1 && board.getCell(posNeig) == 1)||
						(board.getCell(pos) == 1 && board.getCell(posNeig2) == 1))
							return true;
				}
				else if((j == board.getSize() - 1) && (i != board.getSize() - 1)) {
					if((fib == board.getCell(posNeig2)) 
					|| (fibNeig2 == board.getCell(pos))
					||(board.getCell(pos) == 1 && board.getCell(posNeig2) == 1))
						return true;
				}
				else if	((j != board.getSize() - 1) && (i == board.getSize() - 1)) {
					if((fib == board.getCell(posNeig)) 
					|| (fibNeig == board.getCell(pos))||
					(board.getCell(pos) == 1 && board.getCell(posNeig) == 1))
						return true;
				}
			}
		}
		return false;
		
		
		
	}

	@Override
	public int calculatePoints(int value) {
		return value;
	}

}
