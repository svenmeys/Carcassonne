package be.fomp.carcassonne.model.beans;

import java.util.Observable;

public class GameMapBeanImpl extends Observable implements GameMapBean{
	private TileBean[][] map;
	
	private Integer activeXPos;
	private Integer activeYPos;
	private Integer activeFollowerLocation;
	
	@Override
	public Integer getActiveFollowerLocation() {
		return activeFollowerLocation;
	}

	@Override
	public void setActiveFollowerLocation(Integer activeFollowerLocation) {
		this.activeFollowerLocation = activeFollowerLocation;
	}

	@Override
	public TileBean[][] getMap() {
		return this.map;
	}

	@Override
	public void setMap(TileBean[][] map) {
		this.map = map;
	}

	@Override
	public Integer getActiveXPos() {
		return this.activeXPos;
	}

	@Override
	public Integer getActiveYPos() {
		return this.activeYPos;
	}

	@Override
	public void setActiveXPos(Integer xPos) {
		this.activeXPos = xPos;
		setChanged();
	}

	@Override
	public void setActiveYPos(Integer yPos) {
		this.activeYPos = yPos;
		setChanged();
	}

}
