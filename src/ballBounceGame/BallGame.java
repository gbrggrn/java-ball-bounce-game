package ballBounceGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class BallGame extends JPanel implements Runnable, KeyListener{
//class reference variable
	private Listeners listeners;
	
//Game build variables
	private JFrame gameFrame;
	private boolean running;
	private Random rand;
	private int gameFrameWidth = 800;
	private int gameFrameHeight = 700;
	private int runCounter;
	private int newLevelFrequency = 700;
	private int randFrequency = 350;
	private boolean leftKeyPressed = false;
	private boolean rightKeyPressed = false;
	private boolean startText = false;
	private boolean gameOverText = false;
	
//High Score variables
	private int playerScore = 0;
	private Player activePlayer;
	private String playerName;
	private List <Player> highScoreList;
	String fileName = "highScore.ser";
	File highScore = new File(fileName);
	
//Player variables
	private int playerWidth = 120;
	private int playerHeight = 20;
	private int playerPosX = (gameFrameWidth/2) - playerWidth/2;
	private int playerPosYLow = gameFrameHeight;
	private int playerPosYHighLeft = gameFrameHeight;
	private int playerPosYHighRight = gameFrameHeight;
	private int[] playerVertexX = {playerPosX + playerWidth, playerPosX, playerPosX, playerPosX + playerWidth};
	private int[] playerVertexY = {playerPosYHighRight - playerHeight, playerPosYHighLeft - playerHeight, playerPosYLow, playerPosYLow};
	private int playerVertexPoints = 4;
	private int playerSpeed = 15;
	private int tiltValue = 20;
	
//Ball variables
	private int ballWidth = 20;
	private int ballRadius = ballWidth/2;
	private int ballHeight = 20;
	private int ballPosX = 400;
	private int ballPosY = 150;
	private int ballSpeedX = 0;
	private int ballSpeedY = 1;
	private int gravity = 1;
	private int initialBallSpeedY = 5;
	
	public BallGame(Listeners listenersIn) {
		listeners = listenersIn;
		setPreferredSize(new Dimension(gameFrameWidth, gameFrameHeight));
		setBackground(Color.black);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		rand = new Random();
		
		activePlayer = new Player();
		
		shouldCreateSaveFile(highScore);
		
		running = true;
		
		new Thread(this).start();
	}
	
	public void startGame() {
		gameFrame = new JFrame();
		gameFrame.addWindowListener(listeners);
		gameFrame.getContentPane().add(this);
		gameFrame.pack();
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBallGame(g);
	}
	
	public void drawBallGame(Graphics g) {
		g.setColor(Color.white);
//draw ball
		g.fillArc(ballPosX - ballWidth/2, ballPosY, ballWidth, ballHeight, 360, 360);
//draw player
		g.drawPolygon(playerVertexX, playerVertexY, playerVertexPoints);
			g.fillPolygon(playerVertexX, playerVertexY, playerVertexPoints);
//draw start text
		if(startText) {
			g.setColor(Color.yellow);
			g.drawString("Bounce the Ball!", gameFrameWidth/2, gameFrameHeight/2);
		}
		
		if(gameOverText) {
			g.setColor(Color.red);
			g.drawString("You bounced the ball " + Integer.toString(playerScore) + " times!", gameFrameWidth/2, gameFrameHeight/2 + 50);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int pressedKey = e.getKeyCode();
		if(pressedKey == KeyEvent.VK_LEFT) {
			leftKeyPressed = true;
		}
		
		if(pressedKey == KeyEvent.VK_RIGHT) {
			rightKeyPressed = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int keyReleased = e.getKeyCode();
		if(keyReleased == KeyEvent.VK_LEFT) {
			leftKeyPressed = false;
		}
		
		if(keyReleased == KeyEvent.VK_RIGHT) {
			rightKeyPressed = false;
		}
	}
	
	private void updatePlayerPosition() {
		if(leftKeyPressed) {
			playerPosX -= playerSpeed;
			if(playerPosX < 0) {
				playerPosX = 0;
			}
		} 
		
		if(rightKeyPressed){
			playerPosX += playerSpeed;
			if(playerPosX > gameFrameWidth - playerWidth) {
				playerPosX = gameFrameWidth - playerWidth;
			}
		}
		updateVertices();
	}
		
	
	public void updateVertices() {
		playerVertexX[0] = playerPosX + playerWidth;
		playerVertexX[1] = playerPosX;
		playerVertexX[2] = playerPosX;
		playerVertexX[3] = playerPosX + playerWidth;
		
		playerVertexY[0] = playerPosYHighRight - playerHeight;
		playerVertexY[1] = playerPosYHighLeft - playerHeight;
		playerVertexY[2] = playerPosYLow;
		playerVertexY[3] = playerPosYLow;
	}
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		long startPause = 2500;
		
		while(running == true) {
			
			if(System.currentTimeMillis() - startTime < startPause) {
				startText = true;
			} else {
				startText = false;
				moveBall();
				updatePlayerPosition();
				
				if(runCounter % randFrequency == 0) {
					if(shouldGenerateTilt() == true) {
						tiltLeftOrRight();
					}
				}
				
				repaint();
				
				try{
					Thread.sleep(15);
				} catch (InterruptedException ie) {
					
				}
				
				runCounter++;
				if(runCounter > Integer.MAX_VALUE) {
					runCounter = 501;
				}
				
				if(runCounter % newLevelFrequency == 0) { System.out.println("Level Change");
					randFrequency = -50;
				}
			}
		}
	}
	
	public void moveBall() {
	    ballPosX -= ballSpeedX;
	    ballPosY -= ballSpeedY;
	    initialBallSpeedY = 35;
	    
	    if(ballPosY - ballRadius > gameFrameHeight) {
	    	gameOverText = true;
	    	running = false;
	    	//saveHighScore();
	    	}
	    	
	    if(ballPosX - ballRadius <= 0) {
			ballSpeedX = -ballSpeedX;
	    }
		
		if(ballPosX + ballRadius >= gameFrameWidth) {
			ballSpeedX = -ballSpeedX;
		}
	    
	    if(ballPosY + ballRadius >= playerPosYLow - playerHeight &&
	       ballPosY - ballRadius <= playerPosYLow + playerHeight &&
	       ballPosX + ballRadius >= playerPosX &&
	       ballPosX - ballRadius <= playerPosX + playerWidth){
	    	   handlePlayerCollision();
	    	   playerScore++;
	       }
	    
	    ballSpeedY -= gravity;
	    initialBallSpeedY -= gravity;
	}
	
	public boolean shouldGenerateTilt() {
		int shouldGenerateTilt = rand.nextInt(10);
		
		if(shouldGenerateTilt < 5) {
			return true;
		} else {
			return false;
		}
	}
	
	public void tiltLeftOrRight() { System.out.println("tiltLeftOrRight");
		int leftOrRight = rand.nextInt(3);
		
		if(leftOrRight == 1) {
			playerPosYHighRight = gameFrameHeight;
			playerPosYHighLeft = gameFrameHeight + tiltValue;
			updateVertices();
			System.out.println("Tilt Left");
		} else if(leftOrRight == 2){
			playerPosYHighLeft = gameFrameHeight;
			playerPosYHighRight = gameFrameHeight + tiltValue;
			updateVertices();
			System.out.println("Tilt Right");
		} else {
			playerPosYHighRight = gameFrameHeight;
			playerPosYHighLeft = gameFrameHeight;
			System.out.println("No Tilt");
		}
	}

	public void handlePlayerCollision() {
		double playerAngle = Math.atan2(playerPosYHighRight - playerPosYHighLeft, playerWidth);
		
		double ballAngle = Math.atan2(ballSpeedY, ballSpeedX);
		
		double angleOfReflection = 2 * playerAngle - ballAngle;
		
		double speed = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
		
		ballSpeedX = (int) (speed * Math.cos(angleOfReflection));
		ballSpeedY = (int) (initialBallSpeedY * Math.sin(angleOfReflection));
	}
	
	public void shouldCreateSaveFile(File highScore) {
		if(highScore.exists()) {
			System.out.println("High Score file already exists...");
		} else {
			try {
				boolean fileCreated = highScore.createNewFile();
				if(fileCreated) {
					System.out.println("New savefile created: " + highScore.getAbsolutePath());
				} else {
					System.out.println("Failed to create new savefile");
					return;
				}
			} catch (IOException ioe) {
				System.out.println("Failed to create new savefile");
			}
		}
	}
	
	public void shouldSaveHighScore(File highScore) {
		
		
	}
	
	public void loadHighScore(File highScore) {
		
		try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(highScore))) {
			
		} catch (IOException ioe) {
			
		}
	}

}
