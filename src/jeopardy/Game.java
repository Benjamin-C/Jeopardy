package jeopardy;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Game {
	private GamePanel gamePanel;
	private List<Team> teams;
	private JFrame jf;
	private KeyListen keyListen;
	private Mode mode;
	private int numDoubles = 2;
	private ActionCenter actionCenter;
	
	public Game(String inputMode) {
		// Init vars
		teams = new ArrayList<Team>();
		teams.add(new Team(Color.RED, "Red"));
		teams.add(new Team(Color.YELLOW, "Yellow"));
		teams.add(new Team(Color.GREEN, "Green"));
		teams.add(new Team(Color.CYAN, "Blue"));
		jf = new JFrame();
		mode = Mode.INIT;
		
		gamePanel = new GamePanel(jf, teams);
		actionCenter = new ActionCenter(gamePanel, this, teams);
		keyListen = new KeyListen(this, actionCenter);
		
		
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
		actionCenter.setQuestions(nullCatList);
				
		// Setup input modes
		switch(inputMode) {
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
				setMode(Mode.INIT);
				actionCenter.setSyncObject(sync);
				Util.pause(sync); // Wait for boss to be ready
				beginNormalRound(new String[] {"cooking", "doit", "flag", "hiking", "lashings", "lifeordeath"});
				Util.pause(sync); // Wait for round one to be done
				beginNormalRound(new String[] {"fire", "firstaid", "knives", "knots", "scoutstuff", "water"});
				Util.pause(sync); // wait for round two to be done
				System.out.println("Round 2 done");
				System.exit(0);
			}
		};
		t.start();
	}
	
	public void beginNormalRound(String[] names, int scoreMult) {
		List<Category> cat = new ArrayList<Category>();
		for(int i = 0; i < names.length; i++) {
			Category temp = new Category("");
			temp.parse(new File("catagories/" + names[i] + ".jep"), 200);
			cat.add(temp);
		}
		Random r = new Random();
		int doubles = 0;
		while(doubles < numDoubles) {
			Category c = cat.get(r.nextInt(cat.size()));
			Question q = c.getQuestion(r.nextInt(c.size()));
			if(!q.isDouble()) {
				q.setDouble(true);
				doubles++;
				System.out.println("Added double");
			}
		}
		gamePanel.drawMainPanel(cat);
		actionCenter.setQuestions(cat);
		setMode(Mode.SELECT);
	}
	public void beginFinalRound() {
		gamePanel.displayText("Final Jeopardy");
		List<Category> roundTwo = new ArrayList<Category>();
		String namesTwo[] = {"final"};
		for(int i = 0; i < namesTwo.length; i++) {
			Category temp = new Category("");
			temp.parse(new File("catagories/" + namesTwo[i] + ".jep"), 400);
			roundTwo.add(temp);
		}
		actionCenter.setQuestions(roundTwo);
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode m) {
		mode = m;
	}
}
