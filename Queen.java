package chessGame;

import java.util.HashSet;

/**
 * 
 * @author Qusai Elwazir
 *
 *
 *
 */
public class Queen extends Piece {

	public Queen(int xPos, int yPos, Color color) {
		super(xPos, yPos, color);
	}	

	@Override
	public HashSet<Square> getPossibleMoves() {
		HashSet<Square> outSet = new HashSet<Square>();
		super.putDiagonalMoves(outSet);
		super.putStraightMoves(outSet);
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
			} else if(king.row > this.row) { // down straight
				while(!(current.piece instanceof King)) {
					outSet.add(current);
					++currentRow;
					current = Board.spaces[currentRow][currentCol];
				}
			} else if(king.row < this.row){ // up straight
				while(!(current.piece instanceof King)) {
					outSet.add(current);
					--currentRow;
					current = Board.spaces[currentRow][currentCol];
				}	
				
			} else if(king.col > this.col){ // right straight
				while(!(current.piece instanceof King)) {
					outSet.add(current);
					++currentCol;
					current = Board.spaces[currentRow][currentCol];
				}	
			} else if(king.row < this.row && king.col < this.col){ // left straight
				while(!(current.piece instanceof King)) {
					outSet.add(current);
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
