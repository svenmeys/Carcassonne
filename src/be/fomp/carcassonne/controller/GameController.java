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

	void doEditPlayers() throws ActionNotAllowedException;
	//Main events
	void doChangeScaling(double scale);
	
	//PlayerView Events
	void doAddPlayer() throws ActionNotAllowedException;
	void doRemovePlayer() throws ActionNotAllowedException;
	void doSelectNextPlayer();
	void doSelectPreviousPlayer();
	
	//GameControlView Events
	void doStartGame(String name) throws ActionNotAllowedException, TileFactoryException;
	
	void doRotateTile();
	void doPlaceTile() throws ActionNotAllowedException;
	
	void doClickFollowerLocation(int location) throws ActionNotAllowedException;
	void doPlaceFollower() throws ActionNotAllowedException;
	void doNextRound() throws ActionNotAllowedException;
	void doEndGame() throws ActionNotAllowedException;
	//void doNewGame();
	
	//GameMapView Events
	void doClickTile(int x, int y) throws ActionNotAllowedException;
	
	
	void addPlayer(Player player) throws ActionNotAllowedException;
}
