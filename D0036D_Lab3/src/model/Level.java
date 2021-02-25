package model;

import java.util.ArrayList;
import java.util.Observable;

public class Level extends Observable{

	ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	ArrayList<Ship> ships = new ArrayList<Ship>();
	private int id = -1;
	
	public Level(){
		
	}
	
	public void addShip(int id){
		ships.add(new Ship(id));
	}

//	public Vector2D get(){
//		return ships.get(0).getPosition();
//	}
	
//	public Ship getShip(){
//		return ships.get(0);
//	}
	
	public Ship getShipByID(int id){
		for(Ship s : ships){
			if(s.getID() == id){
				return s;
			}
		}
		return null;
	}
	
	public Ship getShip(int index){
		if(ships.size() != 0){
			return ships.get(index);
		}
		return null;

	}
	
	public void move(Vector2D position) {
		ships.get(0).setPosition(position);
	}
	
//	/**
//	 * Move a specific ship to a specific pos
//	 * @param id : int
//	 * @param pos : Vector2D
//	 */
//	public void moveShip(int id, Vector2D pos){
//		
//	}
	
	public int numberOfShips() {
		return ships.size();
	}

	//recalculate all things needed
	public void update() {
		
		//let all ships update their velocity etc
		for(Ship s : ships){
			s.update();
		}
	}

	public void setID(int id) {
		this.id = id;
	}
	
	public int getID(){
		return id;
	}

	public void removeAll() {
		ships.clear();
		id = -1;
	}


	
}
