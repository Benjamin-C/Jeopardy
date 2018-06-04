package benjaminc.jeopardy;

import benjamin.BenTCP.TCPOnDataArrival;
import benjaminc.util.Util;

public class TeamOnDataArrival implements TCPOnDataArrival{

	private int num;
	private ActionCenter actionCenter;
	private Team team;
	private PickNumberCallback numberCallback;
	
	public TeamOnDataArrival(int num, ActionCenter actionCenter, Team t) {
		this.num = num;
		this.actionCenter = actionCenter;
		team = t;
	}
	
	@Override
	public void onDataArrived(byte[] data) {
		if(data.length > 0) {
			switch(data[0]) {
			case 0x50: {// Buzz
				actionCenter.buzz(num);
				System.out.println("Buzzing " + num);
			} break;
			case 0x30: {// Wager
				if(numberCallback != null) {
					System.out.println("odr" + data[0] + " " + data[1] + " " + data[2]);
					if(data.length >= 3) {
						int temp = 0;
						temp = data[1] & 0xff;
						temp = temp << 8;
						temp = temp + (data[2] & 0xFF);
						System.out.println(temp);
						numberCallback.whenDone(temp);
					} else {
						numberCallback.whenCanceled();
					}
				}
			}
			}
		}
	}
	
	public void pickNumber(PickNumberCallback pncb) {
		numberCallback = pncb;
	}

}
