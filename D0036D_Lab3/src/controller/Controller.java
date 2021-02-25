package controller;

import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import model.Level;
import model.Ship;
import model.Vector2D;
import net.NetClient;
import net.NetServer;
import view.KeyboardState;

public class Controller extends Observable implements Observer, Runnable{
	
	NetServer netServer;
	NetClient netClient;
	Level level;
	Level serverLevel;
	long gameTime;
	long update;
	long time = 0;
	
	public Controller(NetClient client, NetServer server){
		netClient = client;
		netServer = server;
		netClient.addObserver(this);
		netServer.addObserver(this);
		level = new Level();
		serverLevel = new Level();
		level.addObserver(this);
	}	
	
	public Level getLevel(){
		return level;
	}

	@Override
	public void update(Observable caller, Object message) {
				
		if(message.getClass() == Vector.class){
			System.out.println("nne");
			setChanged();
			notifyObservers(message);
		}else{
			if(caller == level || caller == this){
				if(netClient != null){
					if(netClient.isConnected()){
						int id = level.getID();
						Ship s = level.getShipByID(id);
						if(s!= null){
							double speed = s.getSpeed();
							double rot = s.getRotation();
							netClient.sendTcpData("move:"+id+":"+speed+ ":" + rot+":");
							
						}					
					}
				}
			}		
					
			if((caller == netClient) && (message != null)){
				if(message.toString().compareTo("connected") == 0){
					setChanged();
					notifyObservers(message);
				}else if(message.toString().compareTo("disconnected") == 0){
					setChanged();
					notifyObservers(message);
				}
			}
			
			if(caller == netClient && message != null){
				
				String[] a = message.toString().split(":");
				int id = -1;
				if(a.length > 1){
					id = Integer.parseInt(a[1]);
				}
				switch (a[0]) {
				case "connected":
					setChanged();
					notifyObservers(a[0]);		//send to GUI that you are connected
					level.setID(id);
					netClient.sendTcpData("newPlayer:"+id);
					break;
				case "disconnected":
					level.removeAll();
					setChanged();
					notifyObservers(a[0]);		//send to GUI that you are disconnected
					break;
				case "playerList":
					
					for(int j = 1; j < a.length; j++){
						int playerID = Integer.parseInt(a[j]);
						boolean alreadyAdded = false;
						for(int i = 0; i < level.numberOfShips(); i++){		//kolla så inte skeppet redan är tillagt
							if(level.getShip(i).getID() == playerID){
								alreadyAdded = true;
							}
						}
						if(alreadyAdded == false){
							level.addShip(playerID);
						}
					}

					break;
				case "move":
					double x = Double.parseDouble(a[2]);
					double y = Double.parseDouble(a[3]);
					double rad = Double.parseDouble(a[4]);
					Vector2D pos = new Vector2D(x, y);
					Ship s = level.getShipByID(id);
					if(s != null){
						s.setAngle(rad);
						s.setPosition(pos);
					}
					break;
				default:
					break;
				}
			}
			
			
			if(caller == netServer && message != null){
				String[] a = ((String)message).split(":");
				int id = -1;
				if(a.length > 1){
					id = Integer.parseInt(a[1]);	
				}
				switch (a[0]) {
				case "newPlayer":
					serverLevel.addShip(id);				
					String playerList = "playerList:";
					for(int i = 0; i < serverLevel.numberOfShips(); i++){		//create a string with all ship-ids
						Ship sh = serverLevel.getShip(i);
						playerList += sh.getID() + ":";
					}

					for(int i = 0; i < serverLevel.numberOfShips(); i++){		//send the playerlist to all players
						Ship sh = serverLevel.getShip(i);
						netServer.sendTcpData(playerList, sh.getID());
					}
					
					break;
				case "move":
					double speed = Double.parseDouble(a[2]);
					double rad = Double.parseDouble(a[3]);
					Ship s = serverLevel.getShipByID(id);
					if(s != null){
						s.setSpeed(speed);
						s.setAngle(rad);
						s.setDirection(new Vector2D(s.getAngle()));
						s.update();	//updates velocity
						s.setPosition(s.getPosition().add(s.getVelocity()));
						netServer.broadcastUdpData("move:" + id + ":" + s.getPosition() +":" + s.getAngle());
					}				
					break;
				case "lost_connection":
					
					break;
				default:
					break;
				}
			}
		}
		
		
	}
	
	
	
	public void updateInput(KeyboardState s) {

		if(netClient != null){
			if(netClient.isConnected()){
				Ship me = level.getShipByID(level.getID());
				if(me != null){
					if(s.VK_LEFT == KeyboardState.PRESSED){		//change direction
						me.rotate(Math.toRadians(-5));
					}
					if(s.VK_RIGHT == KeyboardState.PRESSED){	//change direction
						me.rotate(Math.toRadians(5));
					}
					if(s.VK_UP == KeyboardState.PRESSED){		//move forward
						if(!(me.getSpeed() > 2)){
							me.accelerate(1.5);
						}
					}
					else{
						if(!(me.getSpeed() <1)){	//decrease s
							me.accelerate(-1);
						}
					}
				}
			}
		}
	}

	/**
	 * Gameloop
	 */
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
			}
				this.update(this, gameTime);
				setChanged();
				notifyObservers();
				gameTime = System.currentTimeMillis();
		}
	}

	public void disconnect() {
		
		if(netServer.isConnected()){
			netServer.stopServer();
		}
		
		if(netClient.isConnected()){
			netClient.disconnect();
		}
		
	}

	public void refreshServerlist() throws SocketException {
		netClient.refreshServerlist();
	}

	public void joinGame(String address) {
		netClient.connectToServer(address);
	}

	public void hostGame() {
		netServer.startServer();
		netClient.connectToServer("localhost");
	}

}
