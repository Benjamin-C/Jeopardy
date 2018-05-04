package jeopardy;

import benjamin.BenTCP.TCPOnDataArrival;

public class TeamOnDataArrival implements TCPOnDataArrival{

	private int num;
	private ActionCenter actionCenter;
	
	public TeamOnDataArrival(int num, ActionCenter actionCenter) {
		this.num = num;
	}
	
	@Override
	public void onDataArrived(byte[] data) {
		if(data.length > 0) {
			switch(data[0]) {
			case 0x01: {// Buzz
				actionCenter.buzz(num);
			} break;
			}
		}
	}

}
