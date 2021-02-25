package _old;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Listening for incoming server hosts
 * @author kurt
 */
public class Listener implements Runnable{
	
	ServerSocket serverSocket;
	
	public Listener(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}
	

	/**
	 * Thread for listening
	 */
	@Override
	public void run() {
		//listen for servers
		Socket remoteServer = null;
		try {
			remoteServer = serverSocket.accept();
			MainServer.serverReceived(remoteServer);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
}
