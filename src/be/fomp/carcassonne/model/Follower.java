package be.fomp.carcassonne.model;


public interface Follower {
	Player getOwner();
	Area getLocation();
	
	void setOwner(Player player);
	void setLocation(Area location);
}