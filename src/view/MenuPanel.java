package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements FullscreenObserver {
	private static final long serialVersionUID = 1L;
	public static final int MENU_PANEL_HEIGHT = 40;
	
	private Color menuColor;
	
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private JButton fullscreenButton;
	private JButton infoButton;
	private JButton flagButton;
	private JButton stopButton;
	
	public MenuPanel() {
		super(new BorderLayout());
		menuColor = new Color(215, 215, 215);
		
		leftPanel = new JPanel();
		leftPanel.setBackground(menuColor);
		add(leftPanel, BorderLayout.WEST);
		
		rightPanel = new JPanel();
		rightPanel.setBackground(menuColor);
		add(rightPanel, BorderLayout.EAST);
		
		
		fullscreenButton = createJButton("fullscreen", "fullscreenicon.png", leftPanel);
		
		infoButton = createJButton("info", "info.png", rightPanel);
		
		flagButton = createJButton("flag", "flag.png", rightPanel);
		flagButton.setPressedIcon(getIcon("flagpressed.png"));
		
		stopButton = createJButton("stop", "stop.png", rightPanel);
		stopButton.setPressedIcon(getIcon("stoppressed.png"));
		
        setMaximumSize(new Dimension(PixelatedPlatformerView.FULLSCREEN_WINDOW_WIDTH + 5, MENU_PANEL_HEIGHT));
        //+ 5 to account for weird visual bug ¯\_(ツ)_/¯
        setBackground(menuColor);
	}
	
	
	//Helper methods
	private JButton createJButton(String actionName, String iconFileName, JPanel panel) {
		JButton button = new JButton(getIcon(iconFileName));
		
		button.setActionCommand(actionName);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(30, 30));
        
        panel.add(button);
        return button;
	}
	
	private static Icon getIcon(String iconFileName) {
		return new ImageIcon(PixelatedPlatformerView.getImage(iconFileName));
	}
	
	
	//Public methods
	public void addActionListener(ActionListener listener) {
		fullscreenButton.addActionListener(listener);
		infoButton.addActionListener(listener);
		flagButton.addActionListener(listener);
		stopButton.addActionListener(listener);
	}
	
	
	
	/*
	 * This method updates the fullscreen button. If the boolean is true,
	 * it will switch the the normalscreenicon, and if it is false, it will
	 * switch to fullscreenicon. It's kind of counter intuitive, but it makes sense
	 * because that is what the button switches to when you press it.
	 */
	@Override
	public void fullscreenChanged(boolean fullscreen) {
		fullscreenButton.setIcon(getIcon(fullscreen ? "normalscreenicon.png" : "fullscreenicon.png" ));
	}
}
