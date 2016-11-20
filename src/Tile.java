import java.util.LinkedList;

public class Tile<P> {
	
	private LinkedList<P> currentPlayers; //players on tile
	private boolean eel, escalator, win, lose, start; //varying types of spots on the map
	
	public Tile(){
		LinkedList<P> currentPlayers = new LinkedList<P>();
		eel = false;
		escalator = false;
		win = false;
		lose = false;
		start = false;
	}
	
	public Tile(boolean eelInput, boolean escalatorInput, boolean winInput, boolean loseInput, boolean startInput){
		LinkedList<P> currentPlayers = new LinkedList<P>();
		eel = eelInput;
		escalator = escalatorInput;
		win = winInput;
		lose = loseInput;
		start = startInput;
	}
	
	//mutators
	public void setEel(boolean input){
		eel = input;
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
	
	public void addPlayer(P player){
		currentPlayers.push(player);
	}
	
	public void removePlayer(P player){
		currentPlayers.pop();
	}
	
	
}