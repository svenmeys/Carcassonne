package be.fomp.carcassonne.model;

import java.util.Observable;

import be.fomp.carcassonne.game.objects.AreaType;

public class AreaImpl extends Observable implements Area {

	private Tile location;
	private AreaType areaType;
	private Follower follower;
	private Zone zone;
	private Area connection;
	
	public AreaImpl(){}
	
	public AreaImpl(AreaType type) {
		this();
		this.areaType = type;
		
	}
	
	/**
	 * @return the areaType
	 */
	public AreaType getAreaType() {
		return areaType;
	}

	/**
	 * @param areaType the areaType to set
	 */
	public void setAreaType(AreaType areaType) {
		this.areaType = areaType;
		setChanged();
	}


	/**
	 * @return the follower
	 */
	public Follower getFollower() {
		return follower;
	}

	/**
	 * @return the tile it is on
	 */
	public Tile getLocation() {
		return location;
	}
	
	/**
	 * @param follower the follower to set
	 */
	public void setFollower(Follower follower) {
		this.follower = follower;
		setChanged();
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(Tile location) {
		this.location = location;
		setChanged();
	}

	/**
	 * Remove the follower
	 */
	public void removeFollower(){
		if(this.follower != null) this.follower.setLocation(null);
		this.follower = null;
		this.connection = null;
		this.location.setFollowerLocation(-1);
		setChanged();
	}
	/**
	 * @return the zone
	 */
	public Zone getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	public boolean hasFollower() {
		return this.follower != null;
	}

	public String toString() {
		return this.areaType.toString();
	}

}
