package be.fomp.carcassonne.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import be.fomp.carcassonne.controller.PlayerController;
import be.fomp.carcassonne.exceptions.ActionNotAllowedException;
import be.fomp.carcassonne.exceptions.InvalidPlayerException;
import be.fomp.carcassonne.model.beans.PlayerBean;
import be.fomp.carcassonne.validator.PlayerValidator;

@SuppressWarnings("serial")
public class PlayerViewImpl extends JFrame implements PlayerView, ActionListener{
	private PlayerBean model;
	private PlayerController controller;
	
	private Integer playerId = 0;
	
	private JPanel playerPanel;
		private JLabel playerIdLabel;	
		private JLabel playerNameLabel;
		private JLabel playerAgeLabel;
		private JLabel playerColorLabel;
		private JLabel playerScoreLabel;
		private JLabel playerFollowerLabel;
		
		private JTextField playerNameField;
		private JTextField playerAgeField;
		private JTextField playerColorField;
		private JTextField playerScoreField;
		private JTextField playerFollowerField;

	private JPanel playerActionPanel;
		private JButton nextPlayerButton;
		private JButton previousPlayerButton;
		private JButton addPlayerButton;
		private JButton removePlayerButton;
		private JButton editPlayerButton;
		private JButton savePlayerButton;
		private JButton debugButton;
			
	public PlayerViewImpl(PlayerBean model, PlayerController controller) {
		this.model = model;
		this.controller = controller;
		
		model.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		PlayerBean updatedObject = (PlayerBean)o;
		
		if(o != model) {
			model.deleteObserver(this);
			model = updatedObject;
			model.addObserver(this);
		}
		playerId = (Integer)arg;
		
		refresh();
	}
	
	public void refresh()
	{
		playerIdLabel		.setText(this.playerId.toString());
		
		playerNameField		.setText(model.getName());
		playerAgeField		.setText(model.getAge());
		playerColorField	.setText(model.getColor());
		playerScoreField	.setText(model.getScore());
		playerFollowerField .setText(model.getFollowers());
	}

	public void createView() {
		setTitle("Player data");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200,30,300,180);
		
			playerPanel = new JPanel(new GridLayout(5, 2));
			
				playerNameLabel = new JLabel("Name:", SwingConstants.LEFT);
				playerNameField = new JTextField();
				playerPanel.add(playerNameLabel);
				playerPanel.add(playerNameField);
				
				playerAgeLabel = new JLabel("Age:", SwingConstants.LEFT);
				playerAgeField = new JTextField();
				playerPanel.add(playerAgeLabel);
				playerPanel.add(playerAgeField);
				
				playerColorLabel = new JLabel("Color:", SwingConstants.LEFT);
				playerColorField = new JTextField();
				playerPanel.add(playerColorLabel);
				playerPanel.add(playerColorField);
				
				playerFollowerLabel = new JLabel("Followers:", SwingConstants.LEFT);
				playerFollowerField = new JTextField();
				playerPanel.add(playerFollowerLabel);
				playerPanel.add(playerFollowerField);
				
				playerScoreLabel = new JLabel("Score:", SwingConstants.LEFT);
				playerScoreField = new JTextField();
				playerPanel.add(playerScoreLabel);
				playerPanel.add(playerScoreField);
			
			playerActionPanel = new JPanel(new GridLayout(0, 3));
				addPlayerButton = new JButton("+");
				addPlayerButton.addActionListener(this);
				playerActionPanel.add(addPlayerButton);
				
				playerActionPanel.add(new JLabel());//Spacer
				
				removePlayerButton = new JButton("-");
				removePlayerButton.addActionListener(this);
				playerActionPanel.add(removePlayerButton);
				
				
				previousPlayerButton = new JButton("<<");
				previousPlayerButton.addActionListener(this);
				playerActionPanel.add(previousPlayerButton);
				
				playerIdLabel = new JLabel(this.playerId.toString());
				playerIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
				playerActionPanel.add(playerIdLabel);
				
				nextPlayerButton = new JButton(">>");
				nextPlayerButton.addActionListener(this);
				playerActionPanel.add(nextPlayerButton);
				
				
				debugButton = new JButton("debug");
				debugButton.addActionListener(this);
				playerActionPanel.add(debugButton);
				
				editPlayerButton = new JButton("E");
				editPlayerButton.addActionListener(this);
				playerActionPanel.add(editPlayerButton);
				
				savePlayerButton = new JButton("S");
				savePlayerButton.addActionListener(this);
				playerActionPanel.add(savePlayerButton);
				
		getContentPane().add(playerPanel, BorderLayout.CENTER);
		getContentPane().add(playerActionPanel, BorderLayout.SOUTH);
		//playerFrame.setVisible(true);
		refresh();
	}

	public void editMode() {
		playerNameField.setEditable(true);
		playerNameField.setBackground(Color.WHITE);
		
		playerAgeField.setEditable(true);
		playerAgeField.setBackground(Color.WHITE);
		
		playerColorField.setEditable(true);
		playerColorField.setBackground(Color.WHITE);
		
		playerScoreField.setEditable(true);
		playerScoreField.setBackground(Color.WHITE);
		
		playerFollowerField.setEditable(true);
		playerFollowerField.setBackground(Color.WHITE);
		
		savePlayerButton.setVisible(true);
		editPlayerButton.setVisible(false);
		
		playerActionPanel.remove(editPlayerButton);
		playerActionPanel.add(savePlayerButton);
	}
	
	public void viewMode() {
		playerNameField.setEditable(false);
		playerNameField.setBackground(playerActionPanel.getBackground());
		
		playerAgeField.setEditable(false);
		playerAgeField.setBackground(playerActionPanel.getBackground());
		
		playerColorField.setEditable(false);
		playerColorField.setBackground(playerActionPanel.getBackground());
		
		playerScoreField.setEditable(false);
		playerScoreField.setBackground(playerActionPanel.getBackground());
		
		playerFollowerField.setEditable(false);
		playerFollowerField.setBackground(playerActionPanel.getBackground());
		
		savePlayerButton.setVisible(false);
		editPlayerButton.setVisible(true);
		

		playerActionPanel.remove(savePlayerButton);
		playerActionPanel.add(editPlayerButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		try {
			if        (source == nextPlayerButton) {
				controller.doSelectNextPlayer();
			} else if (source == previousPlayerButton) {
				controller.doSelectPreviousPlayer();
			} else if (source == addPlayerButton) {
				controller.doAddPlayer();
			} else if (source == removePlayerButton) {
				controller.doRemovePlayer();
			} else if (source == editPlayerButton) {
				controller.doEditPlayer();
			} else if (source == savePlayerButton) {
				model.setName(playerNameField.getText());
				model.setAge(playerAgeField.getText());
				model.setColor(playerColorField.getText());
				model.setFollowers(playerFollowerField.getText());
				model.setScore(playerScoreField.getText());

				PlayerValidator.validatePlayer(model);
				controller.doSavePlayer(model);
				
			} else if (source == debugButton) {
				System.out.println("Model = "+model);
			}
		} catch (InvalidPlayerException ipe) {
			JOptionPane.showMessageDialog(this, ipe.getMessage(), null, JOptionPane.ERROR_MESSAGE);
		} catch (ActionNotAllowedException ano) {
			JOptionPane.showMessageDialog(this, ano.getMessage(), null, JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
