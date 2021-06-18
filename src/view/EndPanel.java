package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class EndPanel extends JPanel implements FullscreenObserver {
	private static final long serialVersionUID = 1L;
	
	private Image endscreen;
	private boolean fullscreen;
	
	public EndPanel() {
		endscreen = PixelatedPlatformerView.getImage("endscreen.png");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image endscreenToDraw = fullscreen ? 
				endscreen.getScaledInstance(ContentPanel.FULLSCREEN_PANEL_WIDTH,
											ContentPanel.FULLSCREEN_PANEL_HEIGHT,
											Image.SCALE_DEFAULT):
				endscreen.getScaledInstance(ContentPanel.NORMAL_PANEL_WIDTH,
											ContentPanel.NORMAL_PANEL_HEIGHT,
											Image.SCALE_DEFAULT);
		//This code will give a scaled instance of the image in order to fit fullscreen
		
		g.drawImage(endscreenToDraw, 0, 0, this);
	}

	@Override
	public void fullscreenChanged(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

}
