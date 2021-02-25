package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServer implements Runnable{

	MulticastSocket socket;
	InetAddress group;
	int serverPort;
	String serverAddress;
	
	public MulticastServer(InetAddress inetAddress, int port){
		this.serverPort = port;
		String[] a = inetAddress.toString().split("/");
		this.serverAddress = a[1];
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
			
			String data = "";
			byte[] receiveData = new byte[512];
			DatagramPacket packet = null;
			try {
				packet = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(packet);
				data = new String(packet.getData());
			} catch (IOException e) {
			}

			if(data.contains("SERVICE QUERY JavaGameServer")){
				if(packet != null){
					System.out.println("skickar");
					sendData("SERVICE REPLY JavaGameServer AsteroidGame", packet.getAddress(), packet.getPort());	
				}
			}
		}
	}
	
	
	public String receiveData(){
		
		String data = "";
		byte[] receiveData = new byte[512];
		
		try {
			DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
			socket.receive(packet);
			data = new String(packet.getData());
		} catch (IOException e) {
		}
		return data;
	}
	
	public void sendData(String data, InetAddress address, int port){
		
		data = data + " " + serverAddress + " " + serverPort + " ";
		
        byte[] sendData = new byte[1024];
        sendData = data.getBytes();
//        System.out.println(data + "to: " + address + " port: " + port + ":");
        try {
        	DatagramPacket packet = new DatagramPacket(sendData, sendData.length,
        			address, 1901);
        	socket.send(packet);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	
	
	
}


