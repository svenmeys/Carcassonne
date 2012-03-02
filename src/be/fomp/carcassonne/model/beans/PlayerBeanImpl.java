package be.fomp.carcassonne.model.beans;

import java.util.Observable;


public class PlayerBeanImpl extends Observable implements PlayerBean{
	private String name;
	private String age;
	private String color;
	private String followers;
	private String score;
	private String state;
	
	public PlayerBeanImpl(){}
	public PlayerBeanImpl(String name, String age, String color, String followers, String score, String state)
	{
		setName(name);
		setAge(age);
		setColor(color);
		setFollowers(followers);
		setScore(score);
		setState(state);
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getFollowers() {
		return followers;
	}
	
	public void setFollowers(String followers) {
		this.followers = followers;
	}
	
	public String getScore() {
		return score;
	}
	
	public void setScore(String score) {
		this.score = score;
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

}
