package view;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Controller;

public class GUI implements Observer{
	
	private Display d;
	private Controller controller;
	private JButton hostHameButton, connectButton, disconnectButton, refreshServersButton;
	private JLabel portLabel, ipLabel;
	private	JList<String> serverListbox;
	private JPanel panel, buttonPanel, inputPanel;
	private JTextField ipField, portField;
	private SpringLayout springLayout;	
	private KeyboardState st;
	
	public GUI(Controller c){

		controller = c;
		controller.addObserver(this);
		
		final JFrame frame = new JFrame("Asteroids");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buttonPanel = new JPanel();
		inputPanel = new JPanel();
		panel = new JPanel();
		serverListbox = new JList<String>();		
		serverListbox.setPreferredSize(new Dimension(400, 470));
		serverListbox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ipLabel = new JLabel("IP-address: ");
		portLabel = new JLabel("Port: ");
		ipField = new JTextField();
		portField = new JTextField();
		ipField.setPreferredSize(new Dimension(200, 25));
		portField.setPreferredSize(new Dimension(60, 25));
		hostHameButton = new JButton("Host game");
		connectButton = new JButton("Connect");
		disconnectButton = new JButton("Disconnect");
		refreshServersButton = new JButton("Refresh serverlist");
		buttonPanel.add(hostHameButton);
		buttonPanel.add(connectButton);
		buttonPanel.add(disconnectButton);
		buttonPanel.add(refreshServersButton);
		inputPanel.add(ipLabel);
		inputPanel.add(ipField);
		inputPanel.add(portLabel);
		inputPanel.add(portField);
		d = new Display(controller.getLevel());
		
		panel.add(buttonPanel);
		panel.add(inputPanel);
		panel.add(serverListbox);
		panel.add(d);
		
		d.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.requestFocus();
			}
		});
		
		disconnectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.disconnect();
			}
		});
		
		hostHameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ipField.setText("localhost");
				controller.hostGame();
			}
		});
		
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.joinGame(ipField.getText());
				//controller.joinGame(serverListbox.getSelectedValue().toString());
			}
		});
		
		refreshServersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.refreshServerlist();
				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null, "Error finding servers");
				}
			}
		});
		
		serverListbox.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String[] a = serverListbox.getSelectedValue().split(":");
				ipField.setText(a[0]);
				portField.setText(a[1]);
			}
		});
		
		
		

		springLayout = new SpringLayout();
		panel.setLayout(springLayout);
		
		
		springLayout.putConstraint(SpringLayout.NORTH, buttonPanel, 10,
				SpringLayout.NORTH, panel);
		
		springLayout.putConstraint(SpringLayout.NORTH, inputPanel, 10,
				SpringLayout.SOUTH, buttonPanel);
		
		
		springLayout.putConstraint(SpringLayout.NORTH, serverListbox, 10,
				SpringLayout.SOUTH, inputPanel);
		springLayout.putConstraint(SpringLayout.WEST, serverListbox, 10,
				SpringLayout.WEST, panel);
		springLayout.putConstraint(SpringLayout.WEST, d, 10,
				SpringLayout.EAST, buttonPanel);
		springLayout.putConstraint(SpringLayout.NORTH, d, 10,
				SpringLayout.NORTH, panel);
		
		
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 10,
				SpringLayout.SOUTH, d);
		springLayout.putConstraint(SpringLayout.EAST, panel, 10,
				SpringLayout.EAST, d);
		connectButton.setEnabled(true);
		hostHameButton.setEnabled(true);
		disconnectButton.setEnabled(false);
		refreshServersButton.setEnabled(true);
		
		frame.add(panel);
		st = new KeyboardState();
		frame.addKeyListener(st);
		frame.setFocusable(true);
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
			
		new Thread(controller).start();
	}
	
	
	@Override
	public void update(Observable caller, Object message) {
	
		if(message != null){
			if(message.getClass() == Vector.class){
				serverListbox.setListData((Vector<String>) message);
			}else{
				if((caller == controller) && (message != null)){
					if(message.toString().compareTo("disconnected") == 0){
						connectButton.setEnabled(true);
						hostHameButton.setEnabled(true);
						disconnectButton.setEnabled(false);
						refreshServersButton.setEnabled(true);
					}else if(message.toString().compareTo("connected") == 0){
						connectButton.setEnabled(false);
						hostHameButton.setEnabled(false);
						disconnectButton.setEnabled(true);
						refreshServersButton.setEnabled(false);
					}
				}
			}			
		}
		if (caller == controller) { //controller ropar
			controller.updateInput(st);
			d.repaint();
		}
		

		
	}
}
	
	
