package jeopardy;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	static String mode = "KEYBOARD";
	
	public static void main(String args[]) {
		Game game = new Game("KEYBOARD");
		game.begin();
		
	}
	
	
	@SuppressWarnings("unused")
	private void test() {
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < 5; i++) {
			list.add(new String(scan.nextLine()));
		}
		scan.close();
	}
}
