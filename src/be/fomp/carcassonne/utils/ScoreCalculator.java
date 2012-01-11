package be.fomp.carcassonne.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.fomp.carcassonne.game.objects.AreaType;
import be.fomp.carcassonne.model.Area;
import be.fomp.carcassonne.model.Follower;
import be.fomp.carcassonne.model.Player;
import be.fomp.carcassonne.model.Tile;
import be.fomp.carcassonne.model.Zone;

public final class ScoreCalculator {
	
	/**
	 * - Calculates the score between game rounds
	 * - Assigns that score to the players
	 * - Restores the players followers
	 * 
	 *  //TODO another way of calculating scores would be to keep 
	 *         track of all followers and iterate over them.
	 *         
	 * @param rootTile can be any connected tile
	 */
	public static final void calculateScores(Tile rootTile){
		Set<Area> checkedAreas = new HashSet<Area>();
		
		Zone root = rootTile.getBorder()[0].getZone(); //ROOT ZONE
		Set<Zone> zones = TileUtils.fetchAllZones(root, null);
		
		for(Zone z : zones)						//In every zone
				for(Area az : z.getAreas())										//For each area that:
				{
					if(!az.hasFollower()) continue;								// - has a follower
					if(!checkedAreas.add(az)) continue;							// - has not been checked yet
					
					switch(az.getAreaType()){
						case CLOISTER: 	calculateCloisterScore(az); break;
						
						case CITY:	
						case CITY2: 	calculateCityScore(az);	break;
						
						case ROAD:		calculateRoadScore(az); break;
						default:break;
					}
				}
	}
	
//	(First Edition, included in the English version) Farm Scoring Rules:
//
//		1.       Identify each completed city.
//		
//		2.       Count the total number of farmers adjacent to the city in all 
//				adjacent fields.  These farmers are said to supply the city.
//		
//		3.       The player with the most farmers supplying the city earns the 4 points 
//				(5 if that player also has a pig in an adjacent field).  
//				If the completed city is besieged, earns 8 points (10 if that player 
//				also has a pig on that field) instead.
//		
//		4.       Consider placing a token or a marker of some sort in each scored city; 
//				this may make it easier to accurately tally the points by identifying the
//		 		cities that have already been scored. 
	/**
	 * Calculates the scores at the end of the game
	 * Assigns it to the players
	 * Restores the followers
	**/
	public static final void calculateEndScores(Tile rootTile){
		Set<Area> checkedAreas = new HashSet<Area>();
		
		Zone root = rootTile.getBorder()[0].getZone(); //ROOT ZONE
		Set<Zone> zones = TileUtils.fetchAllZones(root, null);
		
		Set<Zone> completedCities = new HashSet<Zone>();
		Set<Zone> fields = new HashSet<Zone>();
		
		for(Zone z : zones)						//In every zone
				for(Area az : z.getAreas())										//For each area that:
				{
					if(az.getAreaType() == AreaType.CITY || az.getAreaType() == AreaType.CITY2)
						if(isComplete(z)) completedCities.add(z);
					if(!az.hasFollower()) continue;								// - has a follower
					if(!checkedAreas.add(az)) continue;							// - has not been checked yet
					
					switch(az.getAreaType()){
						case CLOISTER: 	calculateEndCloisterScore(az); break;
						
						case CITY:	
						case CITY2: 	calculateEndCityScore(az);	
										break;
						
						case ROAD:		calculateEndRoadScore(az); break;
						case FIELD:		fields.add(z);
						default:break;
					}
				}
		calculateEndFieldScore(fields, completedCities);
	}
	
	private static void restoreFollower(Follower f){
		Player p = f.getOwner();
		p.setFollowers(p.getFollowers() + 1);
		//Clean up followers //TODO clean up here or in controller?
		f.setOwner(null);
		f.getLocation().removeFollower();
		f.setLocation(null);
		
	}
	private static final void calculateCloisterScore(Area cloister){
		Tile t = cloister.getLocation();
		if(!TileUtils.isCompletelySurrounded(t)) return;
		
		Follower f = cloister.getFollower();
		Player p = f.getOwner();
		
		p.setScore(p.getScore() + Ruleset.getCloisterScore());
		restoreFollower(f);
	}
	
	private static final void calculateEndCloisterScore(Area cloister){
		Tile t = cloister.getLocation();
		
		int surroundingTiles = TileUtils.countSurroundingTiles(t);
		
		Follower f = cloister.getFollower();
		Player p = f.getOwner();
	
		p.setScore(p.getScore() + Ruleset.getEndCloisterScore(surroundingTiles));
		
		restoreFollower(f);
	}
	
	/**
	 * From analysis:
		 * Stad: Een stad is compleet als hij volledig door muren omringd is. 
		 * De speler met een ridder in de stad scoort 2 punten voor elke tegel 
		 * waarop de stad ligt plus 2 punten voor elk schildje dat in de stad ligt.
		 * 
		 * Uitzondering: Als de speler een stad vervolledigt dat uit 2 tegels bestaat 
		 * krijgt hij maar 1 punt per tegel plus 1 per schild.
		 * 
		 * De spelers met de meeste volgers krijgen elk de totale score
	 * @param city
	 */
	private static final void calculateCityScore(Area city){
		Zone zone = city.getZone();

		if(!isComplete(zone)) return;									//If the area is not complete, return
		int score = 0;															//Otherwise calculate score
		int tileCount = zone.getTiles().size();
		int pennantCount = 0;
		int mostFollowers = 0;
		
		for(Area a : zone.getAreas())
			if(a.getAreaType() == AreaType.CITY2) pennantCount++;
		
		score = Ruleset.getCityScore(tileCount, pennantCount);
		
		Set<Follower> followers = zone.getFollowers();
		Map<Player, Integer> playerMap = countPlayerFollowers(followers);
		
		mostFollowers = Collections.max(playerMap.values());
		
		for(Player p : playerMap.keySet()) {
			if(playerMap.get(p) != mostFollowers) continue; // pnly the players with most followers can score
			p.setScore(p.getScore() + score);
		}
		
		for(Follower f : followers) restoreFollower(f);
	}
	
	private static final void calculateEndCityScore(Area city){
		Zone zone = city.getZone();

		int score = 0;															//Otherwise calculate score
		int tileCount = zone.getTiles().size();
		int pennantCount = 0;
		int mostFollowers;
		
		for(Area a : zone.getAreas())
			if(a.getAreaType() == AreaType.CITY2) pennantCount++;
		
		score = Ruleset.getEndCityScore(tileCount, pennantCount);
		
		Set<Follower> followers = zone.getFollowers();
		Map<Player, Integer> playerMap = countPlayerFollowers(followers);
		
		mostFollowers = Collections.max(playerMap.values());
		
		for(Player p : playerMap.keySet()) {
			if(playerMap.get(p) != mostFollowers) continue; // only the players with most followers can score
			p.setScore(p.getScore() + score);
		}
		
		for(Follower f : followers) restoreFollower(f);  //Clean up followers
	}
	
	
	private static void calculateRoadScore(Area road) {
		Zone zone = road.getZone();		
		if(!isComplete(zone)) return;

		int tileCount = zone.getTiles().size();
		int score = Ruleset.getRoadScore(tileCount);
		int mostFollowers;
		
		//TODO same as above, put in separate method
		Set<Follower> followers = zone.getFollowers();
		Map<Player, Integer> playerMap = countPlayerFollowers(followers);
		
		mostFollowers = Collections.max(playerMap.values());
		
		for(Player p : playerMap.keySet()) {
			if(playerMap.get(p) != mostFollowers) continue; // pnly the players with most followers can score
			p.setScore(p.getScore() + score);
		}
		
		for(Follower f : followers) restoreFollower(f); //Clean up followers
	}
	
	private static void calculateEndRoadScore(Area road) {
		Zone zone = road.getZone();		

		int tileCount = zone.getTiles().size();
		int score = Ruleset.getEndRoadScore(tileCount);
		
		int mostFollowers = 1;
		
		//TODO same as above, put in separate method
		Set<Follower> followers = zone.getFollowers();
		
		Map<Player, Integer> playerMap = countPlayerFollowers(followers);
		
		mostFollowers = Collections.max(playerMap.values());
		
		for(Player p : playerMap.keySet()) {
			if(playerMap.get(p) != mostFollowers) continue; // only the players with most followers can score
			p.setScore(p.getScore() + score);
		}
		
		for(Follower f : followers) restoreFollower(f); //Clean up followers 
	}

	private static void calculateEndFieldScore(Set<Zone> fields, Set<Zone> cities) {
		Set<Follower> checkedFollowers = new HashSet<Follower>();
		for(Zone city: cities)
			for(Zone field : city.getNeigboringZones())
				if(fields.contains(field) && field.hasFollowers()) {
					int mostFollowers;
					
					//TODO same as above, put in separate method
					Set<Follower> followers = field.getFollowers();
					Map<Player, Integer> playerMap = countPlayerFollowers(followers);
					
					mostFollowers = Collections.max(playerMap.values());
					
					for(Player p : playerMap.keySet()) {
						if(playerMap.get(p) != mostFollowers) continue; // pnly the players with most followers can score
						p.setScore(p.getScore() + Ruleset.getEndFarmerScore(mostFollowers));
					}
					
					checkedFollowers.addAll(followers);  //Tag followers for removal
				}
		
		for(Follower f : checkedFollowers) restoreFollower(f);  //Clean up followers 
	}
	
	private static final boolean isComplete(Zone z) {
		for(Area a : z.getAreas())
		{
			Area[] border = a.getLocation().getBorder();
			Area[] borderConnections = a.getLocation().getBorderConnections();
			
			for(int i=0; i< borderConnections.length; i++)
				if(z == border[i].getZone() && borderConnections[i] == null) 
					return false;
		}
		return true;
	}
	
	private static Map<Player, Integer>  countPlayerFollowers(Set<Follower> followers) {
		Map<Player, Integer> returnValue = new HashMap<Player, Integer>();
		int followerCounter = 1;
		
		for(Follower f: followers) {
			Player p = f.getOwner();
			if(returnValue.containsKey(p)){
				int newCount = returnValue.get(p) + 1;
				returnValue.put(p, newCount);
				if(followerCounter < newCount) followerCounter = newCount;
			}
			else
				returnValue.put(p, 1);
		}
		return returnValue;
	}
}
