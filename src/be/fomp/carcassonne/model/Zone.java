package be.fomp.carcassonne.model;

import java.util.Set;

public interface Zone extends Observable {
	
	Set<Area> getAreas();
	Set<Zone> getNeigboringZones();
	
	void setAreas(Set<Area> areas);
	void setNeigboringZones(Set<Zone> neigboringZones);
	
	Zone mergeInto(Zone zone);
	
	boolean hasFollowers();
	Set<Follower> getFollowers();
	Set<Tile> getTiles();
}
