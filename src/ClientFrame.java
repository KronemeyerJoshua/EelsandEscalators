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
	JLabel playerSprite, playerSprite2, playerSprite3, playerSprite4;
	
	private boolean connected;
	private JLabel statusIndicator = new JLabel();
	private int player, myNumber;
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
		// Player 1
		ImageIcon plSprite = new ImageIcon("src/123.png"); 
		Image image = plSprite.getImage(); 
		Image newimg = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		plSprite = new ImageIcon(newimg);
		playerSprite = new JLabel(plSprite);
		
		// Player 2
		ImageIcon plSprite2 = new ImageIcon("src/124.png"); 
		Image image2 = plSprite2.getImage(); 
		Image newimg2 = image2.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		plSprite2 = new ImageIcon(newimg2);
		playerSprite2 = new JLabel(plSprite2);
		
		// Player 3
		ImageIcon plSprite3 = new ImageIcon("src/125.png"); 
		Image image3 = plSprite3.getImage(); 
		Image newimg3 = image3.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		plSprite3 = new ImageIcon(newimg3);
		playerSprite3 = new JLabel(plSprite3);
		
		// Player 4
		ImageIcon plSprite4 = new ImageIcon("src/126.png"); 
		Image image4 = plSprite4.getImage(); 
		Image newimg4 = image4.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		plSprite = new ImageIcon(newimg4);
		playerSprite4 = new JLabel(plSprite4);
		
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
		// Player 1
		contentPane.add(playerSprite);
		playerSprite.setLocation(400, 687);
		playerSprite.setSize(40,40);
		
		// Player 2
		contentPane.add(playerSprite2);
		playerSprite2.setLocation(400, 730);
		playerSprite2.setSize(40,40);
		
		// Player 3
		contentPane.add(playerSprite3);
		playerSprite3.setLocation(400, 772);
		playerSprite3.setSize(40,40);
		
		// Player 4
		contentPane.add(playerSprite4);
		playerSprite4.setLocation(400, 813);
		playerSprite4.setSize(40,40);
		
		scrollPane.setVisible(true);
		outputText.setVisible(true);
		contentPane.setVisible(true);
		setVisible(true);
		//TODO - Add JTextArea hider/opener
		//outputText.addComponentListener(new addKeyListener(T) {	
		//});
		btnRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 
					if(myTurn) {
						
						outputStream.writeInt(SEND_ROLL_REQUEST);
						int dii = inputStream.readInt();
						lblDi.setText(Integer.toString(dii));
						dii = inputStream.readInt();
						lblDi_1.setText(Integer.toString(dii));
						
						int player = inputStream.readInt();
						int x = inputStream.readInt();
						int y = inputStream.readInt();
						receiveMove(player, x, y);
						waiting = false;
					}
						
					else
						outputText.append("\n" + WAIT_MESSAGE);
					
				} catch (Exception err) {
					outputText.append(err.toString());
				}
			}
		});
		
		/*contentPane.addMouseListener(new MouseListener() {
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

		});*/
		
	}
	
	
	public void getServerInfo() {
		// Enter Server IP
		// Enter Server Port
		// Check format of IP & Port
		try{
			System.out.println("IP: " + host);
			System.out.println("Port: " + port);
			System.out.println("Connection: " + connected);
		} catch(Exception e){
			System.out.println("Error with getServerInfo()");
		}
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
			
			connected = true;
			getServerInfo();
			Thread thread = new Thread(this);
		    thread.start();
			return true;
			
		}
		catch (Exception e) {
			connected = false;
			getServerInfo();
			System.out.println("Problem with connectToServer()");
			return false;
			
		}	
	}
	

	//TODO -need to fix
	public void run() {
		while(true) {
			try {
				int currentStatus = inputStream.readInt();
				System.out.println(currentStatus);
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
				System.out.println(ex.toString());
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
            	playerSprite.setLocation(x, y); 
                break;
            case 2:
            	playerSprite2.setLocation(x, y);
                break;
            case 3:
            	playerSprite3.setLocation(x, y); 
                break;
            case 4:
            	playerSprite4.setLocation(x, y); 
                break;
                
        } 
	}
}
