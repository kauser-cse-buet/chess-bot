package com.stephengware.java.games.chess.bot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.stephengware.java.games.chess.Game;
import com.stephengware.java.games.chess.bot.Bot;
import com.stephengware.java.games.chess.gui.Board;
import com.stephengware.java.games.chess.state.Player;
import com.stephengware.java.games.chess.state.State;



/**
 * A chess bot which selects its next move at random.
 * 
 * @author Stephen G. Ware
 */
public class MyBot extends Bot {

	/** A random number generator */
	private final Random random;
	private Player player = null;
	
	
	/**
	 * Constructs a new chess bot named "My Chess Bot" and whose random  number
	 * generator (see {@link java.util.Random}) begins with a seed of 0.
	 */
	public MyBot() {
		super("mahmmed");
		this.random = new Random(0);
	}

	@Override
	protected State chooseMove(State root) {
		System.out.println(root.player.name());
		
		if(player == null) {
			player = root.player;
		}
	
		// This list will hold all the children nodes of the root.
		ArrayList<State> children = new ArrayList<>();
		// Generate all the children nodes of the root (that is, all the
		// possible next states of the game.  Make sure that we do not exceed
		// the number of GameTree nodes that we are allowed to generate.
				
		Iterator<State> iterator = root.next().iterator();
		State bestChild = null; 
		
		while(!root.searchLimitReached() && iterator.hasNext()) {
			State child = iterator.next();
			
			if(bestChild == null) {
				bestChild = child;
			}
			else if(Utility.evaluate(child, player) > Utility.evaluate(bestChild, player)) {
				bestChild = child;
			}
		}
		
		return bestChild;
	}
}
