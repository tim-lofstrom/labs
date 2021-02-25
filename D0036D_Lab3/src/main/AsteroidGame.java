package main;

import net.NetClient;
import net.NetServer;
import view.GUI;
import controller.Controller;

public class AsteroidGame {

	public static void main(String[] args) {
		
		NetClient client = new NetClient(4444);
		NetServer server = new NetServer(4444);
		Controller controller = new Controller(client, server);
		new GUI(controller);
	}
}
