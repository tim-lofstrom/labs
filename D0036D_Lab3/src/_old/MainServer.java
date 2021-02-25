package _old;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {

	//local variables
	private static ArrayList<Server> serverList = new ArrayList<Server>();
	protected String settingsFileName = "src/mainserver/settings.txt";
	private boolean open = false;
	private static Thread listenerThread;
	private Listener listener;
	private int maxServers;
	private int port;
	
	public MainServer(){
		//init variables
		try {
			readSettings(settingsFileName);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	/**
	 * Print menu to system.out
	 */
	protected String getMenu(){
		String m = 
		"command		argument		description\n"+"\n"
		+"start					Start server"+"\n"
		+"stop					Stop server"+"\n"
		+"status					print status"+"\n"
		+"portchange	portnumber		change port"+"\n"
		+"maxservers	number			set maxservers"+"\n"
		+"exit					exit the program";
		return m;
	}
	
	/**
	 * Parse incoming message from server or terminal
	 * @param message : String
	 */
	protected boolean parseMessage(String message){
		String[] a = message.split(" ");
		switch(a[0]){
		case "host":
			//remote_server vill hosta, lägga till han i listan
			break;
		case "disconnect":
			//ta bort från listan
			break;
		case "start":
			start();
			break;
		case "stop":
			stop();
			break;
		case "portchange":
			if(a.length == 2){
				int port = Integer.parseInt(a[1]);
				changePort(port);
			}
			else{
				System.out.println("Invalid command, type ? for help");
			}
			break;
		case "maxservers":
			if(a.length == 2){
				int max = Integer.parseInt(a[1]);
				setMaxServers(max);
			}
			else{
				System.out.println("Invalid command, type ? for help");
			}
			break;
		case "status":
			System.out.println(status());
			break;
		case "exit":
			try {
				saveSettings(settingsFileName);
				//avsluta lyssnare osv
				//rensa allt
				stop();
				return false;
			} catch (IOException e) {
				System.out.println(e);
			}
			open = false;
		case "?":
			System.out.println(getMenu());
			break;
		default:
			System.out.println("Invalid command, type ? for help");
		}
		return true;
		
	}
	
	protected void serverDisconnected(Socket socket){
		for(int i = 0; i < serverList.size(); i++){
			if(serverList.get(i).socket.getInetAddress() == socket.getInetAddress()){
				try {
					InetAddress addr = socket.getInetAddress();
					serverList.get(i).socket.close();
					System.out.println("Server with address" + addr + " disconnected");
				} catch (IOException e) {
					System.err.println(e);
				
				}
			}
		}
	}
	
	/**
	 * When listener gets a connection
	 */
	protected static void serverReceived(Socket socket){
		Server s = new Server(socket, socket.getInetAddress(), socket.getLocalPort());
		new Thread(s).start();
		serverList.add(s);
		System.out.println("Server with address" + socket.getLocalAddress() + " connected");
	}
	
	
	/**
	 * Start the server and listen for connections
	 */
	protected void start(){
		if(!open){
			try {
				listener = new Listener(new ServerSocket(port));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			listenerThread = new Thread(listener);
			listenerThread.start();
			open = true;
			System.out.println("Mainserver started");			
		}else{
			System.out.println("Server already started");
		}

		
	}
	
	/**
	 * Stop the server from taking connections 
	 */
	@SuppressWarnings("deprecation")
	protected void stop(){
		if(open){
			try {
				listener.serverSocket.close();
				listener.serverSocket = null;
			} catch (IOException e) {
				System.err.println(e);
			}
			listenerThread.stop();
			listenerThread = null;
			open = false;
			System.out.println("Mainserver stopped");
		}else{
			System.out.println("Server already closed");
		}
		
	}
	
	/**
	 * Number of servers online
	 * @return servers : int
	 */
	protected int serversOnline(){
		return serverList.size();
	}
	
	/**
	 * Read server settings from a file
	 * @param filename : String
	 * @throws IOException 
	 */
	protected void readSettings(String filename) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader(filename));
		String s = null;
		while ((s = r.readLine()) != null){
			String[] a = s.split("=");
			switch(a[0]){
			case "port":
				port = Integer.parseInt(a[1]);
				break;
			case "maxservers":
				maxServers = Integer.parseInt(a[1]);
				break;
			default:
				throw new IOException("File corrupted");
			}
		}
		r.close();
		System.err.println("Settings readed from file " + filename);
	}
	
	/**
	 * Save server settings to a file
	 * @param filename : String
	 * @throws IOException 
	 */
	protected void saveSettings(String filename) throws IOException{
		BufferedWriter w = new BufferedWriter(new FileWriter(filename));
		w.write("port="+Integer.toString(port));
		w.write("\n");
		w.write("maxservers="+Integer.toString(maxServers));
		w.close();
		System.err.println("Settings saved to file " + filename);
	}

	protected String status() {
		String s = 	"MainServer open: 	" + open + "\n" +
					"Servers online: 	" + serverList.size() +"\n" +
					"MaxServers: 		" + maxServers + "\n" +
					"Port: 			" + port;
		return s;
	}

	protected void changePort(int port) {
		this.port = port;
		System.err.println("Port changed to: " + port);
	}

	protected void setMaxServers(int max) {
		this.maxServers = max;		
		System.err.println("Max servers is now: " + max);
	}

}
