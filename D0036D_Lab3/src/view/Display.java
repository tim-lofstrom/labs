package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import model.GameConstants;
import model.Level;
import model.Ship;

public class Display extends JPanel {
	
	private static final long serialVersionUID = 1L;
	Level level;
	private KeyboardState state;
	
	public Display(Level level){
		this.level = level;
		state = new KeyboardState();
		addKeyListener(state);
		setBackground(Color.black);
		setPreferredSize(new Dimension(GameConstants.PlayfieldSizeX,GameConstants.PlayfieldSizeY));
		setFocusable(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);


	

			
		for(int i = 0; i < level.numberOfShips(); i++){
			Ship s = level.getShip(i);
			double theta = s.getAngle();
			
			BufferedImage shipImage = s.getImage();
			Graphics2D dd = shipImage.createGraphics();
			dd.clearRect(0, 0, 60, 60);
			dd.rotate(theta, 30, 30);
			dd.drawRect(25, 25, 10, 10);
			g.drawImage((Image)shipImage, (int)s.getPosition().getX(), (int)s.getPosition().getY(), null);
			
		}
	}

	public KeyboardState getState() {
		return state;
	}
	
}