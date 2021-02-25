package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Represents tha game and all contents
 * @author kurt
 */
public class Level extends Observable{

	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private String oldPositions = "";
	private int id = -1;
	
	/**
	 * Adda ships to the gameplan
	 * @param id
	 */
	public void addShip(int id){
		if(this.id == id){
			ships.add(new Ship(id, Color.white));
		}else{
			ships.add(new Ship(id, Color.red));			
		}

	}
	
	/**
	 * get a ship by id
	 * @param id
	 * @return Ship
	 */
	public Ship getShipByID(int id){
		for(Ship s : ships){
			if(s.getID() == id){
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Get ship by index
	 * @param index
	 * @return Ship
	 */
	public Ship getShip(int index){
		if(ships.size() != 0){
			return ships.get(index);
		}
		return null;

	}
	
	/**
	 * Number of ships on the gameplan
	 * @return noberofships int
	 */
	public int numberOfShips() {
		return ships.size();
	}

	/**
	 *recalculate all things needed 
	 */
	public void update() {
		
		//let all ships update their velocity etc
		for(Ship s : ships){
			s.update();
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Set own client-id
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Get own client-id
	 * @return id int
	 */
	public int getID(){
		return id;
	}

	/**
	 * Remove all ships and clear id
	 */
	public void removeAll() {
		ships.clear();
		id = -1;
	}

	/**
	 * Delete a specific ships by id
	 * @param id int
	 */
	public void deletePlayer(int id) {
		for(int i = 0; i < numberOfShips(); i++){
			if(ships.get(i).getID() == id){
				ships.remove(i);
			}
		}
	}

	public String getOldPositions() {
		return oldPositions;
	}
	
	public void setOldPositions(String p){
		oldPositions = p;
	}

	
}
