package jeopardy;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class Game {
	private GamePanel gamePanel;
	private Team teamRed;
	private Team teamYellow;
	private Team teamGreen;
	private Team teamBlue;
	private JFrame jf;
	private KeyListen keyListen;
	
	public Game(String mode) {
		// Init vars
		teamRed = new Team(Color.RED, "Red");
		teamYellow = new Team(Color.YELLOW, "Yellow");
		teamGreen = new Team(Color.GREEN, "Green");
		teamBlue = new Team(Color.CYAN, "Blue");
		jf = new JFrame();
		
		gamePanel = new GamePanel(jf, teamRed, teamYellow, teamGreen, teamBlue);
		keyListen = new KeyListen(gamePanel, this);
		
		jf.addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent e) {keyListen.keyTyped(e);}
			@Override public void keyReleased(KeyEvent e) {keyListen.keyReleased(e);}
			@Override public void keyPressed(KeyEvent e) {keyListen.keyPressed(e);}
		});
		
		// Null Category
		List<Category> nullCatList = new ArrayList<Category>();
		Category nullCat = new Category("null");
		nullCat.add(new Question("null", "null"));
		nullCatList.add(nullCat);
		keyListen.setQuestions(nullCatList);
				
		// Setup input modes
		switch(mode) {
		case "KEYBOARD": { } break;
		case "EXTERNAL": {try { Serial.begin(); } catch (Exception e1) { e1.printStackTrace(); } } break;
		case "APP": { System.out.println("This mode is currently not supported. Please select another mode then try again"); System.exit(1);} break;
		}
		
		
	}
	
	public void begin() {
		gamePanel.displayText("Jeopardy");
		keyListen.enable();
		Thread t = new Thread() {
			Object sync = new Object();
			public void run() {
				keyListen.setMode(Mode.INIT);
				keyListen.setSyncObject(sync);
				Util.pause(sync);
				System.out.println("I think now is a good time to ...");
				beginRoundOne();
				Util.pause(sync);
			}
		};
		t.start();
	}
	
	public void beginRoundOne() {
		List<Category> roundOne = new ArrayList<Category>();
		String names[] = {"cooking", "doit", "flag", "hiking", "lashings", "lifeordeath"};
		for(int i = 0; i < names.length; i++) {
			Category temp = new Category("");
			temp.parse(new File("catagories/" + names[i] + ".jep"), 200);
			roundOne.add(temp);
		}
		keyListen.setQuestions(roundOne);
		gamePanel.drawMainPanel(roundOne);
		keyListen.setMode(Mode.RUN);
	}
	public void beginRoundTwo() {
		List<Category> roundTwo = new ArrayList<Category>();
		String namesTwo[] = {"fire", "firstaid", "knives", "knots", "scoutstuff", "water"};
		for(int i = 0; i < namesTwo.length; i++) {
			Category temp = new Category("");
			temp.parse(new File("catagories/" + namesTwo[i] + ".jep"), 400);
			roundTwo.add(temp);
		}
		// System.out.println(roundTwo.toString());
		gamePanel.drawMainPanel(roundTwo);
		keyListen.setQuestions(roundTwo);
	}
	
	public void beginRoundThree() {
		gamePanel.displayText("Final Jeopardy");
		List<Category> roundTwo = new ArrayList<Category>();
		String namesTwo[] = {"final"};
		for(int i = 0; i < namesTwo.length; i++) {
			Category temp = new Category("");
			temp.parse(new File("catagories/" + namesTwo[i] + ".jep"), 400);
			roundTwo.add(temp);
		}
		keyListen.setQuestions(roundTwo);
	}
}
