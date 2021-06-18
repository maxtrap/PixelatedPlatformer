package model.orangeplatform;

import model.MovementScript;
import view.FullscreenObserver;

public interface OrangePlatformTask extends FullscreenObserver {
	
	void doTask(MovementScript movementScript);
	
	void reset();
	
}
