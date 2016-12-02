
public interface EelsAndEscalatorsInterface {
	// Player ID's
	int PLAYER1 = 1;
	int PLAYER2 = 2;
	int PLAYER3 = 3;
	int PLAYER4 = 4;
	
	// Player Turns
	int[] PLAYER_TURN_ORDER = {PLAYER1, PLAYER2, PLAYER3, PLAYER4};
	
	// Player Win Identification
	int PLAYER_WON = 83;
	int PLAYER1_WIN = 11;
	int PLAYER2_WIN = 22;
	int PLAYER3_WIN = 33;
	int PLAYER4_WIN = 44;
	
	// Player Lose Identification
	int PLAYER1_LOSE = 10;
	int PLAYER2_LOSE = 20;
	int PLAYER3_LOSE = 30;
	int PLAYER4_LOSE = 40;
	
	// Actions Performed
	int SEND_ROLL_REQUEST = 99;
	int PLAYER_WAIT = 12;
	int PLAYER_GO = 13;
	int RECEIVE_ROLL = 14;
	int END_PLAYER_TURN = 17;
	
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
	
	// Player Tokens
	String MRKRABS_TOKEN = "123.png"; 
	String SQUIDWARD_TOKEN = "124.png";
	String SPONGEBOB_TOKEN = "125.png";
	String PATRICK_TOKEN = "126.png";
}
