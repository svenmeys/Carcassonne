package be.fomp.carcassonne.model;

import java.util.List;

import be.fomp.carcassonne.game.objects.TileDeck;
import be.fomp.carcassonne.model.beans.GameBean;
import be.fomp.carcassonne.utils.GameState;

/**
 * @author sven
 *
 */
/**
 * @author sven
 *
 */
/**
 * @author sven
 *
 */
public interface Game extends Observable, Beanable<GameBean> {
	
	/**
	 * @return the name
	 */
	String getName();
	
	/**
	 * @return the players
	 */
	List<Player> getPlayers();
	
	/**
	 * @return the deck
	 */
	TileDeck getDeck();
	
	/**
	 * @return the active tile
	 */
	Tile getActiveTile();
	
	/**
	 * @return the map
	 */
	GameMap getMap();
	
	/**
	 * @return the state of the game
	 */
	GameState getState();
	
	/**
	 * @return the round
	 */
	int getRound();
	
	/**
	 * 
	 * @return the zooming scale of the game map
	 */
	double getScale();
	
	/**
	 * @param name the name of the game
	 */
	void setName(String name);
	
	/**
	 * @param Player adds a player
	 */
	void addPlayer(Player player);
	
	/**
	 * 
	 * @param player the player to remove
	 */
	void removePlayer(Player player);
	
	/**
	 * @param deck the tile deck
	 */
	void setDeck(TileDeck deck);
	
	/**
	 * @param activeTile the active tile
	 */
	void setActiveTile(Tile activeTile);
	
	/**
	 * @param map the game map
	 */
	void setMap(GameMap map);
	
	/**
	 * @param state the game state
	 */
	void setState(GameState state);
	
	/**
	 * @param round the game round
	 */
	void setRound(int round);
	
	/**
	 * @param scale the zoom percentage of the map
	 */
	void setScale(double scale);
}
