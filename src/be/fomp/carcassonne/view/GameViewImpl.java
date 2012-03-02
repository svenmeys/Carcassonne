package be.fomp.carcassonne.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import be.fomp.carcassonne.controller.GameController;
import be.fomp.carcassonne.model.beans.GameBean;
import be.fomp.carcassonne.view.panels.GameViewControlPanel;
import be.fomp.carcassonne.view.panels.GameViewMapPanel;
import be.fomp.carcassonne.view.panels.GameViewMenuBar;
import be.fomp.carcassonne.view.panels.GameViewScorePanel;

@SuppressWarnings("serial")
public class GameViewImpl extends JFrame implements GameView, ActionListener, ChangeListener{
	
	private GameBean model;
	private GameController controller;

	private GameViewMenuBar 		gameMenu;
	private GameViewMapPanel 		gameMapPanel;
	private JScrollPane				gameScrollPane;
	private JPanel panel;
	private GameViewScorePanel 		scorePanel;
	private GameViewControlPanel 	controlPanel;

	private JSlider					zoomSlider;
	
	public GameViewImpl(GameBean model, GameController controller)
	{
		this.model = model;
		this.controller = controller;
		model.addObserver(this);
	}
	
	@Override
	public void createView() {
		this.setTitle("Carcassonne");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocation(50,50);
		this.setSize(new Dimension(WINDOW_W,WINDOW_H));
		
		gameMenu 	 = new GameViewMenuBar		(controller);
		gameMapPanel = new GameViewMapPanel		(model, controller);
		scorePanel 	 = new GameViewScorePanel	(model, controller);
		controlPanel = new GameViewControlPanel	(model, controller);
		
		gameMenu.createView();
		gameMapPanel.createView();
		scorePanel.createView();
		controlPanel.createView();

		gameScrollPane = new JScrollPane(gameMapPanel);
		gameScrollPane.setSize(GAME_AREA_W, GAME_AREA_H);
		gameScrollPane.setLocation(0, 0);
		gameScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		gameScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	
		panel=new JPanel();  
		panel.setLayout(null);
		panel.setBackground(Color.WHITE); 
        panel.setSize(new Dimension(GAME_AREA_W,GAME_AREA_H));
        panel.setLocation(GAME_AREA_X, GAME_AREA_Y);
          
        panel.add(gameScrollPane);
        
        zoomSlider = new JSlider(25, 300, 100);
        zoomSlider.addChangeListener(this);
        zoomSlider.setSize(new Dimension(GAME_AREA_W, 50));
        zoomSlider.setPaintLabels(true);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setMajorTickSpacing(25);
        zoomSlider.setMinorTickSpacing(5);
        zoomSlider.setLocation(GAME_AREA_X, GAME_AREA_Y - 50);
        this.getContentPane().add(zoomSlider);
        
		this.getContentPane().add(gameMenu);
		this.getContentPane().add(panel);
		this.getContentPane().add(scorePanel);
		this.getContentPane().add(controlPanel);
	
	}
	
	@Override
	public void refresh(){
		// TODO Auto-generated method stub
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
		gameMapPanel.update(o, arg);
		scorePanel.update(o, arg);
		controlPanel.update(o,arg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == zoomSlider){
			controller.doChangeScaling((double)zoomSlider.getValue()/100.0);
		}
	}
}
