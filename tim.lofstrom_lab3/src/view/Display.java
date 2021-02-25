package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import model.GameConstants;
import model.Level;
import model.Ship;

public class Display extends JPanel {
	
	private static final long serialVersionUID = 1L;
	Level level;
//	private KeyboardState state;
	
	public Display(Level level){
		this.level = level;
//		state = new KeyboardState();
//		addKeyListener(state);
		setBackground(Color.black);
		setPreferredSize(new Dimension(GameConstants.PlayfieldSizeX,GameConstants.PlayfieldSizeY));
		setFocusable(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(int i = 0; i < level.numberOfShips(); i++){
			Ship s = level.getShip(i);
			g.setColor(s.getColor());
			g.fillRect((int)s.getPosition().getX(), (int)s.getPosition().getY(), s.getWidth(), s.getHeight());
		}
	}

//	public KeyboardState getState() {
//		return state;
//	}
	
}