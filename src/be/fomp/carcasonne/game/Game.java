package be.fomp.carcasonne.game;

import java.util.List;

import be.fomp.carcasonne.game.objects.Player;

public interface Game {
	Game addPlayer(Player player);
	
	void start();
	void restart();
	
	GameState getState();
	int getRound();
	List<Player> getPlayers();
	
	
	
}