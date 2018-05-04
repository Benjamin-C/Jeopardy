package jeopardy;

import java.util.List;

public class ActionCenter {

	// "Public" variables
	private GamePanel gamePanel;
	private List<Category> cat;
	private List<Team> teams;
	private Game game;
	
	// Utility variables

	private Object sync;
	
	// Question selection variables
	private Category selCat;
	private Question selQues;
	
	// Team selection variables
	private Team teamSel;
	private Team teamMod;
	private Team teamAns;
	
	// Number Selection variables
	private Runnable whenNumberSelectionDone;
	private Runnable whenNumberSelectionCancel;
	private boolean isNumberNegitive;
	public boolean isModifyAdditive;
	private int num;
	
	public ActionCenter(GamePanel gp, Game g, List<Team> team) {	
		num = 0;
		teamSel = null;
		teamAns = null;
		teamMod = null;
		gamePanel = gp;
		game = g;
		teams = team;
		isNumberNegitive = false;
		isModifyAdditive = false;
	}
	
	//--------------------------------
	//  _____           _   _   
	//  |_   _|         (_) | |  
	//    | |    _ __    _  | |_ 
	//    | |   | '_ \  | | | __|
	//   _| |_  | | | | | | | |_ 
	//  |_____| |_| |_| |_|  \__|                         
	// init methods
	public void begin() { // Let the handler thread know we are ready to begin
		Util.resume(sync);
	}
	public void setQuestions(List<Category> c) { // Set the current round's questions
		cat = c;
	}
	
	//--------------------------------
	//   _    _   _     _   _ 
	//  | |  | | | |   (_) | |
	//  | |  | | | |_   _  | |
	//  | |  | | | __| | | | |
	//  | |__| | | |_  | | | |
	//   \____/   \__| |_| |_|
	// Utility methods
	public void setSyncObject(Object s) { // Set the object used to let the handler thread know stuff
		sync = s; 
	}
	public void fastGame() { // Mark all but one question done
		for(Category c : cat) {
			for(Question q : c.getQuestions()) {
				q.setIsUsed(true);
			}
		}
		cat.get(0).getQuestion(0).setIsUsed(false);
		gamePanel.drawMainPanel();
	}
	
	//--------------------------------
	//    ____                       
	//   / __ \                      
	//  | |  | |  _   _    ___   ___ 
	//  | |  | | | | | |  / _ \ / __|
	//  | |__| | | |_| | |  __/ \__ \
	//   \___\_\  \__,_|  \___| |___/                               
	// Question selection methods
	public void selectCategory(int num) { // Select which category is being used
		selCat = cat.get(num);
		gamePanel.selCat(num);
	}
	public void selectQuestion(int p) { // Select the question
		if(selCat != null) {
			Question q = selCat.getQuestion(p);
			if(q.isUsed() == false) {
			System.out.println("isDouble == " + q.isDouble());
			for(Team t : teams) {
				t.setGuessed(false);
			}
			gamePanel.displayText(q.getQuestion());
			q.setIsUsed(true);
			selQues = q;
			game.setMode(Mode.BUZZ);
			}
		}
	}
	public void cancelCategorySelection() {
		gamePanel.selCat(-1);
	};
	
	//--------------------------------
	//   _   _                     
	//  | \ | |                    
	//  |  \| |  _   _   _ __ ___  
	//  | . ` | | | | | | '_ ` _ \ 
	//  | |\  | | |_| | | | | | | |
	//  |_| \_|  \__,_| |_| |_| |_|
	// Number selection methods
	private void pickNumber(Runnable whenDone, Runnable whenCancel) {
		whenNumberSelectionDone = whenDone;
		whenNumberSelectionCancel = whenCancel;
		isNumberNegitive = false;
		System.out.println("Getting number");
		game.setMode(Mode.PICK_NUMBER);
		num = 0;
	}
	public void selectNumber(int d) {
		if(d == -1) {
			isNumberNegitive = !isNumberNegitive;
		} else {
			num = (num * 10) + d;
			System.out.println(num);
		}
		String n = "";
		if(isNumberNegitive) {
			n = "-";
		}
		System.out.println(n + num);
		gamePanel.displayText(teamMod.getName() + ": \n" + n + num, teamMod.getColor());
		if(game.getMode() == Mode.WAGER) {
			gamePanel.displayText(teamSel.getName() + " Team\n" + num);
		}
	}
	public void finializeNumberSelection() {
		if(isNumberNegitive) {
						num = num * -1;
					}
					whenNumberSelectionDone.run();
	}
	public void cancelNumberSelection() {
		whenNumberSelectionCancel.run();
	}
	
	//--------------------------------
	//  _______                        
	// |__   __|                       
	//    | |  ___    __ _   _ __ ___  
	//    | | / _ \  / _` | | '_ ` _ \ 
	//    | ||  __/ | (_| | | | | | | |
	//    |_| \___|  \__,_| |_| |_| |_|
	// Team selection methods
	public void selectModTeam(int n) {
		teamMod = teams.get(n);
		gamePanel.displayText(teamMod.getName() + ": \n", teamMod.getColor());
		pickNumber( new Runnable() { @Override public void run() { if(isModifyAdditive) { teamMod.setScore(teamMod.getScore() + num);} else {teamMod.setScore(num); } game.setMode(Mode.SELECT); gamePanel.drawMainPanel(); } },
				new Runnable() {@Override public void run() { game.setMode(Mode.SELECT); gamePanel.drawMainPanel();} });
	}
	public void cancelTeamSelection() {
		selQues.setIsUsed(false);
		gamePanel.drawMainPanel();
		game.setMode(Mode.SELECT);
	}
	// Score modifying methods
	public void setTeamScore() {
		isModifyAdditive = false;
		game.setMode(Mode.SELECT_MOD_TEAM);
	}
	public void addToTeamScore() {
		isModifyAdditive = true;
		game.setMode(Mode.SELECT_MOD_TEAM);
	}
	
	//--------------------------------
	//     /\                  
	//    /  \     _ __    ___ 
	//   / /\ \   | '_ \  / __|
	//  / ____ \  | | | | \__ \
	// /_/    \_\ |_| |_| |___/
    // Answering methods
	public void teamAnsworedCorrectly() {
		gamePanel.displayText(selQues.getAnswer()); // Show answer
		teamAns.addScore(selQues.getScore());
		game.setMode(Mode.SHOW_CORRECT);
	}
	public void teamAnsworedIncorrectly() {
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
	}
	public void doneLookingAtAnswer() {
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
			gamePanel.drawMainPanel();
			game.setMode(Mode.SELECT);
		}
	}
	
	//--------------------------------
	//   ____                      
	//  |  _ \                     
	//  | |_) |  _   _   ____  ____
	//  |  _ <  | | | | |_  / |_  /
	//  | |_) | | |_| |  / /   / / 
	//  |____/   \__,_| /___| /___|                            
	// Buzzing in methods
	public void buzz(int n) {
		Team t = teams.get(n);
		gamePanel.displayText(selQues.getQuestion(), t.getColor());
		teamAns = t;
		game.setMode(Mode.ANSWER);
	}
	
	//--------------------------------
	//                   _     _                   _          
	//    /\            | |   (_)                 | |         
	//   /  \      ___  | |_   _  __   __   __ _  | |_    ___ 
	//  / /\ \    / __| | __| | | \ \ / /  / _` | | __|  / _ \
	// / ____ \  | (__  | |_  | |  \ V /  | (_| | | |_  |  __/
	///_/    \_\  \___|  \__| |_|   \_/    \__,_|  \__|  \___|
	// Activate team buzzer methods
                                         
	public void activate(int n) {
		if(n >= 0 ) {
			teams.get(n).setGuessed(true);
			System.out.println("HasGuessed = true for " + n);
		}
		gamePanel.show4Parts(
				teams.get(0).getName() + "\n" + teams.get(0).hasGuessed(),
				teams.get(1).getName() + "\n" + teams.get(1).hasGuessed(),
				teams.get(2).getName() + "\n" + teams.get(2).hasGuessed(),
				teams.get(3).getName() + "\n" + teams.get(3).hasGuessed()
				);
	}
}
