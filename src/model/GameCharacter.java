package model;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import view.PixelatedPlatformerView;

public class GameCharacter {
	public static final Image LOOKING_FORWARD = PixelatedPlatformerView.getImage("character22px.png");
	public static final Image LOOKING_LEFT = PixelatedPlatformerView.getImage("characterleft22px.png");
	public static final Image LOOKING_RIGHT = PixelatedPlatformerView.getImage("characterright22px.png");
	
	private static final double SCALE_FACTOR = (double) Platform.FULLSCREEN_PLATFORM_SIZE / Platform.NORMAL_PLATFORM_SIZE;
	
	private double xPos, yPos;
	private int size;
	private Image costume;
	private boolean isFullscreen;
	
	private MovementScript movementScript;
	private KeysPressed keysPressed;
	
	public GameCharacter(Platform[][] platforms, Runnable nextLevel, boolean fullscreen) {
		xPos = 66;//66
		yPos = 308;//308
		size = Platform.NORMAL_PLATFORM_SIZE;
		costume = LOOKING_FORWARD;
		movementScript = new MovementScript(this, platforms, nextLevel);
		keysPressed = new KeysPressed();
		isFullscreen = fullscreen;
		if (fullscreen)
			adaptToFullscreen(fullscreen);
		
	}
	
	//Runs the movement script and updates the character's position based on its surroundings
	public void updateCharacter() {
		movementScript.moveCharacter();
	}
	
	public void drawCharacter(Graphics g, JPanel panel) {
		if (isFullscreen) {
			g.drawImage(costume.getScaledInstance(size, size, Image.SCALE_DEFAULT), (int)(xPos * SCALE_FACTOR), (int)(yPos * SCALE_FACTOR), panel);
		}
		else {
			g.drawImage(costume.getScaledInstance(size, size, Image.SCALE_DEFAULT), (int)xPos, (int)yPos, panel);
		}
	}
	
	public void adaptToFullscreen(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
		size = isFullscreen ? Platform.FULLSCREEN_PLATFORM_SIZE : Platform.NORMAL_PLATFORM_SIZE;
		movementScript.adaptToFullscreen(isFullscreen);
	}
	
	public void keyPressed(int keyCode) {
		keysPressed.keyPressed(keyCode);
	}
	
	public void keyReleased(int keyCode) {
		keysPressed.keyReleased(keyCode);
	}
	
	public void releaseAllKeys() {
		keysPressed.releaseAllKeys();
	}
	
	public KeysPressed getKeysPressed() {
		return keysPressed;
	}
	
	
	
	public MovementScript getMovementScript() {
		return movementScript;
	}
	
	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	
	public void changeXby(double xVel) {
		xPos += xVel;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public void changeYby(double yVel) {
		yPos += yVel;
	}

	
	public Image getCostume() {
		return costume;
	}

	public void setCostume(Image costume) {
		this.costume = costume;
	}
	
	public void resetPosition() {
		xPos = 66;
		yPos = 308;
	}
	

	
	
}
