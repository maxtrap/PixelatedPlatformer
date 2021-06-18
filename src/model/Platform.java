package model;

import java.awt.Color;

public class Platform {
	public static final int NORMAL_PLATFORM_SIZE = 22;
	public static final int FULLSCREEN_PLATFORM_SIZE = 52;
	
	public final int x;
	private final int y;
	private final PlatformType platformType;
	
	private int xCoord;
	private int yCoord;
	private int size;
	
	
	public Platform(int x, int y, PlatformType platformType) {
		this.x = x;
		this.y = y;
		this.platformType = platformType;
		
		xCoord = x * NORMAL_PLATFORM_SIZE;
		yCoord = y * NORMAL_PLATFORM_SIZE;
		size = NORMAL_PLATFORM_SIZE;
	}


	public Color getColor() {
		return platformType.color;
	}


	public int getXcoord() {
		return xCoord;
	}


	public int getYcoord() {
		return yCoord;
	}


	public int getSize() {
		return size;
	}
	
	public PlatformType getPlatformType() {
		return platformType;
	}
	
	public void adaptToFullscreen(boolean fullscreen) {
		if (fullscreen) {
			xCoord = x * FULLSCREEN_PLATFORM_SIZE;
			yCoord = y * FULLSCREEN_PLATFORM_SIZE;
			size = FULLSCREEN_PLATFORM_SIZE;
		}
		else {
			xCoord = x * NORMAL_PLATFORM_SIZE;
			yCoord = y * NORMAL_PLATFORM_SIZE;
			size = NORMAL_PLATFORM_SIZE;
		}
	}
	
	public boolean containsPoint(int x, int y) {
		int xPos = this.x * NORMAL_PLATFORM_SIZE;
		int yPos = this.y * NORMAL_PLATFORM_SIZE;
		
		return (x >= xPos && x < xPos + NORMAL_PLATFORM_SIZE) && (y >= yPos && y < yPos + NORMAL_PLATFORM_SIZE);
	}
	
	@Override
	public String toString() {
		return "x=" + x + " y=" + y + " type=" + platformType;
	}
	
	
}
