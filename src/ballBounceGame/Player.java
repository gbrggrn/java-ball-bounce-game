package ballBounceGame;

import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String playerName;
	private int playerScore;
	
	public Player() {
		
	}
	
	public String getName() {
		return playerName;
	}
	
	public void setName(String playerNameIn) {
		this.playerName = playerNameIn;
	}
	
	public int getPlayerScore() {
		return playerScore;
	}
	
	public void setPlayerScore(int playerScoreIn) {
		this.playerScore = playerScoreIn;
	}
}
