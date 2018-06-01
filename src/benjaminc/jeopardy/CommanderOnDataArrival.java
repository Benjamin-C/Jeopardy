package benjaminc.jeopardy;

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
		System.out.println(defaultCase(data));
		if(data.length > 0) {
			switch(game.getMode()) {
			case CONNECT: {
				switch(data[0]) {
				case 0x21: {/* Activate all */actionCenter.activateAll(); System.out.println("Buzzing everyone . . ."); } break;
				default: { System.out.println(defaultCase(data)); } break;
				}
			} break;
			case INIT: {
				switch(data[0]) {
				case 0x20: {/* Start */actionCenter.begin(); } break;
				default: { System.out.println(defaultCase(data)); } break;
				}
			} break;
			case SELECT: {
				switch(data[0]) {
				case 0x30: {/* Select category */ if(data.length > 1) {actionCenter.selectCategory((int) data[1]);}} break;
				case 0x31: {/* Select points */ if(data.length > 1) {actionCenter.selectPoints((int) data[1]);}} break;
				case 0x32: {/* Select question */ actionCenter.selectQuestion(); } break;
				case 0x33: {/* FastGame */ actionCenter.fastGame(); } break;
				case 0x34: {/* Set team score */ if(data.length > 3) { actionCenter.setTeamScore(data[1] & 0xFF, ((data[2] * -2) + 1) * (( ((int) data[3] & 0xFF) << 8) + ((int) data[4] & 0xFF)));} } break;
				case 0x35: {/* add team score */ if(data.length > 3) { actionCenter.addToTeamScore(data[1] & 0xFF, ((data[2] * -2) + 1) * (( ((int) data[3] & 0xFF) << 8) + ((int) data[4] & 0xFF)));} } break;
				case 0x36: {/* Cancel question selection */ actionCenter.cancelCategorySelection();} break;
				default: { System.out.println(defaultCase(data)); } break;
				}
			} break;
			case BUZZ: {
				switch(data[0]) {
				case 0x40: {/* Cancel team selection */ actionCenter.cancelTeamSelection();} break;
				}
			} break;
			case ANSWER: {
				switch(data[0]) {
				case 0x50: {/* correct answer */ actionCenter.teamAnsworedCorrectly();}break;
				case 0x51: {/* wrong answer */ actionCenter.teamAnsworedIncorrectly();}break;
				}
			} break;
			case SHOW_CORRECT: {
				switch(data[0]) {
				case 0x60: {/* done looking at answer */ actionCenter.doneLookingAtAnswer(); }break;
				}
			} break;
			default: { System.out.println(defaultCase(data)); } break;
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
