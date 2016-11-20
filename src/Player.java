import java.util.Random;

/*
 * @Team Group_1
 * @LastEdited 11/4/2016 1:06AM
 * @EditedBy Joshua Kronemeyer, Taylor Greeff
 * @Notes 
 * 
 */

public class Player {
	
	// Declare Vars
	int[] playerLocation = new int[2]; //[0] is x and [1] is y
	int playerCharacter, playerToken; 
	String playerName;
	String[] listNames = {"SpongeBob", "Patrick", "Squidward", "Mr. Krabs"};
	Random rand = new Random();
	
	// Default Constructor
	public Player() {
		playerName = listNames[rand.nextInt()];
		playerLocation[0] = 0;
		playerLocation[1] = 0;
	}
	
	// Constructor
	public Player(int character, int token, String name) { //removed loc, as there should be a set default for starting
		playerLocation[0] = 0;
		playerLocation[1] = 0;
		playerCharacter = character;
		playerToken = token;
		playerName = name;
	}
	
	
	// Mutators
	public void movePlayer(int roll){ //used after the dice has been rolled
		if (roll != 0){
			if((this.playerLocation[0] == 10 && this.playerLocation[1] == 0) || (this.playerLocation[0] == 0 && this.playerLocation[1] == 1)) //moves player to the next upper platform
				playerLocation[1]++;			
			else if(playerLocation[1] == 1) //moves player to the left if in the middle section
				playerLocation[0]--;
			else //moves player normally to the right
				playerLocation[0]++;
				
			movePlayer(roll-1);
		}	
	}
	
	//@param1 Player Location
	public void setLocation(int locX, int locY) {
		playerLocation[0] = locX;
		playerLocation[1] = locY;
	}
	
	//@param1 Player Character Chosen
	public void setCharacter(int character) {
		playerCharacter = character;
	}
	
	//@param1 Unique player token
	public void setToken(int token) {
		playerToken = token;
	}
	
	//@param1 Name set by player
	public void setName(String name) {
		playerName = name;
	}
	
	
	// Accessors
	
	//@return Player's Location
	public int getLocationX() {
		return playerLocation[0];
	}
	
	//@return Player's Location
		public int getLocationY() {
			return playerLocation[1];
		}
	
	//@return ID of player character
	public int getCharacter() {
		return playerCharacter;
	}
	
	//@return Unique player token
	public int getToken() {
		return playerToken;
	}
	
	//@return Name set by player
	public String getName() {
		return playerName;
	}
	
}
