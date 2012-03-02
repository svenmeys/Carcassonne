package be.fomp.carcassonne;

import be.fomp.carcassonne.controller.GameController;
import be.fomp.carcassonne.controller.GameControllerImpl;
import be.fomp.carcassonne.exceptions.GameException;
import be.fomp.carcassonne.model.Player;
import be.fomp.carcassonne.model.PlayerImpl;
import be.fomp.carcassonne.utils.Color;

public class Carcassonne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.setProperty("java.awt.headless", "false");
		
		GameController carcassonneGame = new GameControllerImpl();
		//PlayerController pc = new PlayerControllerImpl(player,gc);
		Player player1 = new PlayerImpl();
		player1.setName("Sven");
		player1.setAge(21);
		player1.setColor(Color.BLACK);
		
		Player player2 = new PlayerImpl();
		player2.setName("Sven2");
		player2.setAge(22);
		player2.setColor(Color.RED);
		
		try{
			carcassonneGame.doAddPlayer(player1);
			carcassonneGame.doAddPlayer(player2);
		} catch (GameException e){System.out.println("error: " + e.getMessage());}
		
	}

}
