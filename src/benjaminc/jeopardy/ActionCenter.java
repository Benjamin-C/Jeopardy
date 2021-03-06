package benjaminc.jeopardy;

import java.awt.Color;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;
import benjaminc.util.Util;

public class ActionCenter {

	// "Public" variables
	private GamePanel gamePanel;
	private List<Category> cat;
	private List<Team> teams;
	private Game game;
	
	// Activate variables
	private boolean beginWithoutAllActivations;
	
	// Utility variables
	private Object sync;
	
	// Question selection variables
	private Category selCat;
	private int selQuesNum;
	private Question selQues;
	
	// Team selection variables
	private Team teamMod;
	private Team teamAns;
	private Team teamLast;
	
	// Number Selection variables
	private PickNumberCallback pickNumberCallback;
	private boolean isNumberNegitive;
	public boolean isModifyAdditive;
	private int num;
	private String selNumLabel;
	private Color teamModColor;
	
	// Double Jeopardy variables
	private int wager;
	
	// Final Jeopardy variables
	private int finalLocation;
	
	public ActionCenter(GamePanel gp, Game g, List<Team> team) {	
		num = 0;
		teamAns = null;
		teamMod = null;
		gamePanel = gp;
		game = g;
		teams = team;
		isNumberNegitive = false;
		isModifyAdditive = false;

		beginWithoutAllActivations = false;
	}
	
	//--------------------------------
	//  _____           _   _   
	//  |_   _|         (_) | |  
	//    | |    _ __    _  | |_ 
	//    | |   | '_ \  | | | __|
	//   _| |_  | | | | | | | |_ 
	//  |_____| |_| |_| |_|  \__|                         
	// init methods
	
	/**
	 * Begin the game
	 */
	public void begin() {
		teamLast = teams.get(0);
		Util.resume(sync);
	}
	/**
	 * Sets the list of questions for the game
	 * 
	 * @param c			A List<Category> of the categories for the round
	 */
	public void setQuestions(List<Category> c) {
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
	
	/**
	 * Set the object used to let the handler thread know stuff
	 * 
	 * @param s	An Object for synchronization
	 */
	public void setSyncObject(Object s) {
		sync = s; 
	}
	/**
	 * Mark all but one question done to make the game faster
	 */
	public void fastGame() {
		System.out.println("Speeding things up a bit");
		boolean isOpen = false;
		for(Category c : cat) {
			for(Question q : c.getQuestions()) {
				if(!q.isUsed()) {
					if(isOpen) {
						q.setIsUsed(true);
					} else {
						isOpen = true;
					}
				}
			}
		}
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
	/**
	 * Select which category is being used
	 * 
	 * @param num	The int number of the current category
	 */
	public void selectCategory(int num) {
		if(num >= 0 && num < cat.size()) {
			selCat = cat.get(num);
			gamePanel.selCat(num);
		}
	}
	/**
	 * Select which question is being used
	 * 
	 * @param num	The int number of the current question
	 */
	public void selectPoints(int p) {
		selQuesNum = p;
		gamePanel.selQues(p);
	}
	/**
	 * Select which question is being used
	 * 
	 * @param p	The int number of the current question
	 */
	public void selectQuestion() {
		if(selCat != null && selQuesNum >= 0 && selQuesNum < selCat.size()) {
			selQues = selCat.getQuestion(selQuesNum);
			if(selQues.isUsed() == false) {
				if(selQues.isDouble()) {
					//gamePanel.displayText(teamLast.getName() + "", teamLast.getColor());
					System.out.println("Get double");
					getTeamWager(selQues.getScore() * 2, teamLast);
				} else {
					for(Team t : teams) {
						t.setGuessed(false);
					}
					gamePanel.displayText(selQues.getQuestion());
					selQues.setIsUsed(true);
					game.setMode(Mode.BUZZ);
				}
				
			}
		}
	}
	private void afterDouble() {
		gamePanel.displayText(selQues.getQuestion());
		selQues.setIsUsed(true);
		buzz(teamLast);
		game.setMode(Mode.ANSWER);
	}
	/**
	 * Cancel the category selection
	 */
	public void cancelCategorySelection() {
		gamePanel.selCat(-1);
	}
	/**
	 * Gets the amount for Double Jeopardy
	 */
	public void getTeamWager(int max, Team t) {
		PickNumberCallback pickNumberCallback = new PickNumberCallback() { 
			@Override public void whenDone(int n) {
				if(n <= max) {
					if(n >= 0) { wager = n; t.setWager(n); afterDouble(); } else {
						System.out.println("Need bigger than 0"); getTeamWager(max, t);
				} } else {
					System.out.println("Must be less than 2x question score"); getTeamWager(max, t);
				}	
				}
			@Override public void whenCanceled() { getTeamWager(max, t);} };
		System.out.println("IsDouble");
		t.getNumber(pickNumberCallback);
		if(t.getWager() <= max) {
			if(t.getWager() >= 0) { wager = t.getWager(); } else {
				System.out.println("Need bigger than 0"); getTeamWager(max, t);
		} } else {
			System.out.println("Must be less than team score"); getTeamWager(max, t);
		}
	}
	public void afterFinal(String ans) {
		selQues = new Question("", ans);
		game.setMode(Mode.FINAL_CORRECT);
		finalLocation = 0;
		teamAns = teams.get(finalLocation);
		gamePanel.displayText(selQues.getAnswer(), teamAns.getColor());
	}
	
	public void isFinalCorrect(boolean cor) {
		if(cor) {
			teamAns.addScore(teamAns.getWager());
		} else {
			teamAns.addScore(-1 * teamAns.getWager());
		}
		if(finalLocation < teams.size() - 1) {
			finalLocation++;
			teamAns = teams.get(finalLocation);
			gamePanel.displayText(selQues.getAnswer(), teamAns.getColor());
		} else {
			Util.resume(sync);
		}
	}
	//--------------------------------
	//   _   _                     
	//  | \ | |                    
	//  |  \| |  _   _   _ __ ___  
	//  | . ` | | | | | | '_ ` _ \ 
	//  | |\  | | |_| | | | | | | |
	//  |_| \_|  \__,_| |_| |_| |_|
	// Number selection methods
	/**
	 * Ask the commander to select a number
	 * 
	 * @param whenDone	The Runnable that is run when the number is selected
	 * @param whenCancel	The Runnable that is run when the selection is canceled
	 * @param label		What to show at the top of the screen
	 */
	public void pickNumber(PickNumberCallback pnc, String label, Color c) {
		pickNumberCallback = pnc;
		isNumberNegitive = false;
		System.out.println("Getting number");
		num = 0;
		selNumLabel = label;
		gamePanel.displayText(selNumLabel + "\n" + num, c);
		System.out.println("No block yet ...");
		game.setMode(Mode.PICK_NUMBER);
		teamModColor = c;
	}
	/**
	 * Select the next digit in the number selection
	 * 
	 * @param d	The int of the next digit
	 */
	public void selectNumber(int d) {
		if(d == -1) {
			isNumberNegitive = !isNumberNegitive;
		} else {
			num = (num * 10) + d;
		}
		String n = "";
		if(isNumberNegitive) {
			n = "-";
		}
		gamePanel.displayText(selNumLabel + "\n" + n + num, teamModColor);
		if(game.getMode() == Mode.WAGER) {
			gamePanel.displayText(selNumLabel + " \n" + num);
		}
	}
	/**
	 * Be done selecting a number, run the whenDone runnable
	 */
	public void finializeNumberSelection() {
		if(isNumberNegitive) {
			num = num * -1;
		}
		pickNumberCallback.whenDone(num);
	}
	/**
	 * Cancel the selection of the number, run the whenCancel runnable
	 */
	public void cancelNumberSelection() {
		pickNumberCallback.whenCanceled();
	}
	
	//--------------------------------
	//  _______                        
	// |__   __|                       
	//    | |  ___    __ _   _ __ ___  
	//    | | / _ \  / _` | | '_ ` _ \ 
	//    | ||  __/ | (_| | | | | | | |
	//    |_| \___|  \__,_| |_| |_| |_|
	// Team selection methods
	/**
	 * Select the team to be modified
	 * 
	 * @param n	The int of the temm number
	 */
	public void selectModTeam(int n) {
		teamMod = teams.get(n);
		gamePanel.displayText(teamMod.getName() + ": \n", teamMod.getColor());
		pickNumber( new PickNumberCallback() {
			@Override public void whenDone(int n) { if(isModifyAdditive) { teamMod.setScore(teamMod.getScore() + n);} else {teamMod.setScore(n); } game.setMode(Mode.SELECT); gamePanel.drawMainPanel(); }
			@Override public void whenCanceled() { game.setMode(Mode.SELECT); gamePanel.drawMainPanel();} },
			teamMod.getName(), teamMod.getColor()
		);
	}
	/**
	 * Cancel selecting the buzzing team selection and return to the selection panel
	 */
	public void cancelTeamSelection() {
		selQues.setIsUsed(false);
		gamePanel.drawMainPanel();
		game.setMode(Mode.SELECT);
	}
	/**
	 * Set the score of a team to a number specified by the Commander
	 */
	public void setTeamScore() {
		isModifyAdditive = false;
		game.setMode(Mode.SELECT_MOD_TEAM);
	}
	/**
	 * Directly set the score of a team
	 * 
	 * @param team	The int number of the team
	 * @param score 	The int new score
	 */
	public void setTeamScore(int team, int score) {
		teams.get(team).setScore(score);
		gamePanel.drawMainPanel();
	}
	/**
	 * Add a number to the score of a team specified by the commander
	 */
	public void addToTeamScore() {
		isModifyAdditive = true;
		game.setMode(Mode.SELECT_MOD_TEAM);
	}
	/**
	 * Directly set the score of a team
	 * 
	 * @param team		The int number of the team
	 * @param score		The amount to add to the team's score
	 */
	public void addToTeamScore(int team, int score) {
		teams.get(team).addScore(score);
		gamePanel.drawMainPanel();
	}
	
	//--------------------------------
	//     /\                  
	//    /  \     _ __    ___ 
	//   / /\ \   | '_ \  / __|
	//  / ____ \  | | | | \__ \
	// /_/    \_\ |_| |_| |___/
    // Answering methods
	/**
	 * Show the correct answer and give the points
	 */
	public void teamAnsworedCorrectly() {
		gamePanel.displayText(selQues.getAnswer()); // Show answer
		System.out.println(selQues.isDouble() + " " + teamLast.getWager());
		if(selQues.isDouble()) {
			teamLast.addScore(teamLast.getWager());
		} else {
			teamAns.addScore(selQues.getScore());
		}
		game.setMode(Mode.SHOW_CORRECT);
		teamLast = teamAns;
	}
	/**
	 * Get another team and remove the points
	 */
	public void teamAnsworedIncorrectly() {
		teamAns.setGuessed(true);
		if(selQues.isDouble()) {
			teamLast.addScore(wager * -1);
			gamePanel.displayText(selQues.getAnswer());
			game.setMode(Mode.SHOW_CORRECT);
		} else {
			teamAns.addScore(selQues.getScore() * -1);
		}
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
	/**
	 * Return the question selection screen after looking at the answer
	 */
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
	/**
	 * Buzz in a team
	 * 
	 * @param n		The int number of the team
	 */
	public void buzz(Team t) {
		if(!t.hasGuessed()) {
			teamAns = t;
			gamePanel.displayText(selQues.getQuestion(), t.getColor());
			game.setMode(Mode.ANSWER);
		}
	}
	
	//--------------------------------
	//                   _     _                   _          
	//    /\            | |   (_)                 | |         
	//   /  \      ___  | |_   _  __   __   __ _  | |_    ___ 
	//  / /\ \    / __| | __| | | \ \ / /  / _` | | __|  / _ \
	// / ____ \  | (__  | |_  | |  \ V /  | (_| | | |_  |  __/
	///_/    \_\  \___|  \__| |_|   \_/    \__,_|  \__|  \___|
	// Activate team buzzer methods
	/**
	 * Activate a team's buzzer
	 * 
	 * @param n		The int number of the team
	 */                        
	public void activate(int n) {
		if(n >= 0 ) {
			teams.get(n).setGuessed(true);
			System.out.println("HasGuessed = true for " + n);
		}
		String[] parts = {"", "", "", ""};
		for(int i = 0; i < parts.length; i++) {
			String t = "Ready";
			if(!teams.get(i).hasGuessed()) {
				try {
					String ad = Inet4Address.getLocalHost().toString();
					String str = ad.substring(ad.indexOf('/')+1);
					t = str + ":" + (500 + i);
				} catch (UnknownHostException e) {
					t = "err";
				}
			}
			t = teams.get(i).getName() + "\n" + t;
			parts[i] = t;
		}
		gamePanel.show4Parts(parts[0], parts[1], parts[2], parts[3]);
		Util.resume(sync);
	}
	public void activateAll() {
		beginWithoutAllActivations = true;
		Util.resume(sync);
	}
	/**
	 * Find out if all teams have activated
	 * @return boolean if all teams have activated
	 */
	public boolean hasEveryoneActivated() {
		if(beginWithoutAllActivations) {
			return true;
		} else {
			boolean isdone = true;
			for(Team t : teams) {
				System.out.println(t.getName() + " " + t.getServer());
				if(t.getServer() == null) {
					isdone = false;
				}
			}
			return isdone;
		}
	}
}
