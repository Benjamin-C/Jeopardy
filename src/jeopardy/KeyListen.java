package jeopardy;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyListen {
	
	//private int team = -1;
	private int num;
	private int numMult;
	private Team teamSel;
	private Team teamAns;
	private Team teamMod;
	private List<Team> teams;
	private List<Category> cat;
	private List<Team> winners;
	private Category selCat;
	private GameType round = GameType.NORMAL;
	private GamePanel gamePanel;
	private Game game;
	private boolean enabled;
	private Object sync;
	private Question selQues;
	
	public KeyListen(GamePanel gp, Game g, List<Team> team) {
		num = 0;
		numMult = 1;
		teamSel = null;
		teamAns = null;
		teamMod = null;
		winners = new ArrayList<Team>();
		round = GameType.NORMAL;
		gamePanel = gp;
		game = g;
		enabled = false;
		teams = team;
	}
	
	public void setSyncObject(Object s) {
		sync = s;
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(enabled) {
			switch(game.getMode()) {
			case INIT: {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getKeyLocation() == 1) {
					Util.resume(sync);
				}
			} break;
			case SELECT: {
				switch(key) {
				// Select category
				case KeyEvent.VK_Q: selCat = cat.get(0); break;
				case KeyEvent.VK_W: selCat = cat.get(1); break;
				case KeyEvent.VK_E: selCat = cat.get(2); break;
				case KeyEvent.VK_R: selCat = cat.get(3); break;
				case KeyEvent.VK_T: selCat = cat.get(4); break;
				case KeyEvent.VK_Y: selCat = cat.get(5); break;
		
				// Select points
				case KeyEvent.VK_A: if(selCat != null) { selectQuestion(selCat.getQuestion(0));} break;
				case KeyEvent.VK_S: if(selCat != null) { selectQuestion(selCat.getQuestion(1));} break;
				case KeyEvent.VK_D: if(selCat != null) { selectQuestion(selCat.getQuestion(2));} break;
				case KeyEvent.VK_F: if(selCat != null) { selectQuestion(selCat.getQuestion(3));} break;
				case KeyEvent.VK_G: if(selCat != null) { selectQuestion(selCat.getQuestion(4));} break;
				
				// Cancel selection
				//case KeyEvent.VK_ESCAPE: if(selQues != null) {selQues.setIsUsed(false);gamePanel.drawMainPanel(cat);exit();} break;
				
				case KeyEvent.VK_M: { for(Category c : cat) { for(Question q : c.getQuestions()) { q.setIsUsed(true); } } cat.get(0).getQuestion(0).setIsUsed(false); gamePanel.drawMainPanel(cat);} break;
				}
			} break; // End mode SELECT
			case BUZZ: {
				switch(key) {
				// Happens when a team buzzes in
				case KeyEvent.VK_F5: {setlectTeam(teams.get(0));} break;
				case KeyEvent.VK_F6: {setlectTeam(teams.get(1));} break;
				case KeyEvent.VK_F7: {setlectTeam(teams.get(2));} break;
				case KeyEvent.VK_F8: {setlectTeam(teams.get(3));} break;
				}
			} break; // end mode BUZZ
			case ANSWER: {
				switch(key) {
				case KeyEvent.VK_BACK_SPACE: {
					gamePanel.displayText(selQues.getAnswer()); // Show answer
					teamAns.addScore(selQues.getScore());
					game.setMode(Mode.SHOW_CORRECT);
				} break;
				case KeyEvent.VK_BACK_SLASH: {
					teamAns.setGuessed(true);
					teamAns.addScore(selQues.getScore() * -1);
					boolean done = true;
					for(Team t : teams) {
						if(!t.hasGuessed()) {
							done = false;
						}
					}
					if(done) {
						gamePanel.displayText(selQues.getAnswer());
						game.setMode(Mode.SHOW_CORRECT);
					} else {
						game.setMode(Mode.BUZZ);
						gamePanel.displayText(selQues.getQuestion()); // Remove the team's color border form the screen
					}
				} break;
				}
			} break; // end mode ANSWER
			case SHOW_CORRECT: {
				switch(key) {
				case KeyEvent.VK_ENTER: {
					if(e.getKeyLocation() == 1) {
						
						boolean done = true;
						for(Category c : cat) {
							for(Question q : c.getQuestions()) {
								if(!q.isUsed()) {
									done = false; // Set done to false if there is a question that is not done
								}
							}
						}
						if(done) {
							Util.resume(sync);
							System.out.println("Done with questions");
						} else {
							gamePanel.drawMainPanel(cat);
							game.setMode(Mode.SELECT);
						}
					}
					
				} break;
				}
			} break; // end mode SHOW_CORRECT
			
			
			case FINAL_CORRECT:
				break;
			case FINAL_JEOPARDY:
				break;
			case RESULTS:
				break;
			case SCORE:
				break;
			case SCORE_CHANGE: {
				pickNumber(key);
			} break;
			case WAGER:
				break;
			default:
				break;
			}
		}
	}

	// TODO update numbers
	private void pickNumber(int key) {
		switch(key) {
		case KeyEvent.VK_NUMPAD0: selectNumber(0); break;
		case KeyEvent.VK_NUMPAD1: selectNumber(1); break;
		case KeyEvent.VK_NUMPAD2: selectNumber(2); break;
		case KeyEvent.VK_NUMPAD3: selectNumber(3); break;
		case KeyEvent.VK_NUMPAD4: selectNumber(4); break;
		case KeyEvent.VK_NUMPAD5: selectNumber(5); break;
		case KeyEvent.VK_NUMPAD6: selectNumber(6); break;
		case KeyEvent.VK_NUMPAD7: selectNumber(7); break;
		case KeyEvent.VK_NUMPAD8: selectNumber(8); break;
		case KeyEvent.VK_NUMPAD9: selectNumber(9); break;
		case KeyEvent.VK_SUBTRACT: selectNumber(10); break;
		}
	}
	private void selectNumber(int d) {
		num = (num * 10) + d;
		int numb = num * numMult;
		System.out.println(numb);
		if(game.getMode() == Mode.WAGER) {
			gamePanel.displayText(teamSel.getName() + " Team\n" + numb);
		}
	}
	private void selectQuestion(Question q) { // Select the question
		if(q.isUsed() == false) {
			for(Team t : teams) {
				t.setGuessed(false);
			}
			gamePanel.displayText(q.getQuestion());
			q.setIsUsed(true);
			selQues = q;
			game.setMode(Mode.BUZZ);
		}
	}
	private void setlectTeam(Team t) {
		gamePanel.displayText(selQues.getQuestion(), t.getColor());
		teamAns = t;
		game.setMode(Mode.ANSWER);
	}
	
	public void enable() {
		enabled = true;
	}
	public void disable() {
		enabled = false;
	}
	
	public void setQuestions(List<Category> c) {
		cat = c;
	}
	
	private void moveOn() {
		isDone = false;
		System.out.println(round);
		switch(round) {
		case NORMAL: {
			game.beginRoundOne();
			round = GameType.DOUBLE;
			//exit(true);
		} break;
		case DOUBLE: {
			game.beginRoundTwo();
			round = GameType.FINAL;
			//exit(true);
		} break;
		case FINAL: {
			game.beginRoundThree();
			//exit(true);
		} break;
		}
	}
	
	public void buzz(int n) {
		// TODO Handle teams buzzing in remotly
	}
	
	@SuppressWarnings("unused")
	private void finalSwitch() {
		switch(teamSel.getName()) {
		case "Red": { teamSel = teams.get(1); } break;
		case "Yellow": { teamSel = teams.get(2); } break;
		case "Green": { teamSel = teams.get(3); } break;
		case "Blue": { game.setMode(Mode.RESULTS); } break;
		}
		if(game.getMode() != Mode.RESULTS) {
			gamePanel.displayText(cat.get(0).getQuestion(0).getAnswer(), teamSel.getColor());
		} else { // TODO make this take less lines, mabe use math.max
			if(teams.get(0).getScore() > teams.get(1).getScore()) {teamSel = teams.get(0);} else {teamSel = teams.get(1);}
			if(teamSel.getScore() < teams.get(2).getScore()) {teamSel = teams.get(2);}
			if(teamSel.getScore() < teams.get(3).getScore()) {teamSel = teams.get(3);}
			if(teams.get(0).getScore() == teamSel.getScore()) {winners.add(teams.get(0));}
			if(teams.get(1).getScore() == teamSel.getScore()) {winners.add(teams.get(1));}
			if(teams.get(2).getScore() == teamSel.getScore()) {winners.add(teams.get(2));}
			if(teams.get(3).getScore() == teamSel.getScore()) {winners.add(teams.get(3));}
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
				game.setMode(Mode.SCORE);
			}
		}
	}
}
