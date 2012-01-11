package be.fomp.carcassonne.utils;

import java.util.Comparator;

import be.fomp.carcassonne.model.Player;

public final class Ruleset {
	//TODO create rulesets for other editions: http://jhywu.tripod.com/CarcRuleV2.htm
	
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 5;

	public static final int MAX_TILES_PER_ROW = 20;
	public static final int MAX_TILES_PER_COL = 20;
	
	public static final int START_FOLLOWERS = 7;
	public static final String DECK_PATH = "/data/deck2.xml";
	
	public static final Comparator<Player> PLAYER_ORDER = new Comparator<Player>() {
																@Override
																public int compare(Player o1, Player o2) {
																	return o2.getAge() - o1.getAge();
																}
															};
															
	public static final int getCityScore(int tiles, int pennants) {
		return (tiles == 2) ? tiles + pennants : 2 * (tiles + pennants);
	}
	
	public static final int getEndCityScore(int tiles, int pennants) {
		return tiles + pennants;
	}
	
	public static final int getCloisterScore() {return 9;}
	public static final int getEndCloisterScore(int surroundingTiles) {return surroundingTiles + 1;}
	public static final int getRoadScore(int tiles) {return 1 * tiles; }
	public static final int getEndRoadScore(int tiles) {return 1 * tiles; }
	
	public static final int getEndFarmerScore(int completeCities) {return 4 * completeCities; }
	private Ruleset(){}
}
