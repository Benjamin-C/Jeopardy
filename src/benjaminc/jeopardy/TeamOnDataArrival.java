package benjaminc.jeopardy;

import benjamin.BenTCP.TCPOnDataArrival;

public class TeamOnDataArrival implements TCPOnDataArrival{

	private int num;
	private ActionCenter actionCenter;
	
	public TeamOnDataArrival(int num, ActionCenter actionCenter) {
		this.num = num;
		this.actionCenter = actionCenter;
	}
	
	@Override
	public void onDataArrived(byte[] data) {
		if(data.length > 0) {
			switch(data[0]) {
			case 0x50: {// Buzz
				actionCenter.buzz(num);
				System.out.println("Buzzing " + num);
			} break;
			}
		}
	}

}
