package be.fomp.carcasonne.game.objects.areas;

import be.fomp.carcasonne.game.objects.Follower;

public abstract class AbstractArea implements Area {
	private AreaType type;
	private Follower follower;
	
	protected void setAreaType(AreaType type)
	{
		this.type = type;
	}
	
	public AreaType getType()
	{
		return this.type;
	}
	
	protected void setFollower(Follower follower)
	{
		this.follower = follower;
	}
	
	public Follower getFollower()
	{
		return this.follower;
	}
	
	public boolean hasFollower()
	{
		return this.follower != null;
	}
}
