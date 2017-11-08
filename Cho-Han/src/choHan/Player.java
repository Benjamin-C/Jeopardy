package choHan;

public class Player {
	private String name;
	private int score;
	private Guess guess;
	
	public Player() {
		name = "Bob";
		score = 0;
		guess = Guess.NULL;
	}
	
	public Player(String n) {
		name = n;
		score = 0;
		guess = Guess.NULL;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public Guess getGuess() {
		return guess;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setScore(int s) {
		score = s;
	}
	
	public void setGuess(Guess g) {
		guess = g;
	}
	
	@Override
	public String toString() {
		return name + " has " + score + " points and guesses " + guess;
	}
	
	public void makeGuess() {
		if(Math.random() > 0.5) {
			guess = Guess.CHO;
		} else {
			guess = Guess.HAN;
		}
	}
	
	public String guessString() {
		switch(guess) {
		case CHO: {
			return Guess.CHO.getName();
		}
		case HAN: {
			return Guess.HAN.getName();
		}
		case NULL: {
			return Guess.NULL.getName();
		}
		default: {
			return "Error";
		}
		}
	}
	
	public void addPoint() {
		score++;
	}
	
	public void addPoints(int p) {
		score = score + p;
	}
}
