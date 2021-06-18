package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class StartPanel extends JPanel implements FullscreenObserver {
	private static final long serialVersionUID = 1L;
	
	private Image thumbnail;
	private boolean fullscreen;
	
	public StartPanel() {
		thumbnail = PixelatedPlatformerView.getImage("pixelatedthumbnail.png");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image thumbailToDraw = fullscreen ?
		thumbnail.getScaledInstance(ContentPanel.FULLSCREEN_PANEL_WIDTH,
											ContentPanel.FULLSCREEN_PANEL_HEIGHT,
											Image.SCALE_DEFAULT) : thumbnail;
		//This code will give a scaled instance of the image in order to fit fullscreen
		
		g.drawImage(thumbailToDraw, 0, 0, this);
	}

	@Override
	public void fullscreenChanged(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

}
