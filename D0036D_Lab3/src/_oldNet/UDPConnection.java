package _oldNet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPConnection implements Runnable {
	
	private Net net;
	private DatagramSocket datagramSocket;
	private int port;
	private InetAddress address;
	private int id;

	public UDPConnection(Net net, DatagramSocket datagramSocket, InetAddress address, int port){
		this.net = net;
		this.datagramSocket = datagramSocket;
		this.port = port;
		this.address = address;
	}
	
	@Override
	public void run() {
		
		while(net.getConnectionStatus() == Net.CONNECTED){
			net.dataReceived(receiveData());
		}
		
	}
	
	public void sendData(String data){
		
        byte[] sendData = new byte[1024];
        sendData = data.getBytes();
        
        try {
        	DatagramPacket packet = new DatagramPacket(sendData, sendData.length,
        			address, port);
        	datagramSocket.send(packet);
		} catch (IOException e) {
			System.err.println(e);
			net.disconnected(id);
		}
        
	}

	
	public String receiveData(){
		
		String data = "";
		
		byte[] receiveData = new byte[1024];
		
		try {
			DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
			datagramSocket.receive(packet);
			data = new String(packet.getData());
		} catch (IOException e) {
			System.err.println(e);
			net.disconnected(id);
		}
		return data;
	}
	
	public void close() {
		datagramSocket.close();
		datagramSocket = null;
	}

	public void setID(int id) {
		this.id = id;		
	}
	
	public int getID(){
		return id;
	}
	

}
