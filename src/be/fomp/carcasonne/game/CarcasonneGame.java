package be.fomp.carcasonne.game;

import java.util.List;

import be.fomp.carcasonne.game.objects.Player;

public class CarcasonneGame implements Game {
	private GameState state;
	private List<Player> players;
	
	public CarcasonneGame() {
		this.state = GameState.INIT;
	}
	@Override
	public void start() {
		// TODO
		if(state == GameState.INIT && players.size() >=2 && players.size() <= 5) 
		{
			//start the game
		}
	}
	
	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public GameState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Player> getPlayers() {
		return this.players;
	}

	@Override
	public Game addPlayer(Player player) {
		// TODO Auto-generated method stub
		return null;
	}
}
