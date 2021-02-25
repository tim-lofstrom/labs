package _oldNet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener implements Runnable{
	
	private NetServer server;
	private ServerSocket serverSocket;

	public ConnectionListener(Net net, ServerSocket serverSocket){
		server = (NetServer) net;
		this.serverSocket = serverSocket;
	}
	
	@Override
	public void run() {
		
		while(server.getConnectionStatus() == Net.CONNECTED){
			Socket socket;
			try {
				socket = serverSocket.accept();
				server.connectionReceived(socket);
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
	
	public void close() throws IOException{
		serverSocket.close();
	}
	
}
