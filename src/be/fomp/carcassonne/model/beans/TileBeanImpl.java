package be.fomp.carcassonne.model.beans;

import java.util.Observable;

public class TileBeanImpl extends Observable implements TileBean {
	
	private Integer id 			 	= -1;
	private Integer rotation 	 	=  0;
	private String followerOwner 	= "";
	private Integer followerLocation= -1;
	
	public TileBeanImpl(){}
	public TileBeanImpl(Integer id, Integer x, Integer y, Integer rotation, String folowerOwner, Integer followerLocation)
	{
		this.id = id;
		this.rotation = rotation;
		this.followerOwner = folowerOwner;
		this.followerLocation = followerLocation;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
		setChanged();
	}
	
	/**
	 * @return the rotation
	 */
	public Integer getRotation() {
		return rotation;
	}
	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(Integer rotation) {
		this.rotation = rotation;
		setChanged();
	}
	/**
	 * @return the followerOwner
	 */
	public String getFollowerOwner() {
		return followerOwner;
	}
	/**
	 * @param followerOwner the followerOwner to set
	 */
	public void setFollowerOwner(String followerOwner) {
		this.followerOwner = followerOwner;
		setChanged();
	}
	/**
	 * @return the followerLocation
	 */
	public Integer getFollowerLocation() {
		return followerLocation;
	}
	/**
	 * @param followerLocation the followerLocation to set
	 */
	public void setFollowerLocation(Integer followerLocation) {
		this.followerLocation = followerLocation;
		setChanged();
	}
}
