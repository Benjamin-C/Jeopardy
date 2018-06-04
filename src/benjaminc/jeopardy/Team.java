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
	
	private TeamOnDataArrival onDataArrival;

	public Team(ActionCenter ac, TeamOnDataArrival odr) {
		this(ac, odr, Color.BLUE, "", null);
	}
	
	public Team(ActionCenter ac, TeamOnDataArrival odr, Color c, InputMode im) {
		this(ac, odr, c, "", im);
	}
	
	public Team(ActionCenter ac, TeamOnDataArrival odr, Color c, String n, InputMode im) {
		score = 0;
		didGuess = false;
		color = c;
		num = ++count;
		name = n;
		inputMode = im;
		syncObj = new Object();
		actionCenter = ac;
		onDataArrival = odr;
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
	
	public TeamOnDataArrival getOnDataArrival() {
		return onDataArrival;
	}

	public void setOnDataArrival(TeamOnDataArrival onDataArrival) {
		this.onDataArrival = onDataArrival;
	}
	
	public void getNumber(PickNumberCallback pnc) {
		if(inputMode == InputMode.APP) {
			onDataArrival.pickNumber(pnc);
		} else {
			System.out.println("Picking num");
			actionCenter.pickNumber(new PickNumberCallback() {
				@Override public void whenDone(int n) { pnc.whenDone(n); }
				@Override public void whenCanceled() { pnc.whenCanceled(); } },
				name + " Team Wager:\n", color
			);
		}
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
