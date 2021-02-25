package _old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Container for remote server information and posibilities to communicate
 * 
 * 	@author kurt
 */
public class Server implements Runnable{

	Socket socket;
	InetAddress address;
	int port;
	boolean online = false;
	
	/**
	 * Constructor for making a handle to a hosted server
	 * @param socket : Socket
	 * @param address : InetAddress
	 * @param port : int
	 */
	public Server(Socket socket, InetAddress address, int port){
		this.socket = socket;
		this.address = address;
		this.port = port;
		this.online = true;
	}
	
	
	@Override
	public void run() {
		
		//listen for incoming data
//		while(online){
//			//parseMessage(receive());
//		}
		
	}

	/**
	 * Send data to the server
	 * @param data : String
	 */
	protected void send(String data){
		try{				
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(data+"#");
			System.err.println(data + " sent from socket: " + socket.getInetAddress());
	    }
		catch (IOException e){
			System.err.println(e);
			online = false;
		}
	}
	
	/**
	 * Listen for data from server
	 * @return received data : String
	 */
	private String receive(){
		String data = "";
		try{
			InputStream in = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			int c;
			while ((c = br.read()) != 35) {
				data = data + (char) c;
			}
			System.err.println(data + " recieved from socket: " + socket.getLocalAddress());
		}
		catch (IOException e){
			System.err.println(e);
			online = false;
		}
		return data;
	}
}
