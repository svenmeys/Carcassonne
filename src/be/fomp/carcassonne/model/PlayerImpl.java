package be.fomp.carcassonne.model;

import java.util.Observable;

import be.fomp.carcassonne.utils.Color;
import be.fomp.carcassonne.utils.PlayerState;

public class PlayerImpl extends Observable implements Player{

	private String 		name;
	private int 		age;
	private Color 		color;
	private int 		followers;
	private int 		score;
	private PlayerState state;
	
	public PlayerImpl()
	{
		//Set Default (Safe) values
		     name = "New Player";
		      age = 1;
		    color = Color.BLACK;
		followers = 0;
		    score = 0;
		    state = PlayerState.WAITING;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		setChanged();
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
		setChanged();
	}
	
	public Color getColor() {
		return color;
	}
	
	public PlayerState getState() {
		return this.state;
	}
	
	public void setColor(Color color) {
		this.color = color;
		setChanged();
	}
	
	public int getFollowers() {
		return followers;
	}
	
	public void setFollowers(int followers) {
		this.followers = followers;
		setChanged();
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
		setChanged();
	}
	
	public void setState(PlayerState state) {
		this.state = state;
		setChanged();
	}
	
	public PlayerBean toBean(){
		return new PlayerBeanImpl(
				name,
				((Integer)age).toString(),
				color.toString(),
				((Integer)followers).toString(),
				((Integer)score).toString(),
				state.toString());
	}
	
	public void setValues(PlayerBean bean) {
		this.name = bean.getName();
		this.age = Integer.parseInt(bean.getAge());
		this.color = Color.valueOf(bean.getColor());
		//this.followers = Integer.parseInt(bean.getFollowers());
		//this.score = Integer.parseInt(bean.getScore());
		//this.state = Integer.parseInt(bean.getState());
		setChanged();
	}

	public String toString() {
		return new StringBuffer()
			.append(this.name)		.append(" ")
			.append(this.age)		.append(" ")
			.append(this.color)		.append(" ")
			.append(this.followers)	.append(" ")
			.append(this.score)		.append(" ")
			.append(this.state)
			.toString();
	}
	
	@Override
	public int compareTo(Player player) {
		return player.getAge() - this.age;
	}
}
