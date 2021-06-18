package model;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import view.ContentPanel;
import view.FullscreenObserver;
import view.PixelatedPlatformerView;

public class Sounds implements FullscreenObserver, Runnable, LineListener {
	private static final int NORMAL_ICON_SIZE = 60;
	private static final int FULLSCREEN_ICON_SIZE = 140;
	
	private int x1, y1, x2, y2;
	//topleft, bottomright
	
	private Image speakerIcon;
	private Image speakerMute;
	private boolean isFullscreen;
	
	private boolean isMuted;
	
	private AudioInputStream music;
	private Clip clip;
	private long currentFrame;
	
	private Thread pauseThread;
	private boolean openingAudio;
	
	private ScheduledExecutorService toggleTimer = Executors.newSingleThreadScheduledExecutor();
	private boolean canToggle = true;
	
	public Sounds(boolean isFullscreen) {
		speakerIcon = PixelatedPlatformerView.getImage("speakericon.png");
		speakerMute = PixelatedPlatformerView.getImage("speakermute.png");
		this.isFullscreen = isFullscreen;
		
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		resetAudioStream();
		adaptToFullscreen();
	}
	
	public void toggleSound() {
		if (!canToggle)
			return;
		
		if (isMuted) {
			resumeAudio();
		}
		else {
			pause();
		}
		canToggle = false;
		toggleTimer.schedule(() -> canToggle = true, 2, TimeUnit.SECONDS);
	}
	
	
	public Image getSoundIcon() {
		if (isMuted) {
			return speakerMute.getScaledInstance(isFullscreen ? FULLSCREEN_ICON_SIZE : NORMAL_ICON_SIZE, isFullscreen ? FULLSCREEN_ICON_SIZE : NORMAL_ICON_SIZE, Image.SCALE_DEFAULT);
		}
		else {
			return speakerIcon.getScaledInstance(isFullscreen ? FULLSCREEN_ICON_SIZE : NORMAL_ICON_SIZE, isFullscreen ? FULLSCREEN_ICON_SIZE : NORMAL_ICON_SIZE, Image.SCALE_DEFAULT);
		}
	}


	
	
	//Credit to geeksforgeeks.org
	public void resetAudioStream() {
		openingAudio = true;
		try {
			if (pauseThread != null) {
				pauseThread.join();
				pauseThread = null;
			}
			music = AudioSystem.getAudioInputStream(Sounds.class.getResource("/sounds/arcade.wav"));
			clip.open(music);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalStateException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.addLineListener(this);
		openingAudio = false;
	}
	
	// Method to pause the audio
    public void pause() {
        if (openingAudio)
        	return;
    	currentFrame = clip.getMicrosecondPosition();
        clip.stop();
        isMuted = true;
    }
    
    
    public void resumeAudio() {
    	
    	resetAudioStream();
    	clip.setMicrosecondPosition(currentFrame);
    	clip.start();
    	isMuted = false;
    }
    
    public void stop() {
    	clip.stop();
    }
    
    
    
    public void draw(Graphics g, JPanel panel) {
    	g.drawImage(getSoundIcon(), x1, y1, panel);
    }
    
    private void adaptToFullscreen() {
    	if (isFullscreen) {
    		x1 = ContentPanel.FULLSCREEN_PANEL_WIDTH - FULLSCREEN_ICON_SIZE - 10;
    		y1 = 0;
    		x2 = ContentPanel.FULLSCREEN_PANEL_WIDTH;
    		y2 = FULLSCREEN_ICON_SIZE;
    	}
    	else {
    		x1 = ContentPanel.NORMAL_PANEL_WIDTH - NORMAL_ICON_SIZE - 7;
    		y1 = 0;
    		x2 = ContentPanel.NORMAL_PANEL_WIDTH;
    		y2 = NORMAL_ICON_SIZE;
    	}
    }

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	@Override
	public void fullscreenChanged(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
		adaptToFullscreen();
	}
	
	@Override
	public void run() {
		clip.close();
	}
	
	@Override
	public void update(LineEvent e) {
		if (e.getType() == LineEvent.Type.STOP) {
			pauseThread = new Thread(this);
			pauseThread.start();
		}
	}

}
