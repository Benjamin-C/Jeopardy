package benjaminc.jeopardy;

import java.util.Scanner;
import benjamin.BenTCP.TCPServer;

public class ServerStarter implements Runnable{
	int num = 0;
	ActionCenter actionCenter;
	Team team;
	
	public ServerStarter(int num, ActionCenter ac, Team t) {
		this.num = num;
		actionCenter = ac;
		team = t;
	}
	
	@Override
	public void run() {
		System.out.println("[Team" + num + "] Waiting for player " + num);
		Scanner s = new Scanner(System.in);
		int port = 500 + num;
		team.setServer(new TCPServer(port, new TeamOnDataArrival(0, actionCenter, team), new DefaultTCPSetupStream(s,  "Team" + num), 1));
		actionCenter.activate(num);
	}
}
