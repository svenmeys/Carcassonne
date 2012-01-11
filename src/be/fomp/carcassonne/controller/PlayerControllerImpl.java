package be.fomp.carcassonne.controller;

import java.util.Observable;

import be.fomp.carcassonne.exceptions.ActionNotAllowedException;
import be.fomp.carcassonne.model.Player;
import be.fomp.carcassonne.model.PlayerBean;
import be.fomp.carcassonne.view.PlayerView;
import be.fomp.carcassonne.view.PlayerViewImpl;

public class PlayerControllerImpl implements PlayerController {
	private Player model;
	private PlayerView view;
	private GameController gameController;
	
	public PlayerControllerImpl(Player model, GameController gameController) {
		this.model = model;
		this.gameController = gameController;
		
		view = new PlayerViewImpl(model.toBean(), this);
		view.createView();
		view.viewMode();
		
	}
	
	Player getModel() {
		return this.model;
	}
	
	@Override
	public void doSelectNextPlayer() {
		gameController.doSelectNextPlayer();
		
		System.out.println("Next Player");
		
	}

	@Override
	public void doSelectPreviousPlayer() {
		gameController.doSelectPreviousPlayer();
		
		System.out.println("Previous Player");
		
	}

	@Override
	public void doAddPlayer() throws ActionNotAllowedException {
		gameController.doAddPlayer();
		gameController.doSelectNextPlayer();
		System.out.println("Add Player");
		
	}

	@Override
	public void doRemovePlayer() throws ActionNotAllowedException{
		gameController.doRemovePlayer();
		System.out.println("Remove Player");
		
	}

	@Override
	public void doEditPlayer() {
		view.editMode();
		doShowView();
		System.out.println("Edit Player");
	}

	@Override
	public void doSavePlayer(PlayerBean player) {
		System.out.println("Save Player");
		view.viewMode();
		//TODO no color validation yet, need to have full model for that 
		model.setValues(player);
		model.notifyObservers();
	}
	
	@Override
	public void doShowView() {
		view.setVisible(true);
		System.out.println("Show view");
		
	}
	
	@Override
	public void doHideView() {
		view.setVisible(false);
		System.out.println("Hide view");
		
	}
	
	public void update(Observable o, Object arg)
	{
		Player updatedModel = (Player)o;
		this.model = updatedModel;
		view.update((Observable)updatedModel.toBean(), arg);
		System.out.println("Player model updated in PlayerController");
	}
}
