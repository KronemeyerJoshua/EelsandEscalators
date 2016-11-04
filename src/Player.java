/*
 * @Team Group_1
 * @LastEdited 11/4/2016 1:06AM
 * @EditedBy Kronemeyer, Joshua
 * @Notes 
 * 
 */

public class Player {
	
	// Declare Vars
	int playerLocation, playerCharacter, playerToken;
	String playerName;
	
	// Default Constructor
	public Player() {
		
	}
	
	// Constructor
	public Player(int loc, int character, int token, String name) {
		playerLocation = loc;
		playerCharacter = character;
		playerToken = token;
		playerName = name;
	}
	
	
	// Mutators
	
	//@param1 Player Location
	public void setLocation(int loc) {
		playerLocation = loc;
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
	public int getLocation() {
		return playerLocation;
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
