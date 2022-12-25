package chessGame;

import java.util.Scanner;

public class Chess { //TODO problem: check & checkmate states //TODO king wont move past half way point
	
	public static boolean isWhiteTurn = true;
	
	// for letters take in character, call tolowercase and subtract 97 (a int va)
	private static final String validLetters = "abcdefghABCDEFGH";
	
	public static void main(String[] args) { 
		Scanner userIn = new Scanner(System.in);
		String line = "";
		Board.initializeBoard();
		Board.printBoard();
		System.out.print("White Move: ");
		
		while(userIn.hasNext()) {
			line = userIn.next();
			if(line.length() != 4 
					|| validLetters.indexOf(line.charAt(0)) < 0 
					|| validLetters.indexOf(line.charAt(2)) < 0
					|| !Character.isDigit(line.charAt(1))
					|| !Character.isDigit(line.charAt(3))) {
				System.out.println("invalid move");
				
				Board.printBoard();
				if(isWhiteTurn == true) {
					System.out.print("White Move: ");
				} else {
					System.out.print("Black Move: ");
				}
				continue;
			}
			char fromCol = line.charAt(0);
			int fromRow = Integer.parseInt(String.valueOf(line.charAt(1)));
			char toCol = line.charAt(2);
			int toRow = Integer.parseInt(String.valueOf(line.charAt(3)));

			userMove(fromRow,fromCol,toRow,toCol);
			Board.printBoard();
			if(Board.checkingPiece() != null){
				System.out.print("check ");
			}
			if(Board.isMate()){
				System.out.print("mate");
			} else {
				System.out.println("");
			}
			if(isWhiteTurn == true) {
				System.out.print("White Move: ");
			} else {
				System.out.print("Black Move: ");
			}
			
		}
		userIn.close();
		
	}
	
	private static void userMove(int fromRow, char fromColChar, int toRow, char toColChar) {
		int fromColNew = Character.toLowerCase(fromColChar) - 97; // converts input chars to an index
		int toColNew = Character.toLowerCase(toColChar) - 97;
		int fromRowNew = Board.spaces.length - fromRow; // converts input index to corresponding array index
		int toRowNew = Board.spaces[0].length - toRow;
		
		if(isWhiteTurn && Board.spaces[fromRowNew][fromColNew].piece != null && Board.spaces[fromRowNew][fromColNew].piece.color != Color.WHITE) {
				System.out.println("invalid move");
				return;
		} else if(!isWhiteTurn && Board.spaces[fromRowNew][fromColNew].piece != null &&  Board.spaces[fromRowNew][fromColNew].piece.color != Color.BLACK) {
				System.out.println("invalid move");
				return;
		}
	
		if(Board.checkingPiece() == null && Board.move(fromRowNew, fromColNew, toRowNew, toColNew)) {
			isWhiteTurn = !isWhiteTurn;
		} else if(Board.checkingPiece() != null && Board.checkMove(fromRowNew, fromColNew, toRowNew, toColNew)) {
			isWhiteTurn = !isWhiteTurn;
			System.out.println("check");		
		} else {
			System.out.println("invalid move");
		}
		
	}
	
}
