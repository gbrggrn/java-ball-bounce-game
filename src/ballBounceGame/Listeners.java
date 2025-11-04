package ballBounceGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class Listeners extends WindowAdapter implements ActionListener {

	private GUI gui;
	private String playerName;
	String fileName = "highScore.ser";
	File highScoreFile = new File(fileName);
	
	public Listeners(GUI guiIn) {
		this.gui = guiIn;
	}
	
	public void windowClosing(WindowEvent e) {
		gui.buildMenu(playerName);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New Game")){
			gui.getMenuFrame().setVisible(false);
			SwingUtilities.invokeLater(() -> {
				new BallGame(this, playerName).startGame();
			});
		}
		
		if(e.getActionCommand().equals("High Score")) {
			gui.getMenuFrame().setVisible(false);
			gui.buildHighScore(loadHighScore());
		}
		
		if(e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
		if(e.getActionCommand().equals("Save")) {
			playerName = gui.nameTxtField.getText();
			gui.getPlayerNameFrame().setVisible(false);
		    gui.buildMenu(playerName);
		}
	}
	
	public String loadHighScore() {
		String line = null;
		StringBuilder highScoreString = new StringBuilder();
		
		try (BufferedReader read = new BufferedReader(new FileReader(highScoreFile))) {
			while ((line = read.readLine()) != null) {
				highScoreString.append(line).append("\n");
			}
			
			return highScoreString.toString();
		} catch (IOException ioe) {
		  return ioe.getMessage();
		}
	}
}
