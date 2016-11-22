import java.util.LinkedList;

public class Tile<P> {
	
	private LinkedList<P> currentPlayers; //players on tile
	private boolean eel1, eel2, eel3, eel4, escalator, win, lose, start; //varying types of spots on the map
	
	public Tile(){
		LinkedList<P> currentPlayers = new LinkedList<P>();
		eel1 = false;
		eel2 = false;
		eel3 = false;
		eel4 = false;
		escalator = false;
		win = false;
		lose = false;
		start = false;
	}
	
	public Tile(boolean eelInput1, boolean eelInput2, boolean eelInput3, boolean eelInput4, boolean escalatorInput, boolean winInput, boolean loseInput, boolean startInput){
		LinkedList<P> currentPlayers = new LinkedList<P>();
		eel1 = eelInput1;
		eel2 = eelInput2;
		eel3 = eelInput3;
		eel4 = eelInput4;
		escalator = escalatorInput;
		win = winInput;
		lose = loseInput;
		start = startInput;
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
	
	public void addPlayer(P player){
		currentPlayers.add(player);
	}
	
	public void removePlayer(P player){
		currentPlayers.remove(player);
	}
	
	public void resetPlayers(){
		currentPlayers = new LinkedList<P>();
	}
	
	
}