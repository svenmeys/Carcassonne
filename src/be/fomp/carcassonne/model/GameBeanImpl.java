package be.fomp.carcassonne.model;

import java.util.Observable;

public class GameBeanImpl extends Observable implements GameBean {
	
	private String name;
	private String state;
	private Integer round;
	private TileBean activeTile;
	private PlayerBean[] players;
	private GameMapBean map;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getRound() {
		return round;
	}
	public void setRound(Integer orund) {
		this.round = orund;
	}
	public TileBean getActiveTile() {
		return activeTile;
	}
	public void setActiveTile(TileBean activeTile) {
		this.activeTile = activeTile;
	}
	public PlayerBean[] getPlayers() {
		return players;
	}
	public void setPlayers(PlayerBean[] players) {
		this.players = players;
	}
	public GameMapBean getMap() {
		return map;
	}
	public void setMap(GameMapBean map) {
		this.map = map;
	}
	
	
}
