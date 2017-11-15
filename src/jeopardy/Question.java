package jeopardy;

public class Question {

	private String question;
	private String answer;
	private int points;
	private boolean isUsed;
	
	public Question(String q, String a, int p) {
		question = q;
		answer = a;
		points = p;
	}
	
	public Question(String q, String a) {
		question = q;
		answer = a;
		points = 0;
	}
	
	@Override
	public String toString() {
		return question + ", " + answer;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public int getScore() {
		return points;
	}
	
	public boolean isUsed() {
		return isUsed;
	}
	
	public void setIsUsed(boolean u) {
		isUsed = u;
	}
}
