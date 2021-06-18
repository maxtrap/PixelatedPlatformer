package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PixelatedPlatformerView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final int NORMAL_WINDOW_WIDTH = ContentPanel.NORMAL_PANEL_WIDTH; //(num of platform * platform width)
	public static final int NORMAL_WINDOW_HEIGHT = ContentPanel.NORMAL_PANEL_HEIGHT + MenuPanel.MENU_PANEL_HEIGHT; //(num of platform * platform height + menubar size)
	
	public static final int FULLSCREEN_WINDOW_WIDTH = ContentPanel.FULLSCREEN_PANEL_WIDTH; //(num of platform * platform width)
	public static final int FULLSCREEN_WINDOW_HEIGHT = ContentPanel.FULLSCREEN_PANEL_HEIGHT + MenuPanel.MENU_PANEL_HEIGHT; //(num of platform * platform height + menubar size);
	
	public static final Dimension NORMAL_PANEL_DIMENSIONS = new Dimension(NORMAL_WINDOW_WIDTH, NORMAL_WINDOW_HEIGHT);
	public static final Dimension FULLSCREEN_PANEL_DIMENSIONS = new Dimension(FULLSCREEN_WINDOW_WIDTH, FULLSCREEN_WINDOW_HEIGHT);
	
	
	private boolean fullscreen;
	private List<FullscreenObserver> fullscreenObservers;
	
	private JPanel mainPanel;
	private MenuPanel menuPanel;
	private ContentPanel contentPanel;
	
	
	public PixelatedPlatformerView() {
		super("Pixelated Platformer");
		
		fullscreen = false;
		fullscreenObservers = new ArrayList<>();
		
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(NORMAL_PANEL_DIMENSIONS);
		this.setContentPane(mainPanel);
		this.pack();
		
		
		menuPanel = new MenuPanel();
		this.registerFullscreenObserver(menuPanel);
		mainPanel.add(menuPanel);
		
		contentPanel = new ContentPanel(this, new CardLayout());
		mainPanel.add(contentPanel);
		
		
		
		
		this.setIconImage(getImage("character22px.png"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.requestFocusInWindow();
	}
	
	public void toggleFullscreen() {
		if (fullscreen) {
			mainPanel.setPreferredSize(NORMAL_PANEL_DIMENSIONS);
			fullscreen = false;
		}
		else {
			mainPanel.setPreferredSize(FULLSCREEN_PANEL_DIMENSIONS);
			fullscreen = true;
		}
		this.pack();
		this.setLocationRelativeTo(null);
		
		notifyFullscreenObservers();
	}
	
	public void registerFullscreenObserver(FullscreenObserver observer) {
		fullscreenObservers.add(observer);
	}
	
	private void notifyFullscreenObservers() {
		fullscreenObservers.forEach(observer -> observer.fullscreenChanged(fullscreen));
	}
	
	
	
	public void addActionListener(ActionListener listener) {
		menuPanel.addActionListener(listener);
	}
	
	public boolean toggleInfo() {
		return contentPanel.toggleInfo();
	}
	
	public void startGame() {
		contentPanel.show(ContentPanel.GAME_PANEL);
	}
	
	@Override
	public void addMouseListener(MouseListener listener) {
		contentPanel.addMouseListener(listener);
	}
	
	@Override
	public void removeMouseListener(MouseListener listener) {
		contentPanel.removeMouseListener(listener);
	}
	
	
	public static Image getImage(String fileName) {
		try {
			return ImageIO.read(PixelatedPlatformerView.class.getResource("/images/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateGamePanel(GameDisplay display) {
		contentPanel.updateGamePanel(display);
	}
	
	public boolean isInfoShowing() {
		return contentPanel.isInfoPanelShowing();
	}
	
	
	
}
