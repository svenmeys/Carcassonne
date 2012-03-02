package be.fomp.carcassonne.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import be.fomp.carcassonne.model.beans.GameMapBean;
import be.fomp.carcassonne.model.beans.GameMapBeanImpl;
import be.fomp.carcassonne.model.beans.TileBean;
import be.fomp.carcassonne.utils.Ruleset;

public class GameMapImpl extends Observable implements GameMap{
	private Map<Integer, Map<Integer, Tile>> gameMap;
	private int activeX;
	private int activeY;
	private int activeFollowerLocation;
	
	public GameMapImpl(){
		this.gameMap = new HashMap<Integer, Map<Integer,Tile>>();
	}

	@Override
	public Tile getTile(int x, int y) {
		if(this.gameMap.get(x) == null)
			return null;
		
		return this.gameMap.get(x).get(y);
	}

	@Override
	public void putTile(Tile tile, int x, int y) {
		if(this.gameMap.get(x) == null)
			this.gameMap.put(x, new HashMap<Integer, Tile>());
		this.gameMap.get(x).put(y, tile);
		setChanged();
	}

	@Override
	public void removeTile(int x, int y) {
		if(this.gameMap.get(x) != null) {
			this.gameMap.remove(y);
			if(this.gameMap.get(x).size() == 0)
				this.gameMap.remove(x);
		}
		setChanged();
	}

	@Override
	public void removeTile(Tile tile) {
		for(Integer x:this.gameMap.keySet())
			for(Integer y: this.gameMap.get(x).keySet())
				if(this.gameMap.get(x).get(y) == tile)
					removeTile(x, y);
	}

	@Override
	public GameMapBean toBean() {
		int xOffset = Ruleset.MAX_TILES_PER_ROW/2;
		int yOffset = Ruleset.MAX_TILES_PER_COL/2;
		
		GameMapBean returnValue = new GameMapBeanImpl();
		TileBean map[][] = new TileBean[Ruleset.MAX_TILES_PER_ROW][Ruleset.MAX_TILES_PER_COL];
		for(Integer x:this.gameMap.keySet())
			for(Integer y: this.gameMap.get(x).keySet())
				map[x+xOffset][y+yOffset] = this.gameMap.get(x).get(y).toBean();
		returnValue.setMap(map);
		returnValue.setActiveXPos(this.activeX+xOffset);
		returnValue.setActiveYPos(this.activeY+yOffset);
		returnValue.setActiveFollowerLocation(this.activeFollowerLocation);
		
		return returnValue;
	}

	@Override
	public void setValues(GameMapBean bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getActiveX() {
		return this.activeX;
	}

	@Override
	public int getActiveY() {
		return this.activeY;
	}

	@Override
	public void setActiveX(int xPos) {
		this.activeX = xPos;
		setChanged();
		
	}

	@Override
	public void setActiveY(int yPos) {
		this.activeY = yPos;
		setChanged();
		
	}

	@Override
	public int getActiveFollowerLocation() {
		return this.activeFollowerLocation;
	}

	@Override
	public void setActiveFollowerLocation(int location) {
		this.activeFollowerLocation = location;
		setChanged();
		
	}
}
