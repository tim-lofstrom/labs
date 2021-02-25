//package _old;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//import _old2.NetClient;
//
///**
// * Listening for incoming
// * @author kurt
// */
//public class Listener2 implements Runnable{
//	
//	
//
//	NetClient client;
//	Socket socket;
//	boolean connected = false;
//	int id;
//	
//	public Listener2(NetClient client, Socket socket, int id){
//		this.client = client;
//		this.socket = socket;
//		this.id = id;
//		connected = true;
//	}
//	
//
//	/**
//	 * Thread for listening
//	 */
//	@Override
//	public void run() {
//		
//		while(connected){
//			String s = receive();
//			client.messageReceived(s);
//		}
//		
//	}
//	
//	/**
//	 * Send data to the server
//	 * @param data : String
//	 */
//	protected void send(String data){
//		try{				
//			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//			out.println(data+"#");
//	    }
//		catch (IOException e){
//			System.err.println(e);
//			connected = false;
//			client.clientDisconnected(id);
//		}
//	}
//	
//	/**
//	 * Listen for data from server
//	 * @return received data : String
//	 */
//	private String receive(){
//		String data = "";
//		try{
//			InputStream in = socket.getInputStream();
//			InputStreamReader isr = new InputStreamReader(in);
//			BufferedReader br = new BufferedReader(isr);
//			int c;
//			while ((c = br.read()) != 35) {
//				data = data + (char) c;
//			}
//		}
//		catch (IOException e){
//			System.err.println(e);
//			connected = false;
//			client.clientDisconnected(id);
//		}
//		return data;
//	}
//	
//	
//	
//}
