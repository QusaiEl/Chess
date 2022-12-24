package chessGame;

import java.util.HashSet;

/**
 * 
 * @author Qusai Elwazir
 *
 *
 *
 */
public class Pawn extends Piece {
	
	public boolean hasMoved;
	
	public Pawn(int Row, int  col, Color color) {
		super(Row, col, color);
		hasMoved = false;
	}

	@Override
	public HashSet<Square> getPossibleMoves() {
		if(this.color == Color.BLACK) {
			return blackMoves();
		} else {
			return whiteMoves();
		}
	}
	
	private HashSet<Square> blackMoves() { //TODO clean up
		HashSet<Square> outSet = new HashSet<Square>();
		if(row + 1 < Board.spaces.length) {
			if(!Board.spaces[this.row + 1][this.col].hasPiece()) {
				outSet.add(Board.spaces[this.row + 1][this.col]);
				if(row + 2 < Board.spaces.length && hasMoved == false && !Board.spaces[this.row + 2][this.col].hasPiece() 
						&& hasMoved == false) {
					outSet.add(Board.spaces[this.row + 2][this.col]);
				}
			}
			if(this.col + 1 < Board.spaces[0].length && Board.spaces[this.row + 1][this.col + 1].hasPiece() 
					&& Board.spaces[this.row + 1][this.col + 1].piece.color != this.color) {
				outSet.add(Board.spaces[this.row + 1][this.col + 1]);
			}
			if(this.col - 1 >= 0 && Board.spaces[this.row + 1][this.col - 1].hasPiece()
					&& Board.spaces[this.row + 1][this.col - 1].piece.color != this.color) { 
				outSet.add(Board.spaces[this.row + 1][this.col - 1]);
			}
		}
		return outSet;
	}
	
	private HashSet<Square> whiteMoves() { // TODO fix indexing 
		HashSet<Square> outSet = new HashSet<Square>();
		if(row - 1 >= 0) {
			if(!Board.spaces[this.row - 1][this.col].hasPiece()) { // one space forward move 
				outSet.add(Board.spaces[this.row - 1][this.col]);
				if(row - 2 >= 0 && !Board.spaces[this.row - 2][this.col].hasPiece() 
						 && hasMoved == false) { // two spaces forward move
					outSet.add(Board.spaces[this.row - 2][this.col]);
				}
			}

			if(this.col + 1 < Board.spaces[0].length && Board.spaces[this.row - 1][this.col + 1].hasPiece() // diagonal right move  (take)
					&& Board.spaces[this.row - 1][this.col + 1].piece.color != this.color) { 
				outSet.add(Board.spaces[this.row - 1][this.col + 1]);
				
			}
			if(this.col - 1 >= 0 && Board.spaces[this.row - 1][this.col - 1].hasPiece() // diagonal left move (take)
					&& Board.spaces[this.row - 1][this.col - 1].piece.color != this.color) { 
				outSet.add(Board.spaces[this.row - 1][this.col - 1]);
			}
		}
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
