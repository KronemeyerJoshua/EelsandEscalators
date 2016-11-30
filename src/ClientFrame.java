import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.net.*;
import java.util.Random;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ClientFrame extends JFrame implements EelsAndEscalatorsInterface, Runnable {

	// Declare Vars
	private JLabel contentPane;
	private JTextArea outputText;
	private boolean myTurn = false;
	private boolean wait = true;
	private String host = "127.0.0.1";
	private int port = 9001;
	private DataInputStream inputStream; //fromServer
	private DataOutputStream outputStream; //toServer
	private Image img;
	private boolean connected;
	private String myToken;
	private boolean continueToPlay = true;
	private JLabel statusIndicator = new JLabel();
	private int dice1, dice2, totalDice, player;
	private Random rand = new Random();
	int numOfPlayers, currentPlayerTurn;
	private final int MAP_X = 10; int MAP_Y = 3;
	private Tile<Player>[][] map; //map

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ClientFrame frame = new ClientFrame();
					frame.connectToServer();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1844, 1080);
		setResizable(false);
		contentPane = new JLabel(new ImageIcon("src/EelsAndEscalatorsYouLose.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 148, 203));
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JTextArea outputText = new JTextArea(30, 30);
        JScrollPane scrollPane = new JScrollPane(outputText);
        contentPane.add(scrollPane, BorderLayout.EAST);
		
        statusIndicator.setBorder(new LineBorder(Color.black, 1)); //TODO - implement
        add(statusIndicator, BorderLayout.NORTH);
        
		JButton btnRoll = new JButton("Roll");
		panel.add(btnRoll);
		
		btnRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { //TODO - finish
					if(myTurn)
						outputStream.writeInt(SEND_ROLL_REQUEST);
					else if(!connected){
						outputText.append("\n" + CONNECTING_MESSAGE);
						connectToServer();
					}
					else
						outputText.append("\n" + WAIT_MESSAGE);
					
				} catch (Exception err) {
					System.out.println(err.toString());
				}
			}
		});
		
		JLabel lblDi = new JLabel("Dice 1");
		panel.add(lblDi);
		
		JLabel lblDi_1 = new JLabel("Dice 2");
		panel.add(lblDi_1);
		
		
		//TODO - Add JTextArea hider/opener
		//outputText.addComponentListener(new addKeyListener(T) {	
		//});
		
	}
	
	public void getServerInfo() {
		// Enter Server IP
		// Enter Server Port
		// Check format of IP & Port
		System.out.println("IP: " + host);
		System.out.println("Port: " + port);
		System.out.println("Connection: " + connected);
	}
	
	//@param1 Host of server
	//@param2 Port of server
	//@return Server connected status
	//TODO - bool return != necessary
	public boolean connectToServer() {
		
		try {
			// Create our socket to connect to the server
			Socket socket = new Socket(host, port);
			
			// Client in and out streams to and from the server
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
		}
		catch (Exception e) {
			connected = false;
		}
		// Attempt to connect to server
		// If connection fails, return false
		// else
		Thread thread = new Thread(this);
	    thread.start();
		connected = true;
		
	    getServerInfo();
		return true;
	}

	//TODO
	public void run() {

		try{
			genMapDefault();
			player = inputStream.readInt();
			String input;
			
			if(player == PLAYER1){
				outputText.append("\n" + CHARSELECTMESSAGE +
						"\nEnter <1> for Mr. Krabs" +
						"\nEnter <2> for Squidward" +
						"\nEnter <3> for SpongeBob" +
						"\nEnter <4> for Patrick\n");
				input = outputText.getText(outputText.getLineCount(), 0); //has not been tested
				
				outputText.append("\nPlayer 1");
				
				//TODO - add status indicator and info on connections
				//TODO - add player start indication
			}
			
			else if(player == PLAYER2){
				outputText.append("\n" + CHARSELECTMESSAGE +
						"\nEnter <1> for Mr. Krabs" +
						"\nEnter <2> for Squidward" +
						"\nEnter <3> for SpongeBob" +
						"\nEnter <4> for Patrick\n");
				input = outputText.getText(); //has not been tested
				
				outputText.append("\nPlayer 2");
			}
			
			else if(player == PLAYER3){
				outputText.append("\n" + CHARSELECTMESSAGE +
						"\nEnter <1> for Mr. Krabs" +
						"\nEnter <2> for Squidward" +
						"\nEnter <3> for SpongeBob" +
						"\nEnter <4> for Patrick\n");
				input = outputText.getText(); //has not been tested
				
				outputText.append("\nPlayer 3");
			}
			
			else{
				outputText.append("\n" + CHARSELECTMESSAGE +
						"\nEnter <1> for Mr. Krabs" +
						"\nEnter <2> for Squidward" +
						"\nEnter <3> for SpongeBob" +
						"\nEnter <4> for Patrick\n");
				input = outputText.getText(); //has not been tested
				
				outputText.append("\nPlayer 4");
			}
			
			switch(input){
			case "1":
				myToken =  MRKRABS_TOKEN;
				break;
			
			case "2":
				myToken = SQUIDWARD_TOKEN;
				break;
			
			case "3":
				myToken = SPONGEBOB_TOKEN;
				break;
				
			case "4":
				myToken = PATRICK_TOKEN;
				break;
			}
			
			while(continueToPlay){
				if(player == PLAYER1){
					receiveInfoFromServer();
					waitForPlayerAction();
					sendMove();
				}
				
				else if(player == PLAYER2){
					receiveInfoFromServer();
					waitForPlayerAction();
					sendMove();
				}
				
				else if(player == PLAYER3){
					receiveInfoFromServer();
					waitForPlayerAction();
					sendMove();
				}
				else{
					receiveInfoFromServer();
					waitForPlayerAction();
					sendMove();
				}
			}
		}
		catch(Exception ex){
			
		}
	}

	private void sendMove() throws IOException { //TODO - check
		outputStream.writeInt(SEND_ROLL_REQUEST);
	}
	
	private void waitForPlayerAction()throws InterruptedException{ //TODO - check
		while(wait){
				Thread.sleep(100);
		}	
		wait = true;
	}
	
	private void receiveInfoFromServer() throws IOException {
		int currentStatus = inputStream.readInt();
		
		if(currentStatus == PLAYER1_WIN){
			continueToPlay = false;
			outputText.append("\nPlayer 1 wins!");
			if(player != PLAYER1){  //if not me
				receiveMove(PLAYER1);
			}
		}
		
		else if(currentStatus == PLAYER2_WIN){
			continueToPlay = false;
			outputText.append("\nPlayer 2 wins!");
			if(player != PLAYER2){  //if not me
				receiveMove(PLAYER2);
			}
		}
		
		else if(currentStatus == PLAYER3_WIN){
			continueToPlay = false;
			outputText.append("\nPlayer 3 wins!");
			if(player != PLAYER3){  //if not me
				receiveMove(PLAYER3);
			}
		}
		
		else if(currentStatus == PLAYER4_WIN){
			continueToPlay = false;
			outputText.append("\nPlayer 4 wins!");
			if(player != PLAYER4){  //if not me
				receiveMove(PLAYER4);
			}
		}
		else if(currentStatus == PLAYER1_LOSE){ //TODO - properly implement losing characteristics
			continueToPlay = false;
			outputText.append("\nPlayer 1 loses!");
			if(player != PLAYER1){  //if not me
				receiveMove(PLAYER1);
			}
		}
		
		else if(currentStatus == PLAYER2_LOSE){
			continueToPlay = false;
			outputText.append("\nPlayer 2 loses!");
			if(player != PLAYER2){  //if not me
				receiveMove(PLAYER2);
			}
		}
		
		else if(currentStatus == PLAYER3_LOSE){
			continueToPlay = false;
			outputText.append("\nPlayer 3 loses!");
			if(player != PLAYER3){  //if not me
				receiveMove(PLAYER3);
			}
		}
		
		else if(currentStatus == PLAYER4_LOSE){
			continueToPlay = true;
			outputText.append("\nPlayer 4 loses!");
			if(player != PLAYER4){  //if not me
				receiveMove(PLAYER4);
			}
		}
		
		else if(currentStatus == PLAYER1){ //player1's turn
			receiveMove(PLAYER1);
			if(player == PLAYER1){  //if me
				outputText.append("\nYour turn!" + ROLLDICEMSG);
				myTurn = true;
			}
		}
		
	
		else if(currentStatus == PLAYER2){ //player2's turn
			receiveMove(PLAYER2);
			if(player == PLAYER2){  //if me
				outputText.append("\nYour turn!" + ROLLDICEMSG);
				myTurn = true;
			}
		}
		
		else if(currentStatus == PLAYER3){ //player3's turn
			receiveMove(PLAYER3);
			if(player == PLAYER3){ //if me
				outputText.append("\nYour turn!" + ROLLDICEMSG);
				myTurn = true;
			}
		}
		
		else if(currentStatus == PLAYER4){ //player4's turn
			receiveMove(PLAYER4);
			if(player == PLAYER4){  //if me
				outputText.append("\nYour turn!" + ROLLDICEMSG);
				myTurn = true;
			}
		}
	}

	
	private void receiveMove(int currentPlayer,int  x,int y){
        switch (currentPlayer){
            case 1:
                PLAYER1.setXLocation(x);
                PLAYER1.setXLocation(y);
                break
            case 2:
                PLAYER2.setXLocation(x);
                PLAYER2.setXLocation(y);
                break
            case 3:
                PLAYER3.setXLocation(x);
                PLAYER3.setXLocation(y);
                break
            case 4:
                PLAYER4.setXLocation(x);
                PLAYER4.setXLocation(y);
                break
                
        }
        
        
	}
	
	public int rollDice(){	
		return rand.nextInt() % 5 +1 ; // dice implementation
	}
	
	public void movePlayer(Player player){ //used after the dice has been rolled //TODO - redesign for player system
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
}
