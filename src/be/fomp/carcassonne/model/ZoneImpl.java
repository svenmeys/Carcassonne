package be.fomp.carcassonne.model;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class ZoneImpl extends Observable implements Zone{
	private Set<Area> areas = new HashSet<Area>();
	private Set<Zone> neigboringZones = new HashSet<Zone>();
	
	@Override
	public Set<Area> getAreas() {
		return this.areas;
	}

	@Override
	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}

	@Override
	public Zone mergeInto(Zone zone) {
		
		for(Area a: this.areas) {
			a.setZone(zone);
			if(!zone.getAreas().contains(a)) zone.getAreas().add(a);
		}
		
		// Using to array, otherwise somehow I get a ConcurrentModificationException. 
		for(Zone neighbor: this.neigboringZones.toArray(new Zone[this.neigboringZones.size()])) {
			neighbor.getNeigboringZones().remove(this);
			
			neighbor.getNeigboringZones().add(zone);
			zone.getNeigboringZones().add(neighbor);
		}
		return zone;
	}

	@Override
	public Set<Zone> getNeigboringZones() {
		return neigboringZones;
	}

	@Override
	public void setNeigboringZones(Set<Zone> neigboringZones) {
		this.neigboringZones = neigboringZones;
	}

	@Override
	public boolean hasFollowers() {
		for(Area a:areas)
			if (a.hasFollower()) return true;
		return false;
	}

	@Override
	public Set<Follower> getFollowers() {
		Set<Follower> followers = new HashSet<Follower>();
		
		for(Area a:areas) 
			if (a.hasFollower()) followers.add(a.getFollower());
		
		return followers;
	}
	
	@Override
	public Set<Tile> getTiles() {
		Set<Tile> tiles = new HashSet<Tile>();
		for(Area a : areas)
			tiles.add(a.getLocation());
	
		return tiles;
	}
	
}
