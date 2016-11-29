import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.util.Date;
import java.util.Random;

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
				Socket player2 = socket.accept();
				Socket player3 = socket.accept();
				Socket player4 = socket.accept();
				
				// Assign our players ID's
				outputText.append("\nPlayer 1 now connecting from " + player1.getInetAddress().getHostAddress());
				new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);
				
				// DEBUGGING_NOTE: Players 2-4 have been disabled to facilitate testing purposes
				outputText.append("\nPlayer 2 now connecting from " + player2.getInetAddress().getHostAddress());
				new DataOutputStream(player2.getOutputStream()).writeInt(PLAYER2);
				
				outputText.append("\nPlayer 3 now connecting from " + player3.getInetAddress().getHostAddress());
				new DataOutputStream(player3.getOutputStream()).writeInt(PLAYER3);
				
				outputText.append("\nPlayer 4 now connecting from " + player4.getInetAddress().getHostAddress());
				new DataOutputStream(player4.getOutputStream()).writeInt(PLAYER4);
				
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
	int numOfPlayers, currentPlayerTurn, dice1, dice2, totalDice;
	int[] turnOrder;
	int[] playerCharacterChoice;
	private Random rand = new Random();
	
	//Create the map tiles
	final int MAP_X = 10; int MAP_Y = 3;
	private Tile<Player>[][] map; //map
	
	// Create our sockets to player clients
	private Socket player1;
	private Socket player2;
	private Socket player3;
	private Socket player4;
	
	//input and output steams
	private DataInputStream fromPlayer1;
	private DataOutputStream toPlayer1;
	private DataInputStream fromPlayer2;
	private DataOutputStream toPlayer2;
	private DataInputStream fromPlayer3;
	private DataOutputStream toPlayer3;
	private DataInputStream fromPlayer4;
	private DataOutputStream toPlayer4;
	
	// Constructor
	// DEBUGGING NOTE: NEED TO ADD PLAYERS TO CONSTRUCTOR
	//2-4 WHEN TESTING OF PLAYER1 IS FINISHED
	//@param1 Our socket connection to player client
	public GameSession(Socket player1) {
		
		// Initialize our player connections before game start
		this.player1 = player1;
		
		genMapDefault(); //generate map
	}
	
	public GameSession(Socket player1, Socket player2) {
		
		// Initialize our player connections before game start
		this.player1 = player1;
		this.player2 = player2;
		
		genMapDefault(); //generate map
	}
	
	public GameSession(Socket player1, Socket player2, Socket player3) {
		
		// Initialize our player connections before game start
		this.player1 = player1;
		this.player2 = player2;
		this.player3 = player3;
		
		genMapDefault(); ////generate map
	}
	
	public GameSession(Socket player1, Socket player2, Socket player3, Socket player4) {
		
		// Initialize our player connections before game start
		this.player1 = player1;
		this.player2 = player2;
		this.player3 = player3;
		this.player4 = player4;
		
		genMapDefault(); //generate map
	}
	
	public void run() {
		try {
			// Initialize our in and out streams to send/receive from our player clients
			DataInputStream fromP1 = new DataInputStream(player1.getInputStream());
			DataInputStream fromP2 = new DataInputStream(player2.getInputStream());
			DataInputStream fromP3 = new DataInputStream(player3.getInputStream());
			DataInputStream fromP4 = new DataInputStream(player4.getInputStream());
			
			DataOutputStream toP1 = new DataOutputStream(player1.getOutputStream());
			DataOutputStream toP2 = new DataOutputStream(player2.getOutputStream());
			DataOutputStream toP3 = new DataOutputStream(player3.getOutputStream());
			DataOutputStream toP4 = new DataOutputStream(player4.getOutputStream());
			
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
		if(currentPlayerTurn < 4)
			return currentPlayerTurn+1;
		else
			return 0;	
	}	
	
	public int setNext() {
		if(currentPlayerTurn < 4)
			return currentPlayerTurn++;
		else
			return currentPlayerTurn = 0;	
	}	
	
	
	public int rollDice(){	
		return rand.nextInt() % 5 +1 ; // dice implementation
	}
	
	public void genMapDefault(){ //regular map generation - added to server TODO
		map = new Tile[MAP_Y][MAP_X];
		for(int x = 0; x < MAP_X; x++){
			for(int y = 0; y < MAP_Y; y++){
				map[y][x] = new Tile<Player>(x, y);
			}
		}
		map[0][0].setStart(true);
		map[0][1].setEel1(true);
		map[0][9].setEel1(true);
		map[0][5].setEel2(true);
		
	}
	
	public void genMapRandom(){ //generates a random map - TODO
		
	}
	
	public void movePlayer(Player player){ //used after the dice has been rolled
		map[player.getYLocation()][player.getXLocation()].removePlayer(player); //remove player from tile
		int moveAmount;
		
		while(totalDice > 0){
			if(player.getXLocation()+totalDice > MAP_X-1 || (player.getXLocation()-totalDice < 0 && player.getYLocation() == 1)){
				moveAmount = (MAP_X-1) - player.getXLocation(); //space between the end of the map and current Tile
				totalDice = totalDice - moveAmount - 1; //
				player.setXLocation(MAP_X-1);
				player.setYLocation(player.getYLocation()+1);
			}
			else if(player.getYLocation() == 1){ //moves player to the left if in the middle section
				player.setXLocation(player.getXLocation()-1);
				totalDice--;
			}
			else{
				player.setXLocation(player.getXLocation()+1);
				totalDice--;
			}
		}
		map[player.getYLocation()][player.getXLocation()].addPlayer(player); //add player to tile
	}
	
}