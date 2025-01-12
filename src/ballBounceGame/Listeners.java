package ballBounceGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

public class Listeners extends WindowAdapter implements ActionListener {

	private GUI gui;
	
	public Listeners(GUI guiIn) {
		this.gui = guiIn;
	}
	
	public void windowClosing(WindowEvent e) {
		gui.buildMenu();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New Game")){
			gui.getMenuFrame().setVisible(false);
			SwingUtilities.invokeLater(() -> {
				new BallGame(this).startGame();
			});
		}
		
		if(e.getActionCommand().equals("High Score")) {
			gui.getMenuFrame().setVisible(false);
			gui.buildHighScore();
		}
		
		if(e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
	}
}
