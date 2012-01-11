package be.fomp.carcassonne.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import be.fomp.carcassonne.controller.GameController;
import be.fomp.carcassonne.exceptions.ActionNotAllowedException;
import be.fomp.carcassonne.model.GameBean;
import be.fomp.carcassonne.utils.Ruleset;
import be.fomp.carcassonne.view.GameView;

@SuppressWarnings("serial")
public class GameViewMapPanel extends JPanel implements GameView {
	
	private GameBean model;
	private GameController controller;
	
	private int mapWidth = Ruleset.MAX_TILES_PER_ROW * TILE_W;
	private int mapHeight = Ruleset.MAX_TILES_PER_COL * TILE_H;

	private GameViewTilePanel[][] tilePanels = 
			new GameViewTilePanel[Ruleset.MAX_TILES_PER_ROW][Ruleset.MAX_TILES_PER_COL];
	
	public GameViewMapPanel(GameBean model, GameController controller) {
		this.model = model;
		this.controller = controller;
	}
	
	@Override
	public void createView() {
		setAutoscrolls(true);
		setLayout(null);
		setBackground(Color.WHITE);
		//setLocation(GAME_AREA_X,GAME_AREA_Y);
		setPreferredSize(new Dimension(mapWidth, mapHeight));
		setLocation(-(mapWidth - GAME_AREA_W)/2, -(mapHeight - GAME_AREA_H)/2);
		setBackground(Color.WHITE);
		for(int x=0; x < Ruleset.MAX_TILES_PER_ROW; x++)
			for(int y=0; y< Ruleset.MAX_TILES_PER_COL; y++){
				tilePanels[x][y] = new GameViewTilePanel(null, controller);
				tilePanels[x][y].addMouseListener(this.mouseListener);
				tilePanels[x][y].setLocation(x * TILE_W, y * TILE_H);
				tilePanels[x][y].createView();
				this.add(tilePanels[x][y]);
			}
		refresh();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		GameBean updatedObject = (GameBean)o;
		
		if(o != model) {
			if(model != null) { 
				model.deleteObserver(this);
				if(model.getMap() != null)
					model.getMap().deleteObserver(this);
			}
			model = updatedObject;
			if(model != null) {
				model.addObserver(this);
				if(model.getMap() != null)model.getMap().addObserver(this);
			}
		}
		if(model != null && model.getMap() != null)
		{
			for(int x=0; x < Ruleset.MAX_TILES_PER_ROW; x++)
				for(int y=0; y< Ruleset.MAX_TILES_PER_COL; y++)
					tilePanels[x][y].update((Observable)model.getMap().getMap()[x][Ruleset.MAX_TILES_PER_COL - 1 - y], arg);
		
		int activePlayerId = model.getRound()%model.getPlayers().length; 
		if("placing_card".equalsIgnoreCase(model.getPlayers()[activePlayerId].getState()))
		if(!(model.getMap().getActiveXPos() == Ruleset.MAX_TILES_PER_ROW/2 && model.getMap().getActiveYPos() == Ruleset.MAX_TILES_PER_COL/2))
			tilePanels[model.getMap().getActiveXPos()][Ruleset.MAX_TILES_PER_COL - 1 - model.getMap().getActiveYPos()].update((Observable)model.getActiveTile(),this);
		}
		refresh();
	}
	
	@Override
	public void refresh() {
		if(model != null && model.getMap() != null) repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private MouseListener mouseListener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			Object source = e.getSource();
			switch(e.getButton()){
				case 1:
					GameViewTilePanel tilePanel;
					if(source instanceof GameViewTilePanel) {
						try {
							tilePanel = (GameViewTilePanel)source;
						
		
							int xPos, yPos;
							xPos = tilePanel.getX() / TILE_W;
							yPos = tilePanel.getY() / TILE_H;
							System.out.println("Clicked Tile at ("+xPos+","+yPos+")");
							
							//if(GameMapValidator.checkFreeCoordinates(model, xPos, yPos))
								controller.doClickTile(xPos, yPos);
						
						} catch(ActionNotAllowedException ana) {
							JOptionPane.showMessageDialog(null, ana.getMessage(), null, JOptionPane.INFORMATION_MESSAGE);
						}
					}
				break;
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {}	
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e){
			switch (e.getButton()){
			case 5:if(getX() > -mapWidth + GAME_AREA_W) setLocation(getX()-1, getY());
					break;
			case 4:if(getX() < 0)	setLocation(getX()+1, getY());
					break;
			}
		}
	};
	
}
