package net;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class ClientTester  {

	public static void main(String[] args) {


		Test t = new Test();
		
		
	}


}

class Test implements Observer{
	
	NetClient client;
	
	public Test(){
		
		
		client = new NetClient(4444);

		client.connectToServer("localhost");
		client.addObserver(this);
		
		Scanner scan = new Scanner(System.in);
		
		while(true){
			System.out.println("skriv meddelande: ");
			client.sendTcpData(scan.nextLine());
		}
		
	}
	
	@Override
	public void update(Observable o, Object message) {
		if(message != null){
			System.out.println("Echo: " + message.toString());
		}
		
	}

}