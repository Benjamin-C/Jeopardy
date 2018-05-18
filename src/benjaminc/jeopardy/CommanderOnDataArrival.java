package benjaminc.jeopardy;

import javax.swing.plaf.synth.SynthSeparatorUI;

import benjamin.BenTCP.TCPOnDataArrival;

public class CommanderOnDataArrival implements TCPOnDataArrival{

	private ActionCenter actionCenter;
	private Game game;
	
	public CommanderOnDataArrival(ActionCenter actionCenter, Game game) {
		this.actionCenter = actionCenter;
		this.game = game;
	}
	
	@Override
	public void onDataArrived(byte[] data) {
		if(data.length > 0) {
			switch(game.getMode()) {
			case CONNECT:
				switch(data[0]) {
				case 0x40: {/* Buzz */for(int i = 0; i < 4; i++) { actionCenter.activate(i); } System.out.println("Buzzing everyone"); } break;
				default: { System.out.println(defaultCase(data)); }
				}
			case INIT: {
				switch(data[0]) {
				case 0x40: {/* Start */actionCenter.begin(); } break;
				default: { System.out.println(defaultCase(data)); }
				}
			}
			case SELECT: {
				switch(data[0]) {
				case 0x39: {/* Select question */ }
				default: { System.out.println(defaultCase(data)); }
				}
			}
			default: { System.out.println(defaultCase(data)); }
			}
		} else {
			System.out.println("Thought I heard something, guess not");
		}
	}
	
	private String defaultCase(byte[] data) {
		String temp = "";
		for(int i = 0; i < data.length; i++) {
			temp = temp + toHex(data[i]);
		} return ("Got " + temp);
	}
	
	private String toHex(byte in) {
	    StringBuilder sb = new StringBuilder();
	    sb.append(String.format("%02X", in) + "");
	    return sb.toString();
	}
}
