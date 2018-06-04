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
		TeamOnDataArrival todr = new TeamOnDataArrival(0, actionCenter, team);
		team.setServer(new TCPServer(port, todr, new DefaultTCPSetupStream(s,  "Team" + num), 1));
		team.setOnDataArrival(todr);
		actionCenter.activate(num);
	}
}
