package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Handles all connection on the internet 
 * @author kurt
 */
public class NetClient extends Observable implements Observer{

	private Socket socket;
	private DatagramSocket datagramSocket;
	private DatagramSocket serverFinderSocket;
	private InetAddress localAddress;
	private int localPort;
	private InetAddress remoteAddress;
	private int remotePort;
	private UDPListener udpListener;
	private TCPListener tcpListener;
	private Thread udpListenerThread;
	private Thread tcpListenerThread;
	private Vector<String> serverList = new Vector<String>();
	
	
	public NetClient(int port){
		this.remotePort = port;
	}
	
	/**
	 * Connects to server
	 * @param address
	 * @throws IOException
	 */
	public void connectToServer(String address) throws IOException {
		
		//find remote address
		remoteAddress = InetAddress.getByName(address);
			
		//make TCP-connection and listener
		socket = new Socket(remoteAddress, remotePort);
		localPort = socket.getLocalPort();
		localAddress = socket.getLocalAddress();
		tcpListener = new TCPListener(socket);
		tcpListener.addObserver(this);
		tcpListenerThread = new Thread(tcpListener);
		tcpListenerThread.setPriority(9);
		tcpListenerThread.start();

		System.out.println("ansluten till server " + remoteAddress + " på port " + remotePort);
	
		//make UDP-connection and listener
		datagramSocket = new DatagramSocket(localPort);
		udpListener = new UDPListener(datagramSocket);
		udpListener.addObserver(this);
		udpListenerThread = new Thread(udpListener);
		udpListenerThread.setPriority(9);
		udpListenerThread.start();
			
		setChanged();
		notifyObservers("connected:"+localPort);
		
	}
	
	
	/**
	 * Send data with TCP to the remote server
	 * @param data
	 */
	public void sendTcpData(String data){
		
		try{				
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(data);
	    }
		catch (IOException e){
			//e.printStackTrace();
		}
	}
	
	/**
	 * Checks if connected
	 * @return connection status boolean
	 */
	public boolean isConnected(){
		if(socket != null){
			return !socket.isClosed();	
		}
		return false;
	}
	
	/**
	 * close and clear everything
	 */
	public void disconnect(){
		try {
			if(socket != null){
				socket.close();	
			}
			if(datagramSocket != null){
				datagramSocket.close();				
			}
			if(serverFinderSocket != null){
				serverFinderSocket.close();
			}
			System.out.println("disconnected from server");
		} catch (IOException e) {
			//e.printStackTrace();
		}
		setChanged();
		notifyObservers("disconnected");
	}
	
	public void deleteServers(){
		serverList.clear();
	}
	
	public void addServer(String s, int port){
		serverList.add(s+":"+port);
	}

	
	/**
	 * message received
	 */
	@Override
	public void update(Observable caller, Object message) {
		
		if(message != null){
			if(message.toString().compareTo("connection_lost") == 0){
				disconnect();
			}else{
				setChanged();
				notifyObservers(message);
			}
		}		
	}

	/**
	 * Ask multicast address for hosted servers
	 * @throws IOException
	 */
	public void refreshServerlist() throws IOException {
		//sök efter servrar

		if(serverFinderSocket != null){
			serverFinderSocket.close();
		}
		serverFinderSocket = null;
		serverFinderSocket = new DatagramSocket(1901);
		byte[] sendData = new byte[512];
		
		sendData = "SERVICE QUERY JavaGameServer".getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("239.255.255.250"), 1900);
		serverFinderSocket.send(sendPacket);

		UDPListener listen = new UDPListener(serverFinderSocket);
		listen.addObserver(this);
		new Thread(listen).start();
		
		serverList.clear();
		setChanged();
		notifyObservers(serverList);
	}

	public Vector<String> getServerlist() {
		return serverList;
	}

	public void changePort(int port) {
		this.remotePort = port;
	}	

}
