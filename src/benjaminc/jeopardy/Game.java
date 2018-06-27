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
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import benjamin.BenTCP.*;
import benjaminc.util.TracedPrintStream;
import benjaminc.util.Util;

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

	private final int PLAYER_COUNT = 4;
	
	public OutputStream serverHostOutputStream;
	
	Scanner scan = new Scanner(System.in);
	
	private PrintStream outputPrintStream;
	private PrintStream logPrintStream;
	private PrintStream tracedPrintStream;
	
	private String[] roundOneCategories;
	private String[] roundTwoCategories;
	private String[] roundFinalQuestions;
	
	private final Object sync;
	
	private Serial serialInput;
	
	public Game(InputMode inMode, String[] roundOneCategories, String[] roundTwoCategories, String[] roundFinalQuestions) {
		// Setup logger
		DateFormat logPath = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat logName = new SimpleDateFormat("yyyyMMddHHmmss");
		Date cal = new Date();
		String logPathStr = "logs/" + logPath.format(cal);
		String logNameStr = logName.format(cal) + ".txt";
		System.out.println("logname =" + logPathStr  + "/" + logNameStr);
		File f = new File(logPathStr);
		f.mkdirs();
		try { logPrintStream = new PrintStream(new PrintStream(logPathStr + "/" + logNameStr), true); } catch (FileNotFoundException e1) { e1.printStackTrace(); }
		outputPrintStream = new PrintStreamDuplicator(logPrintStream, System.out);
		tracedPrintStream = new TracedPrintStream(outputPrintStream, true);
		System.setOut(tracedPrintStream);
		System.setErr(outputPrintStream);
		System.out.println("Jeopardy");
		logPrintStream.flush();
		
		// Init vars
		inputMode = inMode;
		sync = new Object();
		
		// Set up teams
		teams = new ArrayList<Team>();
		
		// Make the JFrame to see things
		jf = new JFrame();
		
		// Setup mode to initilization
		mode = Mode.INIT;
		
		// Create the main parts of the game
		gamePanel = new GamePanel(this, jf, teams);
		actionCenter = new ActionCenter(gamePanel, this, teams);
		keyListen = new KeyListen(this, actionCenter);
		
		// Set up teams
		teams.add(new Team(actionCenter, Color.RED, "Red", null));
		teams.add(new Team(actionCenter, Color.YELLOW, "Yellow", null));
		teams.add(new Team(actionCenter, Color.GREEN, "Green", null));
		teams.add(new Team(actionCenter, Color.CYAN, "Blue", null));
		
		// Set up category lists
		this.roundOneCategories = roundOneCategories;
		this.roundTwoCategories = roundTwoCategories;
		this.roundFinalQuestions = roundFinalQuestions;
		
		// Activate key listeners for commander
		jf.addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent e) {keyListen.keyTyped(e);}
			@Override public void keyReleased(KeyEvent e) {keyListen.keyReleased(e);}
			@Override public void keyPressed(KeyEvent e) {keyListen.keyPressed(e);}
		});
				
		// Setup input modes
		switch(inputMode) {
		case APP:  {
			setMode(Mode.CONNECT);
			for(Team t : teams) {
				t.setInputMode(InputMode.APP);
			}
			System.out.println("[Warn] The mode APP is currently not supported. Preformance may be unreliable");
			TCPOnDataArrival odr = new CommanderOnDataArrival(actionCenter, this);
			gamePanel.displayText("Waiting for host to connect\nPlease do so quickly so I can take a nap");
			serverHost = new TCPServer(499, odr, new DefaultTCPSetupStream(scan, "Host"), 1);
			serverHostOutputStream = serverHost.getOutputStream();
			Object starterSync = new Object();
			actionCenter.setSyncObject(starterSync);
			actionCenter.activate(-1);
			for(int i = 0; i < PLAYER_COUNT; i++) {
				System.out.println("Team " + i + " Server starting");
				ServerStarter st = new ServerStarter(i, actionCenter, teams.get(i));
				new Thread(st, "connectorThread" + i).start();
			}
			boolean done = false;
			do {
				Util.pause(starterSync);
				System.out.println("Has everyone activated yet? " + actionCenter.hasEveryoneActivated());
				done = actionCenter.hasEveryoneActivated();
			} while(!done);
			setMode(Mode.INIT);
		}
		//break;
		//case ARDUINO: try { Serial.begin(actionCenter); } catch (Exception e1) { System.out.println("Something went wrong initializing the arduino!"); /*System.exit(1);*/ }; break;
		case KEYBOARD: System.out.println("allowing keyboard buzzing");keyListen.mayTeamsBuzzByKeyboard(true); break;
		case ARDUINO: {
			keyListen.mayTeamsBuzzByKeyboard(true);
			SerialEvent se = new SerialEvent() {
				
				@Override
				public void onDataAvaliable(byte data) {
					if(data != 13 && data != 10) {
						System.out.println("Serial:" + (data & 0xFF) + " " + (char) data);
					} else {
						System.out.println("Serial:" + (data & 0xFF) + " \\n");
					}
					if(getMode() == Mode.BUZZ) {
						switch((char) data) {
						case '9': { actionCenter.buzz(teams.get(0)); } break;
						case 'a': { actionCenter.buzz(teams.get(1)); } break;
						case 'b': { actionCenter.buzz(teams.get(2)); } break;
						case 'c': { actionCenter.buzz(teams.get(3)); } break;
						case '♪': { System.out.println("Line Break"); } break;
					}
				} }
				
				@Override
				public void IOException(IOException e) { System.out.println(e); gamePanel.displayText(e.getMessage()); }
			};
			System.out.println(Serial.getPorts());
			try {
				serialInput = new Serial(Serial.getPorts().get(0), 250000, se);
				serialInput.startListening();
			} catch(NullPointerException e) {
				setMode(Mode.CRASH);
				gamePanel.displayText(e.getMessage());
				Util.pause(new Object());
			}
		} break;
		}
		
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
		Thread t = new Thread("RoundSelector") {
			public void run() {
				setMode(Mode.INIT);
				actionCenter.setSyncObject(sync);
				Util.pause(sync); // Wait for commander to be ready
				beginNormalRound(roundOneCategories, 1);
				Util.pause(sync); // Wait for round one to be done
				beginNormalRound(roundTwoCategories, 2);
				Util.pause(sync); // wait for round two to be done
				System.out.println("Round 2 done");
				doFinalRound();
				gamePanel.showScores();
				setMode(Mode.DONE);
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
//		Category c = cat.get(0);
//		Question q = c.getQuestion(0);
		while(doubles < numDoubles) {
			Category c = cat.get(r.nextInt(cat.size()));
			Question q = c.getQuestion(r.nextInt(c.size()));
			
			if(!q.isDouble()) {
				q.setDouble(true);
				doubles++;
				System.out.println("Added double>" + c.getName() + ":" + q.getScore());
			}
			c = cat.get(r.nextInt(cat.size()));
			q = c.getQuestion(r.nextInt(c.size()));
		}
		gamePanel.drawMainPanel(cat);
		actionCenter.setQuestions(cat);
		setMode(Mode.SELECT);
	}
	public void doFinalRound() {
		for(int i = 0; i < teams.size(); i++) {
			Team t = teams.get(i);
			getTeamWager(t.getScore(), t);
		}
		gamePanel.displayText(roundFinalQuestions[0]);
		setMode(Mode.FINAL_JEOPARDY);
		Util.pause(sync);
		actionCenter.afterFinal(roundFinalQuestions[1]);
		Util.pause(sync);
		gamePanel.showScores();
	}
	
	public void getTeamWager(final int max, final Team t) {
		Object sncObj = new Object();
		Thread th = new Thread("Pick-Num") {
			private PickNumberCallback pickNumberCallback;
			@Override
			public void run() {
				pickNumberCallback = new PickNumberCallback() { 
					@Override public void whenDone(int n) {
						if(n <= max) {
							if(n >= 0) { t.setWager(n); Util.resume(sncObj); } else {
								System.out.println("Need bigger than 0"); getNum();
							} } else {
								System.out.println("Must be less than 2x question score"); getNum();
							}	
					}
					@Override public void whenCanceled() { getTeamWager(max, t);
					}
				};
				System.out.println("IsDouble");
				getNum();
			}
			
			private void getNum() {
				t.getNumber(pickNumberCallback);
			}
		};
		th.start();
		Util.pause(sncObj);
	}

	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode m) {
		mode = m;
		System.out.println("Setting mode to " + m);
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public void exit() {
		System.out.println("Goodby");
		logPrintStream.close();
		scan.close();
		System.exit(0);
	}
}
