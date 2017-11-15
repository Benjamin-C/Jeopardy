package jeopardy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main{
	
	public static void main(String args[]) {
		try {
			Serial.begin();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		KeyListen.init();
		
		// Null Category
		List<Category> nullCatList = new ArrayList<Category>();
		Category nullCat = new Category("null");
		nullCat.add(new Question("null", "null"));
		nullCatList.add(nullCat);
		
		// System.out.println(roundOne.toString());
		JFrame jf = new JFrame();
		GamePanel.setGamePanel(jf);
		// TODO Make full screen
		jf.setTitle("Jeopardy");
		jf.setSize(1280, 720);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon("icon.png");
		jf.setIconImage(img.getImage());
		jf.addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent e) {KeyListen.keyTyped(e);}
			@Override public void keyReleased(KeyEvent e) {KeyListen.keyReleased(e);}
			@Override public void keyPressed(KeyEvent e) {KeyListen.keyPressed(e);}
		});
		KeyListen.setQuestions(nullCatList);
		GamePanel.displayText("Jeopardy");
	}
	
	public static void beginRoundOne() {
		List<Category> roundOne = new ArrayList<Category>();
		String names[] = {"cooking", "fire", "firstaid", "knives", "scoutstuff", "water"};
		for(int i = 0; i < names.length; i++) {
			Category temp = new Category("");
			temp.parse(new File("catagories/" + names[i] + ".jep"), 200);
			roundOne.add(temp);
		}
		KeyListen.setQuestions(roundOne);
		GamePanel.drawMainPanel(roundOne);
		KeyListen.setMode(Mode.RUN);
	}
	public static void beginRoundTwo() {
		List<Category> roundTwo = new ArrayList<Category>();
		String namesTwo[] = {"fire", "water", "knives", "cooking", "firstaid", "scoutstuff"};
		for(int i = 0; i < namesTwo.length; i++) {
			Category temp = new Category("");
			temp.parse(new File("catagories/" + namesTwo[i] + ".jep"), 400);
			roundTwo.add(temp);
		}
		// System.out.println(roundTwo.toString());
		GamePanel.drawMainPanel(roundTwo);
		KeyListen.setQuestions(roundTwo);
	}
	
	public static void beginRoundThree() {
		GamePanel.displayText("Final Jeopardy");
		List<Category> roundTwo = new ArrayList<Category>();
		String namesTwo[] = {"final"};
		for(int i = 0; i < namesTwo.length; i++) {
			Category temp = new Category("");
			temp.parse(new File("catagories/" + namesTwo[i] + ".jep"), 400);
			roundTwo.add(temp);
		}
		KeyListen.setQuestions(roundTwo);
	}
}
