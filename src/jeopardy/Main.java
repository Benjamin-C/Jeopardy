package jeopardy;

public class Main {
	
	static String mode = "KEYBOARD";
	
	public static void main(String args[]) {
		Game game = new Game("KEYBOARD");
		game.begin();
	}
}
