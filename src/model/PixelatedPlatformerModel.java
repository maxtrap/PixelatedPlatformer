package model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import controller.LevelData;
import model.orangeplatform.Level18Task;
import model.orangeplatform.Level19Task;
import model.orangeplatform.Level20Task;
import view.FullscreenObserver;
import view.GameDisplay;
import view.PixelatedPlatformerDisplay;

public class PixelatedPlatformerModel implements FullscreenObserver {
	private static final String[][] LEVELS = LevelData.LEVELS;
	
	private boolean gameStarted;
	
	private List<DisplayUpdateObserver> displayUpdateObservers;
	
	private boolean isFullscreen;
	private Platform[][] platforms;
	private int currentLevel;
	private GameCharacter character;
	private Sounds sounds;
	
	private Timer updateFrameTimer;
	
	public PixelatedPlatformerModel() {
		displayUpdateObservers = new ArrayList<>();
		
		
	}
	
	
	public void startGame() {
		gameStarted = true;
		
		currentLevel = 0;
		
		platforms = new Platform [LEVELS[currentLevel].length] [LEVELS[currentLevel][0].length()];
		createPlatformsForCurrentLevel();
		
		character = new GameCharacter(platforms, this::nextLevel, isFullscreen);
		character.resetPosition();
		
		
		updateFrameTimer = new Timer(16, action -> {
			character.updateCharacter();
			notifyDisplayUpdateObservers(new PixelatedPlatformerDisplay(platforms, character, sounds));
			});
		
		
		if (sounds != null) {
			sounds.stop();
		}
		sounds = new Sounds(isFullscreen);
		
		notifyDisplayUpdateObservers(new PixelatedPlatformerDisplay(platforms, character, sounds));
		updateFrameTimer.start();
		//Creates a timer that will update the display at (hopefully) 60 fps
	}
	
	
	
	
	private void createPlatformsForCurrentLevel() {
		for (int i = 0; i < LEVELS[currentLevel].length; i++) {
			String rowData = LEVELS[currentLevel][i];
			for (int j = 0; j < rowData.length(); j++) {
				
				switch (rowData.charAt(j)) {
					case ' ' -> platforms[i][j] = new Platform(j, i, PlatformType.NO_PLATFORM);
					case '1' -> platforms[i][j] = new Platform(j, i, PlatformType.BLACK_PLATFORM);
					case '2' -> platforms[i][j] = new Platform(j, i, PlatformType.RED_PLATFORM);
					case '3' -> platforms[i][j] = new Platform(j, i, PlatformType.GOAL_PLATFORM);
					case '4' -> platforms[i][j] = new Platform(j, i, PlatformType.BOUNCY_PLATFORM);
					case '5' -> platforms[i][j] = new Platform(j, i, PlatformType.MISCELLANEOUS_PLATFORM);
				}
				
			}
		}
		if (isFullscreen)
			adaptToFullscreen();
	}
	
	
	public void registerDisplayUpdateObserver(DisplayUpdateObserver observer) {
		displayUpdateObservers.add(observer);
	}
	
	public void notifyDisplayUpdateObservers(GameDisplay display) {
		displayUpdateObservers.forEach(observer -> observer.displayChanged(display));
	}

	@Override
	public void fullscreenChanged(boolean fullscreen) {
		isFullscreen = fullscreen;
		if (platforms != null) {
			adaptToFullscreen();
		}
		
	}
	
	public void adaptToFullscreen() {
		for (Platform[] platformRow : platforms) {
			for (Platform platform : platformRow) {
				platform.adaptToFullscreen(isFullscreen);
			}
		}
		if (character != null)
			character.adaptToFullscreen(isFullscreen);
		if (sounds != null) {
			sounds.fullscreenChanged(isFullscreen);
		}
	}
	
	public void keyPressed(int keyCode) {
		if (keyCode == KeyEvent.VK_R) {
			character.getMovementScript().resetLevel(character);
		}
		character.keyPressed(keyCode);
	}

	public void keyReleased(int keyCode) {
		character.keyReleased(keyCode);
	}
	

	public void releaseAllKeys() {
		if (character != null)
			character.releaseAllKeys();
	}
	
	public void pauseGame() {
		if (updateFrameTimer != null)
			updateFrameTimer.stop();
	}
	
	public void resumeGame() {
		if (platforms != null) {
			updateFrameTimer.start();
		}
	}
	
	public void nextLevel() {
		currentLevel++;
		
		//End of game
		if (currentLevel >= LEVELS.length) {
			platforms = null;
			updateFrameTimer.stop();
			notifyDisplayUpdateObservers(null);
			return;
		}
		
		createPlatformsForCurrentLevel();
		switch (currentLevel) {
			case 17 -> character.getMovementScript().setOrangePlatformTask(new Level18Task(platforms));
			case 18 -> character.getMovementScript().setOrangePlatformTask(new Level19Task(platforms));
			case 19 -> character.getMovementScript().setOrangePlatformTask(new Level20Task());
		}
		character.adaptToFullscreen(isFullscreen);
	}


	public boolean isGameStarted() {
		return gameStarted;
	}

	public Sounds getSounds() {
		return sounds;
	}
	
	
}
