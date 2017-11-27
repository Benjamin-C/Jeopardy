package benjaminc.PacMan;

public class Location {
	private int x;
	private int y;
	public Location() {
	}
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Location [x=" + x + ", y=" + y + "]";
	}

	public boolean equils(Location l) {
		boolean out = true;
		if(l.getX() != x) { out = false;};
		if(l.getY() != y) { out = false;};
		return out;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
