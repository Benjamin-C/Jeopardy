package benjaminc.jeopardy;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import benjamin.BenTCP.*;

public class Game {
	private GamePanel gamePanel;
	private List<Team> teams;
	private JFrame jf;
	private KeyListen keyListen;
	private Mode mode;
	private int numDoubles = 2;
	private volatile ActionCenter actionCenter;
	private InputMode inputMode;
	Serial serial;
	
	public TCPServer serverHost;
	public List<TCPServer> serverPlayers;

	private final int PLAYER_COUNT = 4;
	
	public OutputStream serverHostOutputStream;
	
	Scanner scan = new Scanner(System.in);
	
	private File log;
	private PrintStream outputPrintStream;
	private PrintStream logPrintStream;
	private PrintStream tracedPrintStream;
	
	public Game(InputMode inMode) {
		// Setup logger
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date cal = new Date();
		System.out.println("logname = " + dateFormat.format(cal) + ".txt"); //2016/11/16 12:08:43
		try { logPrintStream = new PrintStream("logs/" + dateFormat.format(cal) + ".txt"); } catch (FileNotFoundException e1) { e1.printStackTrace(); }
		outputPrintStream = new PrintStreamDuplicator(logPrintStream, System.out);
		tracedPrintStream = new TracedPrintStream(outputPrintStream);
		System.setOut(tracedPrintStream);
		System.setErr(outputPrintStream);
		
		// Init vars
		inputMode = inMode;
				
		// Set up teams
		teams = new ArrayList<Team>();
		teams.add(new Team(Color.RED, "Red"));
		teams.add(new Team(Color.YELLOW, "Yellow"));
		teams.add(new Team(Color.GREEN, "Green"));
		teams.add(new Team(Color.CYAN, "Blue"));
		
		// Make the JFrame to see things
		jf = new JFrame();
		
		// Setup mode to initilization
		mode = Mode.INIT;
		
		// Create the main parts of the game
		gamePanel = new GamePanel(jf, teams);
		actionCenter = new ActionCenter(gamePanel, this, teams);
		keyListen = new KeyListen(this, actionCenter);
		
		// Setup input modes
		switch(inputMode) {
		case APP:  {
			setMode(Mode.CONNECT);
			System.out.println("[Warn] The mode APP is currently not supported. Preformance may be unreliable");
			TCPOnDataArrival odr = new CommanderOnDataArrival(actionCenter, this);
			gamePanel.displayText("Waiting for host to connect\nPlease do so quickly so I can take a nap");
			serverHost = new TCPServer(499, odr, new DefaultTCPSetupStream(scan, "Host"), 1);
			serverHostOutputStream = serverHost.getOutputStream();
			serverPlayers = new ArrayList<TCPServer>();
			Object starterSync = new Object();
			actionCenter.setSyncObject(starterSync);
			actionCenter.activate(-1);
			for(int i = 0; i < PLAYER_COUNT; i++) {
				System.out.println("Team " + i + " Server starting");
				serverPlayers.add(i, null);
				ServerStarter st = new ServerStarter(i, actionCenter, serverPlayers);
				new Thread(st, "connectorThread" + i).start();
			}
			boolean done = false;
			do {
				Util.pause(starterSync);
				System.out.println(actionCenter.hasEveryoneActivated());
				done = actionCenter.hasEveryoneActivated();
			} while(!done);
			setMode(Mode.INIT);
		}
		//break;
		//case ARDUINO: try { Serial.begin(actionCenter); } catch (Exception e1) { System.out.println("Something went wrong initializing the arduino!"); /*System.exit(1);*/ }; break;
		case KEYBOARD: System.out.println("allowing kb buzzing");keyListen.mayTeamsBuzzByKeyboard(true); break;
		}
		
		// Activate key listeners for commander
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
	}
	
	public void begin() {
		gamePanel.displayText("Jeopardy");
		keyListen.enable();
		Thread t = new Thread() {
			Object sync = new Object();
			public void run() {
				setMode(Mode.INIT);
				actionCenter.setSyncObject(sync);
				Util.pause(sync); // Wait for commander to be ready
				beginNormalRound(new String[] {"cooking", "doit", "flag", "hiking", "lashings", "lifeordeath"}, 1);
				Util.pause(sync); // Wait for round one to be done
				beginNormalRound(new String[] {"fire", "firstaid", "knives", "knots", "scoutstuff", "water"}, 2);
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
