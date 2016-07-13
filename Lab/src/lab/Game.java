package lab;

// here be dragons but don't worry
// everything is going to be fine
// you just need to believe
// if (believe) 
//	  clapHands();
// else
// 	  believe = true;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
/*import lab.Player;
import lab.MotionMouse;
import lab.Mouse;
import lab.Wall;
import lab.Turn;*/

public class Game extends JPanel {
	
	private int i;
	private int j;
	private static String lastClickedGame = "";
	//colors
	public String player1 = "#588C7E";
	public String player2 = "#8C4646";
	public String player1color = "#6677AA";
	public String player2color = "#AA7766"; 
	private String gridcolor = "#F2AE72";
	private String gridbackgroundcolor ="#D96479";
	private String highlightcolor ="#F2E394";
	public String confirm ="#52BF90";
	public String cancel ="#900C3F";
	public int tileSize;  //tile size: getWidth() / 9.45
	public int marginSize;
	//public int previouswidth;
	public int highlightupw1;
	public int highlightupa1;
	public int highlightups1;
	public int highlightupd1;
	public int highlightupw2;
	public int highlightupa2;
	public int highlightups2;
	public int highlightupd2;
	
	
	
	
	//variables to check whether world ends after double jump
	public boolean highdw1 = false, highda1 = false, highds1 = false, highdd1 = false,
			
			  	   highdw2 = false, highda2 = false, highds2 = false, highdd2 = false; 
    static Point pointStart = null;
    static Point pointEnd = null;
    public boolean gameOver = false;
    private ArrayList<ArrayList<Integer>> allCenters = new ArrayList<ArrayList<Integer>>();
    
	public ArrayList<String> moves = new ArrayList<String>(); // current available moves defined by triangles
	// variables to permit double jump
	boolean doublew = false, doublea = false, doubles = false, doubled = false;
	boolean doublew2 = false, doublea2 = false, doubles2 = false, doubled2 = false;
	// variables that check presence of walls
	public boolean ww1 = false, wa1 = false, ws1 = false, wd1 = false,
		  	   	   ww2 = false, wa2 = false, ws2 = false, wd2 = false; 
	
	//booleans for if players have moved in a direction
	public boolean hasmoved = false;
	public boolean hasWall = false;
	
	public boolean p1hasmovedw = false;
	public boolean p1hasmoveda = false;
	public boolean p1hasmoveds = false;
	public boolean p1hasmovedd = false;
	
	public boolean p2hasmovedw = false;
	public boolean p2hasmoveda = false;
	public boolean p2hasmoveds = false;
	public boolean p2hasmovedd = false;
	
	public boolean p1hasmoveddoublew = false;
	public boolean p1hasmoveddoublea = false;
	public boolean p1hasmoveddoubles = false;
	public boolean p1hasmoveddoubled = false;
	
	public boolean p2hasmoveddoublew = false;
	public boolean p2hasmoveddoublea = false;
	public boolean p2hasmoveddoubles = false;
	public boolean p2hasmoveddoubled = false;
	
	public boolean p1toggleactivitypane = false;
	public boolean p2toggleactivitypane = false;
	public boolean instructions = false;
	
//	public static boolean titlescreen;

	public boolean titlescreen = mml.titlescreen;
	public ArrayList<ArrayList<Integer>> walls1 = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> walls2 = new ArrayList<ArrayList<Integer>>();

	public static JFrame frame;
	
	Player Player1 = new Player(this, 1);  //classid
	Player Player2 = new Player(this, 2);
	Turn turn = new Turn(this);
	Wall wall = new Wall(this);
	static Mouse mml = new Mouse();
	static MotionMouse mml2 = new MotionMouse();


	/*public Rectangle getBounds() { // collider
		return new Rectangle(0, 0, getWidth(), (getHeight()-getWidth())/2);
	}*/ 
	private static boolean rescale = false;
	
	private ArrayList<Integer> wallCenter(ArrayList<Integer> wall) {
		if (wall.get(0) == wall.get(2)) {
			return new ArrayList<Integer>(Arrays.asList(wall.get(0), (wall.get(1)+wall.get(3))/2));
		}
		else {
			return new ArrayList<Integer>(Arrays.asList((wall.get(0)+wall.get(2))/2, wall.get(1)));
		}
	}
	
	private void Rescale() {
			// get current locations
			int currentx1 = Player1.x;
			int currenty1 = Player1.y;
			int currentx2 = Player2.x;
			int currenty2 = Player2.y;
			if (tileSize != 0 && marginSize != 0) {
				int tile_x1 = (int) Math.ceil(currentx1 / (tileSize+marginSize));
				int tile_y1 = (int) Math.ceil((currenty1-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				int tile_x2 = (int) Math.ceil(currentx2 / (tileSize+marginSize));
				int tile_y2 = (int) Math.ceil((currenty2-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				// rescale to same position
				if (tile_y1 > -1 && tile_y2 > -1) {
					Player1.y = ((getHeight()-getWidth())/2 + (tileSize+marginSize)*tile_y1 + tileSize/8);
					Player1.x = (tileSize+marginSize)*(tile_x1) + (tileSize/8);
					Player2.y = ((getHeight()-getWidth())/2 + (tileSize+marginSize)*tile_y2 + tileSize/8);
					Player2.x = (tileSize+marginSize)*(tile_x2) + (tileSize/8);
				} else { // set to default locations
						Player1.y = ((getHeight()-getWidth())/2 + tileSize/8);
						Player1.x = getWidth() /2 - (tileSize-10)/2;
						Player2.y = ((getHeight()-getWidth())/2 + getWidth()) - tileSize + tileSize/8;
						Player2.x = getWidth() /2- (tileSize-10)/2;
				}
				rescale = false;
			}
	}
	
	// returns all walls with size tileSize
	public ArrayList<ArrayList<Integer>> calculateWalls() {
		ArrayList<ArrayList<Integer>> allwalls = new ArrayList<ArrayList<Integer>>();

		for (i = 0; i < walls1.size(); i++) {
			ArrayList<Integer> wallPoints = walls1.get(i);
			if (wallPoints.get(0) == wallPoints.get(2)) {  ////////              4 1 6 1
				allwalls.add(new ArrayList<Integer>(Arrays.asList(wallPoints.get(0), wallPoints.get(1), wallPoints.get(0), (wallPoints.get(1)+wallPoints.get(3))/2)));
				allwalls.add(new ArrayList<Integer>(Arrays.asList(wallPoints.get(0), (wallPoints.get(1)+wallPoints.get(3))/2, wallPoints.get(0), wallPoints.get(3))));
				//allwalls.add(new ArrayList<Integer>(Arrays.asList(wallPoints.get(0), wallPoints.get(3))));
			} else {
				allwalls.add(new ArrayList<Integer>(Arrays.asList(wallPoints.get(0), wallPoints.get(1), (wallPoints.get(0)+wallPoints.get(2))/2, wallPoints.get(1))));
				allwalls.add(new ArrayList<Integer>(Arrays.asList((wallPoints.get(0)+wallPoints.get(2))/2, wallPoints.get(1), wallPoints.get(2), wallPoints.get(1))));
				//allwalls.add(new ArrayList<Integer>(Arrays.asList(wallPoints.get(2), wallPoints.get(1))));
			}
		}
		for (i = 0; i < walls2.size(); i++) {
			ArrayList<Integer> wallPoints = walls2.get(i);
			if (wallPoints.get(0) == wallPoints.get(2)) {
				allwalls.add(new ArrayList<Integer>(Arrays.asList(wallPoints.get(0), wallPoints.get(1), wallPoints.get(0), (wallPoints.get(1)+wallPoints.get(3))/2)));
				allwalls.add(new ArrayList<Integer>(Arrays.asList(wallPoints.get(0), (wallPoints.get(1)+wallPoints.get(3))/2, wallPoints.get(0), wallPoints.get(3))));
			} else {
				allwalls.add(new ArrayList<Integer>(Arrays.asList(wallPoints.get(0), wallPoints.get(1), (wallPoints.get(0)+wallPoints.get(2))/2, wallPoints.get(1))));
				allwalls.add(new ArrayList<Integer>(Arrays.asList((wallPoints.get(0)+wallPoints.get(2))/2, wallPoints.get(1), wallPoints.get(2), wallPoints.get(1))));
			}
		}
		return allwalls;
	}
	
	public void movePlayer(int i) { // moves player in direction defined by i
		if (turn.onePlaysNext) {
			if (i == 0) { 
				if (doublew) {
					Player1.y = Player1.y - tileSize - marginSize- tileSize - marginSize; 
					p1hasmoveddoublew = true;
				}
				else {
					Player1.y = Player1.y - tileSize - marginSize; 
					p1hasmovedw = true;
				}
			} else if (i == 1) {
				if (doublea) {
					Player1.x = Player1.x - tileSize - marginSize - tileSize - marginSize; 
					p1hasmoveddoublea = true;
				}
				else {
					Player1.x = Player1.x - tileSize - marginSize;
					p1hasmoveda = true;
				}
			} else if (i == 2) {
				if (doubles) {
					Player1.y = Player1.y + tileSize + marginSize + tileSize + marginSize; 
					p1hasmoveddoubles = true;
				}
				else {
					Player1.y = Player1.y + tileSize + marginSize; 
					p1hasmoveds = true;
					
				}
			} else if (i == 3) {
				if (doubled) {
					Player1.x = Player1.x + tileSize + marginSize + tileSize + marginSize; 
					p1hasmoveddoubled = true;
				}
				else {
					Player1.x = Player1.x + tileSize + marginSize;
					p1hasmovedd = true;
				}
			}
		}
		else {
			if (i == 0) { 
				if (doublew2) {
					Player2.y = Player2.y - tileSize - marginSize- tileSize - marginSize;
					p2hasmoveddoublew = true;
				}
				else {
					Player2.y = Player2.y - tileSize - marginSize;
					p2hasmovedw = true;
				}
			} else if (i == 1) {
				if (doublea2) {
					Player2.x = Player2.x - tileSize - marginSize - tileSize - marginSize;
					p2hasmoveddoublea = true;
				}
				else {
					Player2.x = Player2.x - tileSize - marginSize;
					p2hasmoveda = true;
				}
			} else if (i == 2) {
				if (doubles2) {
					Player2.y = Player2.y + tileSize + marginSize + tileSize + marginSize;
					p2hasmoveddoubles = true;
				}
				else {
					Player2.y = Player2.y + tileSize + marginSize;
					p2hasmoveds = true;
				}
			} else if (i == 3) {
				if (doubled2) {
					Player2.x = Player2.x + tileSize + marginSize + tileSize + marginSize;
					p2hasmoveddoubled = true;
				}
				else {
					Player2.x = Player2.x + tileSize + marginSize;
					p2hasmovedd = true;
				}
			}
			
		}
		if (p1hasmovedw || p1hasmoveda || p1hasmoveds || p1hasmovedd || p1hasmoveddoublew || p1hasmoveddoublea || p1hasmoveddoubles || p1hasmoveddoubled) {
			mml.p1hasmovedtoggle = true;
			p1toggleactivitypane = true;
			
		}
		if (p2hasmovedw || p2hasmoveda || p2hasmoveds || p2hasmovedd || p2hasmoveddoublew || p2hasmoveddoublea || p2hasmoveddoubles || p2hasmoveddoubled) {
			mml.p2hasmovedtoggle = true;
			p2toggleactivitypane = true;
			
		}
		
		repaint();
		
	}
	
	
	
	

	public void paint(Graphics g) {
		System.out.println("paint");
		if (titlescreen == false){
			repaint();
		
		if (rescale)
			Rescale();
		if (!hasmoved) {
			//System.out.println(moves);
		}
		if (lastClickedGame != "") { // handles clicks
			if (moves.contains(lastClickedGame)) { // if move player attempted is available
				for (int i = 0; i < 4; i++) {
				    if(moves.get(i).equals(lastClickedGame)){
				        if (wall.walltoggle % 2 == 0)
				        	movePlayer(i);
				    }
				}
			}
			if (lastClickedGame != "" && lastClickedGame != null) { // stops placing players in default locations
				Player1.first = false;
				Player2.first = false;
			}
			
			if (lastClickedGame =="side1" || lastClickedGame =="side2") { // call wall grid
				wall.walltoggle++;
			}
			
			
	// player 1 turns
			
			if (lastClickedGame =="p1cancel" && p1hasmovedw == true) {
				Player1.y = Player1.y + tileSize + marginSize; 			
			}
			if (lastClickedGame =="p1cancel" && p1hasmoveda == true) {
				Player1.x = Player1.x + tileSize + marginSize;			
			}
			if (lastClickedGame =="p1cancel" && p1hasmoveds == true) {
				Player1.y = Player1.y - tileSize - marginSize; 		
			}
			if (lastClickedGame =="p1cancel" && p1hasmovedd == true) {
				Player1.x = Player1.x - tileSize - marginSize;
			}
			if (lastClickedGame =="p1cancel" && p1hasmoveddoublew == true) {
				Player1.y = Player1.y + tileSize + marginSize + tileSize + marginSize;			
			}
			if (lastClickedGame =="p1cancel" && p1hasmoveddoublea == true) {
				Player1.x = Player1.x + tileSize + marginSize + tileSize + marginSize;			
			}
			if (lastClickedGame =="p1cancel" && p1hasmoveddoubles == true) {
				Player1.y = Player1.y - tileSize - marginSize- tileSize - marginSize; 		
			}
			if (lastClickedGame =="p1cancel" && p1hasmoveddoubled == true) {
				Player1.x = Player1.x - tileSize - marginSize - tileSize - marginSize;
			}
			
			// player 2 turns
			if (lastClickedGame =="p2cancel" && p2hasmovedw == true) {
				Player2.y = Player2.y + tileSize + marginSize; 			
			}
			if (lastClickedGame =="p2cancel" && p2hasmoveda == true) {
				Player2.x = Player2.x + tileSize + marginSize;			
			}
			if (lastClickedGame =="p2cancel" && p2hasmoveds == true) {
				Player2.y = Player2.y - tileSize - marginSize; 		
			}
			if (lastClickedGame =="p2cancel" && p2hasmovedd == true) {
				Player2.x = Player2.x - tileSize - marginSize;
			}
			if (lastClickedGame =="p2cancel" && p2hasmoveddoublew == true) {
				Player2.y = Player2.y + tileSize + marginSize + tileSize + marginSize;			
			}
			if (lastClickedGame =="p2cancel" && p2hasmoveddoublea == true) {
				Player2.x = Player2.x + tileSize + marginSize + tileSize + marginSize;			
			}
			if (lastClickedGame =="p2cancel" && p2hasmoveddoubles == true) {
				Player2.y = Player2.y - tileSize - marginSize- tileSize - marginSize; 		
			}
			if (lastClickedGame =="p2cancel" && p2hasmoveddoubled == true) {
				Player2.x = Player2.x - tileSize - marginSize - tileSize - marginSize;
			}
			if ((lastClickedGame =="p1cancel" || lastClickedGame == "p2cancel") && hasWall == true) {
				if (turn.onePlaysNext) {
					walls1.remove(walls1.size() - 1);
					hasWall = false;
					mml.p1walltoggle = false;
				}
				else {
					walls2.remove(walls2.size() - 1);
					hasWall = false;
					mml.p2walltoggle = false;
				}

			}
				
			
	//switchplayer
			if (lastClickedGame =="p1confirm" || lastClickedGame =="p2confirm") {
				turn.switchPlayer();
				if (wall.walltoggle % 2 != 0)
					wall.walltoggle++;

				p1hasmovedw = false;
				p1hasmoveda = false;
				p1hasmoveds = false;
				p1hasmovedd = false;
				p2hasmovedw = false;
				p2hasmoveda = false;
				p2hasmoveds = false;
				p2hasmovedd = false;
				p1hasmoveddoublew = false;
				p1hasmoveddoublea = false;
				p1hasmoveddoubles = false;
				p1hasmoveddoubled = false;
				p2hasmoveddoublew = false;
				p2hasmoveddoublea = false;
				p2hasmoveddoubles = false;
				p2hasmoveddoubled = false;
				
				mml.p1hasmovedtoggle = false;
				mml.p2hasmovedtoggle = false;
				mml.p1walltoggle = false;
				mml.p2walltoggle = false;
				p1toggleactivitypane = false;
				p2toggleactivitypane = false;
			}
			if (lastClickedGame =="p1cancel" || lastClickedGame =="p2cancel") {
				
				p1hasmovedw = false;
				p1hasmoveda = false;
				p1hasmoveds = false;
				p1hasmovedd = false;
				p2hasmovedw = false;
				p2hasmoveda = false;
				p2hasmoveds = false;
				p2hasmovedd = false;
				p1hasmoveddoublew = false;
				p1hasmoveddoublea = false;
				p1hasmoveddoubles = false;
				p1hasmoveddoubled = false;
				p2hasmoveddoublew = false;
				p2hasmoveddoublea = false;
				p2hasmoveddoubles = false;
				p2hasmoveddoubled = false;
				
				mml.p1hasmovedtoggle = false;
				mml.p2hasmovedtoggle = false;
				mml.p1walltoggle = false;
				mml.p2walltoggle = false;
				p1toggleactivitypane = false;
				p2toggleactivitypane = false;
			}
			
			
			lastClickedGame = "";
		}
		// win condition
		if (!Player2.first) {
			if (Player2.y <= (getHeight()-getWidth())/2 + tileSize/8) {
				player1 = "#8C4646";
				gameOver = true;
			}
		}
		if (!Player1.first) {
			if (Player1.y >= ((getHeight()-getWidth())/2 + getWidth()) - tileSize + tileSize/8) {
				player2 = "#588C7E";
				gameOver = true;
			}
		}
		tileSize =  (int) Math.ceil(getWidth() / 9.45);
		marginSize = (int) Math.ceil(tileSize / 20);
		
		highlightupw1 = 3;
		highlightupa1 = 3;
		highlightups1 = 3;
		highlightupd1 = 3;
		
		highlightupw2 = 3;
		highlightupa2 = 3;
		highlightups2 = 3;
		highlightupd2 = 3;
		
		Graphics2D g2d = (Graphics2D) g;
		// look nice
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.decode(gridbackgroundcolor));	
		// grid background color
		g2d.fillRect(0, (getHeight()-getWidth())/2, getWidth(), getWidth());
		// ülemine kast
		g2d.setColor(Color.decode(player1));
		g2d.fillRect(0, 0, getWidth(), (getHeight()-getWidth())/2);
		// alumine kast
		g2d.setColor(Color.decode(player2));
		g2d.fillRect(0, (getHeight()-getWidth())/2+getWidth(), getWidth(), (getHeight()-getWidth())/2);
		
		//DRAW GRID
		g2d.setColor(Color.decode(gridcolor));
		for (i=0; i<9 ;i++) {
			for (j=0; j<9 ;j++) {
				if (i == 0 || i == 8) {
					g2d.setColor(Color.decode("#F2BE82"));
					g2d.fillRect(j*tileSize + j*marginSize, (getHeight()-getWidth())/2 + i*tileSize + i*marginSize, tileSize, tileSize);
					g2d.setColor(Color.decode(gridcolor));
				} else
					g2d.fillRect(j*tileSize + j*marginSize, (getHeight()-getWidth())/2 + i*tileSize + i*marginSize, tileSize, tileSize);
			}
		}
		
		
		Player1.paint(g2d);
		Player2.paint(g2d);
		wall.paint(g2d);

		// DRAW WALLS
		for (int x = 0; x < walls1.size(); x++) { // player 1 walls
            g.setColor(Color.decode(wall.wallmarkercolor));
            g2d.setStroke(new BasicStroke(tileSize/7));
			g.drawLine(walls1.get(x).get(0)* (tileSize+marginSize)-1, walls1.get(x).get(1)*(tileSize+marginSize)+((getHeight()-getWidth())/2)-3, walls1.get(x).get(2)*(tileSize+marginSize)-1, walls1.get(x).get(3)*(tileSize+marginSize)+(getHeight()-getWidth())/2-3);
		}
		for (int x = 0; x < walls2.size(); x++) { // player 2 walls 
            g.setColor(Color.decode(wall.wallmarkercolor));
            g2d.setStroke(new BasicStroke(tileSize/7));
			g.drawLine(walls2.get(x).get(0)* (tileSize+marginSize)-1, walls2.get(x).get(1)*(tileSize+marginSize)+((getHeight()-getWidth())/2)-3, walls2.get(x).get(2)*(tileSize+marginSize)-1, walls2.get(x).get(3)*(tileSize+marginSize)+(getHeight()-getWidth())/2-3);
			
		}
		
		//ACTIVITYPANES
		
		
		if (p1toggleactivitypane) {
			g.setColor(Color.decode(confirm));
			g2d.fillRect(0, 0, getWidth()/2, (getHeight()-getWidth())/2);
			g.setColor(Color.decode(cancel));
			g2d.fillRect(getWidth()/2, 0, getWidth()/2, (getHeight()-getWidth())/2); 
		}
		
		if (p2toggleactivitypane) {
			g.setColor(Color.decode(confirm));
			g2d.fillRect(getWidth()/2, (getHeight()-getWidth())/2+ getWidth(), getWidth()/2, (getHeight()-getWidth())/2);
			g.setColor(Color.decode(cancel));
			g2d.fillRect(0, (getHeight()-getWidth())/2+ getWidth(), getWidth()/2, (getHeight()-getWidth())/2);
		}

		// draw things that show how many walls are left
		for (int x = 0; x < 10-walls1.size(); x++) {
			g.setColor(Color.decode(wall.wallmarkercolor));
            g2d.setStroke(new BasicStroke(tileSize/15));
            g.fillRect((int) Math.ceil(tileSize/2.6), x*tileSize/4+tileSize/4, tileSize/2, tileSize/8);
		}
		for (int x = 0; x < 10-walls2.size(); x++) {
			g.setColor(Color.decode(wall.wallmarkercolor));
            g2d.setStroke(new BasicStroke(tileSize/15));
            g.fillRect(getWidth()-(int) Math.ceil(tileSize/2.6) - tileSize/2, getHeight()-x*tileSize/4-tileSize/4 - tileSize/8, tileSize/2, tileSize/8);
		}
		
        if (wall.walltoggle % 2 != 0) {
        	if (!hasWall) {
			if (pointStart != null) {
				
				if ((turn.onePlaysNext && walls1.size() < 10) || (!turn.onePlaysNext && walls2.size() < 10)) {
	            g.setColor(Color.decode(wall.wallmarkercolor)); 
	            g2d.setStroke(new BasicStroke(tileSize/6));
	            g.drawLine(pointStart.x* (tileSize+marginSize)-2, pointStart.y*(tileSize+marginSize)+((getHeight()-getWidth())/2)-2, pointEnd.x, pointEnd.y); // draw unfinished walls
	            
	            // add correct walls to array
	            if (pointEnd.y >= (getHeight()-getWidth())/2-10 && pointEnd.y <= (getHeight()-getWidth())/2 + getWidth()+10) {

        		// get centers of walls to check whether walls cross each other
        		for (int x = 0; x < walls1.size(); x++) {
        			allCenters.add(wallCenter(walls1.get(x)));
        		}
        		for (int x = 0; x < walls2.size(); x++) {
        			allCenters.add(wallCenter(walls2.get(x)));
        		}
	            	if (Math.abs(pointStart.x*(tileSize+marginSize)-2 - pointEnd.x) <= 5 && Math.abs(pointStart.y*(tileSize+marginSize)+((getHeight()-getWidth())/2)-2-pointEnd.y) >= tileSize*2+marginSize) {
	            	if (pointEnd.y > pointStart.y*(tileSize+marginSize)+((getHeight()-getWidth())/2)-2) {
	            		if(!allCenters.contains(wallCenter(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y + 2))))) {
		            		if (turn.onePlaysNext)
		            			walls1.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y + 2)));
		            		else
		            			walls2.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y + 2)));
		            		hasWall = true;
	            		}
	            	} else { 
	            		if(!allCenters.contains(wallCenter(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y - 2))))) {
		            		if (turn.onePlaysNext)
		            			walls1.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y - 2)));
		            		else
		            			walls2.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y - 2)));
		            		hasWall = true;
	            		}
	            	}
	            	mml.Start = null;
	            	pointStart = null;
	            } else if (Math.abs(pointStart.y*(tileSize+marginSize)+((getHeight()-getWidth())/2)-2 - pointEnd.y) <= 5 && Math.abs(pointStart.x* (tileSize+marginSize)-2-pointEnd.x) >= tileSize*2+marginSize) {
	            	if (pointEnd.x > pointStart.x* (tileSize+marginSize)-2) {
	            		if(!allCenters.contains(wallCenter(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x  + 2, pointStart.y))))) {
		            		if (turn.onePlaysNext)
		            			walls1.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x + 2, pointStart.y)));
		            		else
		            			walls2.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x + 2, pointStart.y)));
		            		hasWall = true;
	            		}
	            	} else {
	            		if(!allCenters.contains(wallCenter(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x - 2, pointStart.y))))) {

		            		if (turn.onePlaysNext)
		            			walls1.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x - 2, pointStart.y)));
		            		else
		            			walls2.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x - 2, pointStart.y)));
		            		hasWall = true;
	            		}
	            		
	            	}
	            	mml.Start = null; // stop drawing
	            	pointStart = null;
	            	
	            	
	            }
	            	if (turn.onePlaysNext) {
	            		p1toggleactivitypane = true;
	            		mml.p1walltoggle = true;
	            	} else {
	            		p2toggleactivitypane = true;
	            		mml.p2walltoggle = true;
	            	}
				}
				}
				}
	        }
        }

		mml.getPlayerPosition(Player1.x+(tileSize-10)/2, Player1.y+(tileSize-10)/2, Player2.x+(tileSize-10)/2, Player2.y+(tileSize-10)/2, getWidth(), getHeight());
		repaint(); // send players' coordinates to Mouse
		
		
		// visible directions
		
		int[] xPointsUp = {Player1.x + 8, Player1.x + (tileSize - 10 )/2,Player1.x + tileSize - 18}; //triangle
		int[] yPointsUp = {Player1.y - marginSize - 10 - tileSize/10,Player1.y - marginSize - tileSize/2 - marginSize,Player1.y - marginSize - 10 - tileSize/10};
		
		int[] xPointsRight = {Player1.x + tileSize + marginSize + tileSize/10,Player1.x + tileSize + marginSize + tileSize/10 ,Player1.x + tileSize + (tileSize-10)/2}; //triangle
		int[] yPointsRight = {Player1.y + 8,Player1.y + tileSize - 18, Player1.y + (tileSize-10)/2};
		
		int[] xPointsDown = {Player1.x + 8, Player1.x + (tileSize - 10 )/2,Player1.x + tileSize - 18}; //triangle
		int[] yPointsDown = {Player1.y + marginSize + tileSize/10 + tileSize ,Player1.y + tileSize + marginSize + tileSize/2 + marginSize - 10 ,Player1.y + marginSize + tileSize + tileSize/10};
		
		int[] xPointsLeft = {Player1.x - 8 - tileSize/10 - marginSize,Player1.x - 8 - tileSize/10 - marginSize ,Player1.x - 8 - marginSize - (tileSize-10)/2}; //triangle
		int[] yPointsLeft = {Player1.y + 8,Player1.y + tileSize - 18, Player1.y + (tileSize-10)/2};	
		
		int[] xPointsUp2 = {Player2.x + 8, Player2.x + (tileSize - 10 )/2,Player2.x + tileSize - 18}; //triangle
		int[] yPointsUp2 = {Player2.y - marginSize - 10 - tileSize/10,Player2.y - marginSize - tileSize/2 - marginSize,Player2.y - marginSize - 10 - tileSize/10};
		
		int[] xPointsRight2 = {Player2.x + tileSize + marginSize + tileSize/10,Player2.x + tileSize + marginSize + tileSize/10 ,Player2.x + tileSize + (tileSize-10)/2}; //triangle
		int[] yPointsRight2 = {Player2.y + 8,Player2.y + tileSize - 18, Player2.y + (tileSize-10)/2};
		
		int[] xPointsDown2 = {Player2.x + 8, Player2.x + (tileSize - 10 )/2,Player2.x + tileSize - 18}; //triangle
		int[] yPointsDown2 = {Player2.y + marginSize + tileSize/10 + tileSize ,Player2.y + tileSize + marginSize + tileSize/2 + marginSize - 10 ,Player2.y + marginSize + tileSize + tileSize/10};
		
		int[] xPointsLeft2 = {Player2.x - 8 - tileSize/10 - marginSize,Player2.x - 8 - tileSize/10 - marginSize ,Player2.x - 8 - marginSize - (tileSize-10)/2}; //triangle
		int[] yPointsLeft2 = {Player2.y + 8,Player2.y + tileSize - 18, Player2.y + (tileSize-10)/2};	
		
		
		
		if (turn.onePlaysNext == true) { // shows possible directions of player 1
			if (mml.p1hasmovedtoggle == false) {
			if(Player1.i == 1) {
				moves = new ArrayList<String>();
				for (int j = 0; j < 4; j++)
					moves.add("");
				g2d.setColor(Color.decode(highlightcolor));
				//if second player on adjacent tile, change location of triangle
				doublew = false;
				doublea = false;
				doubles = false;
				doubled = false;

				if (Player1.y - Player2.y <= tileSize+marginSize+2 && Player1.y - Player2.y >= 0 && Math.abs(Player1.x - Player2.x) <= 5) {
					doublew = true;
					if (!highdw1)
						g.fillPolygon(xPointsUp, new int[] {yPointsUp[0]-tileSize-marginSize, yPointsUp[1]-tileSize-marginSize, yPointsUp[2]-tileSize-marginSize}, highlightupw1);
				} else
					if (!ww1)
						g.fillPolygon(xPointsUp, yPointsUp, highlightupw1);
				if (Player2.y - Player1.y <= tileSize+marginSize+2 && Player2.y - Player1.y >= 0 && Math.abs(Player1.x - Player2.x) <= 5) {
					doubles = true;
					if (!highds1)
						g.fillPolygon(xPointsDown, new int[] {yPointsDown[0]+tileSize+marginSize, yPointsDown[1]+tileSize+marginSize, yPointsDown[2]+tileSize+marginSize}, highlightups1);
				} else
					if (!ws1)
						g.fillPolygon(xPointsDown, yPointsDown, highlightups1);
				if (Player1.x - Player2.x <= tileSize+marginSize+2 && Player1.x - Player2.x >= 0 && Math.abs(Player1.y - Player2.y) <= 5) {
					doublea = true;
					g.fillPolygon(new int[] {xPointsLeft[0]-tileSize-marginSize, xPointsLeft[1]-tileSize-marginSize, xPointsLeft[2]-tileSize-marginSize}, yPointsLeft, highlightupa1);
				} else
					if (!wa1)
						g.fillPolygon(xPointsLeft, yPointsLeft, highlightupa1);
				if (Player2.x - Player1.x <= tileSize+marginSize+2 && Player2.x - Player1.x >= 0 && Math.abs(Player1.y - Player2.y) <= 5) {
					doubled = true;
					g.fillPolygon(new int[] {xPointsRight[0]+tileSize+marginSize, xPointsRight[1]+tileSize+marginSize, xPointsRight[2]+tileSize+marginSize}, yPointsRight, highlightupd1);
				} else
					if (!wd1)
						g.fillPolygon(xPointsRight, yPointsRight, highlightupd1);

				
				if (highlightups1 > 0) { // if world doesn't end
					//if (doubles && !true)
					int tile_x = (int) Math.ceil(Player1.x / (tileSize+marginSize));
					int tile_y = (int) Math.ceil((Player1.y+ tileSize + marginSize-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
					if (doubles) {
						tile_y += 1;
						if (!highds1)
							moves.set(2, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
					} else
						if (!ws1)
							moves.set(2, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				}
				if (highlightupw1 > 0) {
					int tile_x = (int) Math.ceil(Player1.x / (tileSize+marginSize));
					int tile_y = (int) Math.ceil((Player1.y- tileSize - marginSize-((getHeight()-getWidth())/2)) / (tileSize+marginSize));

					if (doublew) {
						tile_y -= 1;
						if (!highdw1)
							moves.set(0, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
					} else
						if (!ww1)
							moves.set(0, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				}
				int tile_x = (int) Math.ceil((Player1.x - tileSize - marginSize) / (tileSize+marginSize));
				int tile_y = (int) Math.ceil((Player1.y-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				if (doublea)
					tile_x -= 1;
				if (!wa1)
					moves.set(1, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				tile_x = (int) Math.ceil((Player1.x + tileSize + marginSize) / (tileSize+marginSize));
				tile_y = (int) Math.ceil((Player1.y-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				if (doubled)
					tile_x += 1;
				if (!wd1)
					moves.set(3, Integer.toString(tile_x) + " " + Integer.toString(tile_y));

				highdw1 = false;
				highda1 = false;
				highds1 = false;
				highdd1 = false;
				ww1 = false;
				wa1 = false;
				ws1 = false;
				wd1 = false;
			}

			}	
		} else { //Player 2's possible directions
			if (mml.p2hasmovedtoggle == false) {
			if (Player2.i != 1) {
				g2d.setColor(Color.decode(highlightcolor));
				doublew2 = false;
				doublea2 = false;
				doubles2 = false;
				doubled2 = false;
				
				if (Player2.y - Player1.y <= tileSize+marginSize+2 && Player2.y - Player1.y >= 0 && Math.abs(Player1.x - Player2.x) <= 5) {
					doublew2 = true;
					if (!highdw2)
						g.fillPolygon(xPointsUp2, new int[] {yPointsUp2[0]-tileSize-marginSize, yPointsUp2[1]-tileSize-marginSize, yPointsUp2[2]-tileSize-marginSize}, highlightupw2);
				} else
					if (!ww2) {
						g.fillPolygon(xPointsUp2, yPointsUp2, highlightupw2);
					}
				if (Player1.y - Player2.y <= tileSize+marginSize+2 && Player1.y - Player2.y >= 0 && Math.abs(Player1.x - Player2.x) <= 5) {
					doubles2 = true;
					if (!highds2)
						g.fillPolygon(xPointsDown2, new int[] {yPointsDown2[0]+tileSize+marginSize, yPointsDown2[1]+tileSize+marginSize, yPointsDown2[2]+tileSize+marginSize}, highlightups2);
				} else
					if (!ws2)
						g.fillPolygon(xPointsDown2, yPointsDown2, highlightups2);

				if (Player2.x - Player1.x <= tileSize+marginSize+2 && Player2.x - Player1.x >= 0 && Math.abs(Player1.y - Player2.y) <= 5) {
					doublea2 = true;
					if (!highda2)
						g.fillPolygon(new int[] {xPointsLeft2[0]-tileSize-marginSize, xPointsLeft2[1]-tileSize-marginSize, xPointsLeft2[2]-tileSize-marginSize}, yPointsLeft2, highlightupa2);
				} else
					if (!wa2)
						g.fillPolygon(xPointsLeft2, yPointsLeft2, highlightupa2);
				if (Player1.x - Player2.x <= tileSize+marginSize+2 && Player1.x - Player2.x >= 0 && Math.abs(Player1.y - Player2.y) <= 5) {
					doubled2 = true;
					if (!highdd2)
						g.fillPolygon(new int[] {xPointsRight2[0]+tileSize+marginSize, xPointsRight2[1]+tileSize+marginSize, xPointsRight2[2]+tileSize+marginSize}, yPointsRight2, highlightupd2);
				} else
					if (!wd2)
						g.fillPolygon(xPointsRight2, yPointsRight2, highlightupd2);
				if (highlightups2 > 0) {
					int tile_x = (int) Math.ceil(Player2.x / (tileSize+marginSize));
					int tile_y = (int) Math.ceil((Player2.y+ tileSize + marginSize-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
					if (doubles2) {
						tile_y += 1;
						if (!highds2) 
							moves.set(2, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
					} else
						if (!ws2)
							moves.set(2, Integer.toString(tile_x) + " " + Integer.toString(tile_y));

				}
				if (highlightupw2 > 0) {
					int tile_x = (int) Math.ceil(Player2.x / (tileSize+marginSize));
					int tile_y = (int) Math.ceil((Player2.y- tileSize - marginSize-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
					if (doublew2) {
						tile_y -= 1;
						if (!highdw2) {
							moves.set(0, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
						}
					} else
						if (!ww2)
							moves.set(0, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				}
				int tile_x = (int) Math.ceil((Player2.x - tileSize - marginSize) / (tileSize+marginSize));
				int tile_y = (int) Math.ceil((Player2.y-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				if (doublea2) 
					tile_x -= 1;
				if (!wa2)
					moves.set(1, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				tile_x = (int) Math.ceil((Player2.x + tileSize + marginSize) / (tileSize+marginSize));
				tile_y = (int) Math.ceil((Player2.y-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				if (doubled2) 
					tile_x += 1;
				if (!wd2)
					moves.set(3, Integer.toString(tile_x) + " " + Integer.toString(tile_y));

				highdw2 = false;
				highda2 = false;
				highds2 = false;
				highdd2 = false;

				ww2 = false;
				wa2 = false;
				ws2 = false;
				wd2 = false;
				
				
			}	
		}	
			
		}
		
	}
		else {
			Graphics2D g2d = (Graphics2D) g;		
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
			
			
			int[] xPointsTitle = {getWidth()/9*3 +getWidth()/18,getWidth()/9*3 +getWidth()/18, getWidth()/18*12 +getWidth()/18}; //triangle
			int[] yPointsTitle = {(getHeight()-getWidth())/2+getWidth()/9*2, (getHeight()-getWidth())/2+getWidth()/9*5,(getHeight()-getWidth())/2+getWidth()/18*7} ;	
			
			if (instructions == true) {
				g.setColor(Color.decode(gridcolor));
				g2d.fillRect(0,0,getWidth(),getHeight());
				g.setColor(Color.decode(wall.wallmarkercolor)); 
				g.setFont(new Font("Calibri", Font.PLAIN, getWidth()/18)); 
				g2d.drawString("Instructions",getWidth()/9, (getHeight()-getWidth())/2);
				g.setFont(new Font("Calibri", Font.PLAIN, getWidth()/20)); 
				g2d.drawString("Objective: Reach the opponent's",getWidth()/9, (getHeight()-getWidth())/2 + getWidth()/9);
				g2d.drawString("base line first.",getWidth()/9, (getHeight()-getWidth())/2 + 2*getWidth()/11);
				g2d.drawString("Turn: On his/her turn, the player",getWidth()/9, (getHeight()-getWidth())/2 + 2*getWidth()/11 + getWidth()/9);
				g2d.drawString("can either place a wall or move",getWidth()/9, (getHeight()-getWidth())/2 + 4*getWidth()/11);
				g2d.drawString("in a direction. Walls cannot be",getWidth()/9, (getHeight()-getWidth())/2 + 5*getWidth()/11);
				g2d.drawString("crossed unless you jump over",getWidth()/9, (getHeight()-getWidth())/2 + 6*getWidth()/11);
				g2d.drawString("the other player.",getWidth()/9, (getHeight()-getWidth())/2 + 7*getWidth()/11);
				g2d.drawString("Walls can be built by pressing",getWidth()/9, (getHeight()-getWidth())/2 + 7*getWidth()/11 + getWidth()/9);
				g2d.drawString("on the player's side and",getWidth()/9, (getHeight()-getWidth())/2 + 8*getWidth()/11 + getWidth()/9);
				g2d.drawString("connecting the dots.",getWidth()/9, (getHeight()-getWidth())/2 + 9*getWidth()/11 + getWidth()/9);
				
			}	else {
				
				g.setColor(Color.decode(gridcolor));
				g2d.fillRect(0,0,getWidth(),getHeight());
				g.setColor(Color.decode(player2));
				g2d.fillOval(getWidth()/9, (getHeight()-getWidth())/2, getWidth()/9*7, getWidth()/9*7);
				//g2d.fillOval(getWidth()/9*3, (getHeight()-getWidth())/2+getWidth()/9*5 + getWidth()/3 - getWidth()/36, getWidth()/9*3, getWidth()/9*3);
				g.setColor(Color.decode(highlightcolor));
				g2d.fillPolygon(xPointsTitle, yPointsTitle, 3);
				g.setFont(new Font("Calibri", Font.PLAIN, getWidth()/9)); 
				g.setColor(Color.decode(player1color));  
				g2d.drawString("?",getWidth()/36*17 , (getHeight()-getWidth())/2+getWidth()/9*5 + getWidth()/2);
				//getWidth()/9*3,(getHeight()-getWidth())/2+getWidth()/9*2,getWidth()/9*3, (getHeight()-getWidth())/2+getWidth()/9*6, getWidth()/9*6,(getHeight()-getWidth())/2+getWidth()/18*7 
			}
		}
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		// stuff that makes the game appear and do stuff
		frame = new JFrame("Labyrinth cancer game");
		frame.getContentPane().addMouseListener(mml);
		frame.getContentPane().add(new Game());
		frame.getContentPane().addMouseMotionListener(mml2);
		Game game = new Game();
		frame.setSize(376, 616);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);frame.addComponentListener(new ComponentListener() {
		    public void componentResized(ComponentEvent e) {
		        rescale = true;    
		    }
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
		while (true) {
			// checks for new clicks
			game.repaint();
			
			System.out.println(mml.titlescreen);
			if (mml.lastclicked != "") {
				lastClickedGame = mml.lastclicked;
				mml.lastclicked = "";
			}
			//if (mml.Start != null) {
				pointStart = mml.Start;
				//mml.Start = null;
			//}
			if (mml2.pointEnd != null) {
				pointEnd = mml2.pointEnd;
			}
			
//			if (titlescreen == false) {
//				super.paint();
//			}
			// sleeps
			Thread.sleep(10);
			
		}
	
		
	}
}