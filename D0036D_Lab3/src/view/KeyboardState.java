package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardState implements KeyListener{
	
	public static final int PRESSED = 1;
	public static final int RELEASED = 0;
	public int VK_LEFT = RELEASED;
	public int VK_UP = RELEASED;
	public int VK_RIGHT = RELEASED;
	public int VK_DOWN = RELEASED;

	
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		case 37://vänster 37
			VK_LEFT = PRESSED;
			break;
		case 38://up 38
			VK_UP = PRESSED;
			break;
		case 39://höger 39
			VK_RIGHT = PRESSED;
			break;
		case 40://ner 40
			VK_DOWN = PRESSED;
			break;
		default:
			break;
		};
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()){
		case 37://vänster 37
			VK_LEFT = RELEASED;
			break;
		case 38://up 38
			VK_UP = RELEASED;
			break;
		case 39://höger 39
			VK_RIGHT = RELEASED;
			break;
		case 40://ner 40
			VK_DOWN = RELEASED;
			break;
		default:
			break;
		};
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
