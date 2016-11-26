import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.util.Date;

public class EelsAndEscalatorsServer extends JFrame implements EelsAndEscalatorsInterface {
	
	public static void main(String[] args) {
		EelsAndEscalatorsServer server = new EelsAndEscalatorsServer();
	}
	
	// Default Constructor
	public EelsAndEscalatorsServer() {	
		
		JPanel contentPane;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(860, 440, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 148, 203));
		contentPane.add(panel, BorderLayout.SOUTH);
        
        //Text Area, use outputText.append() to send stuff
        JTextArea outputText = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputText);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
        
		// Attempt to Host Server
		try {
			// Harr harr, server socket is over 9000
			ServerSocket socket = new ServerSocket(9001);

			while (true) {
				
				// Connect our players
				Socket player1 = socket.accept();
				
				// DEBUGGING_NOTE: Players 2-4 have been disabled to facilitate testing purposes
				//Socket player2 = socket.accept();
				//Socket player3 = socket.accept();
				//Socket player4 = socket.accept();
				
				// Assign our players ID's
				outputText.append("Player 1 now connecting from " + player1.getInetAddress().getHostAddress());
				new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);
				
				// DEBUGGING_NOTE: Players 2-4 have been disabled to facilitate testing purposes
				//new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER2);
				//new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER3);
				//new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER4);
				
				// DEBUGGING NOTE: NEED TO ADD PLAYERS TO CONSTRUCTOR
				//2-4 WHEN TESTING OF PLAYER1 IS FINISHED
				
				GameSession session = new GameSession(player1);
				new Thread(session).start();
			}
		}
		catch (Exception e) {
			outputText.append(e.toString());
		}
	}
	
	
}


class GameSession extends JPanel implements Runnable, EelsAndEscalatorsInterface {
	
	// Global Vars
	int numOfPlayers;
	int[] turnOrder;
	int[] playerCharacterChoice;
	
	// Create our sockets to player clients
	private Socket player1;
	//private Socket player2;
	//private Socket player3;
	//private Socket player4;
	
	// Constructor
	// DEBUGGING NOTE: NEED TO ADD PLAYERS TO CONSTRUCTOR
	//2-4 WHEN TESTING OF PLAYER1 IS FINISHED
	//@param1 Our socket connection to player client
	public GameSession(Socket player1) {
		
		// Initialize our player connections before game start
		this.player1 = player1;
		// this.player2 = player2;
		// this.player3 = player3;
		// this.player4 = player4;
	}
	
	public void run() {
		try {
			// Initialize our in and out streams to send/receive from our player clients
			DataInputStream fromP1 = new DataInputStream(player1.getInputStream());
			//DataInputStream fromP2 = new DataInputStream(player2.getInputStream());
			//DataInputStream fromP3 = new DataInputStream(player3.getInputStream());
			//DataInputStream fromP4 = new DataInputStream(player4.getInputStream());
			
			DataOutputStream toP1 = new DataOutputStream(player1.getOutputStream());
			//DataOutputStream toP2 = new DataOutputStream(player2.getOutputStream());
			//DataOutputStream toP3 = new DataOutputStream(player3.getOutputStream());
			//DataOutputStream toP4 = new DataOutputStream(player4.getOutputStream());
			
			while (true) {
				switch (fromP1.readInt()) {
					case SEND_ROLL_REQUEST: // RollDice()
											// Update player info
											break;
					
				}
					
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
	
	
	}

	
	// Add a player to the game
	public void addPlayer() {
		if (numOfPlayers < 4) {
			numOfPlayers++;
		}
		else {
			System.out.println(MAX_CONNECTED);
		}
	}
	
	// @return Returns number of players in game
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	
	public int whosNext() {
		
		return 0;
	}	
	
}