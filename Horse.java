package chessGame;

import java.util.HashSet;

/**
 * 
 * @author Qusai Elwazir
 *
 *
 *
 */
public class Horse extends Piece{

	public Horse(int xPos, int yPos, Color color) {
		super(xPos, yPos, color);
	}

	@Override
	HashSet<Square> getPossibleMoves() {
		HashSet<Square> outSet = new HashSet<Square>();
		super.putHorseMoves(outSet);
		return outSet;
	}

	@Override
	HashSet<Square> getUncheckingSquares() {
		if(this == Board.checkingPiece()) {
			HashSet<Square> outSet = new HashSet<Square>();
			outSet.add(Board.spaces[this.row][this.col]);
			return outSet;
		} else {
			return null;
		}
	}
}
