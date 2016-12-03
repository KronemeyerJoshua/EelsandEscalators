
public interface EelsAndEscalatorsInterface {
	// Player ID's
	int PLAYER1 = 1;
	int PLAYER2 = 2;
	int PLAYER3 = 3;
	int PLAYER4 = 4;
	
	// Player Win Identification
	int PLAYER_WON = 83;
	
	// Player Lose Identification
	int PLAYER_LOST = 84;

	
	// Actions Performed
	int SEND_ROLL_REQUEST = 99;
	int PLAYER_WAIT = 12;
	int PLAYER_GO = 13;
	int END_PLAYER_TURN = 17;
	int EEL_HIT = 19;
	int ESC_HIT = 20;
	
	// Any string constants should be put below this line
	String ROLLDICEMSG = "Please roll dice.";
	String CHARSELECTMESSAGE = "Select your character";
	String WAIT_MESSAGE = "Please wait...";
	String CONNECTING_MESSAGE = "Please wait... Connecting...";
	String MAX_CONNECTED = "The game is full, no more players may join.";
	
	// Character ID's
	int MRKRABS = 123;
	int SQUIDWARD = 124;
	int SPONGEBOB = 125;
	int PATRICK = 126;

}
