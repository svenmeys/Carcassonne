package be.fomp.carcassonne.utils;

import java.util.HashSet;
import java.util.Set;

import be.fomp.carcassonne.exceptions.InvalidTileException;
import be.fomp.carcassonne.game.objects.AreaType;
import be.fomp.carcassonne.model.Area;
import be.fomp.carcassonne.model.Tile;
import be.fomp.carcassonne.model.Zone;

public final class TileUtils {
	
	public static final Set<Zone> fetchAllZones(Zone root, Set<Zone> allZones){
		if(allZones == null) allZones = new HashSet<Zone>();
		
		allZones.add(root);
		for(Zone z: root.getNeigboringZones())
			if(!allZones.contains(z)) 
				fetchAllZones(z, allZones);
		return allZones;
	}
	
	public static final void connectTiles(Tile tile, Tile top, Tile bottom, Tile left, Tile right) throws InvalidTileException
	{
		if(top == null && bottom == null && left == null && right == null)
			throw new InvalidTileException("Must connecto atleast one tile");
		matchTiles(tile, top, bottom, left, right);
		if(top != null) {
			connectBorders(tile, 0, top, 8);
			connectBorders(tile, 1, top, 7);
			connectBorders(tile, 2, top, 6);
		}
		if(right != null) {
			connectBorders(tile, 3, right, 11);
			connectBorders(tile, 4, right, 10);
			connectBorders(tile, 5, right,  9);
		}
		if(bottom != null) {
			connectBorders(tile, 6, bottom, 2);
			connectBorders(tile, 7, bottom, 1);
			connectBorders(tile, 8, bottom, 0);
		}
		if(left != null) {
			connectBorders(tile,  9, left, 5);
			connectBorders(tile, 10, left, 4);
			connectBorders(tile, 11, left, 3);
		}
	}
	
	private static final void connectBorders(Tile t1, int b1, Tile t2, int b2){
		t1.getBorder()[b1].setZone(t1.getBorder()[b1].getZone().mergeInto(t2.getBorder()[b2].getZone()));
		
		t1.getBorderConnections()[b1] = t2.getBorder()[b2];
		t2.getBorderConnections()[b2] = t1.getBorder()[b1];
	} 
	
	private static final void match(Area a1, Area a2) throws InvalidTileException {
		if(a1.getAreaType() != a2.getAreaType() &&
			!((a1.getAreaType() == AreaType.CITY && a2.getAreaType() == AreaType.CITY2) ||
			 (a2.getAreaType() == AreaType.CITY && a1.getAreaType() == AreaType.CITY2))
			) throw new InvalidTileException("Tile can not be placed here, areas do not match");
	}
	
	public static final void matchTiles(Tile tile, Tile top, Tile bottom, Tile left, Tile right) throws InvalidTileException {
		if(tile == null) throw new InvalidTileException("Tile can not be null");
		if(top != null) {
			match(tile.getBorder()[0], top.getBorder()[8]);
			match(tile.getBorder()[1], top.getBorder()[7]);
			match(tile.getBorder()[2], top.getBorder()[6]);
		}
		if(right != null) {
			match(tile.getBorder()[3], right.getBorder()[11]);
			match(tile.getBorder()[4], right.getBorder()[10]);
			match(tile.getBorder()[5], right.getBorder()[9]);
		}
		if(bottom != null) {
			match(tile.getBorder()[6], bottom.getBorder()[2]);
			match(tile.getBorder()[7], bottom.getBorder()[1]);
			match(tile.getBorder()[8], bottom.getBorder()[0]);
		}
		if(left != null) {
			match(tile.getBorder()[9],  left.getBorder()[5]);
			match(tile.getBorder()[10], left.getBorder()[4]);
			match(tile.getBorder()[11], left.getBorder()[3]);
		}	
	}
	
	public static final int getCoordinateLocation(int xPos, int yPos, int width, int height){
		System.out.println(xPos + " " + yPos + " " + width + " " + height);
		int xNorm = (3 * xPos) / width;
		int yNorm = (3 * yPos) / height;
		
		double hNorm = height / 3;
		double wNorm = width / 3;
		
		int location  = -1;
		
		//handle corners
		switch(xNorm) {
			case 0:
				switch(yNorm){
				case 0: location = (getHalfRectanglePosition(xPos, hNorm - yPos, wNorm, hNorm, -1) >= 0) ? 0 : 11;break;
				case 1: location = 10; break;
				case 2: location = (getHalfRectanglePosition(xPos, hNorm - (yPos - 2 * hNorm), wNorm, hNorm, +1) >= 0) ? 9 : 8;break;
				}
				break;
			case 1:
				switch(yNorm) {
					case 0: location =  1; break;
					case 1: location = 12; break;
					case 2: location =  7; break;
				}
				break;
			case 2:
				switch(yNorm){
				case 0: location = (getHalfRectanglePosition(xPos - 2 * wNorm, hNorm - yPos	   , wNorm, hNorm, +1) >= 0) ? 2 : 3;break;
				case 1: location = 4; break;
				case 2: location = (getHalfRectanglePosition(xPos - 2 * wNorm, hNorm - (yPos - 2 * hNorm), wNorm, hNorm, -1) >= 0) ? 5 : 6;break;
				}
				break;
			default: location = -1;
		}
		//handle rest
		
		return location;
	}
	
	/**
	 * Precision = h
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param slope
	 * @return
	 */
	public static final int getHalfRectanglePosition(double x, double y, double w, double h, int slope) {
		System.out.println(x + "\t" + y + "\t" + w + "\t" + h + "\t" + x/w + "\t" + y/h + "\t" + (1-x));
		//Normalize
		x /= w;
		y /= h;
		
		if(slope == -1) x = (1-x);
		return ((int)(y*h) - (int)(x*h));
	}
	
	/**
	 * Converts a border value to its rotated counterpart.
	 * Assuming a tile has 12 surrounding borders with borer 12 its center.
	 * @param b the border
	 * @param r Rotation in degrees
	 * @return the rotated border value
	 */
	public static final int rotateBorder(int b, int r){
		return (b == 12) ? 12 : (b + (3 * ( (r%360) / 90 ) ) )% 11;
	}
	
	
	public static final int countSurroundingTiles(Tile tile) {
		int counter = 0;
		
		boolean tl = false;
		boolean tr = false;
		boolean bl = false;
		boolean br = false;
		
		for(int i = 0; i < 4; i++) {
			Area a = tile.getBorderConnections()[i * 3];
			if(a == null) continue;
			counter ++;
			
			if( i == 0) {
				tl = (a.getLocation().getBorderConnections()[10] != null);
				tr = (a.getLocation().getBorderConnections()[ 4] == null);
			}
			if( i == 1) {
				tr = (a.getLocation().getBorderConnections()[ 1] != null);
				br = (a.getLocation().getBorderConnections()[ 7] != null);
			}
			if( i == 2) {
				br = (a.getLocation().getBorderConnections()[ 4] != null);
				bl = (a.getLocation().getBorderConnections()[10] != null);
			}
			if( i == 3) {
				bl = (a.getLocation().getBorderConnections()[ 7] != null);
				tl = (a.getLocation().getBorderConnections()[ 1] != null);
			}
		}
		return counter + (tl?1:0) + (bl?1:0) + (tr?1:0) + (br?1:0);
	}
	
	public static final boolean isCompletelySurrounded(Tile tile) {
		return (countSurroundingTiles(tile) == 8);
	}
}
