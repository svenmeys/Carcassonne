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
			carcassonneGame.addPlayer(player1);
			carcassonneGame.addPlayer(player2);
		} catch (GameException e){System.out.println("error: " + e.getMessage());}
		
		/**
		CarcassonneGameController game = new CarcassonneGameControllerImpl();
		try {
			game.setupGame();
			
			game.addPlayer(player1);
			game.addPlayer(player2);
			
			game.startGame();
			
		//} catch (GameException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		int round = 0;
		Player activePlayer;
		Tile activeTile = game.pullTile();
		while(game.isRunning())
		{
			activePlayer = game.getActivePlayer();
			
			//FIXME add check for when a tile is pulled but not yet placed
			
			System.out.println(activeTile);
			activeTile.rotate();
			System.out.println(activeTile);
			
			//While location is invalid, request input
			//try {
			//	game.placeTile(activeTile, 0, 1);
			//} catch (ActionNotAllowedException e) {
			//	e.printStackTrace();
			//} catch (InvalidTileException e) {
			//	e.printStackTrace();
			//}
			
			//game.placeFollower(); //FIXME have to add this method
			
			activeTile = game.pullTile();
		}
		
		//Test Routine
		game.restartGame();
		Tile testTile1 = null;
		Tile testTile2 = null;
		Tile testTile3 = null;
		//testing connection of tiles and areas and followers
		
		Tile card = game.pullTile();
		int numcards = 0;
		while (card != null){
			if(card.getId() == 15) 
				if (testTile1 == null)testTile1 = card;
				else if(testTile2 == null) testTile2 = card;
			if(card.getId() == 4) testTile3 = card;
			
			System.out.println(card);
			card = game.pullTile();
			numcards++;
		}
		System.out.println("Number of cards pulled = " + numcards + " Test " + (numcards == 72?"passed":"failed"));
	} catch (GameException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}**/
	}

}
