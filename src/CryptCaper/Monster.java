package CryptCaper;


import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Monster extends Entity {
	
	private Vector velocity;
	private char[][] GridElements = new char[30][15];
	int monX = 1;
	int monY = 1;
	
	boolean moving = false;
	String moveDir = null;
	String[] choices = new String[4];
	
	boolean moveH = false;
	boolean moveV = false;
	
	public Monster(final float x, final float y) {
		super(24 + x * 48, 204 + y * 48);
		addImageWithBoundingBox(ResourceManager
				.getImage(CryptCaperGame.MON_MONIMG_RSC));
		velocity = new Vector(0, 0);
		monX = (int) x;
		monY = (int) y;
	}
	
	public void choosePath() {
		int i = 0;
		boolean deadend = true;
		
		Random ran = new Random();
		int arrChoice;
		String dirChoice = null;
		
		if (moveDir != "Down" && checkDir("Up")) {
			choices[i] = "Up";
			i += 1;
			deadend = false;
		}
		if (moveDir != "Up" && checkDir("Down")) {
			choices[i] = "Down";
			i += 1;
			deadend = false;
		}
		if (moveDir != "Right" && checkDir("Left")) {
			choices[i] = "Left";
			i += 1;
			deadend = false;
		}
		if (moveDir != "Left" && checkDir("Right")) {
			choices[i] = "Right";
			i += 1;
			deadend = false;
		}
		
		if (deadend == true) {
			if (moveDir == "Down")
				choices[i] = "Up";
			if (moveDir == "Up")
				choices[i] = "Down";
			if (moveDir == "Right")
				choices[i] = "Left";
			if (moveDir == "Left")
				choices[i] = "Right";
			i += 1;
		}
		
		if (i > 1) {
			arrChoice = ran.nextInt(i);
			moveDir = choices[arrChoice];
			move(choices[arrChoice]);
		}
		else {
			move(choices[0]);
			moveDir = choices[0];
		}
		moving = true;
			
	}
	
	
	public boolean checkDir(String dir) {
		if (dir == "Up")
			return checkPath(monX, monY - 1);
		else if (dir == "Down")
			return checkPath(monX, monY + 1);
		else if (dir == "Left")
			return checkPath(monX - 1, monY);
		else if (dir == "Right")
			return checkPath(monX + 1, monY);
		else
			return false;
	}
	
	private boolean checkPath(int x, int y) {
		if (GridElements[x][y] == 'X')
			return false;
		else
			return true;
	}
	
public void move(String dir) {
		
		if (dir == "Up") {
			velocity = new Vector(0, -.125f);
			monY -= 1;
			moveV = true;
		}
		else if (dir == "Down") {
			velocity = new Vector(0, .125f);
			monY += 1;
			moveV = true;
		}
		else if (dir == "Left") {
			velocity = new Vector(-.125f, 0);
			monX -= 1;
			moveH = true;
		}
		else if (dir == "Right") {
			velocity = new Vector(.125f, 0);
			monX += 1;
			moveH = true;
		}
	}
	
	public void initMonPath() {
		
		String levelText = CryptCaperGame.Lvl1;
		
		int k = -1;
		for (int i = 0; i < 15; i++) {	
			for (int j = 0; j < 30; j++) {
				k += 1;
				GridElements[j][i] = levelText.charAt(k);
			}
		}
	}
	
	private void checkStop() {
		if (moveV == true) {
			if (getY() > 200 + (monY * 48) && getY() < 208 + (monY * 48) ) {
				velocity = new Vector(0,0);
				setY(204 + monY * 48);
				moving = false;
				moveV = false;	
			}
		}
		if (moveH == true) {
			if (getX() > 20 + (monX * 48) && getX() < 28 + (monX * 48) ) {
				velocity = new Vector(0,0);
				setX(24 + monX * 48);
				moving = false;
				moveH = false;
			}
		}
	}
	
	public void update(final int delta) {
		translate(velocity.scale(delta));
		checkStop();
		if (moving == false) {
			choosePath();
		}
	}
}
