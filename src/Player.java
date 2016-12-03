// Keeps track of player position...
public class Player {
	private int tilePosition;
	private boolean lost;
	
	Player(int tile) {
		tilePosition = tile;
		lost = false;
	}
	
	Player() {
		tilePosition = 0;
		lost = false;
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
}