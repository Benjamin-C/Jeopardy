package benjaminc.jeopardy;

import java.awt.Color;

import benjamin.BenTCP.TCPOnDataArrival;
import benjamin.BenTCP.TCPServer;

public class Team implements TCPOnDataArrival{
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
	
	private PickNumberCallback numberCallback;

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
		System.out.println("Team " + name + "score=" + score);
	}
	
	public void addScore(int s) {
		score += s;
		System.out.println("Team " + name + "score=" + score);
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
	
	public void getNumber(PickNumberCallback pnc) {
		if(inputMode == InputMode.APP) {
			pickNumber(pnc);
		} else {
			System.out.println("Picking num");
			System.out.println("|" + name + "|");
			actionCenter.pickNumber(new PickNumberCallback() {
				@Override public void whenDone(int n) { pnc.whenDone(n); }
				@Override public void whenCanceled() { pnc.whenCanceled(); } },
				name + " Team Wager:", color
			);
			System.out.println("No block yet ...");
		}
	}
	
	public void pickNumber(PickNumberCallback pncb) {
		if(inputMode != InputMode.APP) {
			actionCenter.pickNumber(pncb,  name,  color);
		} else {
			numberCallback = pncb;
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
	
	@Override
	public void onDataArrived(byte[] data) {
		if(data.length > 0) {
			switch(data[0]) {
			case 0x50: {// Buzz
				buzz();
				System.out.println("Buzzing " + num);
			} break;
			case 0x30: {// Wager
				if(numberCallback != null) {
					System.out.println("odr" + data[0] + " " + data[1] + " " + data[2]);
					if(data.length >= 3) {
						int temp = 0;
						temp = data[1] & 0xff;
						temp = temp << 8;
						temp = temp + (data[2] & 0xFF);
						System.out.println(temp);
						numberCallback.whenDone(temp);
					} else {
						numberCallback.whenCanceled();
					}
					numberCallback = null;
				}
			}
			}
		}
	}

	public void buzz() {
		actionCenter.buzz(this);
	}
}
