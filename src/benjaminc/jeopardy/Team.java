package benjaminc.jeopardy;

import java.awt.Color;

import benjamin.BenTCP.TCPServer;

public class Team {
	private int score;
	private boolean didGuess;
	private Color color;
	private static int count;
	private int num;
	private String name;
	private int wager;
	private Object syncObj;

	private TCPServer server;

	private InputMode inputMode;
	
	public Team() {
		this(Color.BLUE, "", null);
	}
	
	public Team(Color c, InputMode im) {
		this(c, "", im);
	}
	
	public Team(Color c, String n, InputMode im) {
		score = 0;
		didGuess = false;
		color = c;
		num = ++count;
		name = n;
		inputMode = im;
		syncObj = new Object();
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int s) {
		score = s;
	}
	
	public void addScore(int s) {
		score += s;
	}
	
	public boolean hasGuessed() {
		return didGuess;
	}
	
	public void setGuessed(boolean g) {
		didGuess = g;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getNum() {
		return num;
	}
	
	public int getWager() {
		return wager;
	}
	
	public void setWager(int w) {
		wager = w;
	}
	
	public int getDoubleAmount() {
		if(inputMode == InputMode.APP) {
			wager = -2;
			Util.pause(syncObj);
		}
		return wager;
	}
	
	public InputMode getInputMode() {
		return inputMode;
	}

	public void setInputMode(InputMode inputMode) {
		this.inputMode = inputMode;
	}
	
	public TCPServer getServer() {
		return server;
	}

	public void setServer(TCPServer server) {
		this.server = server;
	}
	
	public Object getSyncObject() {
		return syncObj;
	}

}