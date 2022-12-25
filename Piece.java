package chessGame;

import java.util.HashSet;
/**
 * 
 * @author Qusai Elwazir
 *
 *
 *
 */
public abstract class Piece {
	
	/**
	 * 
	 */
	public int col, row;
	
	/**
	 * 
	 */
	public Color color;
	
	public boolean hasMoved;

	/**
	 * 
	 * @param row
	 * @param col
	 * @param color
	 */
	public Piece(int row, int col, Color color) {
		this.col = col;
		this.row = row;
		this.color = color;
		Board.spaces[row][col].piece = this;
	}
	
	/**
	 * 
	 * @param p
	 */
	public Piece(Pawn p) { // needs to update boardMoves
		this.col = p.col;
		this.row = p.row;
		this.color = p.color;
		Board.spaces[row][col].piece = this;
	}

	/**
	 * 
	 * @param row
	 * @param col
	 */
	public void setCoords(int row, int col) {
		this.col = col;
		this.row = row;
	}
	
	/**
	 * 
	 * @return
	 */
	abstract HashSet<Square> getPossibleMoves();
	
	/**
	 * 
	 * @return
	 */
	abstract HashSet<Square> getUncheckingSquares();
	
	/**
	 * 
	 * @param outSet
	 */
	protected void putStraightMoves(HashSet<Square> outSet) {
		int i = row + 1;
		while(isMoveableSpace(i, col)) { // down movement
			outSet.add(Board.spaces[i][col]);
			if(Board.spaces[i][col].hasPiece()) {
				break;
			}
			++i;
		}
		
		i = row - 1;
		while(isMoveableSpace(i, col)) { // up movement
			outSet.add(Board.spaces[i][col]);
			if(Board.spaces[i][col].hasPiece()) {
				break;
			}
			--i;
		}
		
		int j = col + 1;
		while(isMoveableSpace(row, j)) { // right movement
			outSet.add(Board.spaces[row][j]);
			if(Board.spaces[row][j].hasPiece() ) {
				break;
			}
			++j;
		}
		
		j = col - 1;
		while(isMoveableSpace(row, j)) { // left movement
			outSet.add(Board.spaces[row][j]);
			if(Board.spaces[row][j].hasPiece() ) {
				break;
			}
			--j;
		}
	}
	
	/**
	 * 
	 * @param outSet
	 */
	protected void putDiagonalMoves(HashSet<Square> outSet) {
		int i = row + 1;
		int j = col + 1;
		//TODO refactor and make clean for all moves
		while(isMoveableSpace(i, j)) { // down right diagonal movement
			outSet.add(Board.spaces[i][j]);
			if(Board.spaces[i][j].hasPiece() ) {
				break;
			}
			++i;
			++j;
		}
		
		i = row + 1;
		j = col - 1;
		while(isMoveableSpace(i, j)) { // down left diagonal movement
			outSet.add(Board.spaces[i][j]);
			if(Board.spaces[i][j].hasPiece()) {
				break;
			}
			++i;
			--j;
		}
		
		i = row - 1;
		j = col + 1;
		while(isMoveableSpace(i, j)) { // up right diagonal movement
			outSet.add(Board.spaces[i][j]);
			if(Board.spaces[i][j].hasPiece()) {
				break;
			}
			--i;
			++j;
		}
		
		i = row - 1;
		j = col - 1;
		while(isMoveableSpace(i, j)) { // up left diagonal movement
			outSet.add(Board.spaces[i][j]);
			if(Board.spaces[i][j].hasPiece()) {
				break;
			}
			--i;
			--j;
		}
	}
	
	/**
	 * 
	 * @param outSet
	 */
	protected void putHorseMoves(HashSet<Square> outSet) {
		
		if(isMoveableSpace(row + 2 , col + 1)) {
			outSet.add(Board.spaces[row + 2][col + 1]);
		}
		if(isMoveableSpace(row + 2 , col - 1)) {
			outSet.add(Board.spaces[row + 2][col - 1]);
		}
		if(isMoveableSpace(row - 2 , col + 1)) {
			outSet.add(Board.spaces[row - 2][col + 1]);
		}
		if(isMoveableSpace(row - 2 , col - 1)) {
			outSet.add(Board.spaces[row - 2][col - 1]);
		}
		if(isMoveableSpace(row + 1 , col + 2)) {
			outSet.add(Board.spaces[row + 1][col + 2]);
		}
		if(isMoveableSpace(row + 1 , col - 2)) {
			outSet.add(Board.spaces[row + 1][col - 2]);
		}
		if(isMoveableSpace(row - 1 , col + 2)) {
			outSet.add(Board.spaces[row - 1][col + 2]);
		}
		if(isMoveableSpace(row - 1 , col - 2)) {
			outSet.add(Board.spaces[row - 1][col - 2]);
		}	
		
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	protected boolean isMoveableSpace(int row, int col) { // use this for fixing checks, issue: need to stop when encountering a piece
		return row < Board.spaces.length 
				&& row >= 0 
				&& col < Board.spaces[0].length 
				&& col >= 0
				&& (!Board.spaces[row][col].hasPiece() || (Board.spaces[row][col].piece.color != this.color));
	}
	
	protected boolean isInBounds(int row, int col) {
		return row < Board.spaces.length 
				&& row >= 0 
				&& col < Board.spaces[0].length 
				&& col >= 0;
	}
	
	
	
	
}
