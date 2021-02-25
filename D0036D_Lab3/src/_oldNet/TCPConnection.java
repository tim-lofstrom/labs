package _oldNet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPConnection implements Runnable{
	
	private Net net;
	private Socket socket;
	private int id;

	public TCPConnection(Net net, Socket socket){
		this.net = net;
		this.socket = socket;
		
	}
	
	@Override
	public void run() {
		
		while(net.getConnectionStatus() == Net.CONNECTED){
			net.dataReceived(receiveData());
		}
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	/**
	 * Send a string of data to the remote client
	 * @param data
	 */
	public void sendData(String data){
		try{				
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(data+"#");
			//System.out.println("sänder " + data);
	    }
		catch (IOException e){
			System.err.println(e);
			net.connectionStatus = Net.UNCONNECTED;
			net.disconnected(id);
		}
	}
	
	/**
	 * receive data from a remote client
	 * @return data String
	 */
	public String receiveData(){
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
			net.connectionStatus = Net.UNCONNECTED;
			net.disconnected(id);
		}
		
		return data;
	}
	
	public void close() {
		try {
			socket.close();
			socket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
