package be.fomp.carcassonne.view.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import be.fomp.carcassonne.controller.GameController;
import be.fomp.carcassonne.model.beans.GameBean;
import be.fomp.carcassonne.model.beans.PlayerBean;
import be.fomp.carcassonne.utils.Color;
import be.fomp.carcassonne.utils.Ruleset;
import be.fomp.carcassonne.view.GameView;



@SuppressWarnings("serial")
public class GameViewScorePanel extends JPanel implements GameView {
	
	private GameBean model;
	private GameController controller;
	
	private List<JLabel> playerNameLabels;
	private List<JLabel> playerScoreLabels;
	
	public GameViewScorePanel(GameBean model, GameController controller) {
		this.model = model;
		this.controller = controller;
		
		model.addObserver(this);
	}
	
	@Override
	public void createView() {
		setLayout(new GridLayout(0,2));
		setBackground(java.awt.Color.LIGHT_GRAY);
		setBorder(BorderFactory.createTitledBorder("Scores"));
		((TitledBorder)getBorder()).setTitleColor(java.awt.Color.RED);
		
		setSize(GameView.SCORE_AREA_W, GameView.SCORE_AREA_H);
		setLocation(SCORE_AREA_X, SCORE_AREA_Y);
		
		this.playerNameLabels = new ArrayList<JLabel>();
		this.playerScoreLabels = new ArrayList<JLabel>();
		
		for(int i=0; i< Ruleset.MAX_PLAYERS;i++){
			JLabel playerNameLabel = new JLabel();
			JLabel playerScoreLabel = new JLabel();
			this.playerNameLabels.add(playerNameLabel);
			this.playerScoreLabels.add(playerScoreLabel);
			
			this.add(playerNameLabels.get(i));
			this.add(playerScoreLabels.get(i));
		}
		this.refresh();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		GameBean updatedObject = (GameBean)o;
		
		if(o != model) {
			model.deleteObserver(this);
			model = updatedObject;
			model.addObserver(this);
		}
		refresh();	
	}
	
	@Override
	public void refresh() {
		int index = 0;
		for(PlayerBean player: model.getPlayers()){
			java.awt.Color textColor = Color.getColor(player.getColor());
			
			playerNameLabels .get(index).setForeground(textColor);
			playerScoreLabels.get(index).setForeground(textColor);
			
			playerNameLabels .get(index).setText(player.getName());
			playerScoreLabels.get(index).setText(player.getScore());
			
			index++;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
