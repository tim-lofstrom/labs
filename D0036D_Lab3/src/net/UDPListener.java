package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Observable;

public class UDPListener extends Observable implements Runnable{

	private DatagramSocket ds;
	private boolean isConnected;
	
	public UDPListener(DatagramSocket ds){
		this.ds = ds;
		isConnected = true;
	}
	
	@Override
	public void run() {
		
		while(isConnected){
			String data = receiveData();
			if(data != null){
				setChanged();
				notifyObservers(data);
			} else {
				setChanged();
				notifyObservers("connection_lost");
			}
		}
	}

	public String receiveData(){
		
		String data = "";
		byte[] receiveData = new byte[1024];
		
		try {
			DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
			ds.receive(packet);
			data = new String(packet.getData());
		} catch (IOException e) {
			//e.printStackTrace();
			isConnected = false;
			data = null;
		}
		System.out.println(data);
		return data;
	}
	
	
}
