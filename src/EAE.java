import java.util.Random;
import java.util.LinkedList;
import java.util.Scanner;

public class EAE<Player> {

	//basic implementations needed
	private Random rand = new Random();
	
	 //basic elements
	final int MAP_X = 9; int MAP_Y = 2;
	private Tile[][] map = new Tile[MAP_Y+1][MAP_X+1]; //map
	private Player currentPlayerTurn;	// current player's turn
	
	private LinkedList<Player> playerTurns = new LinkedList();
	
	private int numPlayers = 0;
	private int dice1 = 1, dice2 = 1, totalDice = 2;
	
	
	public EAE(){
		playerTurns = new LinkedList<Player>();
		numPlayers++;
		
	}
	public int rollDice(){	
		return rand.nextInt() % 5 +1 ; // dice implementation
	}
	
	public void addPlayer(Player player, String playerName, int playerCharacter, int playerToken){ //allows modification of player info
		player = new Player( playerName, playerCharacter,  playerToken);
	}
	
	
	public void genMapDefault(){ //regular map generation
		for(int x = 0; x < MAP_X+1; x++){
			for(int y = 0; y < MAP_Y+1; y++){
				map[y][x] = new Tile<Player>();
			}
		}
		map[0][0].setStart(true);
		map[0][1].setEel1(true);
		map[0][9].setEel1(true);
		map[0][5].setEel2(true);
	}
	
	public void movePlayer(Player player){ //used after the dice has been rolled
		dice1 = rollDice();
		dice2 = rollDice();
		totalDice = dice1 + dice2;
		int moveAmount;
		
		if(player.getXLocation()+totalDice > MAP_X || player.getXLocation()-totalDice < 0 ){
			moveAmount = MAP_X - player.getXLocation(); //space between the end of the map and current Tile
			totalDice = totalDice - moveAmount - 1; //
			player.setxLocation(MAP_X);
			player.setYLocation(player.getYLocation()++);
		}
		else if(player.getYLocation() == 1){ //moves player to the left if in the middle section
			player.setXLocation(player.getXLocation()--);
		}
		else
			player.setXLocation(player.getXLocation()++);
				
		
		
				
	}
	
	public void pushCurrentPlayerPosition(Tile[][] mapTemp, LinkedList<Player> playerTurnsTemp){ //resets the current map
		for(int x = 0; x < MAP_X+1; x++){
			for(int y = 0; y < MAP_Y+1; y++){
				map[y][x].resetPlayers();
			}
		}
		
		int x, y;
		Player[] playerArray;
		playerTurnsTemp.toArray(playerArray);
		
		for(Player player : playerArray){ //iterates over the playerTurns list to place player on the correct tiles
			
			x = player.getXLocation();
			y = player.getYLocation();
			
			map[y][x].addPlayer(player); 
		}
	}
	
	public void pushIndividualPlayerPosition(){
		
	}
	
	public void genMapRandom(){ //generates a random map
		
	}
	
	public int getNumPlayers(){
		return numPlayers;
	}	
	
	public void addPlayer(Player player){
		playerTurns.add(player);
	}
	
	public void removePlayer(Player player){
		playerTurns.remove(player);
	}
	 
	public static void main(String[] args) {
		//Declarations
		EAE game = new EAE();
		Scanner scan = new Scanner(System.in);
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		Player player4 = new Player();
		
		//Number of Players
		int numPlayers;
		System.out.println("How many players would you like to play?");
		numPlayers = scan.nextInt();
		
		if(numPlayers == 1){
			
		}
		else if(numPlayers == 2){
			
		}

	}
}
