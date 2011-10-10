package be.fomp.carcasonne;

import be.fomp.carcasonne.game.CarcasonneGame;
import be.fomp.carcasonne.game.Game;
import be.fomp.carcasonne.game.objects.CarcasonnePlayer;
import be.fomp.carcasonne.utils.Color;

public class Carcasonne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new CarcasonneGame();
		game.addPlayer(new CarcasonnePlayer("Player 1", Color.BLACK));
		game.addPlayer(new CarcasonnePlayer("Player 2", Color.BLACK));
		game.addPlayer(new CarcasonnePlayer("Player 3", Color.BLACK));
		game.addPlayer(new CarcasonnePlayer("Player 4", Color.BLACK));
		game.addPlayer(new CarcasonnePlayer("Player 5", Color.BLACK));
		game.start();
		
								
	}

}
