package be.fomp.carcassonne.controller;

import java.util.Collections;
import java.util.Observable;

import javax.swing.SwingUtilities;

import be.fomp.carcassonne.exceptions.ActionNotAllowedException;
import be.fomp.carcassonne.exceptions.InvalidTileException;
import be.fomp.carcassonne.exceptions.TileFactoryException;
import be.fomp.carcassonne.game.objects.AreaType;
import be.fomp.carcassonne.game.objects.TileDeck;
import be.fomp.carcassonne.game.objects.TileDeckFactory;
import be.fomp.carcassonne.model.Area;
import be.fomp.carcassonne.model.Follower;
import be.fomp.carcassonne.model.FollowerImpl;
import be.fomp.carcassonne.model.Game;
import be.fomp.carcassonne.model.GameImpl;
import be.fomp.carcassonne.model.GameMapImpl;
import be.fomp.carcassonne.model.Player;
import be.fomp.carcassonne.model.PlayerImpl;
import be.fomp.carcassonne.model.Tile;
import be.fomp.carcassonne.utils.GameState;
import be.fomp.carcassonne.utils.PlayerState;
import be.fomp.carcassonne.utils.Ruleset;
import be.fomp.carcassonne.utils.ScoreCalculator;
import be.fomp.carcassonne.utils.TileUtils;
import be.fomp.carcassonne.view.GameView;
import be.fomp.carcassonne.view.GameViewImpl;

public class GameControllerImpl implements GameController{
	
	private Game model;
	private GameView view;
	
	/** Controls the individual players of the game model **/
	private Player			 playerModel;
	private PlayerController playerController;
	
	/** Controls the game map **/
	//private MapController mapController;
	
	public GameControllerImpl()
	{
		model = new GameImpl();
		model.setScale(1);
		model.addObserver(this);
		view = new GameViewImpl(model.toBean(), this);
		view.createView();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {	
				view.setVisible(true);
			}
		});
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Player) {
			//TODO Set somekind of flag so only player views get updates
			
		}else if(o instanceof Game) {
			Game updatedObject = (Game)o;
			
			if(o != model) {
				model.deleteObserver(this);
				model = updatedObject;
				model.addObserver(this);
			}
		}
		view.update((Observable)model.toBean(), arg);
	}
	
	public void addPlayer(Player player) throws ActionNotAllowedException {
		//check game state
		
		if(model.getPlayers().size() >= Ruleset.MAX_PLAYERS)
			throw new ActionNotAllowedException("Maximum Player limit ["+Ruleset.MAX_PLAYERS+"] reached\n");
		player.setState(PlayerState.WAITING);
		player.addObserver(this);
		
		model.addPlayer(player);
		model.notifyObservers();
	}
	
	/* Main panel actions */
	public void doChangeScaling(double scale)
	{
		//TODO add checks
		if(scale > 0)	model.setScale(scale);
		
		model.notifyObservers();
	}
	
	/* GameControl Actions */
	public void doStartGame(String name) throws ActionNotAllowedException, TileFactoryException {
		//TODO do validation in separate class
		if(model.getState() != GameState.INIT) 
			throw new ActionNotAllowedException("Invalid Game State: " + model.getState().toString());
		if(model.getPlayers().size() > Ruleset.MAX_PLAYERS) 
			throw new ActionNotAllowedException("Maximum player limit exceeded");
		if(model.getPlayers().size() < Ruleset.MIN_PLAYERS) 
			throw new ActionNotAllowedException("Minimum player limit exceeded");
		
		//Initialize game variables
		TileDeck deck = TileDeckFactory.getInstance().buildDeck(Ruleset.DECK_PATH); //TODO use variable instead
		model.setDeck(deck);
		model.setMap(new GameMapImpl());
		model.getMap().addObserver(this);
		model.getMap().putTile(deck.getTile(), 0, 0);
		model.getDeck().shuffle();
		
		model.setRound(0);
		Collections.sort(model.getPlayers(), Ruleset.PLAYER_ORDER);
		for(Player p: model.getPlayers()) {
			p.setFollowers(Ruleset.START_FOLLOWERS);
		}
		//TODO maybe add member variable in model for easy and readable access
		getActivePlayer().setState(PlayerState.PLACING_CARD);
		model.setState(GameState.RUNNING);
		model.setActiveTile(deck.getTile());
		model.getActiveTile().addObserver(this);
		
		model.getMap().setActiveX(0);
		model.getMap().setActiveY(0);
		model.getMap().setActiveFollowerLocation(-1);
		
		model.notifyObservers();
	}
	
	/* Player actions */
	
	public void doEditPlayers() throws ActionNotAllowedException {
		//TODO check gameState
		
		//TODO check if model has players in it
		if(model.getPlayers().isEmpty()) {
			addPlayer(new PlayerImpl());
			model.notifyObservers();
		}
		playerModel		 = model.getPlayers().get(0);
		playerModel.addObserver(this);
		if(playerController == null)
			playerController = new PlayerControllerImpl(playerModel, this);
		else 
			playerController.update((Observable)playerModel, 0);
		playerController.doShowView();
	}
	
	public void doAddPlayer() throws ActionNotAllowedException {
		addPlayer(new PlayerImpl());
		playerController.doShowView();
	}
	
	public void doRemovePlayer() throws ActionNotAllowedException {
		//check game state
		
		if(model.getPlayers().size() <= Ruleset.MIN_PLAYERS)
			throw new ActionNotAllowedException("Minimum Player limit ["+Ruleset.MIN_PLAYERS+"] reached\n");
		
		int index = model.getPlayers().indexOf(playerModel);
		playerModel.deleteObservers();
		model.removePlayer(playerModel);
		playerModel = null;
		
		if(index == model.getPlayers().size())
			if(model.getPlayers().size() > 0) //Safeguard for when MIN_PLAYERS is set to 0
				index--;
			else
				doAddPlayer();
		
		model.notifyObservers();
		
		playerModel = model.getPlayers().get(index);
		playerController.update((Observable)playerModel, index);
		
	}
	
	public void doSelectNextPlayer() {
		int index = model.getPlayers().indexOf(playerModel);
		if(model.getPlayers().size() > index + 1 && index != -1)
		{
			playerModel = model.getPlayers().get(index + 1);
			playerController.update((Observable)playerModel, index+1);
		}
	}
	
	public void doSelectPreviousPlayer()
	{
		int index = model.getPlayers().indexOf(playerModel);
		if(index > 0)
		{
			playerModel = model.getPlayers().get(index - 1);
			playerController.update((Observable)playerModel, index-1);
		}
	}

	@Override
	public void doRotateTile() {
		if(model.getState() == GameState.RUNNING &&
			getActivePlayer().getState() == PlayerState.PLACING_CARD)
		model.getActiveTile().rotate();
		model.getActiveTile().notifyObservers();
		
	}

	@Override
	public void doPlaceTile() throws ActionNotAllowedException {
		int xPos = model.getMap().getActiveX();
		int yPos = model.getMap().getActiveY();
		if(model.getMap().getTile(xPos, yPos) != null)
			throw new ActionNotAllowedException("Cannot place tile here, spot taken :(");
		Tile top    = model.getMap().getTile(xPos  , yPos+1);
		Tile right  = model.getMap().getTile(xPos+1, yPos  );
		Tile bottom = model.getMap().getTile(xPos  , yPos-1);
		Tile left   = model.getMap().getTile(xPos-1, yPos  );
		
		try {
		TileUtils.connectTiles(model.getActiveTile(), top, bottom, left, right);
		} catch(InvalidTileException ite) {
			throw new ActionNotAllowedException(ite.getMessage());
		}
		
		model.getMap().putTile(model.getActiveTile(), xPos, yPos);
		System.out.println("putting tile at "+ xPos +", " + yPos);
		model.getMap().setActiveX(0);
		model.getMap().setActiveY(0);
		
		getActivePlayer().setState(PlayerState.PLACING_FOLLOWER);
		model.getMap().notifyObservers();
		
	}

	@Override
	public void doPlaceFollower() throws ActionNotAllowedException {
		Player player = getActivePlayer();
		if(player == null) throw new ActionNotAllowedException("No active player");
		if(player.getFollowers() <= 0) throw new ActionNotAllowedException("You have no more followers");
		
		Tile activeTile = model.getActiveTile();
		int followerLoc = model.getMap().getActiveFollowerLocation();
		System.out.println("placint follower at loc= " + followerLoc);
		//TODO enable button only when there is one
		if(followerLoc < 0) throw new ActionNotAllowedException("No follower placed");
		
		Area followerArea = activeTile.getBorder()[followerLoc];
		
		if(followerArea.getZone().hasFollowers()) throw new ActionNotAllowedException("This zone does already contain followers");
		Follower follower = new FollowerImpl();
		follower.setOwner(player);
		follower.setLocation(followerArea);
		followerArea.setFollower(follower);
		activeTile.setFollowerLocation(followerLoc);
		getActivePlayer().setFollowers(getActivePlayer().getFollowers() -1);
		doNextRound();
	}
	
	@Override
	public void doNextRound() throws ActionNotAllowedException {
		//TODO Fix this (need to pass here to handle endgame) if(model.getState() != GameState.RUNNING) throw new ActionNotAllowedException("Game has not started yet");
		Tile activeTile = model.getDeck().getTile();
		
		if(activeTile == null) doEndGame();
		else {
			getActivePlayer().setState(PlayerState.WAITING);
			calculateScores();
			
			activeTile.addObserver(this);
			model.setRound(model.getRound()+1);
			model.setActiveTile(activeTile);
			model.getMap().setActiveFollowerLocation(-1);
			model.getMap().setActiveX(0);
			model.getMap().setActiveY(0);
			getActivePlayer().setState(PlayerState.PLACING_CARD);
			model.notifyObservers();
		}
	}
	
	@Override
	public void doEndGame() throws ActionNotAllowedException {
		getActivePlayer().setState(PlayerState.WAITING);
		calculateScores();
		calculateEndScores();
		model.getMap().setActiveFollowerLocation(-1);
		model.getMap().setActiveX(0);
		model.getMap().setActiveY(0);
		model.setState(GameState.ENDED);
		model.notifyObservers();
	}
	
	@Override
	public void doClickTile(int x, int y) throws ActionNotAllowedException {
		//Set Y to map coordinates (Bigger = up)
		y = Ruleset.MAX_TILES_PER_COL - 1 - y;
		
		// Set X and Y to map coordinates (center = 0,0)
		x -= Ruleset.MAX_TILES_PER_ROW/2;
		y -= Ruleset.MAX_TILES_PER_COL/2;
		
		if(model.getMap().getTile(x, y) == null) {
			model.getMap().setActiveX(x);
			model.getMap().setActiveY(y);
			model.getMap().notifyObservers();
			//update((Observable)model, this);
		}
	}
	
	@Override
	public void doClickFollowerLocation(int location) throws ActionNotAllowedException {
		AreaType type;
		if(location == 12) type = model.getActiveTile().getBorder()[12].getAreaType();
		else {
			System.out.println("location="+location);
			type = model.getActiveTile().getBorder()[location].getAreaType();
		}
		
		if (type.equals(AreaType.CROSSING) || 
			type.equals(AreaType.NONE) || 
			type.equals(AreaType.UNKNOWN))
			return;
			
		model.getMap().setActiveFollowerLocation(location);
		model.getMap().notifyObservers();
		//update((Observable)model, this);
	}
	
	private Player getActivePlayer() {
		if(model != null && model.getPlayers() != null)
			return model.getPlayers().get(model.getRound()%model.getPlayers().size());
		return null;
	}
	
	private void calculateScores() {
		ScoreCalculator.calculateScores(model.getMap().getTile(0,0));
	}
	
	private void calculateEndScores() {
		ScoreCalculator.calculateEndScores(model.getMap().getTile(0,0));
	}
}
