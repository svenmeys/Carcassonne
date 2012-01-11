package be.fomp.carcassonne.view.panels;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import be.fomp.carcassonne.controller.GameController;
import be.fomp.carcassonne.exceptions.ActionNotAllowedException;
import be.fomp.carcassonne.exceptions.TileFactoryException;
import be.fomp.carcassonne.model.GameBean;
import be.fomp.carcassonne.model.TileBean;
import be.fomp.carcassonne.utils.TileUtils;
import be.fomp.carcassonne.view.GameView;

@SuppressWarnings("serial")
public class GameViewControlPanel extends JPanel implements GameView {

	private GameBean model;
	private GameController controller;
	
	//GameState init
	private JPanel initControlPanel;
		private JLabel gameNameLabel;
		private JTextField gameNameField;
	
		private JButton editPlayersButton;
		private JButton startGameButton;
	
	
	//GameState running
	private JPanel runningControlPanel;
		private GameViewTilePanel tilePanel;
		private JButton rotateTileButton;
		private JButton placeTileButton;
		private JButton placeFollowerButton;
		private JButton skipButton;
		
	//GameState ended
	private JPanel endedControlPanel;
	
	
	//keeps track of game status chenges in order to change layout
	private String currentGameStatus;
	
	public GameViewControlPanel(GameBean model, GameController controller) {
		this.model = model;
		this.controller = controller;
	}

	@Override
	public void createView() {
		this.setLayout(null);
		this.setSize( CONTROL_AREA_W, CONTROL_AREA_H);
		this.setLocation(CONTROL_AREA_X, CONTROL_AREA_Y);
		this.setBorder(BorderFactory.createTitledBorder("Controls"));
		
		initControlPanel = new JPanel();
			initControlPanel.setLayout(null);
			initControlPanel.setLocation(0,0);
			initControlPanel.setSize(this.getSize());
			initControlPanel.setBorder(this.getBorder());
			initControlPanel.setBackground(this.getBackground());
			
			gameNameLabel = new JLabel();
				gameNameLabel.setText("Game name: ");
				gameNameLabel.setBounds(20,20,100,20);
			initControlPanel.add(gameNameLabel);
			
			gameNameField = new JTextField(model.getName());
				gameNameField.setBounds(120,20,100,20);
			initControlPanel.add(gameNameField);
				
			editPlayersButton = new JButton("Edit Players");
				editPlayersButton.setBounds(CONTROL_AREA_W - 140, CONTROL_AREA_H - 50, 120, 30);
				editPlayersButton.addActionListener(this);
			initControlPanel.add(editPlayersButton);
			
			startGameButton = new JButton("Start Game");
				startGameButton.setBounds(20, CONTROL_AREA_H - 50, 120,30);
				startGameButton.addActionListener(this);
			initControlPanel.add(startGameButton);
			
		this.add(initControlPanel);
			
		runningControlPanel = new JPanel();
			runningControlPanel.setLayout(null);
			runningControlPanel.setLocation(0, 0);
			runningControlPanel.setSize(this.getSize());
			runningControlPanel.setBorder(this.getBorder());
			runningControlPanel.setBackground(this.getBackground());
			
			tilePanel = new GameViewTilePanel(model.getActiveTile(), controller);
				tilePanel.setScale(2);
				tilePanel.setLocation((CONTROL_AREA_W-TILE_W*2)/2, (CONTROL_AREA_H - TILE_H*2)/2);
				tilePanel.createView();
				tilePanel.addMouseListener(mouseListener);
			runningControlPanel.add(tilePanel);
			
			rotateTileButton = new JButton("rotate");
				rotateTileButton.setSize(100, 30);
				rotateTileButton.setLocation(tilePanel.getX() + tilePanel.getWidth()/2 - 50, tilePanel.getY() + tilePanel.getHeight());
				rotateTileButton.addActionListener(this);
			runningControlPanel.add(rotateTileButton);
			
			placeTileButton = new JButton("place tile");
				placeTileButton.setSize(120, 30);
				placeTileButton.setLocation(CONTROL_AREA_W/2 - 60, CONTROL_AREA_H - 40);
				placeTileButton.addActionListener(this);
			runningControlPanel.add(placeTileButton);
			
			placeFollowerButton = new JButton("place follower");
				placeFollowerButton.setSize(120,30);
				placeFollowerButton.setLocation(CONTROL_AREA_W/2 - 60, CONTROL_AREA_H - 40);
				placeFollowerButton.addActionListener(this);
			runningControlPanel.add(placeFollowerButton);
			
			skipButton = new JButton("skip");
				skipButton.setSize(80,30);
				skipButton.setLocation(CONTROL_AREA_W/2 + 80, CONTROL_AREA_H - 40);
				skipButton.addActionListener(this);
			runningControlPanel.add(skipButton);
			
		this.add(runningControlPanel);
		
		
		endedControlPanel = new JPanel();
			endedControlPanel.setLayout(null);
			endedControlPanel.setLocation(0,0);
			endedControlPanel.setSize(this.getSize());
			endedControlPanel.setBorder(this.getBorder());
			endedControlPanel.setBackground(this.getBackground());
	
		this.add(endedControlPanel);	
		
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
		if(model.getActiveTile() != null) {
			TileBean activeTile = model.getActiveTile();
			activeTile.setFollowerLocation(model.getMap().getActiveFollowerLocation());
			activeTile.setFollowerOwner(model.getPlayers()[model.getRound()%model.getPlayers().length].getColor());
			this.tilePanel.update((Observable)model.getActiveTile(), arg);
		}
		refresh();
	}

	@Override
	public void refresh() {
		String currentState = model.getState();
		
		System.out.println("Updating view to state= " + currentState);
		this.initControlPanel	.setVisible("init"    .equalsIgnoreCase(currentState));
		this.runningControlPanel.setVisible("running" .equalsIgnoreCase(currentState));
		if(this.runningControlPanel.isVisible()) {
			String playerState = model.getPlayers()[model.getRound()%model.getPlayers().length].getState();
			this.placeTileButton    .setVisible("placing_card"    .equalsIgnoreCase(playerState));
			this.rotateTileButton   .setVisible(placeTileButton.isVisible());
			this.placeFollowerButton.setVisible("placing_follower".equalsIgnoreCase(playerState));
			this.skipButton.setVisible(this.placeFollowerButton.isVisible());
		}
		this.endedControlPanel	.setVisible("ended".equalsIgnoreCase(currentState));
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		try {
			if("init".equalsIgnoreCase(model.getState()))
			{
				if(startGameButton == source) {
					System.out.println("StartGame");
					controller.doStartGame(gameNameField.getText());
				} else if (editPlayersButton == source) {
					System.out.println("EditPlayers");
					controller.doEditPlayers();
				} 
			}
			else if("running".equalsIgnoreCase(model.getState()))
			{
				if (rotateTileButton == source) {
					System.out.println("RotateTile");
					controller.doRotateTile();
				} else if (placeTileButton == source) {
					//TODO move validation to view layer
					controller.doPlaceTile();
				} else if (placeFollowerButton == source) {
					//TODO move validation to view layer
					controller.doPlaceFollower();
				} else if (skipButton == source) {
					//TODO move validation to view layer
					controller.doNextRound();
				}
			}
			else if("ended".equalsIgnoreCase(model.getState()))
			{
				
			}
		} catch (ActionNotAllowedException ano) {
			JOptionPane.showMessageDialog(this, ano.getMessage(), null, JOptionPane.INFORMATION_MESSAGE);
		} catch(TileFactoryException tfe) {
			JOptionPane.showMessageDialog(this, tfe.getMessage(), null, JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private MouseListener mouseListener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}	
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e) {
			if("placing_follower".equalsIgnoreCase(model.getPlayers()[model.getRound() % model.getPlayers().length].getState())) {
				Object source = e.getSource();
				GameViewTilePanel tilePanel;
				if(source instanceof GameViewTilePanel) {
					try {
						tilePanel = (GameViewTilePanel)source;
						int width = tilePanel.getWidth();
						int height = tilePanel.getHeight();
						int xPos = e.getX();
						int yPos = e.getY();
						
						int location = TileUtils.getCoordinateLocation(xPos, yPos, width, height);
						
						controller.doClickFollowerLocation(location);
					
					} catch(ActionNotAllowedException ana) {
						JOptionPane.showMessageDialog(null, ana.getMessage(), null, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	};
}
