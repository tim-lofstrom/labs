package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Ship {
	
	BufferedImage shipGraphics;
	Vector2D velocity;
	Vector2D position;
	Vector2D direction;
	Color color;
	int width;
	int height;
	int id;
	double angle = 0;
	double rot = 0;
	final double scale = 0.1;
	double speed = 0;
	Rectangle rect;
	
	//params ID och color
//	public Ship(){
//		position = new Vector2D(300, 300);
//		velocity = new Vector2D(0, 0);
//		direction = new Vector2D(angle);
//		width = 60;
//		height = 60;
//	}
	
	public Ship(int id){
		position = new Vector2D(300, 300);
		velocity = new Vector2D(0, 0);
		direction = new Vector2D(angle);
		width = 60;
		height = 60;
		shipGraphics = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		rect = new Rectangle((int)position.getX(), (int)position.getY(), 10, 10);
		shipGraphics.createGraphics().draw(rect);		
		this.id = id;
	}
	
	public Rectangle getRect(){
		return rect;
	}
	
	public void setPosition(Vector2D position){
		this.position = position;
	}
	
	public void setVelocity(Vector2D velocity){
		this.velocity = velocity;
	}
	
	public void setDirection(Vector2D direction){
		this.direction = direction;
	}
	
	public Vector2D getPosition(){
		return position;
	}
	
	public Vector2D getVelocity(){
		return velocity;
	}
	
	public double getScale(){
		return scale;
	}
	
	public double getSpeed(){
		return speed;
	}

	public void accelerate(double i) {
		speed += i;
	}
	
	public void setSpeed(double i){
		speed = i;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public void setAngle(double rad) {
		angle = rad;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public double getRotation() {
		return rot;
	}

	public int getID() {
		return id;
	}

	public BufferedImage getImage() {
		return this.shipGraphics;
	}
	
	public void rotate(double d) {
		this.rot += d;
		
		if(this.rot > Math.PI*2){
			this.rot = 0;
		}
		if(this.rot < 0){
			this.rot = Math.PI * 2;
		}
		
	}
	
	public void update(){

		velocity = (direction.dot(scale * speed).add(velocity)).dot(0.98);
		
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

}
 