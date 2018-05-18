package benjaminc.jeopardy;

import java.awt.Color;

public class Team {
	private int score;
	private boolean didGuess;
	private Color color;
	private static int count;
	private int num;
	private String name;
	private int wager;
	
	public Team() {
		score = 0;
		didGuess = false;
		color = Color.BLUE;
		num = ++count;
		name = "";
	}
	
	public Team(Color c) {
		score = 0;
		didGuess = false;
		color = c;
		num = ++count;
		name = "";
	}
	
	public Team(Color c, String n) {
		score = 0;
		didGuess = false;
		color = c;
		num = ++count;
		name = n;
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
}
