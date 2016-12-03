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
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EelsAndEscalatorsServer server = new EelsAndEscalatorsServer();
	}
	
	// Default Constructor
	public EelsAndEscalatorsServer() {	
		
		// BEGIN GUI BUILDING
		
		JPanel		contentPane, panel;
		JButton		exit;
		JTextArea	outputText;
		JScrollPane scrollPane;
		
		// MAIN WINDOW JPANEL
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		
		// PROPERTIES OF WINDOW
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(860, 440, 450, 300);
		setResizable(false);
		setContentPane(contentPane);
		
		
		
		// SOUTHERN PANEL FOR GUI OBJECTS
		panel = new JPanel();
		panel.setBackground(new Color(18, 148, 203));
		contentPane.add(panel, BorderLayout.SOUTH);
		
		// TERMINATE BUTTON
        exit = new JButton("Exit");
        panel.add(exit);

        // TEXTBOX FOR DEBUGGING AND CONNECTION INFO
        outputText = new JTextArea();
        scrollPane = new JScrollPane(outputText);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        setVisible(true);
        // TERMINATE BUTTON ACTION LISTENER
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// END GUI BUILDING
        
		// BEGIN HOSTING SERVER
		try {
			// THIS IS THE SOCKET OUR PLAYERS CONNECT ON
			ServerSocket socket = new ServerSocket(9001);
			
			// DEBUG INFO OUTPUT
			outputText.append(new Date() +
			        ": Server started at socket 9001\n");
			outputText.append("\n" + new Date() + ": Waiting for players...\n");
			
			// WAIT FOR CONNECTION OF PLAYERS, ACCEPT 4 PLAYERS THEN START
			// DEBUGGING NOTE: ONLY 1 PLAYER IS ACTIVE TO FACILITATE TESTING
			Socket player1 = socket.accept();
			outputText.append("\nPlayer 1 now connecting from " + player1.getInetAddress().getHostAddress());
			new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);
			//Notify that player is player 1
			
			/*
			Socket player2 = socket.accept();
			outputText.append("\nPlayer 2 now connecting from " + player2.getInetAddress().getHostAddress());
			//Notify that player is player 2
			new DataOutputStream(player2.getOutputStream()).writeInt(PLAYER2);
			
			Socket player3 = socket.accept();
			outputText.append("\nPlayer 3 now connecting from " + player3.getInetAddress().getHostAddress());
			//Notify that player is player 3
			new DataOutputStream(player3.getOutputStream()).writeInt(PLAYER3);
			
			Socket player4 = socket.accept();
			outputText.append("\nPlayer 4 now connecting from " + player4.getInetAddress().getHostAddress());
			//Notify that player is player 4
			new DataOutputStream(player4.getOutputStream()).writeInt(PLAYER4);
			*/
			
			// ALL PLAYERS HAVE BEEN CONNECTED
			// BEGIN OUR SERVER LOGIC LOOP
			outputText.append("\n" + MAX_CONNECTED);
			GameSession session = new GameSession(player1); //TODO
			new Thread(session).start();
		}
		catch (Exception e) {
			outputText.append(e.toString());
		}
		//outputText.append("\n\n" + new Date() + ": Server is Closed." );
		// END HOSTING SERVER
	}
	
	
}


class GameSession implements Runnable, EelsAndEscalatorsInterface {
	
	// Global Vars
	int numOfPlayers, currentPlayerTurn, x, y;
	int[] dice;
	Random rand = new Random();
	
	//Create the map tiles
	private Tile[] map = new Tile[30]; //map
	
	// Create our sockets to player clients
	private Socket player1Socket;
	private Socket player2Socket;
	private Socket player3Socket;
	private Socket player4Socket;
	
	// Players
	private Player player1, player2, player3, player4, currentPlayer;
	
	// OUR CURRENT PLAYER SELECTION IN AND OUT STREAM
	private DataInputStream currentIn;
	private DataOutputStream currentOut;
	
	// BEGIN CONSTRUCTORS
	// @param1 Our socket connection to player1 client
	public GameSession(Socket player1Socket) { 
		// Initialize our player connections before game start
		this.player1Socket = player1Socket;
		player1 = new Player(0);
		player2 = new Player(0);
		player3 = new Player(0);
		player4 = new Player(0);
		genMapDefault(); //generate map
		
	}
	
	// @param1 Our socket connection to player1 client
	// @param2 Our socket connection to player2 client
	public GameSession(Socket player1Socket, Socket player2Socket) {
		
		// Initialize our player connections before game start
		this.player1Socket = player1Socket;
		this.player2Socket = player2Socket;
		
		genMapDefault(); //generate map
		
	}
	
	// @param1 Our socket connection to player1 client
	// @param2 Our socket connection to player2 client
	// @param3 Our socket connection to player3 client
	public GameSession(Socket player1Socket, Socket player2Socket, Socket player3Socket) {
		
		// Initialize our player connections before game start
		this.player1Socket = player1Socket;
		this.player2Socket = player2Socket;
		this.player3Socket = player3Socket;
		
		genMapDefault(); //generate map
	}
	
	// @param1 Our socket connection to player1 client
	// @param2 Our socket connection to player2 client
	// @param3 Our socket connection to player3 client
	// @param4 Our socket connection to player4 client
	public GameSession(Socket player1Socket, Socket player2Socket, Socket player3Socket, Socket player4Socket) {
		
		// Initialize our player connections before game start
		this.player1Socket = player1Socket;
		this.player2Socket = player2Socket;
		this.player3Socket = player3Socket;
		this.player4Socket = player4Socket;
		
		genMapDefault(); //generate map
		
	}
	
	// END CONSTRUCTORS
	
	
	public void run() {
		try {
			currentPlayerTurn = 0;
			// Initialize our in and out streams to send/receive from our player clients
			// DEBUGGING NOTE: TODO PLAYER IN/OUT STREAMS ARE COMMENTED OUT FOR TESTING PURPOSES
			DataInputStream fromP1 = new DataInputStream(player1Socket.getInputStream());
			/*
			DataInputStream fromP2 = new DataInputStream(player2Socket.getInputStream());
			DataInputStream fromP3 = new DataInputStream(player3Socket.getInputStream());
			DataInputStream fromP4 = new DataInputStream(player4Socket.getInputStream());
			*/
			
			
			DataOutputStream toP1 = new DataOutputStream(player1Socket.getOutputStream());
			/*
			DataOutputStream toP2 = new DataOutputStream(player2Socket.getOutputStream());
			DataOutputStream toP3 = new DataOutputStream(player3Socket.getOutputStream());
			DataOutputStream toP4 = new DataOutputStream(player4Socket.getOutputStream());
			*/
			
			toP1.writeInt(PLAYER_WAIT);
			/*
			toP2.writeInt(PLAYER_WAIT);
			toP3.writeInt(PLAYER_WAIT);
			toP4.writeInt(PLAYER_WAIT);
			*/
			
			// SERVER MAIN LOGIC LOOP
			while (true) {
				// DEBUGGING NOTE: TODO ALL TURNS ARE PLAYER1 FOR TESTING PURPOSES
				// BELOW DECIDES WHICH PLAYER IS THE CURRENT PLAYER
				currentPlayerTurn = whosNext(currentPlayerTurn);
				switch(currentPlayerTurn) {
					case PLAYER1:
						currentIn = fromP1;
						currentOut = toP1;
						currentPlayer = player1;
						break;
						
					case PLAYER2:
						currentIn = fromP1;
						currentOut = toP1;
						currentPlayer = player2;
						break;
						
					case PLAYER3:
						currentIn = fromP1;
						currentOut = toP1;
						currentPlayer = player3;
						break;
						
					case PLAYER4:
						currentIn = fromP1;
						currentOut = toP1;
						currentPlayer = player4;
						break;
				}
				
				// TELL THE CLIENT THAT IT IS THIS PLAYERS TURN
				if (currentPlayer.getLost() == false) {
					currentOut.writeInt(PLAYER_GO);	
				// RECEIVE THE REQUEST FROM THE CLIENT
					switch (currentIn.readInt()) {
						case SEND_ROLL_REQUEST:
							dice = rollDice();
							movePlayer(currentPlayer);
							currentOut.writeInt(dice[0]);
							currentOut.writeInt(dice[1]);
							currentOut.writeInt(PLAYER_WAIT);
							
							// TODO 
							sendMove(toP1, x, y, (currentPlayerTurn));
							/*sendMove(toP2, x, y, (currentPlayerTurn+2));
							sendMove(toP3, x, y, (currentPlayerTurn+2));
							sendMove(toP4, x, y, (currentPlayerTurn+2));*/
							
							if (currentPlayer.getPosition() > 28) {
								toP1.writeInt(PLAYER_WON);
								/*toP2.writeInt(PLAYER_WON);
								toP3.writeInt(PLAYER_WON);
								toP4.writeInt(PLAYER_WON);*/
							}
								
							if (currentPlayer.getLost()) {
								toP1.writeInt(PLAYER_LOST);
								/*toP2.writeInt(PLAYER_LOST);
								toP3.writeInt(PLAYER_LOST);
								toP4.writeInt(PLAYER_LOST);*/
							}
							
						break;
					}
				}
				}
			}
		catch (Exception e) {
			System.out.println("SERVER LOOP: " + e.toString());
		}
		// END SERVER MAIN LOGIC LOOP
	}
	
	private void sendMove(DataOutputStream out, int x, int y, int playerTurn) throws IOException{
		out.writeInt(END_PLAYER_TURN);
		out.writeInt(playerTurn);
		out.writeInt(x);
		out.writeInt(y);
	}
	
	
	private int whosNext(int c) {
		if (c > 3)
			c = 1;
		else 
			c += 1;
		return c;
	}
	
	public int[] rollDice(){
		int[] dice = { rand.nextInt(6) +1, rand.nextInt(6) +1 };
		return dice; // dice implementation
	}
	
	public void genMapDefault(){ //regular map generation 
		
		for (int i = 0; i < 30; i++) {
			map[i] = new Tile();
		}
		
		// Set Start & Finish
		map[0].setStart();
		map[29].setWin();
		map[13].setLose();
		
		
		// Set Positions
		// y = 0
		map[0].setPosition(424,775);
		map[1].setPosition(547,775); // EEL HEAD R1C1
		map[2].setPosition(658,775);
		map[3].setPosition(835,268); //ESCALATOR BOTTOM R1C3
		map[4].setPosition(872,775);
		map[5].setPosition(982,775); // EEL END R1C5
		map[6].setPosition(1096,775);
		map[7].setPosition(1184,507); // ESCALATOR BOTTOM R1C7
		map[8].setPosition(1308,775);
		map[9].setPosition(1434,775); // EEL END R1C9
		
		// y = 1
		map[10].setPosition(1420,507); 
		map[11].setPosition(1292,507); 
		map[12].setPosition(1179,507); // ESCALATOR TOP R2C7
		map[13].setPosition(1080,507); 
		map[14].setPosition(964,507);
		map[15].setPosition(859,507);
		map[16].setPosition(749,507); // EEL END R2C3
		map[17].setPosition(628,507); 
		map[18].setPosition(514,507); // ESCALATOR BOTOM R2C8
		map[19].setPosition(395,507); // EEL HEAD R2C9
		
		// y = 2
		map[20].setPosition(380,268); // ESCALATOR TOP R3C0
		map[21].setPosition(510,268);
		map[22].setPosition(616,268); // EEL END R3C2
		map[23].setPosition(734,268);
		map[24].setPosition(836,268); // ESCALATOR TOP R3C4
		map[25].setPosition(949,268);
		map[26].setPosition(1064,268);
		map[27].setPosition(982,775); // EEL HEAD R3C7
		map[28].setPosition(1275,268);
		map[29].setPosition(1396,268);
		
	}
	
	// Moves players to the appropriate tile
	public void movePlayer(Player player) { //used after the dice has been rolled
		player.setEel(false);
		player.setEsc(false);
		int pos = player.getPosition();
		map[pos].removePlayer(); //remove player from tile
		int totalDice = dice[0] + dice[1];
		switch (pos + totalDice) {
		// Eels
		case 1:
			pos = 9;
			player.setEel(true);
			break;
		case 27:
			pos = 5;
			player.setEel(true);
			break;
		case 19:
			pos = 22;
			player.setEel(true);
			break;
		case 16:
			pos = 0;
			player.setLost(true);
			break;
		// Escalators
		case 3:
			pos = 24;
			player.setEsc(true);
			break;	
		case 7:
			pos = 12;
			player.setEsc(true);
			break;
		case 18:
			pos = 20;
			player.setEsc(true);
			break;
		default:
			pos += totalDice;
			break;
		}
		
		// IF OUR POSITION IS OVER 28 CURRENT PLAYER WINS
		if ( pos > 28) {
			map[29].addPlayer();
			player.setPosition(29);
			x = map[29].getPositionX();
			y = map[29].getPositionY();
		}
		else {
			// Transfer to other tiles if Eel or Escalator -- already transfers to other pieces
			// Set x and y of player
			map[pos].addPlayer();
			player.setPosition(pos);
			x = map[pos].getPositionX();
			y = map[pos].getPositionY();
		
			switch (map[pos].getPlayers()) {
				case 2: 
					y -= 50;
					break;
				case 3:
					y -= 50;
					x -= 50;
					break;
				case 4:
					x -= 50;
					break;
			}
		
		}
}
}