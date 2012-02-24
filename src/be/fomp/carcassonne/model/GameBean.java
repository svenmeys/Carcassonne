package be.fomp.carcassonne.model;

public interface GameBean extends Observable {
	
	String getName();
	String getState();
	Integer getRound();
	
	TileBean getActiveTile();
	
	PlayerBean[] getPlayers();
	GameMapBean getMap();

	double getScaling();
	
	void setName(String name);
	void setState(String state);
	void setRound(Integer round);
	void setActiveTile(TileBean tile);
	void setPlayers(PlayerBean[] players);
	void setMap(GameMapBean map);
	void setScaling(double scaling);
}
