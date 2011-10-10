package be.fomp.carcasonne.game.objects.areas;

import be.fomp.carcasonne.game.objects.Follower;
import be.fomp.carcasonne.game.objects.GameObject;

public interface Area extends GameObject{
	boolean addFollower(Follower follower);
	AreaType getType();
	boolean hasFollower();
}
