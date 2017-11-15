package jeopardy;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyListen {
	public static boolean isDone;
	
	private static int catSel = -1;
	private static int ptSel = -1;
	private static int npt = -1;
	private static int nct = -1;
	private static int team = -1;
	private static int num = 0;
	private static int numMult = 1;
	private static int wrong = 0;
	private static int mayLeave = 0;
	private static boolean ch = false;
	private static boolean canAns = false;
	private static boolean teamPrinted = false;
	private static boolean hasSelCat = false;
	private static String ques = null;
	private static Team teamSel = null;
	private static Team teamAns = null;
	private static Team teamMod = null;
	private static Team teamRed = new Team(Color.RED, "Red");
	private static Team teamYellow = new Team(Color.YELLOW, "Yellow");
	private static Team teamGreen = new Team(Color.GREEN, "Green");
	private static Team teamBlue = new Team(Color.CYAN, "Blue");
	private static Mode mode = Mode.INIT;
	private static List<Category> cat;
	private static List<Team> winners = new ArrayList<Team>();
	private static Robot robot;
	private static int round = 0;
	
	public static void init() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		GamePanel.setTeams(teamRed, teamYellow, teamGreen, teamBlue);
	}
	
	public static void keyTyped(KeyEvent e) {}
	
	public static void keyReleased(KeyEvent e) {}
	
	public static void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		// Select category
		case KeyEvent.VK_Q: catSel = 0; break;
		case KeyEvent.VK_W: catSel = 1; break;
		case KeyEvent.VK_E: catSel = 2; break;
		case KeyEvent.VK_R: catSel = 3; break;
		case KeyEvent.VK_T: catSel = 4; break;
		case KeyEvent.VK_Y: catSel = 5; break;

		// Select points
		case KeyEvent.VK_A: ptSel = 0; break;
		case KeyEvent.VK_S: ptSel = 1; break;
		case KeyEvent.VK_D: ptSel = 2; break;
		case KeyEvent.VK_F: ptSel = 3; break;
		case KeyEvent.VK_G: ptSel = 4; break;
		
		// Number Pad Input Test
		case KeyEvent.VK_NUMPAD0: num = (num * 10) + 0; ch = true; break;
		case KeyEvent.VK_NUMPAD1: num = (num * 10) + 1; ch = true; break;
		case KeyEvent.VK_NUMPAD2: num = (num * 10) + 2; ch = true; break;
		case KeyEvent.VK_NUMPAD3: num = (num * 10) + 3; ch = true; break;
		case KeyEvent.VK_NUMPAD4: num = (num * 10) + 4; ch = true; break;
		case KeyEvent.VK_NUMPAD5: num = (num * 10) + 5; ch = true; break;
		case KeyEvent.VK_NUMPAD6: num = (num * 10) + 6; ch = true; break;
		case KeyEvent.VK_NUMPAD7: num = (num * 10) + 7; ch = true; break;
		case KeyEvent.VK_NUMPAD8: num = (num * 10) + 8; ch = true; break;
		case KeyEvent.VK_NUMPAD9: num = (num * 10) + 9; ch = true; break;
		case KeyEvent.VK_SUBTRACT: numMult = numMult * -1; ch = true; break;
		
		// cancel question selection
		case KeyEvent.VK_ESCAPE: {cat.get(nct).getQuestion(npt).setIsUsed(false);GamePanel.drawMainPanel(cat);exit();} break;
		
		// Happens when a team buzzes in
		case KeyEvent.VK_F9: {teamSel = teamRed;} break;
		case KeyEvent.VK_F10: {teamSel = teamYellow;} break;
		case KeyEvent.VK_F11: {teamSel = teamGreen;} break;
		case KeyEvent.VK_F12: {teamSel = teamBlue;} break;
		
		// Handle correct or wrong answer
		case KeyEvent.VK_BACK_SPACE: { // correct
			if(mode == Mode.FINAL_CORRECT) {
				teamSel.addScore(teamSel.getWager());
				finalSwitch();
			} else {
				if(teamAns != null) {
					GamePanel.displayText(cat.get(nct).getQuestion(npt).getAnswer());
					teamAns.addScore(cat.get(nct).getQuestion(npt).getScore());
					mayLeave = 1;
				}
			}
		} break;
		case KeyEvent.VK_BACK_SLASH: { // wrong
			if(mode == Mode.FINAL_CORRECT) {
				teamSel.addScore(teamSel.getWager() * -1);
				finalSwitch();
			} else {
				if(teamAns != null && canAns == false) {
					GamePanel.displayText(ques);
					canAns = true;
					teamAns.setGuessed(true);
					teamAns.addScore(cat.get(nct).getQuestion(npt).getScore() * -1);
					wrong++;
					System.out.println(teamAns.getName() + " got it wrong " + wrong);
				}
			}
		} break;
		
		// select team
		case KeyEvent.VK_F1: {teamMod = teamRed;} break;
		case KeyEvent.VK_F2: {teamMod = teamYellow;} break;
		case KeyEvent.VK_F3: {teamMod = teamGreen;} break;
		case KeyEvent.VK_F4: {teamMod = teamBlue;} break;
		
		case KeyEvent.VK_I: {
			if(mode != Mode.INIT) {
				mode = Mode.SCORE_SET;
				num = 0;
				numMult = 1;
			}
		} break;
		case KeyEvent.VK_K: {
			if(mode != Mode.INIT) {
				mode = Mode.SCORE_ADD;
				numMult = 1;
				num = 0;
			}
		} break;
		case KeyEvent.VK_ENTER: {
			if(e.getKeyLocation() == 4) {
				switch(mode) {
				case RUN: {} break;
				case SCORE_ADD: teamMod.addScore(num * numMult); GamePanel.drawMainPanel(cat); break;
				case SCORE_SET: teamMod.setScore(num * numMult); GamePanel.drawMainPanel(cat); break;
				case WAGER: {
					teamSel.setWager(num * numMult);
					switch(teamSel.getName()) {
					case "Red": { teamSel = teamYellow; } break;
					case "Yellow": { teamSel = teamGreen; } break;
					case "Green": { teamSel = teamBlue; } break;
					case "Blue": { System.out.println(GamePanel.displayText(cat.get(0).getQuestion(0).getQuestion() + "")); mode = Mode.FINAL_JEOPARDY; System.out.println("Blue Team");} break;
					}
					num = 0;
					numMult = 1;
					if(mode != Mode.FINAL_JEOPARDY) {
						GamePanel.displayText(teamSel.getName() + " Team\n");
					}
				} break;
				default: {} break;
				}	
			} else if(e.getKeyLocation() == 1) {
				switch(mode) {
				case INIT: moveOn(); break;
				case RUN: {
					switch(round) {
					case 3: {
						mode = Mode.WAGER;
						num = 0;
						numMult = 1;
						teamSel = teamRed;
						GamePanel.displayText(teamSel.getName() + " Team\n");
					} break;
					default: {
						if(mayLeave == 1) {
							GamePanel.drawMainPanel(cat);
							exit();
							mayLeave = 0;
						}
					} break;
					}
				} break;
				case FINAL_JEOPARDY: {
					mode = Mode.FINAL_CORRECT;
					teamSel = teamRed;
					GamePanel.displayText(cat.get(0).getQuestion(0).getAnswer(), teamSel.getColor());
				} break;
				case SCORE: {
					GamePanel.showScores();
				}
				}
			}
		} break;
		case KeyEvent.VK_B: {
			System.out.println("b");
			moveOn();
		} break;
		} // end of key listeners
		
		// TODO figure out how to do this properly
		// Bodge to prevent f10 from creating errors
		// Will be removed with the F9-12 buzzing
		// Presses and releases F10 to undo whatever F10 does
		// only sort of works
		if(e.getKeyCode() == KeyEvent.VK_F10) {
		try {
				robot.keyPress(KeyEvent.VK_F10);
				robot.delay(1);
				robot.keyRelease(KeyEvent.VK_F10);
			
			} catch(NullPointerException ex) {
				ex.printStackTrace();
			}
		}
		doer();
	}
	
	private static void finalSwitch() {
		switch(teamSel.getName()) {
		case "Red": { teamSel = teamYellow; } break;
		case "Yellow": { teamSel = teamGreen; } break;
		case "Green": { teamSel = teamBlue; } break;
		case "Blue": { mode = Mode.RESULTS; } break;
		}
		if(mode != Mode.RESULTS) {
			GamePanel.displayText(cat.get(0).getQuestion(0).getAnswer(), teamSel.getColor());
		} else {
			if(teamRed.getScore() > teamYellow.getScore()) {teamSel = teamRed;} else {teamSel = teamYellow;}
			if(teamSel.getScore() < teamGreen.getScore()) {teamSel = teamGreen;}
			if(teamSel.getScore() < teamBlue.getScore()) {teamSel = teamBlue;}
			if(teamRed.getScore() == teamSel.getScore()) {winners.add(teamRed);}
			if(teamYellow.getScore() == teamSel.getScore()) {winners.add(teamYellow);}
			if(teamGreen.getScore() == teamSel.getScore()) {winners.add(teamGreen);}
			if(teamBlue.getScore() == teamSel.getScore()) {winners.add(teamBlue);}
			if(winners.size() == 1) {
				GamePanel.displayText("And the winner is:\n" + teamSel.getName(), teamSel.getColor());
			} else {
				String out = "";
				int red = 0;
				int green = 0;
				int blue = 0;
				for(int i = 0; i < winners.size(); i++) { 
					if(i != 0) {
						out = out + ", ";
					}
					out = out + winners.get(i).getName();
					red = red + winners.get(i).getColor().getRed();
					green = green + winners.get(i).getColor().getGreen();
					blue = blue + winners.get(i).getColor().getBlue();
				}
				red = red / winners.size();
				green = green / winners.size();
				blue = blue / winners.size();
				GamePanel.displayText("And the winner is:\n" + out, new Color(0xfe, 0x67, 0x00));
				mode = Mode.SCORE;
			}
		}
	}
	
	private static void doer() {
		// Begin doers
		
		// Show color if round is 0
		if(round == 0 && teamSel != null) {
			GamePanel.displayText("Jeopardy", teamSel.getColor());
			teamSel = null;
		}
		
		// Handle teams buzzing in
		if(teamSel != null && canAns == true && teamSel.hasGuessed() == false) {
			GamePanel.displayText(ques, teamSel.getColor());
			canAns = false;
			teamAns = teamSel;
			teamSel = null;
		}
		
		// End if everyone has guessed wrong
		if(wrong >= 4) {
			GamePanel.displayText(cat.get(nct).getQuestion(npt).getAnswer());
			mayLeave = 1;
			exit();
		}
		
		// Print number to console
		if(ch == true) {
			ch = false;
			int numb = num * numMult;
			System.out.println(numb);
			if(mode == Mode.WAGER) {
				GamePanel.displayText(teamSel.getName() + " Team\n" + numb);
			}
		}
		ch = false;
		
		// display question screen
		if(catSel != -1 && ptSel != -1 && hasSelCat == false && mode == Mode.RUN) {
			if(cat.get(catSel).getQuestion(ptSel).isUsed() == false) {
				ques = cat.get(catSel).getQuestion(ptSel).getQuestion();
				GamePanel.displayText(ques);
				cat.get(catSel).getQuestion(ptSel).setIsUsed(true);
				npt = ptSel;
				nct = catSel;
				catSel = -1;
				canAns = true;
				hasSelCat = true;
			}
		}
		ptSel = -1;
		
		// Print selected team to console
		if(teamPrinted == true) {
			System.out.println("team " + team);
			teamPrinted = false;
		}
		
		
	}
	
	public static void setQuestions(List<Category> c) {
		cat = c;
	}
	
	private static void exit() {
		npt = -1;
		nct = -1;
		ptSel = -1;
		catSel = -1;
		teamRed.setGuessed(false);
		teamYellow.setGuessed(false);
		teamGreen.setGuessed(false);
		teamBlue.setGuessed(false);
		teamAns = null;
		ques = null;
		wrong = 0;
		canAns = false;
		hasSelCat = false;
	}
	
	private static void exit(boolean noCheck) {
		exit();
		if(noCheck == false) {
			isDone = true;
		for(int i = 0; i < cat.size(); i++) {
			if(cat.get(i).isDone() == false) {
				isDone = false;
				break;
			}
		}
		if(isDone == true) {
			moveOn();
		}
		}
	}
	
	private static void moveOn() {
		isDone = false;
		System.out.println(round);
		switch(round) {
		case 0: {
			canAns = false;
			Main.beginRoundOne();
			round = 1;
			exit(true);
		} break;
		case 1: {
			Main.beginRoundTwo();
			round = 2;
			exit(true);
		} break;
		case 2: {
			Main.beginRoundThree();
			round = 3;
			exit(true);
		} break;
		}
	}
	
	public static void canAns(boolean c) {
		canAns = c;
	}
	
	public static void buzzRed() { teamSel = teamRed; doer(); }
	public static void buzzYellow() { teamSel = teamYellow; doer(); }
	public static void buzzGreen() { teamSel = teamGreen; doer(); }
	public static void buzzBlue() { teamSel = teamBlue; doer(); }
	
	public static void setMode(Mode m) {
		mode = m;
	}
}
