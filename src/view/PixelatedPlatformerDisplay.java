package view;

import java.awt.Graphics;

import javax.swing.JPanel;

import model.GameCharacter;
import model.Platform;
import model.Sounds;

public class PixelatedPlatformerDisplay implements GameDisplay {
	
	private Platform[][] platforms;
	private GameCharacter character;
	private Sounds sounds;
	
	public PixelatedPlatformerDisplay(Platform[][] platforms, GameCharacter character, Sounds sounds) {
		this.platforms = platforms;
		this.character = character;
		this.sounds = sounds;
	}
	
	@Override
	public void draw(Graphics g, JPanel panel) {
		for (Platform[] platformRow : platforms) {
			for (Platform platform : platformRow) {
				g.setColor(platform.getColor());
				g.fillRect(platform.getXcoord(), platform.getYcoord(), platform.getSize(), platform.getSize());
			}
		}
		character.drawCharacter(g, panel);
		sounds.draw(g, panel);
	}

}
