package com.stephengware.java.games.chess.bot;
import com.stephengware.java.games.chess.state.Player;
import com.stephengware.java.games.chess.state.State;

public class Utility {

	/**
	 * Returns the desirability of the current state for player X.
	 * 
	 * @param state the current state of the game
	 * @return a positive or negative number or zero
	 */
	public static double evaluate(State state, Player player) {
		if(state.player != player) {
			System.out.println(state.player.name() + ", " +player.name());
		}
		
		
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
		else {
			int totalPieces = state.board.countPieces();
			int numPiecesMyBot = state.board.countPieces(player);
			int numPiecesOtherBot = totalPieces - numPiecesMyBot;
			
			System.out.println(numPiecesMyBot + ", " + numPiecesOtherBot);
			
			if(numPiecesMyBot > numPiecesOtherBot)
				return 1;
			else if (numPiecesMyBot > numPiecesOtherBot) {
				return -1;
			}
			else
				return 0;
		}
	}
}