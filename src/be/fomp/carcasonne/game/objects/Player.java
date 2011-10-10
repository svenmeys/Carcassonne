package be.fomp.carcasonne.game.objects;

import java.util.List;

import be.fomp.carcasonne.utils.Color;

public interface Player extends GameObject{
	int getNumber();
	Color getColor();
	
	List<Follower> getFollowers();
	int getScore();
	
}
