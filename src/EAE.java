import java.util.Random;
import java.util.LinkedList;

public class EAE<Player> {
	
	//basic implementations needed
	private Random rand = new Random();
	
	 //basic elements
	private Tile[][] map = new Tile[3][10]; //map
	private Player currentPlayerTurn;	// current player's turn
	
	private LinkedList<Player> playersTurn;
	
	private int numPlayers = 0;
	
	
	public EAE(){
		playersTurn = new LinkedList<Player>();
		numPlayers++;
		
	}
	public int rollDice(){	
		return rand.nextInt() % 5 +1 ; // dice implementation
	}
	
	public void addPlayer(String playerName, int playerCharacter, int playerToken){
		//Player player = new Player( playerName, playerCharacter,  playerToken);
	}
	
	public void genMapDefault(){ //regular map generation
		
	}
	
	public void genMapRandom(){ //generates a random map
		
	}
	
	public int getNumPlayers(){
		return numPlayers;
	}	
	 
	public static void main(String[] args) {
		

	}

}
