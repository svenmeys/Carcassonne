package be.fomp.carcassonne.game.objects;

import be.fomp.carcassonne.model.Tile;

public interface TileDeck {
	
	Tile getTile();
	void shuffle();
}
