package _oldNet;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Vector;

public class NetServer extends Net{
	
	private ArrayList<TCPConnection> clientTcpList = new ArrayList<TCPConnection>();
	private ArrayList<UDPConnection> clientUdpList = new ArrayList<UDPConnection>();
	
	private ConnectionListener connectionListener;
	private DatagramSocket ds;
	private int maxPlayers = 10;
	
	public NetServer(int port) {
		super(port);
	}
	
	/**
	 * Host a new server open for client connections
	 * @throws IOException
	 */
	public void hostGame() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			ds = new DatagramSocket(port);
			connectionListener = new ConnectionListener(this, serverSocket);
			new Thread(connectionListener).start();
			connectionStatus = CONNECTED;
			System.out.println("Server Startad");
			setChanged();
			notifyObservers();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Leaves the game and close the socket
	 * @throws IOException
	 */
	public void leaveGame() {
		try {
			for(int i = 0; i < clientTcpList.size();i++){
				clientTcpList.get(i).close();
			}
			clientTcpList.clear();
			clientUdpList.clear();
			ds.close();
			ds = null;
			connectionListener.close();
			connectionStatus = UNCONNECTED;
			setChanged();
			notifyObservers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getUniqueID(){
		int id = -1;
		for(int i = 0; i < maxPlayers; i++){
			id = i;
			for(int j = 0; j < clientTcpList.size(); j++){
				if(id == clientTcpList.get(j).getID()){
					id = -1;
				}
			}
			if(id != -1){
				return id;
			}
		}
	
		return id;
	}
	
	/**
	 * A new client connected
	 * @param socket Socket
	 */
	protected void connectionReceived(Socket socket){
		if(clientTcpList.size() < maxPlayers){
			
			TCPConnection tcpConnection = new TCPConnection(this, socket);
			int id = getUniqueID();
			if(id != -1){
				tcpConnection.setID(id);
			}else{
				disconnected(id);
			}
			
			UDPConnection udpConnection = new UDPConnection(this, ds, socket.getInetAddress(), socket.getPort());
			udpConnection.setID(id);
			clientTcpList.add(tcpConnection);
			clientUdpList.add(udpConnection);
			
			String welcomeMessage = "newConnection:"+id;
			
			new Thread(tcpConnection).start();
			new Thread(udpConnection).start();
			setChanged();
			notifyObservers(welcomeMessage);
		}
	}

	/**
	 * Send data to the remote client
	 * @param data
	 */
	public void sendTcpData(String data, int id){
		for(TCPConnection t : clientTcpList){
			if(id == t.getID()){
				t.sendData(data);
			}
		}
	}
	
	public void sendUdpData(String data, int id){
		for(UDPConnection u : clientUdpList){
			if(id == u.getID()){
				u.sendData(data);
			}
		}
	}
	
	public void broadCastUdpData(String data) {
		for(UDPConnection u : clientUdpList){
			u.sendData(data);
		}
	}


	/**
	 * Received som data from a socket
	 */
	@Override
	public void dataReceived(String data) {
		setChanged();
		notifyObservers(data);
	}

	@Override
	public void disconnected(int id) {
		for(int i = 0; i < clientTcpList.size(); i++){
			if(clientTcpList.get(i).getID() == id){
				clientTcpList.get(i).close();
				clientTcpList.remove(i);
				clientUdpList.get(i).close();
				clientUdpList.remove(i);
			}
		}
	}

	public Vector<Integer> getConnectedPlayers() {
		Vector<Integer> conn = new Vector<Integer>();
		for(TCPConnection t : clientTcpList){
			conn.add(t.getID());
		}
		return conn;
	}


}
