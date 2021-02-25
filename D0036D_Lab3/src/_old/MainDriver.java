package _old;

import java.io.IOException;
import java.util.Scanner;

public class MainDriver {

	public static void main(String[] args) {
		
		MainServer mainServer = new MainServer();
		String in = "";
		Scanner scan = new Scanner(System.in);
		boolean running = true;
		System.out.println("Welcome to MainServer controller, type ? for help");
		do{
			System.out.println(">");
			in = scan.nextLine();
		}while(mainServer.parseMessage(in));
		
		System.out.println("Terminating program");
		System.exit(0);
		
	}

}

