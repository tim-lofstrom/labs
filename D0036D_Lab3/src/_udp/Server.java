package _udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		S s = new S();

	}

}


class S{

	DatagramSocket ds;
	DatagramPacket pack;
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	
	String m = "hej på dig";
	
	public S(){
		
		sendData = m.getBytes();
		
		try {
			ds = new DatagramSocket(7777);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		System.out.println("startad");
		
		pack = new DatagramPacket(receiveData, receiveData.length);
		
		try {
			ds.receive(pack);
			System.out.println("server mottog " +new String(receiveData));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(pack.getPort());
		DatagramPacket pack2 = new DatagramPacket(sendData, sendData.length, pack.getAddress(), pack.getPort());
		
		try {
			System.out.println("Server skickar");
			ds.send(pack2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}