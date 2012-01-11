package be.fomp.carcassonne.view.panels;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import be.fomp.carcassonne.controller.GameController;
import be.fomp.carcassonne.view.GameView;

@SuppressWarnings("serial")
public class GameViewMenuBar extends JMenuBar implements GameView {

	private GameController controller;
	
	public GameViewMenuBar(GameController controller) {
		this.controller = controller;
		
	}
	
	@Override
	public void createView() {
		this.setBounds(0, 0, GameView.WINDOW_W, GameView.MENU_H);
		
		this.add(new JMenu("Menu 1"));
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
