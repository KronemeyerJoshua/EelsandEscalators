import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

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
	private boolean waiting = true;
	private Image img;
	JButton btnRoll;
	JLabel lblDi;
	
	private boolean connected;
	private int myToken;
	private Player myPlayer, player1, player2, player3, player4;
	private JLabel statusIndicator = new JLabel();
	private int player;
	int numOfPlayers, currentPlayerTurn;
	

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
		// TESTING PLAYER SPRITE IMAGE RESCALING
		ImageIcon plSprite = new ImageIcon("src/123.png"); 
		Image image = plSprite.getImage(); 
		Image newimg = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		plSprite = new ImageIcon(newimg);
		JLabel playerSprite = new JLabel(plSprite);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1844, 1080);
		setResizable(false);
		contentPane = new JLabel(new ImageIcon("src/EelsAndEscalatorsYouLose.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 148, 203));
		contentPane.add(panel);
		panel.setSize(50,80);
		panel.setLocation(50,900);
		
		JTextArea outputText = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputText);
        contentPane.add(scrollPane);
        scrollPane.setSize(200,800);
        scrollPane.setLocation(1600,100);
		
        statusIndicator.setBorder(new LineBorder(Color.black, 1)); //TODO - implement
        add(statusIndicator, BorderLayout.NORTH);
        
		btnRoll = new JButton("Roll");
		panel.add(btnRoll);
		
		JLabel lblDi = new JLabel("Dice 1");
		panel.add(lblDi);
		
		JLabel lblDi_1 = new JLabel("Dice 2");
		panel.add(lblDi_1);
		
		// ADD PLAYER SPRITE
		contentPane.add(playerSprite);
		playerSprite.setLocation(400, 700);
		playerSprite.setSize(40,40);
		
		setVisible(true);
		//TODO - Add JTextArea hider/opener
		//outputText.addComponentListener(new addKeyListener(T) {	
		//});
		btnRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { //TODO - finish
					if(myTurn) {
						outputStream.writeInt(SEND_ROLL_REQUEST);
						int dii = inputStream.readInt();
						lblDi.setText(Integer.toString(dii));
						dii = inputStream.readInt();
						lblDi_1.setText(Integer.toString(dii));
						waiting = false;
					}
						
					else
						outputText.append("\n" + WAIT_MESSAGE);
					
				} catch (Exception err) {
					outputText.append(err.toString());
				}
			}
		});
		
		contentPane.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				outputText.append("X: " + x + ", Y: " + y + "\n");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

		});
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
		return true;
	}
	
/*	player = inputStream.readInt();
	switch(player){
		case 1:
			myToken =  MRKRABS_TOKEN;
			myPlayer = new Player(player, MRKRABS_TOKEN);
			player1 = new Player(player, MRKRABS_TOKEN);
			break;
	
		case 2:
			myToken = SQUIDWARD_TOKEN;
			myPlayer = new Player(player, SQUIDWARD_TOKEN);
			player2 = new Player(player, SQUIDWARD_TOKEN);
			break;
		
		case 3:
			myToken = SPONGEBOB_TOKEN;
			myPlayer = new Player(player, SPONGEBOB_TOKEN);
			player3 = new Player(player, SPONGEBOB_TOKEN);
			break;
		
		case 4:
			myToken = PATRICK_TOKEN;
			myPlayer = new Player(player, PATRICK_TOKEN);
			player4 = new Player(player, PATRICK_TOKEN);
			break;
	}*/

	//TODO -need to fix
	public void run() {
		while(true) {
			try {
				int currentStatus = inputStream.readInt();
				switch(currentStatus) {
				
					case PLAYER_WAIT:
						myTurn = false;
						break;
						
					case PLAYER_GO:
						myTurn = true;
						waitForPlayer();
						break;
				}
			}
			catch (Exception ex) {
				outputText.append(ex.toString());
			}
			}
		}
	
	  private void waitForPlayer() throws InterruptedException {
		    while (waiting) {
		      Thread.sleep(100);
		    }

		    waiting = true;
		  }

	
	private void receiveMove(int currentPlayer, int x ,int y){
        switch (currentPlayer){
            case 1:
                player1.setXLocation(x);
                player1.setYLocation(y);
                break;
            case 2:
                player2.setXLocation(x);
                player2.setYLocation(y);
                break;
            case 3:
                player3.setXLocation(x);
                player3.setYLocation(y);
                break;
            case 4:
                player4.setXLocation(x);
                player4.setYLocation(y);
                break;
                
        } 
	}
}
