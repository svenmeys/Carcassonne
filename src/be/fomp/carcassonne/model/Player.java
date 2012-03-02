package be.fomp.carcassonne.model;

import be.fomp.carcassonne.model.beans.PlayerBean;
import be.fomp.carcassonne.utils.Color;
import be.fomp.carcassonne.utils.PlayerState;

/**
 * The carcassonne player model.
 * @author sven
 *
 */
public interface Player extends Observable, Comparable<Player>, Beanable<PlayerBean> {
	
	/**
	 * @return the name
	 */
	String getName();
	
	/**
	 * @return the age
	 */
	int getAge();
	
	/**
	 * @return the color
	 */
	Color getColor();
	
	/**
	 * @return the amount of followers
	 */
	int getFollowers();
	
	/**
	 * @return the score
	 */
	int getScore();
	
	/**
	 * @return the state
	 */
	PlayerState getState();
	
	/**
	 * @param name the name to set
	 */
	void setName(String name);
	
	/**
	 * @param age the age to set
	 */
	void setAge(int age);
	
	/**
	 * @param color the color to set
	 */
	void setColor(Color color);
	
	/**
	 * @param followers the amount of followers to set
	 */
	void setFollowers(int followers);
	
	/**
	 * @param score the score to set
	 */
	void setScore(int score);
	
	/**
	 * @param state the player state
	 */
	void setState(PlayerState state);
}
