package be.fomp.carcasonne.game.objects;

import java.util.List;

import be.fomp.carcasonne.utils.Color;

public class CarcasonnePlayer implements Player {
	private String name;
	private Color color;
	private int number;
	private int score;
	private List<Follower> followers;
	
	public CarcasonnePlayer(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public Color getColor() {
		return this.color;
	}
	@Override
	public List<Follower> getFollowers() {
		return followers;
	}

	@Override
	public int getScore() {
		return this.score;
	}

}
