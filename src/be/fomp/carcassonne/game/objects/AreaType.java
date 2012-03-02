package be.fomp.carcassonne.game.objects;

/**
 * Represents the different types of areas that a tile can have.
 * @author sven
 *
 */
public enum AreaType {
	NONE,
	UNKNOWN,
	FIELD,
	ROAD,
	CITY,
	CITY2,
	CROSSING,
	CLOISTER;
	
	public static AreaType getAreaType(String str){
		if(str.equalsIgnoreCase("field"))    return FIELD;
		if(str.equalsIgnoreCase("city"))     return CITY;
		if(str.equalsIgnoreCase("city2"))    return CITY2;
		if(str.equalsIgnoreCase("road"))     return ROAD;
		if(str.equalsIgnoreCase("crossing")) return CROSSING;
		if(str.equalsIgnoreCase("cloister")) return CLOISTER;
		
		return UNKNOWN;
	}
	
	public String toString(){
		switch(this){
			case FIELD:		return "F";
			case ROAD: 		return "R";
			case CROSSING:	return "X";
			case CITY:		return "C";
			case CITY2:		return "c";
			case CLOISTER:	return "K";
			case UNKNOWN:	return "?";
			case NONE:		return "0";
			default: 		return "0";
		}
	}
}
