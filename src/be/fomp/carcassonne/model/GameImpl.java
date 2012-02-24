package be.fomp.carcassonne.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import be.fomp.carcassonne.game.objects.TileDeck;
import be.fomp.carcassonne.utils.GameState;

public class GameImpl extends Observable implements Game {

	private String name;
	private List<Player> players;
	private TileDeck deck;
	private Tile activeTile;
	private GameMap map; //private Map<Integer, Map<Integer, Tile>> gameMap;
	private GameState state;
	private int round;
	private double scale;
	
	public GameImpl() {
		this.state = GameState.INIT;
		this.players = new ArrayList<Player>();
	}

	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
		setChanged();
	}
		
	public List<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		if(!this.players.contains(player))
				this.players.add(player);
		setChanged();
	}

	public void removePlayer(Player player) {
		while(this.players.contains(player))
			this.players.remove(player);
		setChanged();
	}
	
	public TileDeck getDeck() {
		return deck;
	}

	public void setDeck(TileDeck deck) {
		this.deck = deck;
		setChanged();
	}

	
	public Tile getActiveTile() {
		return activeTile;
	}

	public void setActiveTile(Tile activeTile) {
		this.activeTile = activeTile;
		setChanged();
	}

	public GameMap getMap(){
		return this.map;
	}
	
	
	public GameState getState() {
		return state;
	}

	public void setMap(GameMap map) {
		this.map = map;
		setChanged();
	}
	
	public void setState(GameState state) {
		this.state = state;
	}

	public int getRound() {
		return round;
		
	}

	public void setRound(int round) {
		this.round = round;
		setChanged();
	}

	public double getScale() {
		return scale;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
		setChanged();
	}
	
	@Override
	public GameBean toBean() {
		GameBean returnValue = new GameBeanImpl();
		
		PlayerBean[] playerBeans = new PlayerBean[this.players.size()];
		for(int i=0; i< this.players.size();i++)
			playerBeans[i] = this.players.get(i).toBean();

		returnValue.setActiveTile(this.activeTile == null?null:this.activeTile.toBean());
		returnValue.setMap(this.map==null?null:this.map.toBean());
		returnValue.setName(this.name);
		returnValue.setRound(this.round);
		returnValue.setState(this.state.toString());
		returnValue.setPlayers(playerBeans);
		returnValue.setScaling(scale);
		return returnValue;
	}
	
	@Override
	public void setValues(GameBean bean) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public synchronized void addObserver(Observer o) {
		for(Player p: this.players)
			p.addObserver(o);
		if(this.activeTile != null) this.activeTile.addObserver(o);
		if(this.map != null) this.map.addObserver(o);
		
		System.out.println("adding observer");
		super.addObserver(o);
	}
}
