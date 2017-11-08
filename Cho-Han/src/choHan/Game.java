package choHan;

public class Game {
	private Dealer dealer;
	private Player pOne;
	private Player pTwo;
	private int rounds;
	
	public Game() {
		dealer = new Dealer();
		pOne = new Player();
		pTwo = new Player();
		rounds = 0;
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
	public void runGame() {
		
	}
}
