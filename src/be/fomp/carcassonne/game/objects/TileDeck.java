package be.fomp.carcassonne.game.objects;

import be.fomp.carcassonne.model.Tile;

/**
 * A card deck with 2 basic functions: get and shuffle. Everything else is automated.
 * @author sven
 *
 */
public interface TileDeck {
	
	Tile getTile();
	void shuffle();
}
