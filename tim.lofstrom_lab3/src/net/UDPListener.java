package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Observable;

/**
 * General listener for UDP-data
 * @author kurt
 *
 */
public class UDPListener extends Observable implements Runnable{

	private DatagramSocket ds;
	private boolean isConnected;
	private long oldMessageTime = 0;
	
	public UDPListener(DatagramSocket ds){
		this.ds = ds;
		isConnected = true;
	}
	
	@Override
	public void run() {
		
		while(isConnected){
			String data = receiveData();
			if(data != null){
				String[] dataCheck = data.split("&");
				if(dataCheck.length > 1){
					long newMessageTime = Long.parseLong(dataCheck[1]);
					if(oldMessageTime < newMessageTime){
						oldMessageTime = newMessageTime;
						setChanged();
						notifyObservers(dataCheck[0]);
					}else{
					}
				}else {
					setChanged();
					notifyObservers(data);
				}
			} else {
				setChanged();
				notifyObservers("connection_lost");
			}
		}
	}

	/**
	 * receive data from a remote client
	 * @return data String
	 */
	public String receiveData(){
		
		String data = "";
		byte[] receiveData = new byte[512];
		
		try {
			DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
			ds.receive(packet);
			data = new String(packet.getData());
		} catch (IOException e) {
			//e.printStackTrace();
			isConnected = false;
			data = null;
		}
		return data;
	}
	
	
}
