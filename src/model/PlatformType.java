package model;

import java.awt.Color;

public enum PlatformType {
	NO_PLATFORM(Color.WHITE),
	BLACK_PLATFORM(Color.BLACK),
	BOUNCY_PLATFORM(Color.BLUE),
	MISCELLANEOUS_PLATFORM(Color.ORANGE),
	GOAL_PLATFORM(Color.CYAN),
	RED_PLATFORM(Color.RED),
	
	EDGE_PLATFORM(null);
	//This is a special type of platform. It is used to represent collision if the player goes offscreen.
	//It is never actually drawn.
	
	final Color color;
	
	PlatformType(Color color) {
		this.color = color;
	}

}
