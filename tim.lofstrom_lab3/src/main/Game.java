package main;

import net.NetClient;
import net.NetServer;
import view.GUI;
import controller.Controller;

/**
 * AsteroidGame main starter
 * @author kurt
 */
public class Game {

	public static void main(String[] args) {
		
		NetClient client = new NetClient(4444);
		NetServer server = new NetServer(4444);
		Controller controller = new Controller(client, server);
		new GUI(controller);
	}
}
