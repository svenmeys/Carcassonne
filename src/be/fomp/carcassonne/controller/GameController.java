package be.fomp.carcassonne.controller;

import java.util.Observer;

import be.fomp.carcassonne.exceptions.ActionNotAllowedException;
import be.fomp.carcassonne.exceptions.TileFactoryException;
import be.fomp.carcassonne.model.Player;


/**
 * The main controller of the carcassonne game.
 * Handles all main application calls and delegates actions to specific controllers
 * for each individual subcomponent.
 * @author sven
 *
 */
public interface GameController extends Observer {

	/**
	 * Action that opens the player view to change the players
	 * @throws ActionNotAllowedException
	 */
	void doEditPlayers() throws ActionNotAllowedException;

	
	//Main events
	/**
	 * Action to change the scale of the tile map
	 * @param scale the sale to set
	 */
	void doChangeScaling(double scale);
	
	//PlayerView Events
	/**
	 * Action to add a player
	 * @throws ActionNotAllowedException
	 */
	void doAddPlayer() throws ActionNotAllowedException;
	
	/**
	 * Action that adds a player to the game
	 * @param player the new player
	 * @throws ActionNotAllowedException when the game is in progress or the limit was reached
	 */
	void doAddPlayer(Player player) throws ActionNotAllowedException;
	
	/**
	 * Action to remove the selected player
	 * @throws ActionNotAllowedException
	 */
	void doRemovePlayer() throws ActionNotAllowedException;
	
	/**
	 * Action that selects the next player if there is one
	 */
	void doSelectNextPlayer();
	
	/**
	 * Action that selects the previous player if there is one
	 */
	void doSelectPreviousPlayer();
	
	//GameControlView Events
	/**
	 * Action that starts the game
	 * @param name game name
	 * @throws ActionNotAllowedException when the game state is invalid
	 * @throws TileFactoryException when the tiles fail to load
	 */
	void doStartGame(String name) throws ActionNotAllowedException, TileFactoryException;
	
	/**
	 * Action to rotate a tile
	 */
	void doRotateTile();
	
	/**
	 * Action to confirm placing a tile on the map
	 * @throws ActionNotAllowedException
	 */
	void doPlaceTile() throws ActionNotAllowedException;
	
	/**
	 * Action which places the follower on a specified point on a tile.
	 * @param location the coordinates to set the follower at
	 * @throws ActionNotAllowedException when the game state is wrong
	 */
	void doClickFollowerLocation(int location) throws ActionNotAllowedException;
	
	/**
	 * Action that confirms placing a follower
	 * @throws ActionNotAllowedException when the location is invalid
	 */
	void doPlaceFollower() throws ActionNotAllowedException;
	
	/**
	 * Action that starts the next game round
	 * @throws ActionNotAllowedException when the game state is invalid
	 */
	void doNextRound() throws ActionNotAllowedException;
	
	/**
	 * Action that ends the game. 
	 * @throws ActionNotAllowedException
	 */
	void doEndGame() throws ActionNotAllowedException;
	//void doNewGame();
	
	//GameMapView Events
	/**
	 * Action which places a tile on the game map when a user selects the location.
	 * @param x x coordinate
	 * @param y y coordinate
	 * @throws ActionNotAllowedException when this action is called in a wrong game state
	 */
	void doClickTile(int x, int y) throws ActionNotAllowedException;
}
