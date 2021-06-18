package view;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import model.Platform;

public class ContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	public static final int NORMAL_PANEL_WIDTH = Platform.NORMAL_PLATFORM_SIZE * 22; //(num of platform * platform width)
	public static final int NORMAL_PANEL_HEIGHT = Platform.NORMAL_PLATFORM_SIZE * 16; //(num of platform * platform height)
	
	public static final int FULLSCREEN_PANEL_WIDTH = Platform.FULLSCREEN_PLATFORM_SIZE * 22; //(num of platform * platform width)
	public static final int FULLSCREEN_PANEL_HEIGHT = Platform.FULLSCREEN_PLATFORM_SIZE * 16; //(num of platform * platform height)
	
	public static final Dimension NORMAL_PANEL_DIMENSIONS = new Dimension(NORMAL_PANEL_WIDTH, NORMAL_PANEL_HEIGHT);
	public static final Dimension FULLSCREEN_PANEL_DIMENSIONS = new Dimension(FULLSCREEN_PANEL_WIDTH, FULLSCREEN_PANEL_HEIGHT);
	
	public static final String START_PANEL = "StartPanel";
	public static final String INFO_PANEL = "InfoPanel";
	public static final String GAME_PANEL = "GamePanel";
	public static final String END_PANEL = "EndPanel";
	
	
	private CardLayout cardLayout;
	private String currentCard;
	private String panelShownBeforeInfo;
	
	private StartPanel startPanel;
	private InfoPanel infoPanel;
	private GamePanel gamePanel;
	private EndPanel endPanel;
	
	
	public ContentPanel(PixelatedPlatformerView view, CardLayout cardLayout) {
		super(cardLayout);

		this.cardLayout = cardLayout;
		
		startPanel = new StartPanel();
		view.registerFullscreenObserver(startPanel);
		this.add(startPanel, START_PANEL);
		
		infoPanel = new InfoPanel();
		view.registerFullscreenObserver(infoPanel);
		this.add(infoPanel, INFO_PANEL);
		
		gamePanel = new GamePanel();
		this.add(gamePanel, GAME_PANEL);
		
		endPanel = new EndPanel();
		view.registerFullscreenObserver(endPanel);
		this.add(endPanel, END_PANEL);
		
		show(START_PANEL);
		
	}
	
	public boolean toggleInfo() {
		if (panelShownBeforeInfo == null) {
			panelShownBeforeInfo = currentCard;
			show(INFO_PANEL);
			return true;
		}
		else {
			show(panelShownBeforeInfo);
			panelShownBeforeInfo = null;
			return false;
		}
	}
	
	public void show(String name) {
		currentCard = name;
		cardLayout.show(this, name);
	}

	public void updateGamePanel(GameDisplay display) {
		if (display == null) {
			show(END_PANEL);
		}
		else {
			gamePanel.updatePanel(display);
		}
	}
	
	public boolean isInfoPanelShowing() {
		return currentCard.equals(INFO_PANEL);
	}
	
}
