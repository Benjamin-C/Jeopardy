package jeopardy;

import benjamin.BenTCP.TCPOnDataArrival;

public class CommanderOnDataArrival implements TCPOnDataArrival{

	private ActionCenter actionCenter;
	
	public CommanderOnDataArrival(ActionCenter actionCenter) {
		this.actionCenter = actionCenter;
	}
	
	@Override
	public void onDataArrived(byte[] data) {
		if(data.length > 0) {
			switch(data[0]) {
			case 0x40: {// Buzz
				actionCenter.activate(0);
				actionCenter.activate(1);
				actionCenter.activate(2);
				actionCenter.activate(3);
				System.out.println("Buzzing everyone");
			} break;
			default: {
				String temp = "";
				for(int i = 0; i < data.length; i++) {
					temp = temp + toHex(data[i]);
				} System.out.println("Got " + temp);
			}
			}
		} else {
			System.out.println("Thought I heard something, guess not");
		}
	}
	
	private String toHex(byte in) {
	    StringBuilder sb = new StringBuilder();
	    sb.append(String.format("%02X", in) + "");
	    return sb.toString();
	}
}
