package com.stephengware.java.games.chess.bot;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.stephengware.java.games.chess.gui.Board;
import com.stephengware.java.games.chess.state.Bishop;
import com.stephengware.java.games.chess.state.King;
import com.stephengware.java.games.chess.state.Knight;
import com.stephengware.java.games.chess.state.Pawn;
import com.stephengware.java.games.chess.state.Piece;
import com.stephengware.java.games.chess.state.Player;
import com.stephengware.java.games.chess.state.Queen;
import com.stephengware.java.games.chess.state.Rook;
import com.stephengware.java.games.chess.state.State;

public class Utility {

	/**
	 * Returns the desirability of the current state for player X.
	 * 
	 * @param state the current state of the game
	 * @return a positive or negative number or zero
	 */
	public static double evaluate(State state, Player player) {
		if(state.over) {
			if(state.check) {
				if(state.player == player)
					return -1;
				else
					return 1;
			}
			else
				return 0;
		}
		
		else
			return materialScore(state, player) - materialScore(state, player.other());
	}
	
	public static double materialScore(State state, Player player) {
		double score = 0.0;
		
		for (Piece piece: state.board) {
			if(piece.player == player) {
//				if(piece.getClass() == King.class) {
//					score += 9.0;
//				}
				if(piece.getClass() == Queen.class) {
					score += 9.0;
				}
				if(piece.getClass() == Rook.class) {
					score += 5.0;
				}
				if(piece.getClass() == Bishop.class) {
					score += 3.0;
				}
				if(piece.getClass() == Knight.class) {
					score += 3.0;
				}
				if(piece.getClass() == Pawn.class) {
					score += 1.0;
				}
			}
			
		}
		
		return score / (9.0 + 2* 5.0 + 2* 3.0 + 8.0);
	}
	
	
}