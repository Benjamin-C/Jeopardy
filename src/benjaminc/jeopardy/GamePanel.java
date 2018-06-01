//TODO figure out why window resizing can cause drawing issues

package benjaminc.jeopardy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel{

	private JFrame jf;
	private JPanel panel;
	
	private List<Team> teams;
	
	private int selCat;
	private int selQues;
	
	private List<Category> categories;
	
	private Color textColor;
	
	public GamePanel(JFrame j, List<Team> teams) {
		jf = j;
		this.teams = teams;
		jf.setTitle("Jeopardy");
		jf.setSize(1280, 720);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon("icon.png");
		jf.setIconImage(img.getImage());
		selCat = -1;
		selQues = -1;
		categories = null;
		textColor = Color.WHITE;
	}

	public void selCat(int n) {
		selCat = n;
		drawMainPanel();
	}
	public void selQues(int n) {
		selQues = n;
		drawMainPanel();
	}
	
	public void setCategory(List<Category> cat) {
		categories = cat;
	}
	
	public boolean drawMainPanel() {
		return drawMainPanel(categories);
	}
	
	@SuppressWarnings("serial")
	public boolean drawMainPanel(List<Category> cat) {
		categories = cat;
		panel = new JPanel() {
			@Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0,  0,  getWidth(), getHeight());
                g.setColor(Color.BLUE);
                String text = "";
                int sw;
                int sh = g.getFontMetrics().getHeight();
                int x;
                int y;
                int bw = getWidth() / 7;
                int bh = getHeight() / 6;
                g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 20));
                for(int i = 0; i < cat.size(); i++) {
                	if(!cat.get(i).isDone()) {
                		if(i == selCat) {
                			g.setColor(new Color(255, 128, 0)); // Set light red if category is selected
                			textColor = Color.BLACK;
                		} else {
                			g.setColor(Color.RED);
                			textColor = Color.WHITE;
                		}
	                	text = cat.get(i).getName();
	                	g.fillRect((getWidth() / 7) * i, 0, (getWidth() / 7) - 10, (getHeight() / 6) - 10);
	                	sw = g.getFontMetrics().stringWidth(text);
	            		sh = g.getFontMetrics().getHeight();
	            		x = (bw * i) + ((bw - sw) / 2);
	            		y = ((bh - sh) / 2);
	            		g.setColor(textColor);
	            		g.drawString(text, x, y);
	                	for(int j = 0; j < cat.get(i).size(); j++) {
	                		if(!cat.get(i).getQuestion(j).isUsed()) {
	                			g.setColor(Color.BLUE);
                				textColor = Color.WHITE;
                				if(i == selCat) {
	                				g.setColor(new Color(64, 128, 255));
	                				textColor = Color.BLACK;
	                			}
                				if (j == selQues) {
	                				g.setColor(new Color(64, 128, 255));
	                				textColor = Color.BLACK;
	                			}
                				if (i == selCat && j == selQues){
	                				g.setColor(new Color(255, 128, 0));
	                				textColor = Color.BLACK;
	                			}
                				g.fillRect((getWidth() / 7) * i, (getHeight() / 6) * (j + 1), (getWidth() / 7) - 10, (getHeight() / 6) - 10);
		                		text = cat.get(i).getQuestion(j).getScore() + "";
		                		sw = g.getFontMetrics().stringWidth(text);
		                		x = (bw * i) + ((bw - sw) / 2);
		                		y = (bh * (j + 1)) + ((bh - sh) / 2);
		                		g.setColor(textColor);
		                		g.drawString(text, x, y);
	                		}
	                	}
                	}
                }
                for(int i = 0; i < teams.size(); i++) {
                	drawScore(g, teams.get(i), this, cat.size(), i);
                }
            }
        };
        jf.add(panel);
        jf.validate();
        return true;
	}
	
	private void drawScore(Graphics g, Team t, JPanel p, int w, int j) {
		int bw = p.getWidth() / 7;
        int bh = p.getHeight() / 6;
        String text = t.getName();
        int sw = g.getFontMetrics().stringWidth(text);
        int sh = g.getFontMetrics().getHeight();
        int x = (bw * w) + ((bw - sw) / 2);
        int y = (bh * (j)) + ((bh - sh) / 2);
        g.setColor(t.getColor());
		g.drawString(text, x, y);
		y = y + sh;
		text = ((Integer) t.getScore()).toString();
        sw = g.getFontMetrics().stringWidth(text);
        x = (bw * w) + ((bw - sw) / 2);
        g.drawString(text,  x,  y);
	}
	
	public boolean displayText(String text) {
		return displayText(text, Color.BLUE);
	}
	
	@SuppressWarnings("serial")
	public boolean displayText(String text, Color c) {
		if(text.equals(null)) {
			return false;
		}
		panel = new JPanel() {
        	@Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0,  0,  getWidth(), getHeight());
                g.setColor(c);
                g.fillRect(10,  10, getWidth() - 20, getHeight() - 20);
                g.setColor(Color.BLACK);
                g.fillRect(20,  20, getWidth() - 40, getHeight() - 40);
                g.setColor(Color.BLUE);
                g.fillRect(25,  25, getWidth() - 50, getHeight() - 50);
                g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 60));
                int sw = g.getFontMetrics().stringWidth(text);
                int sh;
                int x;
                int y;
                String textA = "";
                String textB = "";
                boolean twoLine = false;
                int loc = 0;
                if(text.contains("\n")) {
                	loc = text.indexOf("\n");
                	textA = text.substring(0,  loc++);
                	textB = text.substring(loc, text.length());
                	twoLine = true;
                } else if(text.contains("\\n")) {
                	loc = text.indexOf("\\n");
                	textA = text.substring(0,  loc++);
                	textB = text.substring(++loc, text.length());
                	twoLine = true;
                } else if(sw > (double) getWidth() * (9d/10d)) {
                	loc = text.length() / 2;
                	while(text.charAt(loc) != ' ') {
                		loc++;
                	}
                	textA = text.substring(0, loc++);
                	textB = text.substring(loc, text.length());
                	twoLine = true;
                }
                if(twoLine) {
                	sh = g.getFontMetrics().getHeight();
                	
                	sw = g.getFontMetrics().stringWidth(textA);
            		x = ((getWidth() - sw) / 2);
            		y = ((getHeight() / 2) - (sh / 2));
            		g.setColor(Color.WHITE);
            		g.drawString(textA, x, y);
            		
            		sw = g.getFontMetrics().stringWidth(textB);
            		x = ((getWidth() - sw) / 2);
            		y = ((getHeight() / 2) + (sh / 2));
            		g.setColor(Color.WHITE);
            		g.drawString(textB, x, y);
                } else {
	                sh = g.getFontMetrics().getHeight();
	        		x = ((getWidth() - sw) / 2);
	        		y = ((getHeight() - sh) / 2);
	        		g.setColor(Color.WHITE);
	        		g.drawString(text, x, y);
                }
        	}
		};
		jf.add(panel);
        jf.validate();
        return true;
	}
	
	@SuppressWarnings("serial")
	public boolean showScores() {
		panel = new JPanel() {
        	@Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
				int x = jf.getWidth() / 3;
				int y = jf.getHeight() / 3;
				g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 40));
		        g.setColor(Color.BLACK);
		        g.fillRect(0,  0,  getWidth(), getHeight());
				displayScores(teams.get(0), x, y, g);
				displayScores(teams.get(1), x * 2, y, g);
				displayScores(teams.get(2), x, y * 2, g);
				displayScores(teams.get(3), x * 2, y * 2, g);
        	}
		};
		jf.add(panel);
        jf.validate();
		return true;
	}
	
	private void displayScores(Team team, int cx, int ch, Graphics g) {
        int sw;
        int sh = g.getFontMetrics().getHeight();
        int x;
        int y;
        sw = g.getFontMetrics().stringWidth(team.getName());
        x = cx - (sw / 2);
		y = ch - (sh / 2);
		g.setColor(team.getColor());
		g.drawString(team.getName(), x, y);
		sw = g.getFontMetrics().stringWidth(Integer.toString(team.getScore()));
		x = cx - (sw / 2);
		y = ch + (sh / 2);
		g.drawString(Integer.toString(team.getScore()), x, y);
	}
	
	@SuppressWarnings("serial")
	public boolean show4Parts(String tl, String tr, String bl, String br) {
		panel = new JPanel() {
        	@Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
				int x = jf.getWidth() / 3;
				int y = jf.getHeight() / 3;
				g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 40));
		        g.setColor(Color.BLACK);
		        g.fillRect(0,  0,  getWidth(), getHeight());
		        double mw = getWidth() / 2 * 0.9d;
		        displayCornerText(tl, Color.RED, x, y, mw, g);
		        displayCornerText(tr, Color.YELLOW, x * 2, y, mw, g);
		        displayCornerText(bl, Color.GREEN, x, y * 2, mw, g);
		        displayCornerText(br, Color.BLUE, x * 2, y * 2, mw, g);
        	}
		};
		jf.add(panel);
        jf.validate();
		return true;
	}
	
	private void displayCornerText(String text, Color col, int cx, int ch, double maxWidth, Graphics g) {
        int sw = g.getFontMetrics().stringWidth(text);
        int sh = g.getFontMetrics().getHeight();
        int x;
        int y;
        
        double width = maxWidth;
        		
        String textA = "";
        String textB = "";
        boolean twoLine = false;
        int loc = 0;
        if(text.contains("\n")) {
        	loc = text.indexOf("\n");
        	textA = text.substring(0,  loc++);
        	textB = text.substring(loc, text.length());
        	twoLine = true;
        } else if(text.contains("\\n")) {
        	loc = text.indexOf("\\n");
        	textA = text.substring(0,  loc++);
        	textB = text.substring(++loc, text.length());
        	twoLine = true;
        } else if(sw > width * (9d/10d)) {
        	loc = text.length() / 2;
        	try {
	        	while(text.charAt(loc) != ' ') {
	        		loc++;
	        	}
        	} catch (StringIndexOutOfBoundsException e) {
        		loc = text.length() / 2;
        	}
        	textA = text.substring(0, loc++);
        	textB = text.substring(loc, text.length());
        	twoLine = true;
        }
        if(twoLine) {
	        sw = g.getFontMetrics().stringWidth(textA);
	        x = cx - (sw / 2);
			y = ch - (sh / 2);
			g.setColor(col);
			g.drawString(textA, x, y);
			sw = g.getFontMetrics().stringWidth(textB);
			x = cx - (sw / 2);
			y = ch + (sh / 2);
			g.drawString(textB, x, y);
        } else {
        	sw = g.getFontMetrics().stringWidth(textA);
	        x = cx - (sw / 2);
			y = ch;
			g.setColor(col);
			g.drawString(text, x, y);
        }
	}
}