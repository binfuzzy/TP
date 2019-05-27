package tp.pr3.logic.multigames;

import java.util.Random;

import tp.pr3.Board;
import tp.pr3.Cell;
import tp.pr3.Position;
import tp.pr3.util.MyMathsUtil;

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
	
	public boolean canMergeNeighbours(Cell cell1, Cell cell2){
		
		return false;
		
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
		
		//********************************************//
		Cell fib = null, fibN = null, fibN2 = null;
		
		for(int i = 0; i < board.getSize(); i++) {
			for(int j = 0; j < board.getSize(); j++) {
				pos = new Position(i, j);
				posNeig = new Position(i, j+1);
				posNeig2 = new Position(i+1, j);
				
				fib = new Cell(MyMathsUtil.nextFib(board.getCellValue(pos)));
				
				if((j != board.getSize() - 1) && (i != board.getSize() - 1)) {
					fibN = new Cell(MyMathsUtil.nextFib(board.getCellValue(posNeig)));
					fibN2 = new Cell(MyMathsUtil.nextFib(board.getCellValue(posNeig2)));
					if((canMergeNeighbours(fib, board.getCell(posNeig))) || (canMergeNeighbours(fib, board.getCell(posNeig2)))
						|| (canMergeNeighbours(fibN, board.getCell(pos))) || (canMergeNeighbours(fibN2, board.getCell(pos)))||
						(board.getCellValue(pos) == 1 && board.getCellValue(posNeig) == 1)||
						(board.getCellValue(pos) == 1 && board.getCellValue(posNeig2) == 1))
							return true;
				}
				else if((j == board.getSize() - 1) && (i != board.getSize() - 1)) {
					fibN2 = new Cell(MyMathsUtil.nextFib(board.getCellValue(posNeig2)));
					if((canMergeNeighbours(fib, board.getCell(posNeig2))) 
					|| (canMergeNeighbours(fibN2, board.getCell(pos)))
					||(board.getCellValue(pos) == 1 && board.getCellValue(posNeig2) == 1))
						return true;
				}
				else if	((j != board.getSize() - 1) && (i == board.getSize() - 1)) {
					fibN = new Cell(MyMathsUtil.nextFib(board.getCellValue(posNeig)));
					if((canMergeNeighbours(fib, board.getCell(posNeig))) 
					|| (canMergeNeighbours(fibN, board.getCell(pos)))||
					(board.getCellValue(pos) == 1 && board.getCellValue(posNeig) == 1))
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
