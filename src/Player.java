// Keeps track of player position...
public class Player {
	private int tilePosition;
	private boolean lost, eel, esc;
	
	Player(int tile) {
		tilePosition = tile;
		lost = false;
		esc = false;
		eel = false;
	}
	
	Player() {
		tilePosition = 0;
		lost = false;
		esc = false;
		eel = false;
	}
	
	public int getPosition() {
		return tilePosition;
	}
	
	public void setPosition(int pos) {
		tilePosition = pos;
	}
	
	public void setLost(boolean lost) {
		this.lost = lost;
	}
	
	public boolean getLost() {
		return lost;
	}
	
	public boolean getEel() {
		return eel;
	}
	
	public void setEel(boolean eel) {
		this.eel = eel;
	}
	
	public void setEsc(boolean esc) {
		this.esc = esc;
	}
	
	public boolean getEsc() {
		return esc;
	}
}