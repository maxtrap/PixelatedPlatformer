package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.DisplayUpdateObserver;
import model.PixelatedPlatformerModel;
import model.Sounds;
import view.ContentPanel;
import view.GameDisplay;
import view.PixelatedPlatformerView;

public class PixelatedPlatformerController implements ActionListener, MouseListener,
												FocusListener, KeyListener, DisplayUpdateObserver {
	
	private PixelatedPlatformerModel model;
	private PixelatedPlatformerView view;
	
	//Contructor
	public PixelatedPlatformerController() {
		this.view = new PixelatedPlatformerView();
		this.model = new PixelatedPlatformerModel();
		
		model.registerDisplayUpdateObserver(this);
		view.addActionListener(this);
		view.addMouseListener(this);
		view.addKeyListener(this);
		view.addFocusListener(this);
		view.registerFullscreenObserver(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "fullscreen" -> {
				view.toggleFullscreen();
				view.repaint();
			}
			case "info" -> {
				if (view.toggleInfo()) {
					model.pauseGame();
				} else {
					model.resumeGame();
				}
				view.repaint();
			}
			case "flag" -> startGame();
			case "stop" -> System.exit(0);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!model.isGameStarted() && e.getSource() instanceof ContentPanel) {
			startGame();
		}
		if (e.getSource() instanceof ContentPanel) {
			int xPos = e.getX();
			int yPos = e.getY();
			Sounds sounds = model.getSounds();
			
			if ((sounds != null) && (xPos > sounds.getX1() && xPos < sounds.getX2()) &&
					yPos > sounds.getY1() && yPos < sounds.getY2()) {
				model.getSounds().toggleSound();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void displayChanged(GameDisplay display) {
		view.updateGamePanel(display);
		view.repaint();
		view.requestFocusInWindow();
	}
	
	private void startGame() {
		model.startGame();
		view.startGame();
		view.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!view.isInfoShowing()) {
			model.keyPressed(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		model.keyReleased(e.getKeyCode());
	}

	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {
		model.releaseAllKeys();
	}
	
}
