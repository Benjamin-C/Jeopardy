package jeopardy;

public class temp {
	switch(e.getKeyCode()) {
	
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
