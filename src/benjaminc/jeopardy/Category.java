package benjaminc.jeopardy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Category {
	private String name;
	private List<Question> questions = new ArrayList<Question>();
	
	public Category(String n) {
		name = n;
	}
	
	public void add(Question q) {
		questions.add(q);
	}
	
	public Question getQuestion(int i) {
		return questions.get(i);
	}
	
	public List<Question> getQuestions() {
		return questions;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public boolean isDone() {
		boolean isDone = true;
		for(int i = 0; i < questions.size(); i++) {
			if(questions.get(i).isUsed() == false) {
				isDone = false;
				break;
			}
		}
		return isDone;
	}
	
	@Override
	public String toString() {
		String out = name + "[\"";
		boolean f = false;
		for(int i = 0; i < questions.size(); i++) {
			if(f) {
				out = out + "> ";
			} else {
				f = true;
			}
			out = out + questions.get(i).toString();
		}
		out = out + "]";
		return out;
	}
	
	public int size() {
		return questions.size();
	}
	
	@SuppressWarnings("resource")
	public void parse(File f, int pp) {
		try {
			String line = "";
			int l = 0;
			String q = "";
			String a = "";
			
			Scanner s = new Scanner(f);
			name = s.nextLine();
			int pts;
			while(s.hasNextLine()) {
				line = s.nextLine();
				l = 1;
				q = "";
				a = "";
				while(line.charAt(l) != '"') {
					q = q + line.charAt(l);
					l++;
				}
				l++;
				while(line.charAt(l) != '"') {
					l++;
				}
				l++;
				while(line.charAt(l) != '"') {
					a = a + line.charAt(l);
					l++;
				}
				pts = (questions.size() + 1) * pp;
				questions.add(new Question(q, a, pts, false));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
