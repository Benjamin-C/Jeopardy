package jeopardy;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	static String mode = "KEYBOARD";
	
	public static void main(String args[]) {
		
		Serial s = new Serial("", 0, new SerialEvent() {

			@Override
			public void onDataAvaliable(byte data) {
				// TODO Auto-generated method stub
				System.out.println(data);
			}

			@Override
			public void IOException(java.io.IOException e) {
				// TODO Auto-generated method stub
				e.printStackTrace();
			}
			
		});
		s.startListening();
		System.out.println("Starting");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		s.stopListening();
		System.out.println("Stopping");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("listening: " + s.checkListening());
		
		System.out.println("Done");
		System.exit(0);
		Game game = new Game(InputMode.ARDUINO);
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
