package be.fomp.carcassonne.model;

import be.fomp.carcassonne.game.objects.AreaType;

public interface Area extends Observable {
	Tile getLocation();
	AreaType getAreaType();
	Follower getFollower();
	Zone getZone();
	
	void setLocation(Tile location);
	void setAreaType(AreaType type);
	void setFollower(Follower follower);
	void removeFollower();
	void setZone(Zone zone);
	
	boolean hasFollower();
}
