package model;

import java.awt.Color;

/**
 * Represent a ship to play with
 * @author kurt
 */
public class Ship {
	
	Vector2D oldDirection;
	Vector2D oldPosition;
	Vector2D position;
	Vector2D direction;
	Color color;
	int width;
	int height;
	int id;
	final double scale = 0.7;
	
	public Ship(int id, Color color){
		this.color = color; 
		position = new Vector2D(-100, -100);
		oldPosition = position;
		direction = new Vector2D(0,0);
		oldDirection = direction;
		width = 60;
		height = 60;
		this.id = id;
	}
	
	public boolean intersect (Vector2D v){
		return(!(this.position.x > v.x+width		||
				this.position.x + this.width < v.x	||
				this.position.y > v.y+height		||
				this.position.y + this.height < v.y	));
	}
	
	
	
	public Color getColor(){
		return color;
	}
	
	public void setOldDirection(Vector2D old){
		oldDirection = old;
	}
	
	public Vector2D getOldDirection(){
		return oldDirection;
	}
	
	public void setOldPosition(Vector2D old){
		oldPosition = old;
	}
	
	public Vector2D getOldPosition(){
		return oldPosition;
	}
		
	public void setPosition(Vector2D position){
		this.position = position;
	}
		
	public void setDirection(Vector2D direction){
		this.direction = direction;
	}
	
	public Vector2D getPosition(){
		return position;
	}	

	public Vector2D getDirection() {
		return direction;
	}

	public int getID() {
		return id;
	}
	
	/**
	 * Calculate the ships movement
	 */
	public void update(){
		
		//om man åker utanför kanten
		if (position.x > GameConstants.PlayfieldSizeX)
			position.x = 0 - width;
        if (position.x + width < 0)
        	position.x = GameConstants.PlayfieldSizeX;
        if (position.y > GameConstants.PlayfieldSizeY)
        	position.y = 0 - height;
        if (position.y + height < 0)
        	position.y = GameConstants.PlayfieldSizeY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}



	public double getScale() {
		return scale;
		
	}

}
 