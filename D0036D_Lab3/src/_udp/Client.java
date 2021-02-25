package _udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		C c = new C();

	}

}


class C{
	
	DatagramSocket ds;
	DatagramPacket pack;
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	
	String m = "hejhopp";
	
	
	public C(){
		
		sendData = m.getBytes();
		
		
		try {
			ds = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		try {
			pack = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 7777);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("client skickar");
			ds.send(pack);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DatagramPacket pack3 = new DatagramPacket(receiveData, receiveData.length);
		
		try {
			ds.receive(pack3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Clienten mottog " + new String(receiveData));
		
		
		
		
		
	}
	
}