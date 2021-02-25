package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Game server can handle multiple connections and communication between them
 * @author kurt
 *
 */
public class NetServer extends Observable implements Runnable{

	private ArrayList<RemoteClient> clients = new ArrayList<RemoteClient>();
	private ServerSocket serverSocket;
	private DatagramSocket datagramSocket;
	private InetAddress localAddress;
	private MulticastServer multicast;
	private int localPort;
	
	public NetServer(int port){
		localPort = port;
	}
	
	/**
	 * Server status
	 * @return true if connected
	 */
	public boolean isConnected(){
		if(serverSocket != null){
			return !serverSocket.isClosed();
		}
		
		return false;
		
	}
	
	/**
	 * Stops the server
	 */
	public void stopServer(){
		try {
			serverSocket.close();
			datagramSocket.close();
			multicast.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server stopped");
	}
	
	/**
	 * Start the server
	 */
	public void startServer(){
		
		try {
			serverSocket = new ServerSocket(localPort);
			datagramSocket = new DatagramSocket(localPort);
			localAddress = serverSocket.getInetAddress();
			multicast = new MulticastServer(InetAddress.getLocalHost(), serverSocket.getLocalPort());
			new Thread(this).start();
			new Thread(multicast).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Server Started");
		
	}
	
	/**
	 * Listen for new clients
	 */
	@Override
	public void run() {
		
		while(!serverSocket.isClosed()){
			Socket s = clientReceived();
			if(s != null){
				clients.add(new RemoteClient(s));
			}
		}
	}
	
	/**
	 * Sending data with tcp to specified client-id
	 * @param data String
	 * @param id int
	 */
	public void sendTcpData(String data, int id){
		
		RemoteClient c = getClientByID(id);
		try{				
			PrintWriter out = new PrintWriter(c.remoteSocket.getOutputStream(), true);
			out.println(data);
	    }
		catch (IOException e){
			System.out.println("failed");
			//e.printStackTrace();
		}
	}

	/**
	 * Sending data with tcp to specified client-id
	 * @param data String
	 * @param id int
	 */
	public void sendUdpData(String data, int id){
		
		
		RemoteClient c = getClientByID(id);
		
        byte[] sendData = new byte[512];
        sendData = data.getBytes();
        
        try {
        	DatagramPacket packet = new DatagramPacket(sendData, sendData.length,
        			c.remoteAddress, c.remotePort);
        	datagramSocket.send(packet);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	

	/**
	 * Holds info about connected clients
	 * @author kurt
	 *
	 */
	private class RemoteClient implements Observer {
		
		TCPListener tcpListener;
		Thread tcpListenerThread;
		Socket remoteSocket;
		InetAddress remoteAddress;
		int remotePort;
		
		RemoteClient(Socket s){
			remoteSocket = s;
			remoteAddress = s.getInetAddress();
			remotePort = s.getPort();
			tcpListener = new TCPListener(remoteSocket);
			tcpListener.addObserver(this);
			tcpListenerThread = new Thread(tcpListener);
			tcpListenerThread.setPriority(9);			
			tcpListenerThread.start();
			
		}

		@Override
		public void update(Observable caller, Object message) {
			
			if((message.toString()).compareTo("connection_lost") == 0){
				disconnectClient(remotePort);
			}

			if(message != null){
				setChanged(); 
				notifyObservers(message.toString()+":"+ remotePort);
			}
			
			
		}
		
	}
	
	/**
	 * remove and disconnect everything with the client
	 * @param id int port of the client
	 */
	public void disconnectClient(int id){
		
		for(int i = 0; i < clients.size(); i++){
			RemoteClient c = clients.get(i);
			if(c.remotePort == id){
				try {
					c.remoteSocket.close();
					clients.remove(i);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Client "+ id + " disconnected");
	}
	
	/**
	 * Finds a client by id
	 * @param id int
	 * @return RemoteClient
	 */
	private RemoteClient getClientByID(int id){
		for(RemoteClient c : clients){
			if(c.remotePort == id){
				return c;
			}
		}
		return null;
	}
	
	/**
	 * New client connected
	 * @return Socket
	 */
	private Socket clientReceived(){
		Socket s = null;
		try {
			s = serverSocket.accept();
			System.out.println("client connected " + s.getPort());
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		return s;
	}
	

	/**
	 * Send udp-data to all connected clients
	 * @param data
	 */
	public void broadcastUdpData(String data) {
		for(RemoteClient c : clients){
			sendUdpData(data, c.remotePort);
		}
	}

	public void changePort(int port) {
		this.localPort = port;
	}
}
