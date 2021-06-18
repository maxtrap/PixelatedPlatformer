package model;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeysPressed {
	private Map<Integer, Boolean> keys;
	
	public KeysPressed() {
		keys = new HashMap<>();
		keys.put(KeyEvent.VK_UP, false);
		keys.put(KeyEvent.VK_LEFT, false);
		keys.put(KeyEvent.VK_RIGHT, false);
		keys.put(KeyEvent.VK_W, false);
		keys.put(KeyEvent.VK_A, false);
		keys.put(KeyEvent.VK_D, false);
	}
	
	public void keyPressed(int keyCode) {
		keys.replace(keyCode, true);
	}
	
	public void keyReleased(int keyCode) {
		keys.replace(keyCode, false);
	}
	
	public boolean isUpPressed() {
		return keys.get(KeyEvent.VK_UP) || keys.get(KeyEvent.VK_W);
	}
	
	public boolean isLeftPressed() {
		return keys.get(KeyEvent.VK_LEFT) || keys.get(KeyEvent.VK_A);
	}
	
	public boolean isRightPressed() {
		return keys.get(KeyEvent.VK_RIGHT) || keys.get(KeyEvent.VK_D);
	}
	
	public Map<Integer, Boolean> getKeys() {
		return keys;
	}

	public void releaseAllKeys() {
		keys.forEach((key, value) -> keys.replace(key, false));
	}
}
