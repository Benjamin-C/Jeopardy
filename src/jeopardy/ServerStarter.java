package jeopardy;

import java.util.List;
import java.util.Scanner;

import benjamin.BenTCP.TCPServer;

public class ServerStarter implements Runnable{
	int num = 0;
	ActionCenter actionCenter;
	List<TCPServer> serverPlayers;
	
	public ServerStarter(int num, ActionCenter ac, List<TCPServer> sp) {
		this.num = num;
		actionCenter = ac;
		serverPlayers = sp;
	}
	
	@Override
	public void run() {
		System.out.println("[Team" + num + "] Waiting for player " + num);
		Scanner s = new Scanner(System.in);
		int port = 500 + num;
		serverPlayers.set(num, new TCPServer(port, new TeamOnDataArrival(0, actionCenter), new DefaultTCPSetupStream(s,  "Team" + num), 1));
		actionCenter.activate(num);
	}
}
