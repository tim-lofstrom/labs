package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Observable;

public class MulticastServer implements Runnable{

	MulticastSocket socket;
	InetAddress group;
	
	public MulticastServer(){
		
		try {
			group = InetAddress.getByName("239.255.255.250");
			socket = new MulticastSocket(1900);
			socket.joinGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		while(true){
			String query = receiveData();
			if(query.compareTo("SERVICE QUERY JavaGameServer") == 0){
				sendData("SERVICE REPLY JavaGameServer AsteroidGame", socket.getLocalAddress(), socket.getPort());
			}
		}
	}
	
	
	public String receiveData(){
		
		String data = "";
		byte[] receiveData = new byte[1024];
		
		try {
			DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
			socket.receive(packet);
			data = new String(packet.getData());
		} catch (IOException e) {
		}
		return data;
	}
	
	public void sendData(String data, InetAddress address, int port){
		
		data = data + " " + address + " " + port;
		
        byte[] sendData = new byte[1024];
        sendData = data.getBytes();
        
        try {
        	DatagramPacket packet = new DatagramPacket(sendData, sendData.length,
        			address, port);
        	socket.send(packet);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	
	
	
}


