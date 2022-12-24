package chessGame;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 * @author Qusai Elwazir
 *
 *
 *
 */
public class King extends Piece{ // issue with white king taking on the its first move
	
	public boolean hasMoved = false;
	
	public King(int xPos, int yPos, Color color) {
		super(xPos, yPos, color);
	}

	@Override
	HashSet<Square> getPossibleMoves() {
		HashSet<Square> outSet = new HashSet<Square>();
		putKingMoves(outSet);
		return outSet;
	}
	
	private void putKingMoves(HashSet<Square> outSet) { // can be done with a loop excluding 0 from -1 to 1
		
		if(isMoveableSpace(row + 1, col) && isSafeMove(row + 1, col)) {
			outSet.add(Board.spaces[row + 1][col]);	
		}
		if(isMoveableSpace(row - 1, col) && isSafeMove(row - 1, col)) {
			outSet.add(Board.spaces[row - 1][col]);	
		}
		if(isMoveableSpace(row, col + 1) && isSafeMove(row, col + 1)) {
			outSet.add(Board.spaces[row][col + 1]);	
		}
		if(isMoveableSpace(row, col - 1) && isSafeMove(row, col - 1)) {
			outSet.add(Board.spaces[row][col - 1]);	
		}
		if(isMoveableSpace(row + 1, col + 1) && isSafeMove(row + 1, col + 1)) {
			outSet.add(Board.spaces[row + 1][col + 1]);
		}
		if(isMoveableSpace(row + 1, col - 1) && isSafeMove(row + 1, col - 1)) {
			outSet.add(Board.spaces[row + 1][col - 1]);
		}
		if(isMoveableSpace(row - 1, col + 1) && isSafeMove(row - 1, col + 1)) {
			outSet.add(Board.spaces[row - 1][col + 1]);
		}
		if(isMoveableSpace(row - 1, col - 1) && isSafeMove(row - 1, col - 1)) {
			outSet.add(Board.spaces[row - 1][col - 1]);
		}
			
	}
	
	private boolean canCastleKing(int row, int col) { // need to check spaces between
		Square castleSquare = Board.spaces[row][col];
		
		if(!(castleSquare.piece instanceof Rook)) { // base case for if chosen position does not contain a rook
			return false;
		}
		Rook rook = (Rook) Board.spaces[row][col].piece;
		if(!this.hasMoved && castleSquare.hasPiece() 
				&& !rook.hasMoved  
				&& castleSquare.piece.color == this.color){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isSafeMove(int row, int col) { // edge problem here
		for(Piece piece : Board.pieceMoves.keySet()) {
			if(piece.color != this.color) {
				
				if(!(piece instanceof Pawn) && Board.pieceMoves.get(piece).contains(Board.spaces[row][col])) { // pawns are the only piece that can move somewhere they cannot take
					return false;
				} else if(this.color == Color.WHITE){
					if((isInBounds(row - 1, col + 1) && Board.spaces[row - 1][col + 1].piece instanceof Pawn 
								&& Board.spaces[row - 1][col + 1].piece.color != this.color)
							|| (isInBounds(row - 1, col - 1) && Board.spaces[row - 1][col - 1].piece instanceof Pawn 
								&& Board.spaces[row - 1][col - 1].piece.color != this.color)) {
						return false;
					}
				} else if(this.color == Color.BLACK) {
					if((isInBounds(row + 1, col + 1) && Board.spaces[row + 1][col + 1].piece instanceof Pawn 
								&& Board.spaces[row + 1][col + 1].piece.color != this.color) 
							|| (isInBounds(row + 1, col - 1) && Board.spaces[row + 1][col - 1].piece instanceof Pawn 
								&& Board.spaces[row + 1][col - 1].piece.color != this.color)) {
						return false;
					}
				}
				
			}
		}
		return true;
	}

	@Override
	HashSet<Square> getUncheckingSquares() {
		return null;
	}

}
