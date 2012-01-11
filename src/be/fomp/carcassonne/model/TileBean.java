package be.fomp.carcassonne.model;

public interface TileBean extends Observable {

	Integer getId();
	Integer getRotation();
	String getFollowerOwner();
	Integer getFollowerLocation();
	
	void setId(Integer id);
	void setRotation(Integer rotation);
	void setFollowerOwner(String followerOwner);
	void setFollowerLocation(Integer followerLocation);
}
