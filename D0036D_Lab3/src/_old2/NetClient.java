//package _old2;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.Observable;
//import java.util.Vector;
//
//import _old.Listener2;
//
//public class NetClient extends Observable implements Runnable{
//
//	Vector<String> avaliableServers = new Vector<String>();
//	ServerSocket serverSocket;
//	InetSocketAddress remoteAddress;
//	public static final int SERVER = 2;
//	public static final int CLIENT = 1;
//	public static final int UNCONNECTED = 0;
//	private int status;
//	
//	//local variables
//	private static ArrayList<Listener2> clientList = new ArrayList<Listener2>();
//	private int maxPlayers = 10;
//	private int port;
//
//	public NetClient(int port) {
//		this.port = port;
//		this.status = UNCONNECTED;
//	}
//	
//	public Vector<String> getServers(){
//		//refreshServerList();
//		avaliableServers.clear();
//		avaliableServers.add("localhost");
//		return avaliableServers;
//	}
//	
//	@Override
//	public void run() {
//		
//		while(status == NetClient.SERVER){
//			Socket socket = null;
//			try {
//				socket = serverSocket.accept();
//			} catch (IOException e) {
//				System.err.println(e);
//				break;
//			}
//			if(clientList.size() < maxPlayers){
//				System.out.println("client connected " + socket.getRemoteSocketAddress());
//				clientReceived(socket);	
//			}
//		}
//		
//	}
//	
//	public void sendMessage(String m, int id){
//		Listener2 l = getClientByID(id);
//		if(l != null){
//			l.send(m);			
//		}
//	}
//	
//	public void broadcastMessage(String m){
//		for(Listener2 l : clientList){
//			l.send(m);
//		}
//	}
//	
//	
//	private int createID(){
//		int id = 0;
//
//		for(int i = 0; i <= clientList.size(); i++){
//			id = i;
//			for(Listener2 l : clientList){
//				if(id == l.id){
//					id = 0;
//				}
//			}
//			if(id != 0){
//				break;
//			}
//		}
//		return id;
//	}
//
//	protected void clientReceived(Socket socket){
//		Listener2 l = new Listener2(this, socket, 0);
//		clientList.add(l);
//		new Thread(l).start();
//		setChanged();
//		notifyObservers();
//	}
//	
//	public Listener2 getClientByID(int id){
//		for(Listener2 l : clientList){
//			if(l.id == id){
//				return l;
//			}
//		}
//		return null;
//	}
//	
//	public int getConnectionStatus() {
//		return status;
//	}
//
//	public void messageReceived(String data) {
//		setChanged();
//		notifyObservers(data);
//	}
//
//	public void clientDisconnected(int id) {
//		System.out.println("Client med id " + id + " disconnected");
//		for(int i = 0; i < clientList.size(); i++){
//			if(clientList.get(i).id == id){
//				clientList.remove(i);
//				break;
//			}
//		}
//	}
//
//	public void connectToServer(String address) {
//		remoteAddress = new InetSocketAddress(address, port);
//		try {
//			Socket s = new Socket(address, port);
//			clientReceived(s);
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		this.status = CLIENT;
//		setChanged();
//		notifyObservers("connected");
//	}
//
//	public void hostGame() {
//		try {
//			System.out.println("Server Startad");
//			serverSocket = new ServerSocket(port);
//		} catch (IOException e) {
//		}
//		this.status = SERVER;
//		setChanged();
//		notifyObservers();
//		
//		new Thread(this).start();
//	}
//
//	public void disconnect () throws IOException {
//		if(status == SERVER){
//			serverSocket.close();
//			while(clientList.size() > 0){
//				clientList.get(0).send("disconnect");
//				clientList.get(0).socket.close();
//				clientList.remove(0);
//			}
//		}else if(status == CLIENT){
//			System.out.println(clientList.size());
//			clientList.get(0).send("disconnect");
//			clientList.get(0).socket.close();
//		}
//		status = UNCONNECTED;
//		setChanged();
//		notifyObservers();
//	}
//}
//
