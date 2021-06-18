package model.orangeplatform;

import model.MovementScript;

public class Level20Task implements OrangePlatformTask {

	@Override
	public void fullscreenChanged(boolean isFullscreen) {}

	@Override
	public void doTask(MovementScript movementScript) {
		movementScript.setyVel(-18);
	}

	@Override
	public void reset() {}

}
