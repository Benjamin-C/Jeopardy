package choHan;

public class Game {
	private Dealer dealer;
	private Player pOne;
	private Player pTwo;
	private int rounds;
	private UserInterface ui;
	
	public Game() {
		dealer = new Dealer();
		pOne = new Player();
		pTwo = new Player();
		rounds = 0;
		ui = new UserInterface();
	}
	
	public Dealer getDealer() {
		return dealer;
	}
	
	public Player getPlayerOne() {
		return pOne;
	}
	
	public Player getPlayerTwo() {
		return pTwo;
	}
	
	public int getRounds() {
		return rounds;
	}
	
	public void setDealer(Dealer d) {
		dealer = d;
	}
	
	public void setPlayerOne(Player p) {
		pOne = p;
	}
	
	public void setPlayerTwo(Player p) {
		pTwo = p;
	}
	
	public void setRounds(int r) {
		rounds = r;
	}

	public void setUserInterface(UserInterface u) {
		ui = u;
	}
	
	public void setupGame() {
		ui.println("What is your name, Player 1?");
		pOne.setName(ui.getString());
		ui.println("What is your name, Player 2?");
		pTwo.setName(ui.getString());
		String in;
		boolean flag;
		do {
			ui.println("How many rounds?");
			flag = false;
			in = ui.getString();
			if(ui.isNumeric(in)) {
				flag = true;
				rounds = Integer.parseInt(in);
			}
		} while (flag == false);
	}
	
	public void playGame() {
		for(int i = 0; i < rounds; i++) {
			ui.println("Round " + i);
			dealer.rollDice();
			pOne.makeGuess();
			pTwo.makeGuess();
			determineResults();
		}
	}
	
	public void determineResults() {
		ui.println(dealer.isChoOrHan().getName());
		checkGuess(pOne);
		checkGuess(pTwo);
	}
	
	public void checkGuess(Player p) {
		if(dealer.isChoOrHan().equals(p.getGuess())) {
			p.addPoint();
			ui.println(p.getName() + " has earned a point");
		} else {
			ui.println(p.getName() + " has not earned a point");
		}
	}
	
	public void displayGrandWinner() {
		ui.println("The final results after " + rounds + " rounds");
		ui.println("\t" + pOne.getName() + ":" + pOne.getScore() + " points");
		ui.println("\t" + pTwo.getName() + ":" + pTwo.getScore() + " points");
		if(pOne.getScore() < pTwo.getScore()) {
			ui.println(pTwo.getName() + " wins");
		} else {
			if(pOne.getScore() == pTwo.getScore()) { // set score
				ui.println("It's a tie between " + pOne.getName() + pTwo.getName());
			} else {
				ui.println(pOne.getName() + " wins");
			}
		}
	}
}
