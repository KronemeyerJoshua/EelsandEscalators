import java.util.LinkedList;
import javax.swing.JPanel;

public class Tile<P> extends JPanel {
	
	private LinkedList<P> currentPlayers; //players on tile
	
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
		LinkedList<P> currentPlayers = new LinkedList<P>();
	}
	
	public Tile(int posX, int posY){
		this();
		positionX = posX;
		positionY = posY;
	}
	
	public void addPlayer(P player){
		currentPlayers.add(player);
	}
	
	public void removePlayer(P player){
		currentPlayers.remove(player);
	}
	
	public void resetPlayers(){
		currentPlayers = new LinkedList<P>();
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
	public boolean getEel1(boolean input){
		return eel1;
	}
		
	public boolean getEel2(boolean input){
		return eel2;
	}
		
	public boolean getEel3(boolean input){
		return eel3;
	}
		
	public boolean getEel4(boolean input){
		return eel4;
	}
		
	public boolean getEscalator(boolean input){
		return escalator;
	}
		
	public boolean getWin(boolean input){
		return win;
	}
	
	public boolean getLose(boolean input){
		return lose;
	}
		
	public boolean getStart(boolean input){
		return start;
	}
	
	public int getPositionX(int posX){
		return positionX;
	}
		
	public int getPositionY(int posY){
		return positionY;
	}
	
}