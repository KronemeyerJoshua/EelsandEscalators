import java.io.*;
import java.net.*;

public class Client {
	
	// Declare Vars
	private boolean myTurn = false;
	private boolean wait = true;
	private String host = "127.0.0.1";
	private int port = 9001;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	
	
	public static void main(String[] args) {
		Client client = new Client();
	}
	
	public Client() {
		getServerInfo();
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
