import java.util.LinkedList;
import javax.swing.JPanel;

public class Tile<P> extends JPanel {
	
	private Player[] currentPlayers; //players on tile
	
	private boolean eel1 = false; //varying types of tiles on the map
	private boolean eel2 = false; 
	private boolean eel3 = false; 
	private boolean eel4 = false;
	private boolean escalator = false; 
	private boolean win = false;
	private boolean lose = false;
	private boolean start = false; 
	
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
	public void setEel1(boolean input){
		eel1 = input;
	}
	
	public void setEel2(boolean input){
		eel2 = input;
	}
	
	public void setEel3(boolean input){
		eel3 = input;
	}
	
	public void setEel4(boolean input){
		eel4 = input;
	}
	
	public void setEscalator(boolean input){
		escalator = input;
	}
	
	public void setWin(boolean input){
		win = input;
	}
	
	public void setLose(boolean input){
		lose = input;
	}
	
	public void setStart(boolean input){
		start = input;
	}
	
	public void setPositionX(int posX){
		positionX = posX;
	}
	
	public void setPositionY(int posY){
		positionY = posY;
	}
	
	//accessors
	public boolean isEel1(){
		return eel1;
	}
		
	public boolean isEel2(){
		return eel2;
	}
		
	public boolean isEel3(){
		return eel3;
	}
		
	public boolean isEel4(){
		return eel4;
	}
		
	public boolean isEscalator(){
		return escalator;
	}
		
	public boolean isWin(){
		return win;
	}
	
	public boolean isLose(){
		return lose;
	}
		
	public boolean isStart(){
		return start;
	}
	
	public int getPositionX(){
		return positionX;
	}
		
	public int getPositionY(){
		return positionY;
	}
	
}