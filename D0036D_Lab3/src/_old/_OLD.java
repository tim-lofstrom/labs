//package _old;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.util.ArrayList;
//
//import _old2.NetClient;
//
///**
// * Server for hosting online games
// * @author kurt
// *
// */
//public class _OLD {
//	
//	private static Listener listener;
//	private Thread clientListenerThread;
//	private ArrayList<NetClient> clientList = new ArrayList<NetClient>();
//	private String settingsFileName = "settings.txt";
//	private boolean online = false;
//	private int maxClients;
//	private int port;
//	private String mainServer;
//	private InetAddress mainServerAddress;
//
//	
//	public _OLD(){
//		
//	}
//	
//	/**
//	 * Start the server and listen for connections
//	 */
//	private void start(){
//		System.out.println("Server started");
//		//starta listerner tråden		
//	}
//	
//	/**
//	 * Stop the server from taking connections 
//	 */
//	private void stop(){
//		System.out.println("Server stopped");
//		//stoppa listener tråden
//	}
//
//	/**
//	 * When listener gets a connection
//	 */
//	protected static void clientRecieved(Socket socket){	//listener skickar en mottagen socket
//		System.out.println("Client with address" + socket.getLocalAddress() + " connected");
//		
//	}
//	
//	/**
//	 * Number of clients connected to the server
//	 * @return number of clients : int
//	 */
//	private int clientsOnline(){
//		return clientList.size();
//	}
//}
//
//
