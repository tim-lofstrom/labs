package _oldNet;

import java.util.Observable;

public abstract class Net extends Observable{
	
	public static final int CONNECTED = 1;
	public static final int UNCONNECTED = 0;
	
	protected int port;
	protected int connectionStatus;
	
	public Net(int port){
		this.port = port;
		connectionStatus = UNCONNECTED;
	}
	
	public int getConnectionStatus() {
		return connectionStatus;
	}
	
	public abstract void dataReceived(String data);

	public abstract void disconnected(int id);

}
