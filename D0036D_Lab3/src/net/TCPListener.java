package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;

public class TCPListener extends Observable implements Runnable{
	
	private Socket socket;
	private boolean isConnected;
	
	public TCPListener(Socket s){
		socket = s;
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

	/**
	 * receive data from a remote client
	 * @return data String
	 */
	private String receiveData(){
		String data = "";
		try{
			InputStream in = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			int c;
			while ((c = br.read()) != 35) {
				data = data + (char) c;
			}
		}
		catch (IOException e){
			System.out.println("TCPDISSAD");
			//e.printStackTrace();
			isConnected = false;
			data = null;
		}
		return data;
	}
	
}
