package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InfoPanel extends JPanel implements FullscreenObserver {
	private static final long serialVersionUID = 1L;
	
	private JTextArea infoText;
	
	public InfoPanel() {
		super(new BorderLayout());
		
		infoText = new JTextArea("Arrow keys or WASD to move. \"R\" to restart a level. "
				+ "Pressing the flag will restart the whole game.\r\n"
				+ "\r\n"
				+ "Black = platform\r\n"
				+ "Red = lava\r\n"
				+ "Light blue = goal\r\n"
				+ "Blue = bouncy\r\n"
				+ "Orange = ???\r\n"
				+ "\r\n"
				+ "Song: Arcade by Vexento\r\n"
				+ "\r\n"
				+ "This is a remake of an old game that I made in fifth grade on the block-based visual programming language Scratch. "
				+ "I decided to keep some of the classic scratch format by adding a scratch-looking menu bar to keep the Scratch style of the game. "
				+ "This looks cool in my opinion, and adds a bit of nostalgia to it as well. "
				+ "The original project link is https://scratch.mit.edu/projects/90340338/. "
				+ "The scratch version of this game, however, is old and buggy and probably doesn't work, which is part of why I remade it on Java.");
		
		infoText.setFont(new Font("Futura", Font.PLAIN, 13));
		infoText.setWrapStyleWord(true);
		infoText.setLineWrap(true);
		infoText.setEditable(false);
		
		infoText.setBackground(new Color(230, 255, 230));
		infoText.setPreferredSize(ContentPanel.NORMAL_PANEL_DIMENSIONS);
		infoText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.add(infoText, BorderLayout.CENTER);
	}

	@Override
	public void fullscreenChanged(boolean fullscreen) {
		Font currentFont = infoText.getFont();
		infoText.setFont(currentFont.deriveFont(fullscreen ? 32F : 13F));
		
		int borderSize = fullscreen ? 25 : 10;
		infoText.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
	}

}
