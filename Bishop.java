package chessGame;

import java.util.HashSet;

/**
 * 
 * @author Qusai Elwazir
 *
 *
 *
 */
public class Bishop extends Piece{

	public Bishop(int row, int col, Color color) {
		super(row, col, color);
	}
	

	@Override
	HashSet<Square> getPossibleMoves() {
		HashSet<Square> outSet = new HashSet<Square>();
		super.putDiagonalMoves(outSet);
		return outSet;
	}


	@Override
	HashSet<Square> getUncheckingSquares() {
		HashSet<Square> outSet = new HashSet<Square>();
		if(this == Board.checkingPiece()) {
			King king = Board.whiteKing;
			if(this.color == Color.WHITE) {
				king = Board.blackKing;
			}
			
			Square current = Board.spaces[this.row][this.col];
			int currentRow = this.row;
			int currentCol = this.col;
			if(king.row > this.row && king.col > this.col) { // down-right diagonal
				while(!(current.piece instanceof King)) {
					outSet.add(current);
					++currentRow;
					++currentCol;
					current = Board.spaces[currentRow][currentCol];
				}
			} else if(king.row > this.row && king.col < this.col){ // down-left diagonal
				while(!(current.piece instanceof King)) {
					outSet.add(current);
					++currentRow;
					--currentCol;
					current = Board.spaces[currentRow][currentCol];
				}	
				
			} else if(king.row < this.row && king.col > this.col){ // up-right diagonal
				while(!(current.piece instanceof King)) {
					outSet.add(current);
					--currentRow;
					++currentCol;
					current = Board.spaces[currentRow][currentCol];
				}	
			} else if(king.row < this.row && king.col < this.col){ // up-left diagonal
				while(!(current.piece instanceof King)) {
					outSet.add(current);
					--currentRow;
					--currentCol;
					current = Board.spaces[currentRow][currentCol];
				}
			}
			return outSet;
		} else {
			return null;
		}
	}
}
