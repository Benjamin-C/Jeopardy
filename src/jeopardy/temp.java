package jeopardy;

public class temp {
	switch(e.getKeyCode()) {
	// Number Pad Input Test
	
	
	// Handle correct or wrong answer
	// Correct answer
	case KeyEvent.VK_BACK_SPACE: {
		if(mode == Mode.FINAL_CORRECT) {teamSel.addScore(teamSel.getWager()); finalSwitch();}
		else {if(teamAns != null) {}}
	} break;
	// Wrong answer
	case KeyEvent.VK_BACK_SLASH: {
		if(mode == Mode.FINAL_CORRECT) {teamSel.addScore(teamSel.getWager() * -1); finalSwitch();}
		else {if(teamAns != null && canAns == false) {
				
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
}
