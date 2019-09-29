package CryptCaper;


import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Monster extends Entity {
	
	int waitTime;
	boolean waitMode = false;
	
	boolean active = false;
	
	int chaseTime;
	boolean chaseMode = false;
	
	int startX[] = new int[10];
	int startY[] = new int[10];
	
	private Vector velocity;
	float speedMod = 0;
	
	
	private char[][] GridElements = new char[30][15];
	int monX = 1;
	int monY = 1;
	int expX;
	int expY;
	
	boolean moving = false;
	String moveDir = null;
	String[] choices = new String[4];
	
	boolean moveH = false;
	boolean moveV = false;
	
	public Monster(final float x, final float y) {
		super(24 + x * 48, 204 + y * 48);
		addImageWithBoundingBox(ResourceManager
				.getImage(CryptCaperGame.MON_DOWNIMG_RSC));
		velocity = new Vector(0, 0);
		monX = (int) x;
		monY = (int) y;
	}
	
	public void setStartLocation() {
		initMonPath();
		Random ran = new Random();
		int arrChoice = ran.nextInt(10);
		monX = startX[arrChoice];
		monY = startY[arrChoice];
		setPosition(24 + monX * 48, 204 + monY * 48);
		active = false;
		waitMode = true;
		waitTime = 100;
	}
	
	public void deactivate() {
		changeFace("Down");
		moving = false;
		moveDir = null;
		moveH = false;
		moveV = false;
		velocity = new Vector(0, 0);
		waitMode = false;
		active = false;
		setPosition(24 + 35 * 48, 204 + 0 * 48);
		monX = 35;
		monY = 0;
	}
	
	public void setExpLoc(int x, int y) {
		expX = x;
		expY = y;
	}
	
	public void choosePath() {
		int i = 0;
		boolean deadend = true;
		
		Random ran = new Random();
		int arrChoice;
		
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
			changeFace(choices[arrChoice]);
		}
		else {
			move(choices[0]);
			moveDir = choices[0];
			changeFace(choices[0]);
		}
		moving = true;
			
	}
	
	
	public boolean checkDir(String dir) {
		if (dir == "Up" && monY > 0)
			return checkPath(monX, monY - 1);
		else if (dir == "Down" && monY < 14)
			return checkPath(monX, monY + 1);
		else if (dir == "Left" && monX > 0)
			return checkPath(monX - 1, monY);
		else if (dir == "Right" && monX < 29)
			return checkPath(monX + 1, monY);
		else
			return false;
	}
	
	private boolean checkPath(int x, int y) {
		if (GridElements[x][y] == 'X' || GridElements[x][y] == 'M')
			return false;
		else
			return true;
	}
	
public void move(String dir) {
		
		if (dir == "Up") {
			velocity = new Vector(0, -.125f - speedMod);
			monY -= 1;
			moveV = true;
		}
		else if (dir == "Down") {
			velocity = new Vector(0, .125f + speedMod);
			monY += 1;
			moveV = true;
		}
		else if (dir == "Left") {
			velocity = new Vector(-.125f - speedMod, 0);
			monX -= 1;
			moveH = true;
		}
		else if (dir == "Right") {
			velocity = new Vector(.125f + speedMod, 0);
			monX += 1;
			moveH = true;
		}
	}

	private void changeFace(String dir) {
		removeImage(ResourceManager.getImage(CryptCaperGame.MON_UPIMG_RSC));
		removeImage(ResourceManager.getImage(CryptCaperGame.MON_DOWNIMG_RSC));
		removeImage(ResourceManager.getImage(CryptCaperGame.MON_LEFTIMG_RSC));
		removeImage(ResourceManager.getImage(CryptCaperGame.MON_RIGHTIMG_RSC));
		if (dir == "Up") 
			addImageWithBoundingBox(ResourceManager
					.getImage(CryptCaperGame.MON_UPIMG_RSC));
		else if (dir == "Down") 
			addImageWithBoundingBox(ResourceManager
					.getImage(CryptCaperGame.MON_DOWNIMG_RSC));
		else if (dir == "Left") 
			addImageWithBoundingBox(ResourceManager
					.getImage(CryptCaperGame.MON_LEFTIMG_RSC));
		else if (dir == "Right") 
			addImageWithBoundingBox(ResourceManager
					.getImage(CryptCaperGame.MON_RIGHTIMG_RSC));
	}
	
	public void initMonPath() {
		
		String levelText = CryptCaperGame.Lvl1;
		
		int k = -1;
		int l = 0;
		for (int i = 0; i < 15; i++) {	
			for (int j = 0; j < 30; j++) {
				k += 1;
				GridElements[j][i] = levelText.charAt(k);
				if (levelText.charAt(k) == 'M') {
					startX[l] = j;
					startY[l] = i;
					l += 1;
				}
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
	
	private void checkSight() {
		int checkX = monX;
		int checkY = monY;
		
		if (moveDir == "Up") {
			while (checkY >= 0) {
				if (GridElements[checkX][checkY] == 'X' || 
						GridElements[checkX][checkY] == 'M')
					break;
				if (checkX == expX && checkY == expY)
					setChase();
				checkY -= 1;
			}
		}
		if (moveDir == "Down") {
			while (checkY <= 14) {
				if (GridElements[checkX][checkY] == 'X' || 
						GridElements[checkX][checkY] == 'M')
					break;
				if (checkX == expX && checkY == expY)
					setChase();
				checkY += 1;
			}
		}
		if (moveDir == "Left") {
			while (checkX >= 0) {
				if (GridElements[checkX][checkY] == 'X' || 
						GridElements[checkX][checkY] == 'M')
					break;
				if (checkX == expX && checkY == expY)
					setChase();
				checkX -= 1;
			}
		}
		if (moveDir == "Right") {
			while (checkX <= 29) {
				if (GridElements[checkX][checkY] == 'X' || 
						GridElements[checkX][checkY] == 'M')
					break;
				if (checkX == expX && checkY == expY)
					setChase();
				checkX += 1;
			}
		}
	}
	
	public void setChase() {
		chaseMode = true;
		chaseTime = 100;
		speedMod = .1f;
	}
	
	public void stopChase() {
		chaseMode = false;
		speedMod = 0;
	}
	
	public void update(final int delta) {
		
		if (waitMode)
			waitTime -= 1;
		if (waitTime <= 0 && waitMode) {
			waitMode = false;
			active = true;
		}
		
		if (active) {
			checkSight();
			if (chaseMode)
				chaseTime -= 1;
			if (chaseTime <= 0 && chaseMode)
				stopChase();
			
			translate(velocity.scale(delta));
			checkStop();
			if (moving == false) {
				choosePath();
			}
		}
	}
}
