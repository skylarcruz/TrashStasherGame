package TrashStasher;

import java.util.ArrayList;
import java.util.List;
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
	int raccX;
	int raccY;
	
	boolean moving = false;
	String moveDir = null;
	String bestDir = null;
	String[] choices = new String[4];
	
	boolean moveH = false;
	boolean moveV = false;
	boolean moveU = false;
	boolean moveD = false;
	boolean moveL = false;
	boolean moveR = false;
	
	public List<Character> barrierChars = new ArrayList<Character>(); 
	
	public Monster(final float x, final float y) {
		super(24 + x * 48, 204 + y * 48);
		addImageWithBoundingBox(ResourceManager
				.getImage(TrashStasherGame.MON_DOWNIMG_RSC));
		velocity = new Vector(0, 0);
		monX = (int) x;
		monY = (int) y;
		
		barrierChars.add('X');
		barrierChars.add('M');
		barrierChars.add('D');
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
		moveU = false;
		moveD = false;
		moveL = false;
		moveR = false;
		velocity = new Vector(0, 0);
		waitMode = false;
		stopChase();
		active = false;
		setPosition(24 + 35 * 48, 204 + 0 * 48);
		monX = 35;
		monY = 0;
	}
	
	public void setRaccLoc(int x, int y) {
		raccX = x;
		raccY = y;
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
		if (barrierChars.contains(GridElements[x][y]) == true)
			return false;
		else
			return true;
	}
	
public void move(String dir) {
		
		if (dir == "Up") {
			velocity = new Vector(0, -.125f - speedMod);
			monY -= 1;
			moveU = true;
		}
		else if (dir == "Down") {
			velocity = new Vector(0, .125f + speedMod);
			monY += 1;
			moveD = true;
		}
		else if (dir == "Left") {
			velocity = new Vector(-.125f - speedMod, 0);
			monX -= 1;
			moveL = true;
		}
		else if (dir == "Right") {
			velocity = new Vector(.125f + speedMod, 0);
			monX += 1;
			moveR = true;
		}
		
		moving = true;
	}

	private void changeFace(String dir) {
		removeImage(ResourceManager.getImage(TrashStasherGame.MON_UPIMG_RSC));
		removeImage(ResourceManager.getImage(TrashStasherGame.MON_DOWNIMG_RSC));
		removeImage(ResourceManager.getImage(TrashStasherGame.MON_LEFTIMG_RSC));
		removeImage(ResourceManager.getImage(TrashStasherGame.MON_RIGHTIMG_RSC));
		if (dir == "Up") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.MON_UPIMG_RSC));
		else if (dir == "Down") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.MON_DOWNIMG_RSC));
		else if (dir == "Left") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.MON_LEFTIMG_RSC));
		else if (dir == "Right") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.MON_RIGHTIMG_RSC));
	}
	
	public void initMonPath() {
		
		String levelText = TrashStasherGame.Lvl1;
		
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
		if (moveL == true) {
			if (getX() < 28 + (monX * 48)) {
				velocity = new Vector(0,0);
				setX(24 + monX * 48);
				moving = false;
				moveL = false;
			}
		}
		if (moveR == true) {
			if (getX() > 20 + (monX * 48) ) {
				velocity = new Vector(0,0);
				setX(24 + monX * 48);
				moving = false;
				moveR = false;
			}
		}
		if (moveU == true) {
			if (getY() < 208 + (monY * 48) ) {
				velocity = new Vector(0,0);
				setY(204 + monY * 48);
				moving = false;
				moveU = false;	
			}
		}
		if (moveD == true) {
			if (getY() > 200 + (monY * 48) ) {
				velocity = new Vector(0,0);
				setY(204 + monY * 48);
				moving = false;
				moveD = false;	
			}
		}
	}
	
	private void checkSight() {
		int checkX = monX;
		int checkY = monY;
		
		if (moveDir == "Up") {
			while (checkY >= 0) {
				if (barrierChars.contains(GridElements[checkX][checkY]) == true)
					break;
				if (checkX == raccX && checkY == raccY)
					setChase();
				checkY -= 1;
			}
		}
		if (moveDir == "Down") {
			while (checkY <= 14) {
				if (barrierChars.contains(GridElements[checkX][checkY]) == true)
					break;
				if (checkX == raccX && checkY == raccY)
					setChase();
				checkY += 1;
			}
		}
		if (moveDir == "Left") {
			while (checkX >= 0) {
				if (barrierChars.contains(GridElements[checkX][checkY]) == true)
					break;
				if (checkX == raccX && checkY == raccY)
					setChase();
				checkX -= 1;
			}
		}
		if (moveDir == "Right") {
			while (checkX <= 29) {
				if (barrierChars.contains(GridElements[checkX][checkY]) == true)
					break;
				if (checkX == raccX && checkY == raccY)
					setChase();
				checkX += 1;
			}
		}
	}
	
	public void setChase() {
		chaseMode = true;
		chaseTime = 400;
		speedMod = .05f;
	}
	
	public void stopChase() {
		chaseMode = false;
		chaseTime = 0;
		speedMod = 0;
	}
	
	public void setBestDir(String dir) {
		bestDir = dir;
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
				if (chaseMode == false)
					choosePath();
				else {
					move(bestDir);
					changeFace(bestDir);
				}
			}
		}
	}
}
