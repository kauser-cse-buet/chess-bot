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
	
//		// This list will hold all the children nodes of the root.
//		ArrayList<State> children = new ArrayList<>();
//		// Generate all the children nodes of the root (that is, all the
//		// possible next states of the game.  Make sure that we do not exceed
//		// the number of GameTree nodes that we are allowed to generate.
//				
//		Iterator<State> iterator = root.next().iterator();
//		State bestChild = null; 
//		
//		while(!root.searchLimitReached() && iterator.hasNext()) {
//			State child = iterator.next();
//			
//			if(bestChild == null) {
//				bestChild = child;
//			}
//			else if(Utility.evaluate(child, player) >= Utility.evaluate(bestChild, player)) {
//				bestChild = child;
//			}
//		}
		
		State bestChild = iterativeDeepeningSearch(root, player);
		
		return bestChild;
	}

	private State iterativeDeepeningSearch(State state, Player player) {
		int depth = 4;
		
		GameTree root = new GameTree(state);
		double value;
		if(state.player == this.player)
			value = findMax(root, player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depth);
		else
			value = findMin(root, player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depth);
		
		for(GameTree child : root.children)
			if(child.value == value)
				return child.state;
		
		return null;
	}

	private double findMin(GameTree tree, Player player, double alpha, double beta, int depth) {
		// This method is simply the opposite of #findMax.
		if(tree.isTerminal(tree.state) || depth == 0) {
			return Utility.evaluate(tree.state, player);	 
		}
		
		double min = Double.POSITIVE_INFINITY;
		 
		
		while(tree.hasNextChild()) {
			GameTree child = tree.getNextChild();
			child.value = findMax(child, player, alpha, beta, depth -  1);
			
			min = Math.min(min, child.value);
			
			if(min <= alpha) {
				return min;
			}
			beta = Math.min(min, beta);
		}
		
		// The parameter 'alpha' holds the highest utility value that has been
		// discovered so far in this branch of the game tree.  We are currently
		// looking for the child with the lowest value, but if we find something
		// that is less than or equal to alpha, there is no reason to bother checking
		// more children nodes because a better move must already exist somewhere
		// else that has already been explored.

		// Update beta to be the lowest value discovered so far.
		
		return min;
	}

	private double findMax(GameTree tree, Player player, double alpha, double beta, int depth) {
		// 		// First, check if this node is a leaf node (i.e. the game is over)
		// using Tree#state#isTerminal().  If so, simply return the utility of
		// this state.
		if(tree.isTerminal(tree.state) || depth == 0) {
			return Utility.evaluate(tree.state, player);
		}
		
		// If this node is not a leaf, then we need to expand all of its
		// children and find the one with the highest minimum utility value.
		// Start with the lowest possible number, Double#NEGATIVE_INFINITY and
		// work your way up from there.
		double max = Double.NEGATIVE_INFINITY;
		// You can check if a node has more children that have not yet been
		// explored using GameTree#hasNextChild().
		while(tree.hasNextChild()) {
			GameTree child = tree.getNextChild();
			child.value = findMin(child, player, alpha, beta, depth - 1);
			max = Math.max(child.value, max);
			if(max >= beta) {
				return max;
				
			}
			alpha = Math.max(alpha, max);
			
		}
		// You can get the next unexplored child node with GameTree#getNextChild().
		
		// Find the lowest possible utility value the child node can have.
		
		// Update 'max' based on this new information.  'max' should always hold the
		// largest value we have discovered so far.
		
		// The parameter 'beta' holds the lowest utility value that has been
		// discovered so far in this branch of the game tree.  We are currently
		// looking for the child with the highest value, but if we find something
		// that is greater than or equal to alpha, there is no reason to bother
		// checking more children nodes because a better move must already exist
		// somewhere else that has already been explored.
		
		// Update alpha to be the lowest value discovered so far.
		
		return max;
	}
}
