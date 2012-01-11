package be.fomp.carcassonne.utils;

public enum Direction {
	TOP(0),
	BOTTOM(1),
	RIGHT(2),
	LEFT(3);
	
	private int val;
	
	private Direction(int val)
	{
		this.val = val;
	}
	
	public int value(){
		return this.val;
	}
}
