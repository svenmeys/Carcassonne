package be.fomp.carcassonne.model.beans;

import java.io.Serializable;

import be.fomp.carcassonne.model.Observable;

public interface TileBean extends Observable, Serializable {

	Integer getId();
	Integer getRotation();
	String getFollowerOwner();
	Integer getFollowerLocation();
	
	void setId(Integer id);
	void setRotation(Integer rotation);
	void setFollowerOwner(String followerOwner);
	void setFollowerLocation(Integer followerLocation);
}
