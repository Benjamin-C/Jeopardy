package jeopardy;

public enum Mode {
	INIT, // Get everything set up
	SELECT, // Select a question
	BUZZ, // Wait for temas to buzz in
	ANSWER, // Check for correct answer
	SHOW_CORRECT, // Show the correct answer
	SCORE_CHANGE,
	WAGER,
	FINAL_JEOPARDY,
	FINAL_CORRECT,
	RESULTS,
	SCORE;
}
