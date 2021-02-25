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
	
	public void connectToServer(String address){
		
		//find remote address
		try {
			remoteAddress = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		//make TCP-connection and listener
		try {
			socket = new Socket(remoteAddress, remotePort);
			localPort = socket.getLocalPort();
			localAddress = socket.getLocalAddress();
			tcpListener = new TCPListener(socket);
			tcpListener.addObserver(this);
			tcpListenerThread = new Thread(tcpListener);
			tcpListenerThread.setPriority(9);
			tcpListenerThread.start();

			System.out.println("ansluten till server " + remoteAddress + " på port " + remotePort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		//make UDP-connection and listener
		try {
			datagramSocket = new DatagramSocket(localPort);
			udpListener = new UDPListener(datagramSocket);
			udpListener.addObserver(this);
			udpListenerThread = new Thread(udpListener);
			udpListenerThread.setPriority(9);
			udpListenerThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		setChanged();
		notifyObservers("connected:"+localPort);
		
	}
	
	public void sendTcpData(String data){
		
		try{				
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(data+"#");
	    }
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
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
			socket.close();
			datagramSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("disconnected from server");
		setChanged();
		notifyObservers("disconnected");
	}

	
	/**
	 * message received
	 */
	@Override
	public void update(Observable caller, Object message) {
		
		String[] a = message.toString().split(" ");
		
		if(a.length > 0){
			switch (a[0]) {
			case "connection_lost":
				disconnect();
				break;
			case "SERVICE":
				if(a.length > 5){
					if((a[1] == "REPLY") && (a[2] == "JavaGameServer") && (a[3] == "AsteroidGame")){
						serverList.add(a[4] + ":" + a[5]);
						setChanged(); 
						notifyObservers(serverList);
					}					
				}
				break;
			default:
				setChanged(); 
				notifyObservers(message);
				break;
			}
		}		
	}

	public void refreshServerlist() throws SocketException {
		//sök efter servrar
		
		if(serverFinderSocket == null){
			serverFinderSocket = new DatagramSocket();
		}
		
		sendUdpData("SERVICE QUERY JavaGameServer");
		serverList.clear();
		serverList.add("localhost:"+remotePort);
		setChanged();
		notifyObservers(serverList);
	}

	public void sendUdpData(String data){
				
        byte[] sendData = new byte[1024];
        sendData = data.getBytes();
        
        try {
        	DatagramPacket packet = new DatagramPacket(sendData, sendData.length,
        			InetAddress.getByName("239.255.255.250"), 1900);
        	serverFinderSocket.send(packet);
		} catch (IOException e) {
			//e.printStackTrace();
		}
        
		String d = "";
		byte[] receiveData = new byte[1024];
		
		try {
			System.out.println("väntar på data");
			DatagramPacket asd = new DatagramPacket(receiveData, receiveData.length);
			serverFinderSocket.receive(asd);
			d = new String(asd.getData());
		} catch (IOException e) {
			//e.printStackTrace();
		}
		System.out.println(d);
        
	}
	

}
