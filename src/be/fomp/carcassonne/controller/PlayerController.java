package be.fomp.carcassonne.controller;

import java.util.Observer;

import be.fomp.carcassonne.exceptions.ActionNotAllowedException;
import be.fomp.carcassonne.model.beans.PlayerBean;


/**
 * A controller specific for browsing and editing players.
 * 
 * @author sven
 *
 */
public interface PlayerController extends Observer{
	
	/**
	 * Fetch the next player.
	 */
	void doSelectNextPlayer();
	
	/**
	 * Fetch the previous player
	 */
	void doSelectPreviousPlayer();
	
	/**
	 * Add a player to the pool.
	 * @throws ActionNotAllowedException when playerlimit is reached
	 */
	void doAddPlayer() throws ActionNotAllowedException;
	
	/**
	 * Remove a player from the pool
	 * @throws ActionNotAllowedException when minimum player limit is reached
	 */
	void doRemovePlayer() throws ActionNotAllowedException;
	
	/**
	 * Switches to edit mode. The player can now be edited in the view
	 * @throws ActionNotAllowedException
	 */
	void doEditPlayer();
	
	/**
	 * Saves the changes to the player model
	 * @param player the values returned by the view
	 */
	void doSavePlayer(PlayerBean player);

	/**
	 * Shows the player view
	 */
	void doShowView();
	
	/**
	 * Hides the player view
	 */
	void doHideView();
}
