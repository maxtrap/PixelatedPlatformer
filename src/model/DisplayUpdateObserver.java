package model;

import view.GameDisplay;

public interface DisplayUpdateObserver {
	
	void displayChanged(GameDisplay display);
	
}
