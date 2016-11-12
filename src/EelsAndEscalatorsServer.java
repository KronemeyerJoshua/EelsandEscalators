import java.net.*;
import java.io.*;

public class EelsAndEscalatorsServer implements EelsAndEscalatorsInterface {
	
	// Global Vars
	int numOfPlayers;
	int[] turnOrder;
	int[] playerCharacterChoice;
	
	public static void main(String[] args) {
	}
	
	// Default Constructor
	public void EelsAndEscalatorServer() {
		numOfPlayers = 0;
		turnOrder = new int[4];
		playerCharacterChoice = new int[4];
		
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
				new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);
				
				// DEBUGGING_NOTE: Players 2-4 have been disabled to facilitate testing purposes
				//new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER2);
				//new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER3);
				//new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER4);
				
			}
		}
		catch (Exception e) {
			
		}
	}
	
	// Add a player to the game
	public void addPlayer() {
		if (numOfPlayers < 4) {
			numOfPlayers++;
		}
		else {
			System.out.println(max_Connected);
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


class GameSession implements Runnable, EelsAndEscalatorsInterface {
	
	public void run() {
		
	}
	
}