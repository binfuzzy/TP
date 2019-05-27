package tp.pr3.logic.multigames;

import java.util.Random;

import tp.pr3.Board;
import tp.pr3.Cell;
import tp.pr3.Position;

public class RulesInverse implements GameRules {

	private static final int STOP_VALUE = 2;
	private static final int C_2048 = 2048;
	private static final int C_1024 = 1024;
	
	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		int value;
		value = initialCell(rand);
		board.setCell(pos, value);
	}

	@Override
	public int merge(Cell self, Cell other) {
		
		int merge = 0;
		
		if(self.getValue() == other.getValue()) {
			merge = self.getValue()/2;
			other.setValue(merge);
			self.setValue(0);
		}
		
		return merge;
	}

	@Override
	public int getWinValue(Board board) {
		return board.getMinValue();
	}

	@Override
	public boolean win(Board board) {
		return getWinValue(board) == STOP_VALUE;
	}
	
	private int initialCell(Random r) {

		int value = r.nextInt(10);
		if(value != 4)
			value = C_2048;
		else
			value = C_1024;
		
		return value;
	}
	
	public boolean checkMoves(Board board) {
		
		Position pos = null, posNeig = null, posNeig2 = null;
		
		for(int i = 0; i < board.getSize(); i++) {
			for(int j = 0; j < board.getSize(); j++) {
				pos = new Position(i, j);
				posNeig = new Position(i, j+1);
				posNeig2 = new Position(i+1, j);
				
				if((j != board.getSize() - 1) && (i != board.getSize() - 1)) {
					if(canMergeNeighbours(board.getCell(pos), board.getCell(posNeig)) ||
						(canMergeNeighbours(board.getCell(pos), board.getCell(posNeig2))))
							return true;
				}
				else if((j == board.getSize() - 1) && (i != board.getSize() - 1)) {
					if(canMergeNeighbours(board.getCell(pos), board.getCell(posNeig2)))
							return true;
				}
				else if	((j != board.getSize() - 1) && (i == board.getSize() - 1)) {
					if(canMergeNeighbours(board.getCell(pos), board.getCell(posNeig)))
							return true;
				}
			}
		}
		return false;

	}

	@Override
	public int calculatePoints(int value) {
		return C_2048/value;
	}
	
}
