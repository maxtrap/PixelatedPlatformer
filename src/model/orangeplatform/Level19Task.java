package model.orangeplatform;

import model.MovementScript;
import model.Platform;
import model.PlatformType;

public class Level19Task implements OrangePlatformTask {
	//around (13, 15)
	
	private Platform[][] platforms;
	private boolean isFullscreen;
	
	public Level19Task(Platform[][] platforms) {
		this.platforms = platforms;
	}

	@Override
	public void doTask(MovementScript movementScript) {
		for (int i = 12; i < 15; i++) {
			for (int j = 14; j < 17; j++) {
				if (platforms[i][j].getPlatformType() == PlatformType.RED_PLATFORM) {
					Platform newPlatform = new Platform(j, i, PlatformType.NO_PLATFORM);
					newPlatform.adaptToFullscreen(isFullscreen);		
					platforms[i][j] = newPlatform;
				}
			}
		}
	}

	@Override
	public void reset() {
		for (int i = 12; i < 15; i++) {
			for (int j = 14; j < 17; j++) {
				if (platforms[i][j].getPlatformType() == PlatformType.NO_PLATFORM) {
					Platform newPlatform = new Platform(j, i, PlatformType.RED_PLATFORM);
					newPlatform.adaptToFullscreen(isFullscreen);		
					platforms[i][j] = newPlatform;
				}
			}
		}
	}

	@Override
	public void fullscreenChanged(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
	}

}
