package be.fomp.carcassonne.view.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import be.fomp.carcassonne.controller.GameController;
import be.fomp.carcassonne.game.objects.TileImageFactory;
import be.fomp.carcassonne.model.TileBean;
import be.fomp.carcassonne.model.TileBeanImpl;
import be.fomp.carcassonne.utils.Color;
import be.fomp.carcassonne.view.GameView;

@SuppressWarnings("serial")
public class GameViewTilePanel extends JPanel implements GameView {
	
	private static Border border = BorderFactory.createLineBorder(java.awt.Color.WHITE, 1);
	
	private BufferedImage tileImage;
	private int scale = 1;
	
	private TileBean model;
	private GameController controller;
	
	public GameViewTilePanel(TileBean model, GameController controller) {
		if(model == null) this.model = new TileBeanImpl();
		else this.model = model;
		this.controller = controller;
	}
	@Override
    public void paintComponent(Graphics g) {
		if(model == null) {
			g.setColor(TILE_BACKGROUND);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		else {
			drawTile(g);
			drawFollower(g);		
		}
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
	@Override
	public void createView() {
		setLayout(null);
		setSize(TILE_W * scale, TILE_H * scale);
		//setBorder(border);
		setBackground(TILE_BACKGROUND);
		refresh();
	}

	@Override
	public void refresh() {
		if(model != null && model.getId() != -1) {
			if(tileImage != TileImageFactory.getTileImage(model.getId()))
				tileImage = TileImageFactory.getTileImage(model.getId());
		}
		//else tileImage = TileImageFactory.getTileImage(-1);
		
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		TileBean updatedObject = (TileBean)o;
		
		if(o != model) {
			if(model != null) model.deleteObserver(this);
			model = updatedObject;
			if(model != null) model.addObserver(this);
		}
		refresh();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void drawTile(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		if(model != null && model.getId() == -1){
			g.fillRect(0, 0, TILE_W * scale, TILE_H * scale);
			return;
		}
		double rotation = model==null?0:Math.toRadians(model.getRotation());
		double tx = 0;
		double ty = 0;

		if(model != null)
		switch(model.getRotation()){
			case 90: 					  ty = TILE_H * scale;break;
			case 180:tx = TILE_W * scale; ty = TILE_H * scale;break;
			case 270:tx = TILE_W * scale;					  break;
		}
		
		g2d.translate(tx, ty);
		g2d.rotate(-rotation); //Rotation of tiles is mapped CCW
		
		g2d.drawImage(tileImage, 
    			0, 0, TILE_W * scale, TILE_H * scale, 
    			0, 0, tileImage.getWidth(), tileImage.getHeight(),
    			null
		);
		
		//Revert rotation
		g2d.rotate(rotation);
		g2d.translate(-tx, -ty);
		
	}
	
	private void drawFollower(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		if(model != null && model.getFollowerLocation() != null && model.getFollowerLocation() >= 0) {
			int loc = model.getFollowerLocation();
			double xPos = ((double)this.getWidth() * 
					   ((loc==0 || loc==8) ? 0.3  : 
				   	   (loc==1 || loc== 7 || loc == 12) ? 0.5  : 
					   (loc==2 || loc==6) ? 0.7 : 
					   (loc==3 || loc==5) ? 0.9 : 
					   (loc==4) ? 0.9 : 
					   (loc==9 || loc==11) ? 0.1 : 
					   (loc==10) ? 0.1:0)) - 2.5;
			
			double yPos = ((double)this.getWidth() * 
					   ((loc==0 || loc==2) ? 0.1  : 
					   (loc==10 || loc== 12 || loc == 4) ? 0.5  : 
					   (loc==5 || loc==9) ? 0.7 : 
					   (loc==6 || loc==8) ? 0.9 : 
					   (loc==1) ? 0.1 : 
					   (loc==11 || loc==3) ? 0.3 : 
					   (loc==7) ? 0.9:0)) - 4;
			
			g2d.setColor(Color.getColor(model.getFollowerOwner()));
			xPos -= 3;
			Ellipse2D.Double circleCenter = new Ellipse2D.Double(xPos, yPos, 5, 8);
			Ellipse2D.Double circleHead = new Ellipse2D.Double(xPos, yPos-5, 5, 5);
			Ellipse2D.Double circleArm1 = new Ellipse2D.Double(xPos-3, yPos, 6, 3);
			Ellipse2D.Double circleArm2 = new Ellipse2D.Double(xPos+3, yPos, 6, 3);
			Ellipse2D.Double circleLeg1 = new Ellipse2D.Double(xPos-1, yPos+5, 2, 5);
			Ellipse2D.Double circleLeg2 = new Ellipse2D.Double(xPos+3, yPos+5, 2, 5);
			g2d.fill(circleCenter);
			g2d.fill(circleHead);
			g2d.fill(circleArm1);
			g2d.fill(circleArm2);
			g2d.fill(circleLeg1);
			g2d.fill(circleLeg2);
		}
	}
}
