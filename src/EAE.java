import java.util.Random;
import java.util.LinkedList;
import java.util.Scanner;

public class EAE<P> extends LinkedList<P> {

	//basic implementations needed
	private Random rand = new Random();
	
	 //basic elements
	final int MAP_X = 10; int MAP_Y = 3;
	private Tile<Player>[][] map; //map
	private Player currentPlayerTurn;	// current player's turn
	
	private LinkedList<Player> playerTurns = new LinkedList<Player>();
	
	private int numPlayers = 0;
	private int dice1 = 1, dice2 = 1, totalDice = 2;
	
	
	public EAE(){
		playerTurns = new LinkedList<Player>();	
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		Player player4 = new Player();
		
		this.setNumPlayers();
		
		if(this.getNumPlayers() == 1){
			this.addPlayer(player1);
		}
		else if(this.getNumPlayers() == 2){
			this.addPlayer(player1);
			this.addPlayer(player2);
		}
		else if(this.getNumPlayers() == 3){
			this.addPlayer(player1);
			this.addPlayer(player2);
			this.addPlayer(player3);
		}
		else if(this.getNumPlayers() == 4){
			this.addPlayer(player1);
			this.addPlayer(player2);
			this.addPlayer(player3);
			this.addPlayer(player4);
		}
	}
	
	public int rollDice(){	
		return rand.nextInt() % 5 +1 ; // dice implementation
	}

	public void genMapDefault(){ //regular map generation
		map = new Tile[MAP_Y][MAP_X];
		for(int x = 0; x < MAP_X; x++){
			for(int y = 0; y < MAP_Y; y++){
				map[y][x] = new Tile<Player>();
			}
		}
		map[0][0].setStart(true);
		map[0][1].setEel1(true);
		map[0][9].setEel1(true);
		map[0][5].setEel2(true);
		
	}
	
	public void genMapRandom(){ //generates a random map
		
	}
	
	public void movePlayer(Player player){ //used after the dice has been rolled
		map[player.getYLocation()][player.getXLocation()].removePlayer(player); //remove player from tile
		
		dice1 = rollDice();
		dice2 = rollDice();
		totalDice = dice1 + dice2;
		int moveAmount;
		
		while(totalDice > 0)
		if(player.getXLocation()+totalDice > MAP_X-1 || (player.getXLocation()-totalDice < 0 && player.getYLocation() == 1)){
			moveAmount = (MAP_X-1) - player.getXLocation(); //space between the end of the map and current Tile
			totalDice = totalDice - moveAmount - 1; //
			player.setXLocation(MAP_X-1);
			player.setYLocation(player.getYLocation()+1);
		}
		else if(player.getYLocation() == 1){ //moves player to the left if in the middle section
			player.setXLocation(player.getXLocation()-1);
			totalDice--;
		}
		else{
			player.setXLocation(player.getXLocation()+1);
			totalDice--;
		}
		
		map[player.getYLocation()][player.getXLocation()].addPlayer(player); //add player to tile
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
	
	public int setNumPlayers(){	//ask for the number of players
		//Number of Players
		Scanner scan = new Scanner(System.in);
		System.out.println("How many players would you like to play?");
		numPlayers = scan.nextInt();
				
		while (numPlayers > 4 || numPlayers < 1){
			System.out.println("\nIncorrect value. Please enter another value.");
			numPlayers = scan.nextInt();
		}
		
		return numPlayers;
	}
	 
	public static void main(String[] args) {
		EAE game = new EAE();
	}
}
