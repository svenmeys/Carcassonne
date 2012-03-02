package be.fomp.carcassonne.model.beans;

import be.fomp.carcassonne.model.Observable;
import be.fomp.carcassonne.model.Player;

/**
 * A bean model for the {@link Player} class. 
 * Contains only string members for effective data transfer from/to views.
 * 
 * @author sven
 *
 */
public interface PlayerBean extends Observable{
	/**
	 * @return the name
	 */
	String getName();
	
	/**
	 * @return the age
	 */
	String getAge();
	
	/**
	 * @return the color
	 */
	String getColor();
	
	/**
	 * @return the amount of followers
	 */
	String getFollowers();
	
	/**
	 * @return the score
	 */
	String getScore();
	
	/**
	 * @return the state
	 */
	String getState();
	
	/**
	 *  @param name the name to set
	 */
	void setName(String name);
	
	/**
	 *  @param age the age to set
	 */
	void setAge(String age);
	
	/**
	 *  @param color the color to set
	 */
	void setColor(String color);
	
	/**
	 *  @param followers the amount of followers to set
	 */
	void setFollowers(String followers);
	
	/**
	 *  @param score the score to set
	 */
	void setScore(String score);
	
	/**
	 * @param state the state to set
	 */
	void setState(String state);
}
