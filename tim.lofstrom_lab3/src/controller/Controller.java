package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;

import model.GameConstants;
import model.Level;
import model.Ship;
import model.Vector2D;
import net.NetClient;
import net.NetServer;
import view.KeyboardState;

/**
 * Controls the model
 * @author kurt
 */
public class Controller extends Observable implements Observer, Runnable{

	boolean isServer = false;
	boolean run = false;
	NetServer netServer;
	NetClient netClient;
	Level level;
	Level serverLevel;
	long gameTime;
	long update;
	long time = 0;
	double oldSpeed = 0;
	double oldRot = 0;

	/**
	 * Constructor
	 * @param client NetClient
	 * @param server NetServer
	 */
	public Controller(NetClient client, NetServer server){
		netClient = client;
		netServer = server;
		netClient.addObserver(this);
		netServer.addObserver(this);
		level = new Level();
		serverLevel = new Level();
		level.addObserver(this);
		run = true;
	}	

	/**
	 * Returns the level that represents the players and the playfield
	 * @return level Level
	 */
	public Level getLevel(){
		return level;
	}

	private void serverUpdate(String message){
		
		String[] a = message.split(":");
		int id = -1;
		if(a.length > 1){
			id = Integer.parseInt(a[1]);
		}
		
		if(message.contains("disconnected")){
			serverLevel.deletePlayer(id);
			netServer.disconnectClient(id);
			for(int i = 0; i < serverLevel.numberOfShips(); i++){
				Ship sh = serverLevel.getShip(i);
				netServer.sendTcpData("playerLeft:"+id+":", sh.getID());
			}
		}
		if(message.contains("newPlayer")){
			serverLevel.addShip(id);
			Ship s = serverLevel.getShipByID(id);
			Random r = new Random();
			
			Vector2D pos = new Vector2D(0,0);
			boolean collide = true;
			int j = 0;
			while((collide == true) && (j < 100)){
				collide = false;
				int x = r.nextInt(GameConstants.PlayfieldSizeX - s.getWidth());
				int y = r.nextInt(GameConstants.PlayfieldSizeY - s.getHeight());
				pos = new Vector2D(x, y);			
				for(int i = 0; i < serverLevel.numberOfShips(); i++){
					if(serverLevel.getShip(i).intersect(pos)){
						collide = true;
					}
				}
				j++;
			}
			s.setPosition(pos);
			String playerList = "playerList:";
			String positions = "";
			for(int i = 0; i < serverLevel.numberOfShips(); i++){		//create a string with all ship-ids
				Ship sh = serverLevel.getShip(i);
				playerList += sh.getID() + ":";
				positions += ":"+sh.getID() +":" + sh.getPosition().toInt();
			}
			
			for(int i = 0; i < serverLevel.numberOfShips(); i++){		//send the playerlist to all players
				Ship sh = serverLevel.getShip(i);
				netServer.sendTcpData(playerList, sh.getID());
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
				netServer.sendTcpData("move"+positions, sh.getID());
			}
		}
		if(message.contains("move")){
			double x = Double.parseDouble(a[2]);
			double y = Double.parseDouble(a[3]);
			Ship s = serverLevel.getShipByID(id);
			if(s != null){
				s.setDirection(new Vector2D(x, y));
			}
		}
	}		


	private void clientUpdate(String message){
		
		String[] a = message.split(":");
		int id = -1;
		if(a.length > 1){
			id = Integer.parseInt(a[1]);
		}
		
		if(message.contains("disconnected")){
			level.removeAll();
			setChanged();
			notifyObservers(message);
		}
		if(message.contains("connected")){
			setChanged();
			notifyObservers(message);		//send to GUI that you are connected
			level.setID(id);
			netClient.sendTcpData("newPlayer:"+id);
		}
		if(message.contains("playerList")){
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
		}
		if(message.contains("move")){
			ArrayList<String> props = new ArrayList<String>();
			for(int i = 1; i < a.length; i++){
				props.add(a[i]);
			}
			if(props != null){
				while(props.size() != 0){
					int pID = Integer.parseInt(props.get(0));
					props.remove(0);
					double x = Double.parseDouble(props.get(0));
					props.remove(0);
					double y = Double.parseDouble(props.get(0));
					props.remove(0);
					Ship s = level.getShipByID(pID);
					if(s != null){
//						s.setPosition(new Vector2D(x, y));	
						
						
						if(level.getID() == pID){
							Vector2D temp = level.getShipByID(level.getID()).getDirection();
							if((temp.getX() == 0) && (temp.getY() == 0)){
								netClient.sendTcpData("move:"+s.getID()+":"+s.getDirection());
							}
							s.setPosition(new Vector2D(x, y));
						} else {
							s.setPosition(new Vector2D(x, y));	
						}
						
					}	
				}
			}
		}
		if(message.contains("playerLeft")){
			level.deletePlayer(id);
		}
		if(message.contains("SERVICE REPLY JavaGameServer AsteroidGame")){
			netClient.deleteServers();
			String[] servProps = message.split(" ");
			String s = servProps[4];
			int port = Integer.parseInt(servProps[5]);
			netClient.addServer(s, port);
			setChanged();
			notifyObservers(netClient.getServerlist());
		}
	}


	/**
	 * Refresh and calculate what to do
	 */
	@Override
	public void update(Observable caller, Object message) {
		
		if(message.getClass() == Vector.class){
			setChanged();
			notifyObservers(message);
		}else if(caller == netClient && message != null){
			clientUpdate(message.toString());
		}
		if(caller == netServer && message != null){
			serverUpdate(message.toString());
		}
		if(netClient != null && netClient.isConnected()){
			Ship s = level.getShipByID(level.getID());
			if(s != null){
				if(!(s.getDirection().compare(s.getOldDirection()))){
					netClient.sendTcpData("move:"+s.getID()+":"+s.getDirection());
				}
				s.setOldDirection(s.getDirection());
			}
		}		
	}
		


			/**
			 * Checks the state of the keyboard, so the player can move
			 * @param s KeyboardState
			 */
			public void updateInput(KeyboardState s) {

				if(netClient != null){
					if(netClient.isConnected()){
						Ship me = level.getShipByID(level.getID());
						if(me != null){
//							me.setDirection(new Vector2D(0, 0));
							Vector2D newDir = new Vector2D(0, 0);
							if(s.VK_LEFT == KeyboardState.PRESSED){
								newDir = newDir.add(new Vector2D(-1.0, 0.0));
//								me.setDirection(new Vector2D(-1.0, 0.0).add(me.getDirection()));
							}
							if(s.VK_RIGHT == KeyboardState.PRESSED){
//								me.setDirection(new Vector2D(1.0 , 0.0).add(me.getDirection()));
								newDir = newDir.add(new Vector2D(1.0, 0.0));
							}
							if(s.VK_UP == KeyboardState.PRESSED){
//								me.setDirection(new Vector2D(0.0, -1.0).add(me.getDirection()));
								newDir = newDir.add(new Vector2D(0.0, -1.0));
							}
							if(s.VK_DOWN == KeyboardState.PRESSED){
//								me.setDirection(new Vector2D(0.0, 1.0).add(me.getDirection()));
								newDir = newDir.add(new Vector2D(0.0, 1.0));
							}
							if(!newDir.compare(me.getDirection())){
								me.setDirection(newDir);
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

			/**
			 * Disconnect and close the current game
			 */
			public void disconnect() {

				if(netClient.isConnected()){
					netClient.sendTcpData("disconnected:"+level.getID());
					netClient.disconnect();
				}
				if(netServer.isConnected()){
					netServer.stopServer();
					isServer = false;
				}
				
				level.removeAll();
				serverLevel.removeAll();
				run = false;


			}

			/**
			 * find servers
			 * @throws IOException
			 */
			public void refreshServerlist() throws IOException {
				netClient.refreshServerlist();
			}

			/**
			 * Start a new game by joining a netserver
			 * @param address
			 * @throws IOException
			 */
			public void joinGame(String address, int port) throws IOException {
				netClient.changePort(port);
				netClient.connectToServer(address);
				run = true;
			}

			/**
			 * Host a new game, also gets connected to yourself
			 * @throws IOException
			 */
			public void hostGame(int port) throws IOException {
				isServer = true;
				netServer.changePort(port);
				netClient.changePort(port);
				netServer.startServer();
				netClient.connectToServer("localhost");
				new Thread(new GameServer(serverLevel, netServer)).start();
				run = true;
			}

			public boolean running() {
				return run;
			}

		}
