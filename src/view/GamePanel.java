package view;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GameDisplay gameDisplay;
	
	public void updatePanel(GameDisplay gameDisplay) {
		this.gameDisplay = gameDisplay;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameDisplay.draw(g, this);
	}

}
