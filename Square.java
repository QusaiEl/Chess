package chessGame;

/**
 * 
 * @author Qusai Elwazir
 *
 *
 *
 */
public class Square {
	
	/**  
	 * the piece currently occupying this Square
	 */
	public Piece piece; 
	
	/**
	 * Creates a Square with a piece occupying it
	 * @param piece
	 */
	public Square(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * creates an empty Square
	 */
	public Square() {
		this.piece = null;
	}
	
	/**  
	 * 
	 * @return true if this Square is occupied; false otherwise
	 */
	public boolean hasPiece() {
		return piece != null;
	}
	
	/**
	 * removes the piece currently occupying this piece
	 */
	public void removePiece() {
		piece = null;
	}
	
}
