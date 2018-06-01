package benjaminc.jeopardy;

import java.awt.Color;

import benjamin.BenTCP.TCPServer;
import benjaminc.util.Util;

public class Team {
	private int score;
	private boolean didGuess;
	private Color color;
	private static int count;
	private int num;
	private String name;
	private int wager;
	private Object syncObj;
	private ActionCenter actionCenter;

	private TCPServer server;

	private InputMode inputMode;
	
	public Team(ActionCenter ac) {
		this(ac, Color.BLUE, "", null);
	}
	
	public Team(ActionCenter ac, Color c, InputMode im) {
		this(ac, c, "", im);
	}
	
	public Team(ActionCenter ac, Color c, String n, InputMode im) {
		score = 0;
		didGuess = false;
		color = c;
		num = ++count;
		name = n;
		inputMode = im;
		syncObj = new Object();
		actionCenter = ac;
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
	
	public int getNumber() {
		if(inputMode == InputMode.APP) {
			wager = -2;
		} else {
			actionCenter.pickNumber(new PickNumberCallback() {
				@Override public void whenDone(int n) { wager = n; }
				@Override public void whenCanceled() { } },
				name + " Team Wager:\n"
			);
		}
		System.out.println("Pausing ...");
		Util.pause(syncObj);
		System.out.println("Done pausing");
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
