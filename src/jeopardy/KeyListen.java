package jeopardy;

import java.awt.event.KeyEvent;

public class KeyListen {
	
	private ActionCenter actionCenter;
	private Game game;
	private boolean enabled;
	private boolean mayBuzzByKeyboard;
	
	public KeyListen(Game g, ActionCenter ac) {
		actionCenter = ac;
		game = g;
		mayBuzzByKeyboard = false;
	}
	
	public void enable() {
		enabled = true; // Allow use of the stuffs
	}
	public void disable() {
		enabled = false; // Prevent use of the stuffs
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(enabled) {
			switch(game.getMode()) {
			case CONNECT: {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getKeyLocation() == 1) {
					for(int i = 0; i < 4; i++) { actionCenter.activate(i); }
				}
			} break;
			case INIT: {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getKeyLocation() == 1) {
					actionCenter.begin();
				}
			} break;
			case SELECT: {
				switch(key) {
				// Select category
				case KeyEvent.VK_Q: actionCenter.selectCategory(0); break;
				case KeyEvent.VK_W: actionCenter.selectCategory(1); break;
				case KeyEvent.VK_E: actionCenter.selectCategory(2); break;
				case KeyEvent.VK_R: actionCenter.selectCategory(3); break;
				case KeyEvent.VK_T: actionCenter.selectCategory(4); break;
				case KeyEvent.VK_Y: actionCenter.selectCategory(5); break;
		
				// Select points
				case KeyEvent.VK_A: actionCenter.selectQuestion(0); break;
				case KeyEvent.VK_S: actionCenter.selectQuestion(1); break;
				case KeyEvent.VK_D: actionCenter.selectQuestion(2); break;
				case KeyEvent.VK_F: actionCenter.selectQuestion(3); break;
				case KeyEvent.VK_G: actionCenter.selectQuestion(4); break;
				
				// Cancel selection
				//case KeyEvent.VK_ESCAPE: if(selQues != null) {selQues.setIsUsed(false);gamePanel.drawMainPanel(cat);exit();} break;
				
				// FastGame™
				case KeyEvent.VK_M: actionCenter.fastGame(); break;
				
				// Set Team Score
				case KeyEvent.VK_I: actionCenter.setTeamScore(); break;
				case KeyEvent.VK_K: actionCenter.addToTeamScore(); break;
				
				// Cancel category selection
				case KeyEvent.VK_ESCAPE: actionCenter.cancelCategorySelection(); break;
				}	
			} break; // End mode SELECT
			case BUZZ: {
				if(mayBuzzByKeyboard) {
					switch(key) {
					// Happens when a team buzzes in
					case KeyEvent.VK_F5: actionCenter.buzz(0); break;
					case KeyEvent.VK_F6: actionCenter.buzz(1); break;
					case KeyEvent.VK_F7: actionCenter.buzz(2); break;
					case KeyEvent.VK_F8: actionCenter.buzz(3); break;
					}
				}
				//Cancel team selection
				if(key == KeyEvent.VK_ESCAPE) { actionCenter.cancelTeamSelection(); }
			} break; // end mode BUZZ
			case ANSWER: {
				switch(key) {
				case KeyEvent.VK_BACK_SPACE: actionCenter.teamAnsworedCorrectly(); break;
				case KeyEvent.VK_BACK_SLASH: actionCenter.teamAnsworedIncorrectly(); break;
				}
			} break; // end mode ANSWER
			case SHOW_CORRECT: {
				switch(key) {
				case KeyEvent.VK_ENTER: if(e.getKeyLocation() == 1) { actionCenter.doneLookingAtAnswer();} break;
				}
			} break; // end mode SHOW_CORRECT
			case SELECT_MOD_TEAM: {
				switch(key) {
				case KeyEvent.VK_F1: actionCenter.selectModTeam(0); break;
				case KeyEvent.VK_F2: actionCenter.selectModTeam(1); break;
				case KeyEvent.VK_F3: actionCenter.selectModTeam(2); break;
				case KeyEvent.VK_F4: actionCenter.selectModTeam(3); break;
				}
			} break;
			case SCORE_CHANGE: {
				
				
			} break;
			case PICK_NUMBER: {
				switch(key) {
				case KeyEvent.VK_NUMPAD0: actionCenter.selectNumber(0); break;
				case KeyEvent.VK_NUMPAD1: actionCenter.selectNumber(1); break;
				case KeyEvent.VK_NUMPAD2: actionCenter.selectNumber(2); break;
				case KeyEvent.VK_NUMPAD3: actionCenter.selectNumber(3); break;
				case KeyEvent.VK_NUMPAD4: actionCenter.selectNumber(4); break;
				case KeyEvent.VK_NUMPAD5: actionCenter.selectNumber(5); break;
				case KeyEvent.VK_NUMPAD6: actionCenter.selectNumber(6); break;
				case KeyEvent.VK_NUMPAD7: actionCenter.selectNumber(7); break;
				case KeyEvent.VK_NUMPAD8: actionCenter.selectNumber(8); break;
				case KeyEvent.VK_NUMPAD9: actionCenter.selectNumber(9); break;
				case KeyEvent.VK_SUBTRACT: actionCenter.selectNumber(-1); break;
				case KeyEvent.VK_ENTER: if(e.getKeyLocation() == 4) { actionCenter.finializeNumberSelection();} break;
				case KeyEvent.VK_ESCAPE: actionCenter.cancelNumberSelection(); break;
				}
			}
			
			case FINAL_CORRECT:
				break;
			case FINAL_JEOPARDY:
				break;
			case RESULTS:
				break;
			case SCORE:
				break;
			case WAGER:
				break;
			default:
				break;
			}
		}
	}
	
	public void mayTeamsBuzzByKeyboard(boolean may) {
		mayBuzzByKeyboard = may;
	}
}
