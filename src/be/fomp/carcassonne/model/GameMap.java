package be.fomp.carcassonne.model;


public interface GameMap extends Observable, Beanable<GameMapBean> {
	
	int getActiveX();
	int getActiveY();
	int getActiveFollowerLocation();
	Tile getTile(int x, int y);
	
	void putTile(Tile tile, int x, int y);
	void setActiveX(int xPos);
	void setActiveY(int yPos);
	void setActiveFollowerLocation(int location);
	
	void removeTile(int x, int y);
	void removeTile(Tile tile);
}
