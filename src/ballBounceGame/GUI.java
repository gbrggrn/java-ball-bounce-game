package ballBounceGame;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame{
	
	private Listeners listeners;
	
	public GUI() {
		this.listeners = new Listeners(this);
	}
	
	private JFrame playerNameFrame;
	private JPanel playerNamePanel;
	private JButton okButton;
	public JTextField nameTxtField;
	
	public void buildEnterPlayerName() {
		playerNameFrame = new JFrame("Enter Player Name");
		playerNamePanel = new JPanel();
		okButton = new JButton("Save");
		nameTxtField = new JTextField(10);
		
		playerNameFrame.setSize(200, 120);
		playerNameFrame.setResizable(false);
		playerNameFrame.setLocationRelativeTo(null);
		playerNameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		okButton.addActionListener(listeners);
		okButton.setActionCommand(okButton.getText());
		
		playerNamePanel.add(nameTxtField);
		playerNamePanel.add(okButton);
		playerNameFrame.add(playerNamePanel);
		playerNameFrame.setVisible(true);
	}
	
	public JFrame getPlayerNameFrame() {
		return playerNameFrame;
	}
	
	private JFrame menuFrame;
	private JPanel menuPanel;
	private JPanel menuButtonPanel;
	private JButton newGameButton, highScoreButton, exitButton;
	
	public void buildMenu(String playerNameIn) {
		menuFrame = new JFrame("Active player: " + playerNameIn);
		menuPanel = new JPanel();
		menuButtonPanel = new JPanel();
		
		menuFrame.setSize(150, 270);
		menuFrame.setResizable(true);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		menuButtonPanel.setLayout(new BoxLayout(menuButtonPanel, BoxLayout.Y_AXIS));
		
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(listeners);
		newGameButton.setActionCommand(newGameButton.getText());
		
		highScoreButton = new JButton("High Score");
		highScoreButton.addActionListener(listeners);
		highScoreButton.setActionCommand(highScoreButton.getText());
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(listeners);
		exitButton.setActionCommand(exitButton.getText());
		
		menuButtonPanel.add(Box.createVerticalStrut(20));
		menuButtonPanel.add(newGameButton);
		menuButtonPanel.add(Box.createVerticalStrut(20));
		menuButtonPanel.add(highScoreButton);
		menuButtonPanel.add(Box.createVerticalStrut(20));
		menuButtonPanel.add(exitButton);
		
		menuPanel.add(menuButtonPanel);
		menuFrame.add(menuPanel);
		menuFrame.setVisible(true);
	}
	
	public JFrame getMenuFrame() {
		return menuFrame;
	}
	
	
	private JFrame highScoreFrame;
	private JPanel highScorePanel;
	private JTextArea highScoreArea;
	
	public void buildHighScore(String highScoreIn) {
		highScoreFrame = new JFrame("High Score");
		highScoreFrame.setSize(200,300);
		highScoreFrame.setLocationRelativeTo(null);
		highScoreFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		highScoreFrame.setResizable(true);
		highScoreFrame.addWindowListener(listeners);
		
		highScorePanel = new JPanel();
		
		highScoreArea = new JTextArea();
		highScoreArea.setSize(180, 280);
		highScoreArea.setText(highScoreIn);
		highScoreArea.setEditable(false);
		
		highScorePanel.add(highScoreArea);
		highScoreFrame.add(highScorePanel);
		highScoreFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.buildEnterPlayerName();
	}
	
}
