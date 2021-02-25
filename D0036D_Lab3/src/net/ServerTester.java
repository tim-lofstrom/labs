package net;

import java.util.Observable;
import java.util.Observer;


public class ServerTester  {
	

	public static void main(String[] args) {
		
		Tester t = new Tester();

	}



}


class Tester implements Observer{
	
	NetServer server;
	
	public Tester(){
		server = new NetServer(4444);
		server.startServer();
		server.addObserver(this);
	}
	
	
	@Override
	public void update(Observable caller, Object message) {
		String[] a = message.toString().split(":");
		if(message != null){
			server.sendUdpData("UDPSVAR" + message, Integer.parseInt(a[1]));
			server.sendTcpData("TCPSVAR" + message, Integer.parseInt(a[1]));
		}
	}
	
}