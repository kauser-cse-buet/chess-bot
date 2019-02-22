package com.stephengware.java.games.chess.bot;

import java.util.ArrayList;
import java.util.Iterator;
import com.stephengware.java.games.chess.state.State;


/**
 * A game tree is a representation of all the possible states that could occur
 * during the play of game and how one state is reached from another.
 * 
 * @author Stephen G. Ware
 */
public class GameTree{
	public final State state;
	public double value = 0;
	/** This node's children nodes (i.e. all possible next states) */
	public final ArrayList<GameTree> children = new ArrayList<>();
	
	/** An iterator of the next possible moves to make */
	private final Iterator<State> nextMoves;
	
	protected GameTree(State state) {
		this.state = state;
		this.nextMoves = state.next().iterator();
	}
	
	/**
	 * Returns the number of nodes in this tree.
	 * 
	 * @return the number of nodes
	 */
	public int size() {
		int size = 1;
		for(GameTree child : children)
			size += child.size();
		return size;
	}
	
	/**
	 * Returns true if this node has more children nodes which have not yet
	 * been expanded.
	 * 
	 * @return true if there are more children nodes to add, false otherwise
	 */
	public boolean hasNextChild() {
		if(!state.searchLimitReached() && nextMoves.hasNext()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Constructs and returns the next child node of this node.
	 * 
	 * @return the next child node
	 */
	public GameTree getNextChild() {
		State childState = nextMoves.next();
		GameTree child = new GameTree(childState);
		children.add(child);
		return child;
	}
}
