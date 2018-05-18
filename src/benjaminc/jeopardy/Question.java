package benjaminc.jeopardy;

public class Question {

	private String question;
	private String answer;
	private int points;
	private boolean isUsed;
	private boolean isDouble;
	
	public Question(String q, String a, int p, boolean d) {
		question = q;
		answer = a;
		points = p;
		isDouble = d;
	}
	
	public Question(String q, String a) {
		question = q;
		answer = a;
		points = 0;
		isDouble = false;
	}
	
	@Override
	public String toString() {
		return question + ", " + answer;
	}
	
	public boolean isDouble() {
		return isDouble;
	}

	public void setDouble(boolean isDouble) {
		this.isDouble = isDouble;
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
