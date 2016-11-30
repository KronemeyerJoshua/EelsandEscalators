import java.util.LinkedList;
import javax.swing.JPanel;

public class Tile<P> extends JPanel {
	
	private Player[] currentPlayers; //players on tile
	
	//Traits of tiles for the map
	//eel1 = 1; 
	//eel2 = 2; 
	//eel3 = 3; 
	//eel4 = 4;
	//escalator1H = 5; 
	//escalator2H = 6; 
	//escalator3H = 7;
	//escalator1T = 15; 
	//escalator2T = 16; 
	//escalator3T = 17;
	//win = 8;
	//lose = 9;
	//start = 0; 
	
	private int trait; //indicates type of tile
	private int positionX = 0; //tile position
	private int positionY = 0;	
	
	
	
	public Tile(){
		currentPlayers = new Player[4];
	}
	
	public Tile(int posX, int posY){
		this();
		positionX = posX;
		positionY = posY;
	}
	
	public void addPlayer (Player player){ 
		int x;
		try{
			for(x = 0; currentPlayers[x] != null; x++){} //TODO - requires further testing
			
			currentPlayers[x] = player;
		}
		catch(Exception e){
			System.out.println("Too many players!");
		}
	}
	
	public void removePlayer(Player player){ 
		int x;
		try{
			for(x = 0; !currentPlayers[x].equals(player); x++){}//TODO - requires further testing
			
			currentPlayers[x] = null;
		}
		catch(Exception e){
			System.out.println("Too many players!");
		}
	}
	
	public void resetPlayers(){
		currentPlayers = new Player[4];
	}
	
	//mutators
	public void setTrait(int x){
		trait = x;
	}
	
	public void setEel1(){
		trait = 1;
	}
	
	public void setEel2(){
		trait = 2;
	}
	
	public void setEel3(){
		trait = 3;
	}
	
	public void setEel4(){
		trait = 4;
	}
	
	public void setEscalator1(){
		trait = 5;
	}
	
	public void setEscalator2(){
		trait = 6;
	}
	
	public void setEscalator3(){
		trait = 7;
	}
	
	public void setWin(){
		trait = 8;
	}
	
	public void setLose(){
		trait = 9;
	}
	
	public void setStart(){
		trait = 0;
	}
	
	public void setPositionX(int posX){
		positionX = posX;
	}
	
	public void setPositionY(int posY){
		positionY = posY;
	}
	
	//accessors
	public boolean isEel1(){
		return trait == 1;
	}
		
	public boolean isEel2(){
		return trait == 2;
	}
		
	public boolean isEel3(){
		return trait == 3;
	}
		
	public boolean isEel4(){
		return trait == 4;
	}
		
	public boolean isEscalator1(){
		return trait == 5;
	}
	
	public boolean isEscalator2(){
		return trait == 6;
	}
	
	public boolean isEscalator3(){
		return trait == 7;
	}
		
	public boolean isWin(){
		return trait == 8;
	}
	
	public boolean isLose(){
		return trait == 9;
	}
		
	public boolean isStart(){
		return trait == 0;
	}
	
	public int getPositionX(){
		return positionX;
	}
		
	public int getPositionY(){
		return positionY;
	}
	
	public int getTrait(){
		return trait;
	}
	
}