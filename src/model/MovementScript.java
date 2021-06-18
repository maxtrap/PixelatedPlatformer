package model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import model.orangeplatform.OrangePlatformTask;

public class MovementScript {
	
	private double xVel, yVel;
	private OrangePlatformTask orangePlatformTask;
	private GameCharacter character;
	private Platform[][] platforms;
	private Runnable nextLevel;
	private Map<PlatformType, Boolean> platformsTouching;
	
	
	public MovementScript(GameCharacter character, Platform[][] platforms, Runnable nextLevel) {
		this.character = character;
		this.platforms = platforms;
		this.nextLevel = nextLevel;
		platformsTouching = new HashMap<>();
		for (PlatformType type : EnumSet.complementOf(EnumSet.of(PlatformType.NO_PLATFORM))) {
			platformsTouching.put(type, false);
		}
	}
	//nextLevel is a runnable that allows this method to know how to move on to the next level if it touches a light blue square
	
	
	public void moveCharacter() {
		
		if (character.getKeysPressed().isLeftPressed()) {
			xVel -= 1.2;
			if (!character.getKeysPressed().isRightPressed()) {
				character.setCostume(GameCharacter.LOOKING_LEFT);
			}
		}
		if (character.getKeysPressed().isRightPressed()) {
			xVel += 1.2;
			if (!character.getKeysPressed().isLeftPressed()) {
				character.setCostume(GameCharacter.LOOKING_RIGHT);
			}
		}
		xVel *= .8; //Original was .8
		character.changeXby(xVel);
		
		boolean isTouchingWall = false;
		for (int i = 0; i < 6; i++) {
			if (isTouchingPlatforms(PlatformType.BLACK_PLATFORM, PlatformType.BOUNCY_PLATFORM, PlatformType.EDGE_PLATFORM)) {
				character.changeYby(-1);
				isTouchingWall = true;
			}
			else {
				isTouchingWall = false;
				break;
			}
		}
		
		if (isTouchingWall) {
			boolean canWallBounce = isTouchingPlatforms(PlatformType.BLACK_PLATFORM, PlatformType.BOUNCY_PLATFORM);
			character.changeXby(-xVel);
			character.changeYby(6);
			if (canWallBounce && character.getKeysPressed().isUpPressed()) {
				xVel = xVel > 0 ? -5 : 5;
				yVel = -8;
			}
			else {
				xVel = 0;
			}
		}
		
		yVel += .7;
		character.changeYby(yVel);
		
		if (isTouchingPlatforms(PlatformType.BLACK_PLATFORM, PlatformType.BOUNCY_PLATFORM, PlatformType.EDGE_PLATFORM)) {
			character.changeYby(-yVel);
			yVel = 0;
		}
		
		character.changeYby(1);
		if (character.getKeysPressed().isUpPressed()) {
			if (isTouchingPlatforms(PlatformType.BOUNCY_PLATFORM)) {
				yVel = -14;
			}
			else if (isTouchingPlatforms(PlatformType.BLACK_PLATFORM, PlatformType.EDGE_PLATFORM)) {
				yVel = -11;
			}
		}
		character.changeYby(-1);
		
		
		if (xVel < 1 && xVel > -1 && character.getKeysPressed().isLeftPressed() == character.getKeysPressed().isRightPressed()) {
			character.setCostume(GameCharacter.LOOKING_FORWARD);
		}
		
		if (isTouchingPlatforms(PlatformType.GOAL_PLATFORM)) {
			nextLevel.run();
			resetLevel(character);
		}
		
		if (isTouchingPlatforms(PlatformType.RED_PLATFORM)) {
			resetLevel(character);
		}
		
		if (orangePlatformTask != null && isTouchingPlatforms(PlatformType.MISCELLANEOUS_PLATFORM)) {
			orangePlatformTask.doTask(this);
		}
		
	}
	
	
	
	/*
	 * This method looks kind of long an ugly, and is really hard to read, so let me try to explain the best
	 * that I can. My main focus in terms of efficiency was to make it so that I didn't need to check for collision
	 * with every single platform on the screen, but rather only the ones that are close to the game character.
	 * The surroundings array is a 3x3 array that contains the 9 platforms that surround the player, and only checks
	 * collision with those. The check corner method is then used to check each corner of the game character
	 * with the Platform. If a corner is touching a platform, it can then know for sure that the game character must be
	 * touching a platform.
	 */
	
	private void findTouchingPlatforms() {
		Platform[][] surroundings = new Platform[3][3];
		
		double playerCenterX = character.getxPos() + Platform.NORMAL_PLATFORM_SIZE/2.0;
		double playerCenterY = character.getyPos() + Platform.NORMAL_PLATFORM_SIZE/2.0;
		
		int centerPlatformRow = (int) (playerCenterY/Platform.NORMAL_PLATFORM_SIZE);
		int centerPlatformCol = (int) (playerCenterX/Platform.NORMAL_PLATFORM_SIZE);
		
		
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int row = i + centerPlatformRow - 1;
				int col = j + centerPlatformCol - 1;
				if ((row >= 0 && row < platforms.length) && (col >= 0 && col < platforms[i].length)) {
					surroundings[i][j] = platforms[row][col];
				}
				else {
					surroundings[i][j] = new Platform(col, row, PlatformType.EDGE_PLATFORM);
				}
			}
		}
		
		platformsTouching.replaceAll( (key, value) -> value = false);
		
		checkCorner((int) character.getxPos(), (int) character.getyPos(), 0, 0, surroundings);
		checkCorner((int) character.getxPos() + Platform.NORMAL_PLATFORM_SIZE - 1, (int) character.getyPos(), 0, 1, surroundings);
		checkCorner((int) character.getxPos(), (int) character.getyPos() + Platform.NORMAL_PLATFORM_SIZE - 1, 1, 0, surroundings);
		checkCorner((int) character.getxPos() + Platform.NORMAL_PLATFORM_SIZE - 1, (int) character.getyPos() + Platform.NORMAL_PLATFORM_SIZE - 1, 1, 1, surroundings);
	}
	
	private void checkCorner(int cornerX, int cornerY, int startRowIndex, int startColIndex, Platform[][] surroundings) {
		for (int i = startRowIndex; i < startRowIndex + 2; i++) {
			for (int j = startColIndex; j < startColIndex + 2; j++) {
				
				Platform platform = surroundings[i][j];
				if (platform.containsPoint(cornerX, cornerY)) {
					platformsTouching.replace(platform.getPlatformType(), true);
				}
				
			}
		}
	}
	
	
	private boolean isTouchingPlatforms(PlatformType... platformTypes) {
		findTouchingPlatforms();
		for (PlatformType type : platformTypes) {
			if (platformsTouching.get(type)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	public void resetLevel(GameCharacter character) {
		xVel = yVel = 0;
		character.resetPosition();
		if (orangePlatformTask != null) {
			orangePlatformTask.reset();
		}
	}


	public void setxVel(double xVel) {
		this.xVel = xVel;
	}

	public void setyVel(double yVel) {
		this.yVel = yVel;
	}
	
	//This is the strategy pattern. Super helpful in this case.
	public void setOrangePlatformTask(OrangePlatformTask orangePlatformTask) {
		this.orangePlatformTask = orangePlatformTask;
	}
	
	public void adaptToFullscreen(boolean isFullscreen) {
		if (orangePlatformTask != null) {
			orangePlatformTask.fullscreenChanged(isFullscreen);
		}
	}
	

}
