package be.fomp.carcassonne.model;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class TileImpl extends Observable implements Tile {
	private final int id;
	private int followerLocation;
	private int rotation;
	private Area border[];
	private Area borderConnections[];
	private Set<Area> areas;
	
	public TileImpl(int id){
		this.rotation = 0;
		this.followerLocation = -1;
		this.areas = new HashSet<Area>();
		this.borderConnections = new Area[12];
		this.id = id;
	}
	/**
	 * @return the borderConnections
	 */
	public Area[] getBorderConnections() {
		return borderConnections;
	}
	/**
	 * @param borderConnections the borderConnections to set
	 */
	public void setBorderConnections(Area[] borderConnections) {
		this.borderConnections = borderConnections;
	}
	@Override
	public void rotate()
	{
		rotation = (rotation + 90) % 360;
		
		for(int r=0; r<3;r++)
		{
			Area buffer = border[r];
			for(int i=0 ; i<4 ; i++)
				border[r + 3 * i] = (i<3) ? border[(r + 3 * (i + 1) )] : buffer;
		}
		setChanged();
	}
	
	@Override
	public int getId() {
		
		return this.id;
	}

	@Override
	public int getRotation() {
		
		return this.rotation;
	}

	@Override
	public int getFollowerLocation() {
		
		return this.followerLocation;
	}
	
	@Override
	public Area[] getBorder() {
		
		return this.border;
	}

	@Override
	public Set<Area> getAreas() {
		
		return this.areas;
	}

	@Override
	public void setRotation(int rotation) {
		this.rotation = rotation;
		setChanged();
	}
	
	@Override
	public void setFollowerLocation(int followerLocation) {
		this.followerLocation = followerLocation;
		setChanged();
	}


	@Override
	public void setBorder(Area[] border) {
		this.border = border;
		setChanged();
	}


	@Override
	public void setAreas(Set<Area> areas) {
		this.areas = areas;
		setChanged();
	}

	public String toString()
	{
		return 	" " + " "  + border[0] + " " + border[1] + " " + border[2] + "\n" +
			    border[11] + " " + " " + " " + " " + " " + " " + " " +     border[3] + "\n" +
				border[10] + " " + " " + " " + border[12] + " " + " " + " " +  border[4] + "\n"+
			    border[9]  + " " + " " + " " + " " + " " + " " + " " +     border[5] + "\n" +
			    " " + " "  + border[8] + " " + border[7] + " " + border[6] + "\n";
 		
	}
	@Override
	public TileBean toBean() {
		TileBean returnValue = new TileBeanImpl();
		
		returnValue.setId(id);
		returnValue.setRotation(rotation);

		returnValue.setFollowerLocation(followerLocation);
		if(followerLocation != -1)
			returnValue.setFollowerOwner(border[followerLocation].getFollower().getOwner().getColor().toString());
		return returnValue;
	}
	@Override
	public void setValues(TileBean bean) {
		this.rotation = bean.getRotation();
	}
	
	@Override
	public synchronized void addObserver(Observer o) {
		for(Area a : this.areas)
			a.addObserver(o);
		
		super.addObserver(o);
	}
}
