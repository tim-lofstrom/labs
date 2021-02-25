package controller;

import net.NetClient;
import model.Level;
import model.Ship;

public class GameClient implements Runnable{

	private NetClient netClient;
	private Level level;
	private int counter = 0;
	
	public GameClient(Level l, NetClient c){
		level = l;
		netClient = c;
	}
	
	@Override
	public void run() {
		if(netClient != null && netClient.isConnected()){
			Ship s = level.getShipByID(level.getID());
			if(s != null){
				if(!(s.getDirection().compare(s.getOldDirection()))){
					netClient.sendTcpData("move:"+s.getID()+":"+s.getDirection());
				}
				s.setOldDirection(s.getDirection());
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
	}

}
