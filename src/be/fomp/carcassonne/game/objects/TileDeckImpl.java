package be.fomp.carcassonne.game.objects;

import java.util.Collections;
import java.util.List;

import be.fomp.carcassonne.model.Tile;

/**
 * The tile deck of the game.
 * 
 * Contains the starting tile and the deck.
 * 
 * @author sven
 *
 */
public class TileDeckImpl  implements TileDeck{
	protected TileDeckImpl(){}
	
	/**
	 * The actual deck
	 */
	private List<Tile> deck;
	
	/**
	 * The first tile is kept in a separate variable since this one must always 
	 * be placed first.
	 */
	private Tile firstTile;
	
	/**
	 * Refreshes the deck with new cards
	 * and sets the first tile in its separate variable, 
	 * since pulled cards are randomized.
	 * @param deck
	 */
	protected void setDeck(List<Tile> deck){
		this.firstTile = deck.get(0);
		deck.remove(0);
		
		this.deck = deck;
	}
	
	/**
	 * Shuffles the cards in the deck
	 */
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	/**
	 * Fetches a tile and removes it from the deck.
	 * @return The top tile or null if the deck is empty.
	 */
	public Tile getTile(){
		if (this.deck.size() == 0) return null;
		if (this.firstTile != null) return getFirstTile();

		Tile returnValue = this.deck.get(0);
		this.deck.remove(0);
		return returnValue;
	}
	
	/**
	 * Fetches the first tile which is kept in a separate variable.
	 * (Seemed a good idea for future use)
	 * @return The first tile
	 */
	private Tile getFirstTile(){
		Tile returnValue = this.firstTile;
		this.firstTile = null;
		return returnValue;
	}
}
