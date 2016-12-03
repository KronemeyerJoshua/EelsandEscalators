/*
 * Keeps track of tile x,y
 * How many players are on one tile
 */
public class Tile {

	// DECLARE VARS
	private int players;
	private int positionX = 0;
	private int positionY = 0;	
	private boolean isStart;
	private boolean isFinish;
	private boolean isLose;
	
	
	public Tile(){
		players = 0;
		
	}
	
	public Tile(int posX, int posY){
		this();
		positionX = posX;
		positionY = posY;
	}
	
	public void addPlayer (){ 
		players++;
	}
	
	public void removePlayer(){ 
		players--;
	}
	
	
	public void setPositionX(int posX){
		positionX = posX;
	}
	
	public void setPositionY(int posY){
		positionY = posY;
	}
	
	public void setPosition(int posX, int posY){
		positionX = posX;
		positionY = posY;
	}
	
	public int getPositionX(){
		return positionX;
	}
		
	public int getPositionY(){
		return positionY;
	}
	
	// Mutators... Kind of pointless without random gen.
	// Board is fixed to 30 tiles
	public boolean getStart() {
		return isStart;
	}
	
	public void setStart() {
		isStart = true;
	}

	public boolean getLose() {
		return isLose;
	}
	
	public void setLose() {
		isLose = true;
	}
	public boolean getWin() {
		return isFinish;
	}
	
	public void setWin() {
		isFinish = true;
	}
	
}