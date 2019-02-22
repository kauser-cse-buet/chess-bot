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
//		
//		while(!root.searchLimitReached() && iterator.hasNext())
//			children.add(iterator.next());
//		// Choose one of the children at random.
//		
//		return children.get(random.nextInt(children.size()));
		
		return minMax(root);
	}
	
	public State minMax(State state) {
		GameTree root = new GameTree(state);
		double value;
				
		if(state.player.name() == player.name())
			value = findMax(root);
		else
			value = findMin(root);
		
		for(GameTree child : root.children)
			if(child.value == value)
				return child.state;
		return null;
	}
	
	/**
	 * Given a {@link GameTree} node, expand its children (if any) to find the
	 * node with the highest minimum utility value.
	 * 
	 * @param tree the node whose children need to be expanded
	 * @return the utility value of the node with the highest minimum utility
	 */
	private double findMax(GameTree tree) {
		// First, check if this node is a leaf node (i.e. the game is over)
		// using Tree#state#isTerminal().  If so, simply return the utility of
		// this state.
//		if(tree.state.isTerminal()) {
//			return Utility.evaluate(tree.state);
//		}
		
		if(!tree.hasNextChild()) {
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
			child.value = findMin(child);
			max  = Math.max(max, child.value);			
				
		}
		
		// You can get the next unexplored child node with GameTree#getNextChild().
		
		// Find the lowest possible utility value the child node can have.
		
		// Update 'max' based on this new information.  'max' should always hold the
		// largest value we have discovered so far.
		
		// Return the highest utility value of all the children nodes.
		return max;
	}
	
	/**
	 * Given a {@link GameTree} node, expand its children (if any) to find the
	 * node with the lowest maximum utility value.
	 * 
	 * @param tree the node whose children need to be expanded
	 * @return the utility value of the node with the lowest maximum utility
	 */
	private double findMin(GameTree tree) {
		// This method is simply the opposite of #findMax.
//		if(tree.state.isTerminal()) {
//			return Utility.evaluate(tree.state);
//		}
		
		if(!tree.hasNextChild()) {
			return Utility.evaluate(tree.state, player);
		}
		
		double min = Double.POSITIVE_INFINITY;
		
		while(tree.hasNextChild()) {
			GameTree child = tree.getNextChild();
			child.value = findMax(child);
			min = Math.min(child.value, min);
			
		}
		return min;
	}
}
