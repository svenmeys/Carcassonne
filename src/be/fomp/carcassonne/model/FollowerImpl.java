package be.fomp.carcassonne.model;


public class FollowerImpl implements Follower{

	private Area location;
	private Player owner;
	
	@Override
	public Player getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(Player player) {
		this.owner = player;
	}
	
	@Override
	public Area getLocation() {
		return this.location;
	}
	
	@Override
	public void setLocation(Area location) {
		this.location = location;
	}
}
