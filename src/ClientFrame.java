import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.net.*;

public class ClientFrame extends JFrame {

	// Declare Vars
	private JLabel contentPane;
	private boolean myTurn = false;
	private boolean wait = true;
	private String host = "127.0.0.1";
	private int port = 9001;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Image img;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ClientFrame frame = new ClientFrame();
					frame.setVisible(true);
					
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
		setBounds(100, 100, 1920, 1080);
		setResizable(false);
		contentPane = new JLabel(new ImageIcon("src/EelsAndEscalatorsYouLose.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 148, 203));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnRoll = new JButton("Roll");
		panel.add(btnRoll);
		
		JLabel lblDi = new JLabel("Dice 1");
		panel.add(lblDi);
		
		JLabel lblDi_1 = new JLabel("Dice 2");
		panel.add(lblDi_1);
	
	}
	
	public void getServerInfo() {
		// Enter Server IP
		// Enter Server Port
		// Check format of IP & Port
		if (connectToServer(host,port)) {
			System.out.println("Successfully connected!");
		}
	}
	
	//@param1 Host of server
	//@param2 Port of server
	//@return Server connected status
	public boolean connectToServer(String h, int p) {
		try {
			// Create our socket to connect to the server
			Socket socket = new Socket(h, p);
			
			// Client in and out streams to and from the server
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
		}
		catch (Exception e) {
			
		}
		// Attempt to connect to server
		// If connection fails, return false
		// else
		return true;
	}

}