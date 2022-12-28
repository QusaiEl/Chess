package chessGame;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * @author Qusai Elwazir
 *
 *
 *
 */
public class Board {
	
	/**
	 * 
	 */
	public static Square[][] spaces = new Square[8][8];	
	
	/**
	 * 
	 */
	public static HashMap<Piece, HashSet<Square>> pieceMoves;
	
	/**
	 * 
	 */
	public static King whiteKing;
	
	/**
	 * 
	 */
	public static King blackKing;

	/**
	 * 
	 * @return
	 */
	public static Piece checkingPiece() {
		
		Square whiteKingSquare = spaces[whiteKing.row][whiteKing.col];
		Square blackKingSquare = spaces[blackKing.row][blackKing.col];
		
		for(Piece piece : pieceMoves.keySet()) {
			if((piece.color != Color.BLACK && pieceMoves.get(piece).contains(blackKingSquare))
					|| (piece.color != Color.WHITE && pieceMoves.get(piece).contains(whiteKingSquare))) {
				return piece;
			}
		}
		
		return null;		
	}
	
	public static Piece additionalCheckingPiece() {
		
		Piece checkingPiece = checkingPiece();
		
		if(checkingPiece == null) {
			return null;
		}
		
		Square whiteKingSquare = spaces[whiteKing.row][whiteKing.col];
		Square blackKingSquare = spaces[blackKing.row][blackKing.col];
		
		for(Piece piece : pieceMoves.keySet()) {
			if(piece != checkingPiece && ((piece.color != Color.BLACK && pieceMoves.get(piece).contains(blackKingSquare))
					|| (piece.color != Color.WHITE && pieceMoves.get(piece).contains(whiteKingSquare)))) {
				return piece;
			}
		}
		
		return null;
	}
	
	/** doesnt work yet
	 * 
	 * @return
	 */
	public static boolean isMate() {
		
		if(validCheckMoves() == null){
			return false;
		} else if(validCheckMoves().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 * @return
	 */
	public static boolean move(int fromRow, int fromCol, int toRow, int toCol) { // dont let check happen on self: if becomes checkon my own, move back!
		Square from = spaces[fromRow][fromCol];
		Square destination = spaces[toRow][toCol]; 
		Piece destinationPiece = destination.piece; // this can be set to null
		Piece fromPiece = from.piece;
		
		boolean hasMovedPrevious = false;
		
		//invalid move cases
		if(!from.hasPiece()) { // case for no piece at desired square
			return false; 
		}
		// basic valid move Case
		if(pieceMoves.get(from.piece).contains(destination)) {
			
			if(destination.hasPiece()) { // removes taken pieces
				pieceMoves.remove(destination.piece);
			}
			
			hasMovedPrevious = fromPiece.hasMoved;
			fromPiece.hasMoved = true;
			
			destination.piece = from.piece;
			destination.piece.setCoords(toRow, toCol);  
			from.removePiece();
			
			updateBoard(fromPiece);
			
			Piece checkingPiece = checkingPiece(); 
			if(checkingPiece != null && checkingPiece.color != destination.piece.color) { // checks if own move put self in check

				fromPiece.hasMoved = hasMovedPrevious;
				
				destination.piece = destinationPiece;
				if(destination.piece != null ) {
					destination.piece.setCoords(toRow, toCol); 
					pieceMoves.put(destination.piece, null);
				}
				
				from.piece = fromPiece;
				from.piece.setCoords(fromRow, fromCol);
				
				updateBoard(fromPiece);
				
				return false;
			}
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * 
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 * @return
	 */
	public static boolean checkMove(int fromRow, int fromCol, int toRow, int toCol) { // TODO new case, piece move that is not king creates check
		Square from = spaces[fromRow][fromCol];
		Square destination = spaces[toRow][toCol];
		Piece destinationPiece = destination.piece; // this can be set to null
		Piece fromPiece = from.piece;
		
		boolean hasMovedPrevious = false;
		
		HashMap<Piece, HashSet<Square>> validCheckMoves = validCheckMoves();
		
		if(!from.hasPiece()) { // case for no piece at desired square
			return false; 
		}

		// valid move Cases
		if(validCheckMoves.get(from.piece).contains(destination)) { 
			
			if(destination.hasPiece()) { // removes taken piece
				pieceMoves.remove(destination.piece);
			}
			
			hasMovedPrevious = fromPiece.hasMoved;
			fromPiece.hasMoved = true;

			destination.piece = from.piece;
			destination.piece.setCoords(toRow, toCol);  
			from.removePiece();
			updateBoard(fromPiece);
			
			
			Piece additionalCheckingPiece = additionalCheckingPiece(); // bad move need case for second checking piece
			if(additionalCheckingPiece != null && additionalCheckingPiece.color != destination.piece.color) { // checks if own move put self in check
				
				fromPiece.hasMoved = hasMovedPrevious;
				
				destination.piece = destinationPiece;
				if(destination.piece != null ) {
					destination.piece.setCoords(toRow, toCol); 
					pieceMoves.put(destination.piece, null);
				}
				
				from.piece = fromPiece;
				from.piece.setCoords(fromRow, fromCol);

				updateBoard(fromPiece);
								
				return false;
			}
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static HashMap<Piece, HashSet<Square>> validCheckMoves() { 
		Piece checkingPiece = checkingPiece();
		if(checkingPiece == null) {
			return null;
		}
		HashMap<Piece, HashSet<Square>> outHashMap = new HashMap<Piece, HashSet<Square>>();
		
		// just look at checking piece and its path
		// king moves 
		if(checkingPiece().color == Color.BLACK) {
			
			
			HashSet<Square> uncheckingSquares = checkingPiece.getUncheckingSquares(); // set of all squares that will uncheck king if filled
			
			for(Piece piece : pieceMoves.keySet()) { // looks through every piece's move set to see if any contain any of the uncheckingMoves
				HashSet<Square> valSet = new HashSet<Square>();
				if(piece.color == Color.WHITE) {
					for(Square square : uncheckingSquares) { // populates the current hashSet with squares overlapping with the unChecking moves
						if(pieceMoves.get(piece).contains(square)) {
							valSet.add(square);
						}
					}
					outHashMap.put(piece, valSet); // associates the populated hash set with the current piece
				}
			}
			outHashMap.put(whiteKing, pieceMoves.get(whiteKing));
			
		} else {
			
			
			HashSet<Square> uncheckingSquares = checkingPiece.getUncheckingSquares(); // set of all squares that will uncheck king if filled
			
			for(Piece piece : pieceMoves.keySet()) { // looks through every piece's move set to see if any contain any of the uncheckingMoves
				HashSet<Square> valSet = new HashSet<Square>();
				if(piece.color == Color.BLACK) {
					for(Square square : uncheckingSquares) { // populates the current hashSet with squares overlapping with the unChecking moves
						if(pieceMoves.get(piece).contains(square)) {
							valSet.add(square);
						}
					}
					outHashMap.put(piece, valSet); // associates the populated hash set with the current piece
				}
			}
			outHashMap.put(blackKing, pieceMoves.get(blackKing));
		}
		return outHashMap;
	} 

	/**
	 * 
	 */
	public static void initializeBoard() {
		
		pieceMoves = new HashMap<Piece, HashSet<Square>>();
		
		for(int i = 0; i < spaces.length; ++i) {
			for(int j = 0; j < spaces[0].length; ++j) {
				spaces[i][j] = new Square();
			}
		}
		
		for(int i = 0; i < spaces.length; ++i) { // Initializes black pawns
			spaces[1][i].piece = new Pawn(1, i, Color.BLACK);
			pieceMoves.put(spaces[1][i].piece, spaces[1][i].piece.getPossibleMoves());
		}
		
		for(int i = 0; i < spaces.length; ++i) { // Initializes white pawns
			spaces[6][i].piece = new Pawn(6, i, Color.WHITE);
			pieceMoves.put(spaces[6][i].piece, spaces[6][i].piece.getPossibleMoves());
		}
		
		spaces[0][0].piece = new Rook(0, 0, Color.BLACK);
		spaces[0][1].piece = new Horse(0, 1, Color.BLACK);
		spaces[0][2].piece = new Bishop(0, 2, Color.BLACK);
		spaces[0][3].piece = new Queen(0, 3, Color.BLACK);
		spaces[0][5].piece = new Bishop(0, 5, Color.BLACK);
		spaces[0][6].piece = new Horse(0, 6, Color.BLACK);
		spaces[0][7].piece = new Rook(0, 7, Color.BLACK);
		
		spaces[0][4].piece = new King(0, 4, Color.BLACK);
		blackKing = (King) spaces[0][4].piece;
		
		spaces[7][0].piece = new Rook(7, 0, Color.WHITE);
		spaces[7][1].piece = new Horse(7, 1, Color.WHITE);
		spaces[7][2].piece = new Bishop(7, 2, Color.WHITE);
		spaces[7][3].piece = new Queen(7, 3, Color.WHITE);
		spaces[7][5].piece = new Bishop(7, 5, Color.WHITE);
		spaces[7][6].piece = new Horse(7, 6, Color.WHITE);
		spaces[7][7].piece = new Rook(7, 7, Color.WHITE);
		
		spaces[7][4].piece = new King(7, 4, Color.WHITE);
		whiteKing = (King) spaces[7][4].piece;
		
		
		for(int i = 0; i < spaces.length; i += 7) {
			for(int j = 0; j < spaces[0].length; ++j) {
				pieceMoves.put(spaces[i][j].piece, spaces[i][j].piece.getPossibleMoves());
			}
		}
		
	}
	
	/**
	 * 
	 */
	public static void printBoard() {
		System.out.println("");
		System.out.print("   A     B     C     D     E     F     G     H");
		
		for(int i = 0; i < spaces.length; ++i) {
			System.out.println("");
			System.out.println(" ┌    ┐┌    ┐┌    ┐┌    ┐┌    ┐┌    ┐┌    ┐┌    ┐");
			System.out.print(8 - i);
			System.out.print("  ");
			for(int j = 0; j < spaces[0].length; ++j) {
				if(spaces[i][j].piece == null) {
					System.out.print("  ");
					
				} else if(spaces[i][j].piece instanceof Pawn) {
					if(spaces[i][j].piece.color == Color.BLACK) {
						System.out.print("Bp");
					} else {
						System.out.print("Wp");
					}
					
				} else if(spaces[i][j].piece instanceof Rook) {
					if(spaces[i][j].piece.color == Color.BLACK) {
						System.out.print("BR");
					} else {
						System.out.print("WR");
					}
					
				} else if(spaces[i][j].piece instanceof Horse) {
					if(spaces[i][j].piece.color == Color.BLACK) {
						System.out.print("BH");
					} else {
						System.out.print("WH");
					}
					
				} else if(spaces[i][j].piece instanceof Bishop) {
					if(spaces[i][j].piece.color == Color.BLACK) {
						System.out.print("BB");
					} else {
						System.out.print("WB");
					}
					
				} else if(spaces[i][j].piece instanceof King) {
					if(spaces[i][j].piece.color == Color.BLACK) {
						System.out.print("BK");	
					} else {
						System.out.print("WK");	
					}
					
				} else if(spaces[i][j].piece instanceof Queen) {
					if(spaces[i][j].piece.color == Color.BLACK) {
						System.out.print("BQ");
					} else {
						System.out.print("WQ");
					}
					
				}
				System.out.print("    ");
				
			}
			System.out.println("");
			System.out.print(" └    ┘└    ┘└    ┘└    ┘└    ┘└    ┘└    ┘└    ┘");
		}
		System.out.println("");
	}
	
}
