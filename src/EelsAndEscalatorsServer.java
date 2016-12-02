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
			//Notify that player is player 1
			new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);
			
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
			GameSession session = new GameSession(player1 /*, player2, player3, player4*/); //TODO
			new Thread(session).start();
			session.run();
		}
		catch (Exception e) {
			outputText.append(e.toString());
		}
		outputText.append("\n\n" + new Date() + ": Server is Closed." );
		// END HOSTING SERVER
	}
	
	
}


class GameSession implements Runnable, EelsAndEscalatorsInterface {
	
	// Global Vars
	int numOfPlayers, currentPlayerTurn, x, y;
	int[] playerCharacterChoice;
	int[] dice;
	Random rand = new Random();
	
	//Create the map tiles
	final int MAP_X = 10; int MAP_Y = 3;
	private Tile[][] map; //map
	
	// Create our sockets to player clients
	private Socket player1;
	private Socket player2;
	private Socket player3;
	private Socket player4;
	
	// OUR CURRENT PLAYER SELECTION IN AND OUT STREAM
	private DataInputStream currentIn;
	private DataOutputStream currentOut;
	
	// BEGIN CONSTRUCTORS
	// @param1 Our socket connection to player1 client
	public GameSession(Socket player1) { 
		// Initialize our player connections before game start
		this.player1 = player1;
		
		genMapDefault(); //generate map
		
	}
	
	// @param1 Our socket connection to player1 client
	// @param2 Our socket connection to player2 client
	public GameSession(Socket player1, Socket player2) {
		
		// Initialize our player connections before game start
		this.player1 = player1;
		this.player2 = player2;
		
		genMapDefault(); //generate map
		
	}
	
	// @param1 Our socket connection to player1 client
	// @param2 Our socket connection to player2 client
	// @param3 Our socket connection to player3 client
	public GameSession(Socket player1, Socket player2, Socket player3) {
		
		// Initialize our player connections before game start
		this.player1 = player1;
		this.player2 = player2;
		this.player3 = player3;
		
		genMapDefault(); //generate map
	}
	
	// @param1 Our socket connection to player1 client
	// @param2 Our socket connection to player2 client
	// @param3 Our socket connection to player3 client
	// @param4 Our socket connection to player4 client
	public GameSession(Socket player1, Socket player2, Socket player3, Socket player4) {
		
		// Initialize our player connections before game start
		this.player1 = player1;
		this.player2 = player2;
		this.player3 = player3;
		this.player4 = player4;
		
		genMapDefault(); //generate map
		
	}
	
	// END CONSTRUCTORS
	
	
	public void run() {
		try {
			currentPlayerTurn = -1;
			// Initialize our in and out streams to send/receive from our player clients
			// DEBUGGING NOTE: TODO PLAYER IN/OUT STREAMS ARE COMMENTED OUT FOR TESTING PURPOSES
			DataInputStream fromP1 = new DataInputStream(player1.getInputStream());
			/*
			DataInputStream fromP2 = new DataInputStream(player2.getInputStream());
			DataInputStream fromP3 = new DataInputStream(player3.getInputStream());
			DataInputStream fromP4 = new DataInputStream(player4.getInputStream());
			*/
			
			
			DataOutputStream toP1 = new DataOutputStream(player1.getOutputStream());
			/*
			DataOutputStream toP2 = new DataOutputStream(player2.getOutputStream());
			DataOutputStream toP3 = new DataOutputStream(player3.getOutputStream());
			DataOutputStream toP4 = new DataOutputStream(player4.getOutputStream());
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
				switch(PLAYER_TURN_ORDER[whosNext(currentPlayerTurn)]) {
					case PLAYER1_TURN:
						currentIn = fromP1;
						currentOut = toP1;
						
						break;
						
					case PLAYER2_TURN:
						currentIn = fromP1;
						currentOut = toP1;
						
						break;
						
					case PLAYER3_TURN:
						currentIn = fromP1;
						currentOut = toP1;
						
						break;
						
					case PLAYER4_TURN:
						currentIn = fromP1;
						currentOut = toP1;
						
						break;
				}
				
				// TELL THE CLIENT THAT IT IS THIS PLAYERS TURN
				currentOut.writeInt(whosNext(currentPlayerTurn));
				currentOut.writeInt(PLAYER_GO);
				
				// RECEIVE THE REQUEST FROM THE CLIENT
					switch (currentIn.readInt()) {
						case SEND_ROLL_REQUEST:
							dice = rollDice();
							movePlayer(currentPlayerTurn);
							currentOut.writeInt(dice[0]);
							currentOut.writeInt(dice[1]);

							// Sends to all
							sendMove(toP1, x, y);
							/* TODO
							sendMove(toP2, x, y);
							sendMove(toP3, x, y);
							sendMove(toP4, x, y);
							*/
							currentOut.writeInt(PLAYER_WAIT);
							break;	
					}

				}
			}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		// END SERVER MAIN LOGIC LOOP
	}
	
	private void sendMove(DataOutputStream out, int x, int y) throws IOException{
		out.writeInt(x);
		out.writeInt(y);
	}
	
	
	private int whosNext(int c) {
		if (c > 2) 
			c = 0;
		else
			c += 1;
		return c;
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

	public int[] rollDice(){
		int[] dice = { rand.nextInt(6) +1, rand.nextInt(6) +1 };
		return dice; // dice implementation
	}
	
	public void genMapDefault(){ //regular map generation 
		map = new Tile[MAP_Y][MAP_X];
		for(int x = 0; x < MAP_X; x++){
			for(int y = 0; y < MAP_Y; y++){
				map[y][x] = new Tile(x, y);
			}
		}
		// Set Start & Finish
		map[0][0].setStart();
		map[2][9].setWin();
		map[1][3].setLose();
		
		// Set Eels
		map[0][1].setEel1();
		map[0][9].setEel1();
		map[0][5].setEel2();
		map[2][7].setEel2();
		map[1][0].setEel3();
		map[2][2].setEel3();
		
		// Set Escalators
		map[0][3].setEscalator1H();
		map[2][4].setEscalator1T();
		map[0][7].setEscalator2H();
		map[1][7].setEscalator2T();
		map[1][1].setEscalator3H();
		map[2][0].setEscalator3T();
		
		// Set Positions
		// y = 0
		map[0][0].setPosition(424,775);
		map[0][1].setPosition(1434,775); // eel 1
		map[0][2].setPosition(658,775);
		map[0][3].setPosition(835,268); //escalator 1 H
		map[0][4].setPosition(872,775);
		map[0][5].setPosition(1162,268); //eel 2
		map[0][6].setPosition(1096,775);
		map[0][7].setPosition(1184,507); // escalator 2 H
		map[0][8].setPosition(1308,775);
		map[0][9].setPosition(547,775); // eel 1
		
		// y = 1
		map[1][0].setPosition(618,268); //eel 3
		map[1][1].setPosition(392,268); //escalator 3 H
		map[1][2].setPosition(628,507);
		map[1][3].setPosition(183,757); // You Lose
		map[1][4].setPosition(858,507);
		map[1][5].setPosition(966,507);
		map[1][6].setPosition(1081,507);
		map[1][7].setPosition(1184,507); // escalator 2 T
		map[1][8].setPosition(1293,507);
		map[1][9].setPosition(1424,507);
		
		// y = 2
		map[2][0].setPosition(392,268); //escalator 3 T
		map[2][1].setPosition(516,268);
		map[2][2].setPosition(392,507); //eel 3 
		map[2][3].setPosition(725,268);
		map[2][4].setPosition(835,268); //escalator 1 T
		map[2][5].setPosition(948,268);
		map[2][6].setPosition(1055,268);
		map[2][7].setPosition(985,775); //eel 2
		map[2][8].setPosition(1271,268);
		map[2][9].setPosition(1388,268);
		
	}
	
	public void genMapRandom(){ //generates a random map - TODO
		
	}
	
	//TODO - implement into server function
	public void movePlayer(int player){ //used after the dice has been rolled
		int moveAmount, val;
		int totalDice = dice[0] + dice[1];
		for(int a = 0; a < MAP_X; a++){
			for(int b = 0; b < MAP_Y; b++){				
					val = map[b][a].getArray(player);
					if(player == val){
						x = a;
						y = b;
					}
			}
		}
		
		map[y][x].removePlayer(player); //remove player from tile
		
		while(totalDice > 0){
			if(x + totalDice > MAP_X-1 || (x - totalDice < 0 && y == 1)){
				
				moveAmount = (MAP_X-1) - x; //space between the end of the map and current Tile
				totalDice = totalDice - moveAmount - 1; //set amount left
				
				x = MAP_X - 1;
				y++;
				
			}
			
			else if(y == 1){ //moves player to the left if in the middle section				
				x--;
				totalDice--;
			}
			
			else{
				x++;
				totalDice--;
			}
		}
		
		// Transfer to other tiles if Eel or Escalator -- already transfers to other pieces
		// Set x and y of player
		map[y][x].addPlayer(player);
		x = map[y][x].getPositionX();
		y = map[y][x].getPositionY();
		
	}


public class Tile extends JPanel {
	
	private static final long serialVersionUID = 1L;

	//private Player[] currentPlayers; //players on tile
	private int[] players;
	
	//Traits of tiles for the map
	//eel1 = 1; 
	//eel2 = 2; 
	//eel3 = 3; 
	//eel4 = 4;
	//escalator1H = 5; 
	//escalator2H = 6; 
	//escalator3H = 7;
	//escalator1T = 15; 
	//escalator2T = 16; 
	//escalator3T = 17;
	//win = 8;
	//lose = 9;
	//start = 10; 
	
	private int trait = 0; //indicates type of tile
	private int positionX = 0; //tile position
	private int positionY = 0;	
	
	
	
	public Tile(){
		players = new int[4];
	}
	
	public Tile(int posX, int posY){
		this();
		positionX = posX;
		positionY = posY;
	}
	
	public void addPlayer (int player){ 
		players[player] = player;
	}
	
	public void removePlayer(int player){ 
		players[player] = 4;
	}
	
	public void resetPlayers(){
		players = new int[4];
	}
	
	//mutators
	public void setTrait(int x){
		trait = x;
	}
	
	public void setEel1(){
		trait = 1;
	}
	
	public void setEel2(){
		trait = 2;
	}
	
	public void setEel3(){
		trait = 3;
	}
	
	public void setEel4(){
		trait = 4;
	}
	
	public void setEscalator1H(){
		trait = 5;
	}
	
	public void setEscalator2H(){
		trait = 6;
	}
	
	public void setEscalator3H(){
		trait = 7;
	}
	
	public void setEscalator1T(){
		trait = 15;
	}
	
	public void setEscalator2T(){
		trait = 16;
	}
	
	public void setEscalator3T(){
		trait = 17;
	}
	
	public void setWin(){
		trait = 8;
	}
	
	public void setLose(){
		trait = 9;
	}
	
	public void setStart(){
		trait = 10;
	}
	
	public void setPositionX(int posX){
		positionX = posX;
	}
	
	public void setPositionY(int posY){
		positionY = posY;
	}
	
	public void setPosition(int posX, int posY){
		positionX = posX;
		positionY = posY;
	}
	
	//accessors
	public boolean isEel1(){
		return trait == 1;
	}
		
	public boolean isEel2(){
		return trait == 2;
	}
		
	public boolean isEel3(){
		return trait == 3;
	}
		
	public boolean isEel4(){
		return trait == 4;
	}
		
	public boolean isEscalator1H(){
		return trait == 5;
	}
	
	public boolean isEscalator2H(){
		return trait == 6;
	}
	
	public boolean isEscalator3H(){
		return trait == 7;
	}
	
	public boolean isEscalator1T(){
		return trait == 15;
	}
	
	public boolean isEscalator2T(){
		return trait == 16;
	}
	
	public boolean isEscalator3T(){
		return trait == 17;
	}
		
	public boolean isWin(){
		return trait == 8;
	}
	
	public boolean isLose(){
		return trait == 9;
	}
		
	public boolean isStart(){
		return trait == 0;
	}
	
	public int getPositionX(){
		return positionX;
	}
		
	public int getPositionY(){
		return positionY;
	}
	
	public int getTrait(){
		return trait;
	}
	/*
	public Player getPlayerArray(int index){
		return currentPlayers[index];
	}
	
	public Player[] getPlayerArray(){
		return currentPlayers;
	}
	*/
	public int getArray(int index){
		return players[index];
	}
	
	public int[] getArray(){
		return players;
	}
	
}
}