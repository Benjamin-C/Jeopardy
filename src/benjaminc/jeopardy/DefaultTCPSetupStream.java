package benjaminc.jeopardy;

import java.util.Scanner;

import benjamin.BenTCP.TCPSetupStream;

public class DefaultTCPSetupStream implements TCPSetupStream {

	private Scanner scan;
	String name;
	
	public DefaultTCPSetupStream(Scanner s, String name) {
		scan = s;
		this.name = name;
	}
	
	@Override
	public void close() {
		System.out.println(name + " has closed");
	}

	@Override
	public String read() {
		return scan.next();
	}

	@Override
	public void write(String arg0) {
		System.out.println("[" + name + "]:" + arg0);
	}
	

}
