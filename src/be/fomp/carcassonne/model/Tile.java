package be.fomp.carcassonne.model;

import java.util.Set;

import be.fomp.carcassonne.model.beans.TileBean;

/**
 * Border locations
 * 
 *    1  2  3
 *  12        4
 *  11   0    5
 *  10        6
 *    9  8  7
 * 
 */
public interface Tile extends Observable, Beanable<TileBean> {
	
	void rotate();
	
	int getId();
	int getRotation();
	int getFollowerLocation();
	
	Area[] getBorder();
	Area[] getBorderConnections();
	Set<Area> getAreas();
	
	void setRotation(int rotation);
	void setFollowerLocation(int followerLocation);
	void setBorder(Area[] border);
	void setBorderConnections(Area[] border);
	void setAreas(Set<Area> areas);
}
