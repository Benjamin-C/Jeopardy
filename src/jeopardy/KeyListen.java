package jeopardy;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyListen {
	private boolean isDone;
	
	//private int team = -1;
	private int num = 0;
	private int numMult = 1;
	private int wrong = 0;
	private int mayLeave = 0;
	private boolean numberChanged = false;
	private boolean canAns = false;
	private Team teamSel = null;
	private Team teamAns = null;
	private Team teamMod = null;
	private Team teamRed = new Team(Color.RED, "Red");
	private Team teamYellow = new Team(Color.YELLOW, "Yellow");
	private Team teamGreen = new Team(Color.GREEN, "Green");
	private Team teamBlue = new Team(Color.CYAN, "Blue");
	private Mode mode = Mode.INIT;
	private List<Category> cat;
	private List<Team> winners = new ArrayList<Team>();
	private Category selCat;
	private Question selQues;
	private GameType round = GameType.NORMAL;
	private GamePanel gamePanel;
	private Game game;
	
	public KeyListen(GamePanel gp, Game g) {
		num = 0;
		numMult = 1;
		wrong = 0;
		mayLeave = 0;
		numberChanged = false;
		canAns = false;
		teamSel = null;
		teamAns = null;
		teamMod = null;
		teamRed = new Team(Color.RED, "Red");
		teamYellow = new Team(Color.YELLOW, "Yellow");
		teamGreen = new Team(Color.GREEN, "Green");
		teamBlue = new Team(Color.CYAN, "Blue");
		mode = Mode.INIT;
		winners = new ArrayList<Team>();
		round = GameType.NORMAL;
		gamePanel = gp;
		game = g;
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	
	@SuppressWarnings("incomplete-switch")
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		// Select category
		case KeyEvent.VK_Q: selCat = cat.get(0); break;
		case KeyEvent.VK_W: selCat = cat.get(1); break;
		case KeyEvent.VK_E: selCat = cat.get(2); break;
		case KeyEvent.VK_R: selCat = cat.get(3); break;
		case KeyEvent.VK_T: selCat = cat.get(4); break;
		case KeyEvent.VK_Y: selCat = cat.get(5); break;

		// Select points
		case KeyEvent.VK_A: if(selCat != null) { selQues = selCat.getQuestion(0);} break;
		case KeyEvent.VK_S: if(selCat != null) { selQues = selCat.getQuestion(1);} break;
		case KeyEvent.VK_D: if(selCat != null) { selQues = selCat.getQuestion(2);} break;
		case KeyEvent.VK_F: if(selCat != null) { selQues = selCat.getQuestion(3);} break;
		case KeyEvent.VK_G: if(selCat != null) { selQues = selCat.getQuestion(4);} break;
		
		// Number Pad Input Test
		case KeyEvent.VK_NUMPAD0: num = (num * 10) + 0; numberChanged = true; break;
		case KeyEvent.VK_NUMPAD1: num = (num * 10) + 1; numberChanged = true; break;
		case KeyEvent.VK_NUMPAD2: num = (num * 10) + 2; numberChanged = true; break;
		case KeyEvent.VK_NUMPAD3: num = (num * 10) + 3; numberChanged = true; break;
		case KeyEvent.VK_NUMPAD4: num = (num * 10) + 4; numberChanged = true; break;
		case KeyEvent.VK_NUMPAD5: num = (num * 10) + 5; numberChanged = true; break;
		case KeyEvent.VK_NUMPAD6: num = (num * 10) + 6; numberChanged = true; break;
		case KeyEvent.VK_NUMPAD7: num = (num * 10) + 7; numberChanged = true; break;
		case KeyEvent.VK_NUMPAD8: num = (num * 10) + 8; numberChanged = true; break;
		case KeyEvent.VK_NUMPAD9: num = (num * 10) + 9; numberChanged = true; break;
		case KeyEvent.VK_SUBTRACT: numMult = numMult * -1; numberChanged = true; break;
		
		// cancel question selection
		case KeyEvent.VK_ESCAPE: if(selQues != null) {selQues.setIsUsed(false);gamePanel.drawMainPanel(cat);exit();} break;
		
		// Happens when a team buzzes in
		case KeyEvent.VK_F5: {teamSel = teamRed;} break;
		case KeyEvent.VK_F6: {teamSel = teamYellow;} break;
		case KeyEvent.VK_F7: {teamSel = teamGreen;} break;
		case KeyEvent.VK_F8: {teamSel = teamBlue;} break;
		
		// Handle correct or wrong answer
		// Correct answer
		case KeyEvent.VK_BACK_SPACE: {
			if(mode == Mode.FINAL_CORRECT) {teamSel.addScore(teamSel.getWager()); finalSwitch();}
			else {if(teamAns != null) {gamePanel.displayText(selQues.getAnswer()); teamAns.addScore(selQues.getScore()); mayLeave = 1;}}
		} break;
		// Wrong answer
		case KeyEvent.VK_BACK_SLASH: {
			if(mode == Mode.FINAL_CORRECT) {teamSel.addScore(teamSel.getWager() * -1); finalSwitch();}
			else {if(teamAns != null && canAns == false) {
					gamePanel.displayText(selQues.getQuestion());
					canAns = true;
					teamAns.setGuessed(true);
					teamAns.addScore(selQues.getScore() * -1);
					wrong++;
					System.out.println(teamAns.getName() + " got it wrong " + wrong);
			}}
		} break;
		case KeyEvent.VK_CLOSE_BRACKET: {
			if(teamAns != null && canAns == false) {
				gamePanel.displayText(selQues.getQuestion());
				canAns = true;
				teamAns.setGuessed(true);
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
				System.out.println(mode);
				switch(mode) {
				case RUN: {} break;
				case SCORE_ADD: teamMod.addScore(num * numMult); gamePanel.drawMainPanel(cat); break;
				case SCORE_SET: teamMod.setScore(num * numMult); gamePanel.drawMainPanel(cat); break;
				case WAGER: {
					teamSel.setWager(num * numMult);
					switch(teamSel.getName()) {
					case "Red": { teamSel = teamYellow; } break;
					case "Yellow": { teamSel = teamGreen; } break;
					case "Green": { teamSel = teamBlue; } break;
					case "Blue": { System.out.println(gamePanel.displayText(cat.get(0).getQuestion(0).getQuestion() + "")); mode = Mode.FINAL_JEOPARDY; System.out.println("Blue Team");} break;
					}
					num = 0;
					numMult = 1;
					if(mode != Mode.FINAL_JEOPARDY) {
						gamePanel.displayText(teamSel.getName() + " Team\n");
					}
				} break;
				default: {} break;
				}	
			} else if(e.getKeyLocation() == 1) {
				switch(mode) {
				case INIT: moveOn(); break;
				case RUN: {
					switch(round) {
					case DOUBLE: {
						mode = Mode.WAGER;
						num = 0;
						numMult = 1;
						teamSel = teamRed;
						gamePanel.displayText(teamSel.getName() + " Team\n");
					} break;
					default: {
						if(mayLeave == 1) {
							gamePanel.drawMainPanel(cat);
							exit();
							mayLeave = 0;
						}
					} break;
					}
				} break;
				case FINAL_JEOPARDY: {
					System.out.println(mode);
					mode = Mode.FINAL_CORRECT;
					teamSel = teamRed;
					gamePanel.displayText(cat.get(0).getQuestion(0).getAnswer(), teamSel.getColor());
				} break;
				case SCORE: {
					gamePanel.showScores();
				}
				}
			}
		} break;
		case KeyEvent.VK_B: {
			System.out.println("b");
			moveOn();
		} break;
		} // end of key listeners
		doer();
	}
	
	private void doer() {
		// Begin doers
		
		// Show color if round is 0
		if(round == GameType.NORMAL && teamSel != null) {
			gamePanel.displayText("Jeopardy", teamSel.getColor());
			teamSel = null;
		}
		
		// Handle teams buzzing in
		if(teamSel != null && canAns == true && teamSel.hasGuessed() == false) {
			gamePanel.displayText(selQues.getQuestion(), teamSel.getColor());
			canAns = false;
			teamAns = teamSel;
			teamSel = null;
		}
		
		// End if everyone has guessed wrong
		if(wrong >= 4) {
			gamePanel.displayText(selQues.getAnswer());
			mayLeave = 1;
			exit();
		}
		
		// Print number to console
		if(numberChanged == true) {
			numberChanged = false;
			int numb = num * numMult;
			System.out.println(numb);
			if(mode == Mode.WAGER) {
				gamePanel.displayText(teamSel.getName() + " Team\n" + numb);
			}
		}
		numberChanged = false;
		
		// display question screen
		if(selQues != null && mode == Mode.RUN) {
			if(selQues.isUsed() == false) {
				gamePanel.displayText(selQues.getQuestion());
				selQues.setIsUsed(true);
				canAns = true;
			}
		}	
	}

	public void setQuestions(List<Category> c) {
		cat = c;
	}
	
	private void exit() {
		teamRed.setGuessed(false);
		teamYellow.setGuessed(false);
		teamGreen.setGuessed(false);
		teamBlue.setGuessed(false);
		teamAns = null;
		wrong = 0;
		canAns = false;
	}
	
	private void exit(boolean noCheck) {
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
	
	private void moveOn() {
		isDone = false;
		System.out.println(round);
		switch(round) {
		case NORMAL: {
			canAns = false;
			game.beginRoundOne();
			round = GameType.DOUBLE;
			exit(true);
		} break;
		case DOUBLE: {
			game.beginRoundTwo();
			round = GameType.FINAL;
			exit(true);
		} break;
		case FINAL: {
			game.beginRoundThree();
			exit(true);
		} break;
		}
	}
	
	private void canAns(boolean c) {
		canAns = c;
	}
	
	public void buzzRed() { teamSel = teamRed; doer(); }
	public void buzzYellow() { teamSel = teamYellow; doer(); }
	public void buzzGreen() { teamSel = teamGreen; doer(); }
	public void buzzBlue() { teamSel = teamBlue; doer(); }
	
	public void setMode(Mode m) {
		mode = m;
	}
	
	private void finalSwitch() {
		switch(teamSel.getName()) {
		case "Red": { teamSel = teamYellow; } break;
		case "Yellow": { teamSel = teamGreen; } break;
		case "Green": { teamSel = teamBlue; } break;
		case "Blue": { mode = Mode.RESULTS; } break;
		}
		if(mode != Mode.RESULTS) {
			gamePanel.displayText(cat.get(0).getQuestion(0).getAnswer(), teamSel.getColor());
		} else { // TODO make this take less lines, mabe use math.max
			if(teamRed.getScore() > teamYellow.getScore()) {teamSel = teamRed;} else {teamSel = teamYellow;}
			if(teamSel.getScore() < teamGreen.getScore()) {teamSel = teamGreen;}
			if(teamSel.getScore() < teamBlue.getScore()) {teamSel = teamBlue;}
			if(teamRed.getScore() == teamSel.getScore()) {winners.add(teamRed);}
			if(teamYellow.getScore() == teamSel.getScore()) {winners.add(teamYellow);}
			if(teamGreen.getScore() == teamSel.getScore()) {winners.add(teamGreen);}
			if(teamBlue.getScore() == teamSel.getScore()) {winners.add(teamBlue);}
			if(winners.size() == 1) {
				gamePanel.displayText("And the winner is:\n" + teamSel.getName(), teamSel.getColor());
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
				gamePanel.displayText("And the winner is:\n" + out, new Color(0xfe, 0x67, 0x00));
				mode = Mode.SCORE;
			}
		}
	}
}
