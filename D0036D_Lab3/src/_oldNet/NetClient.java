package _oldNet;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Vector;

public class NetClient extends Net{

	private Vector<String> serverList = new Vector<String>();
	TCPConnection tcpConnection;
	UDPConnection udpConnection;
	int id;
	
	public NetClient(int port) {
		super(port);
	}

	/**
	 * Join a game over internet
	 * @param address String
	 * @throws IOException
	 */
	public void joinGame(String address, int port)  {
		this.port = port;
		try {
			Socket socket = new Socket(address, port);
			DatagramSocket ds = new DatagramSocket(socket.getLocalPort());
			System.out.println("skickar till "+ port);
			tcpConnection = new TCPConnection(this, socket);
			udpConnection = new UDPConnection(this, ds,socket.getLocalAddress(), port);
			new Thread(tcpConnection).start();
			new Thread(udpConnection).start();
			connectionStatus = CONNECTED;
			System.out.println("Joined server");
			setChanged();
			notifyObservers();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Disconnect from the game
	 * @throws IOException
	 */
	public void leaveGame() {
		if(tcpConnection != null){
			tcpConnection.close();	
		}
		if(udpConnection != null){
			udpConnection.close();
		}
		connectionStatus = UNCONNECTED;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Finds server online on the internet
	 * @return ServerList Vector
	 */
	public Vector<String> getServerlist(){
		//hämta möjliga servrar via UDP multicast
		serverList.clear();
		serverList.add("localhost");
		return serverList;
	}
	
	/**
	 * Send data to the remote client
	 * @param data
	 */
	public void sendTcpData(String data){
		tcpConnection.sendData(data);
	}
	
	/**
	 * Send data to the remote client
	 * @param data
	 */
	public void sendUdpData(String data){
		tcpConnection.sendData(data);
	}

	/**
	 * Some data received from the remote host
	 */
	@Override
	public void dataReceived(String data) {
		setChanged();
		notifyObservers(data);
	}

	@Override
	public void disconnected(int id) {
		if(this.id == id){
			tcpConnection.close();
			tcpConnection = null;
			udpConnection.close();
			udpConnection = null;
		}
	}
}
