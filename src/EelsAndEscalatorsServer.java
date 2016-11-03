
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
