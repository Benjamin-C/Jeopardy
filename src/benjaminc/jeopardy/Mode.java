package benjaminc.jeopardy;

public enum Mode {
	CONNECT, // wait for teams to connect
	INIT, // Get everything set up
	SELECT, // Select a question
	BUZZ, // Wait for temas to buzz in
	ANSWER, // Check for correct answer
	SHOW_CORRECT, // Show the correct answer
	SELECT_MOD_TEAM, // Select a team to modify
	SCORE_CHANGE, // Change a team's score
	WAGER,
	FINAL_JEOPARDY,
	FINAL_CORRECT,
	RESULTS,
	SCORE,
	PICK_NUMBER,
	DONE,
	CRASH;
}
