package be.fomp.carcassonne.utils;

public enum Color{
	RED,
	GREEN,
	BLUE,
	YELLOW,
	BLACK;
	
	public static java.awt.Color getColor(String color){
		if(color.equalsIgnoreCase("red")) return java.awt.Color.RED;
		if(color.equalsIgnoreCase("green")) return java.awt.Color.GREEN;
		if(color.equalsIgnoreCase("blue")) return java.awt.Color.BLUE;
		if(color.equalsIgnoreCase("yellow")) return java.awt.Color.YELLOW;
		if(color.equalsIgnoreCase("black")) return java.awt.Color.BLACK;
		return java.awt.Color.BLACK;	
	}
}
