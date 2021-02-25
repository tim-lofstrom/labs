package controller;

import model.Level;
import model.Ship;
import model.Vector2D;
import net.NetServer;

public class GameServer implements Runnable {
	
	Level serverLevel;
	NetServer netServer;
	
	public GameServer(Level l, NetServer s){
		serverLevel = l;
		netServer = s;
	}

	@Override
	public void run() {
		
		while(netServer != null && netServer.isConnected()){
			String positions = "";
			for(int i = 0; i < serverLevel.numberOfShips(); i++){
				Ship s = serverLevel.getShip(i);
				Vector2D newPos = (s.getPosition().add(s.getDirection().dot(s.getScale())));
				boolean collide = false;
				for(int j = 0; j < serverLevel.numberOfShips(); j++){
					Ship tempShip = serverLevel.getShip(j);
					if((tempShip.getID() != s.getID()) && (tempShip.intersect(newPos))){
						s.setDirection(new Vector2D(0, 0));
						collide = true;
					}
				}
				if(!collide) { 
					s.setPosition(newPos);
					s.update();
				}
				positions += ":"+s.getID() +":" + s.getPosition().toInt();
			}
			if((positions.compareTo(serverLevel.getOldPositions()) == 1)){
				netServer.broadcastUdpData("move"+positions+"&"+System.currentTimeMillis()+"&");
			}
			if((positions.compareTo(serverLevel.getOldPositions()) == -1)){
				netServer.broadcastUdpData("move"+positions+"&"+System.currentTimeMillis()+"&");
			}
			serverLevel.setOldPositions(positions);
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
			}
		}
		
	}

}
