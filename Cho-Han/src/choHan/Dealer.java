package choHan;

import java.util.Random;

public class Dealer {
	private Random r;
	private int dieOne;
	private int dieTwo;
	
	public Dealer() {
		dieOne = 0;
		dieTwo = 0;
		r = new Random();
	}
	
	public int getDieOne() {
		return dieOne;
	}
	
	public int getDieTwo() {
		return dieTwo;
	}
	
	public void setDieOne(int v) {
		dieOne = v;
	}
	
	public void setDieTwo(int v) {
		dieTwo = v;
	}
	
	@Override
	public String toString() {
		return dieOne + ", " + dieTwo;
	}
	
	public Guess isChoOrHan() {
		if((dieOne + dieTwo) % 2 == 0) {
			return Guess.CHO;
		} else {
			return Guess.HAN;
		}
	}
	
	public void rollDice() {
		dieOne = r.nextInt(5) + 1;
		dieTwo = r.nextInt(5) + 1;
	}
}
