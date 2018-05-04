package jeopardy;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	static String mode = "KEYBOARD";
	volatile static Serial ser;
	
	public static void main(String args[]) {
		System.out.println("Jeopardy");
		Game game = new Game(InputMode.APP);
		game.begin();
	}
	
	public static void networkTest0() {
		System.out.println("Starting");
		try {
			Socket cientConn = new ServerSocket(8878).accept();
			InputStream is = cientConn.getInputStream();
			for(int i = 0; i < 16; i++) {
				System.out.println(is.read());
			}
			cientConn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void networkTest() {
		System.out.println("Starting");
		try {
			byte[] address = {(byte)10,  (byte)197, (byte)131, (byte)229};
			int port = 555;
			Socket cientConn = new Socket(InetAddress.getLocalHost(), 8878);
			InputStream is = cientConn.getInputStream();
			for(int i = 0; i < 16; i++) {
				System.out.println(is.read());
			}
			cientConn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void serialTest() {
		Scanner sca = new Scanner(System.in);
		
		List<String> ports = Serial.getPorts();
		for(String st : ports) {
			System.out.println(st);
		}
		System.out.println("Please type the name of the port you want");
		
		ser = new Serial(sca.nextLine(), 115200, new SerialEvent() {

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
		ser.startListening();
		
		System.out.println("Ready");
		
		Thread t = new Thread() {
			@Override
			public void run() {
				Scanner s = new Scanner(System.in);
				while(true) {
					ser.println(s.nextLine());
				}
			}
		};

		while(true) {}
	}
	
	public void run() {
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
