package model.orangeplatform;

import model.MovementScript;
import model.Platform;
import model.PlatformType;

public class Level18Task implements OrangePlatformTask {
	
	private static final int ROW_TO_EDIT = 12;
	private static final int COL_TO_EDIT = 15;
	
	private Platform[][] platforms;
	private boolean isFullscreen;
	
	public Level18Task(Platform[][] platforms) {
		this.platforms = platforms;
	}

	@Override
	public void doTask(MovementScript movementScript) {
		
		if (platforms[ROW_TO_EDIT][COL_TO_EDIT].getPlatformType() == PlatformType.NO_PLATFORM) {
			Platform newPlatform = new Platform(COL_TO_EDIT, ROW_TO_EDIT, PlatformType.BLACK_PLATFORM);
			newPlatform.adaptToFullscreen(isFullscreen);		
			platforms[ROW_TO_EDIT][COL_TO_EDIT] = newPlatform;
		}	
	}

	@Override
	public void reset() {
		if (platforms[ROW_TO_EDIT][COL_TO_EDIT].getPlatformType() == PlatformType.BLACK_PLATFORM) {
			Platform newPlatform = new Platform(COL_TO_EDIT, ROW_TO_EDIT, PlatformType.NO_PLATFORM);
			newPlatform.adaptToFullscreen(isFullscreen);
			platforms[ROW_TO_EDIT][COL_TO_EDIT] = newPlatform;
		}
	}

	@Override
	public void fullscreenChanged(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
	}

}
