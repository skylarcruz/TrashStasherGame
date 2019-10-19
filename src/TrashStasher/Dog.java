package TrashStasher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Animation;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Dog extends Entity {
	
	int waitTime;
	boolean waitMode = false;
	
	boolean active = false;
	
	int chaseTime;
	boolean chaseMode = false;
	int barkTimer;
	
	int startX[] = new int[10];
	int startY[] = new int[10];
	
	private Vector velocity;
	float speedMod = 0;
	
	private char[][] GridElements = new char[30][15];
	int dogX = 1;
	int dogY = 1;
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
	
	private Animation walkDown;
	private Animation walkUp;
	private Animation walkLeft;
	private Animation walkRight;
	
	public Dog(final float x, final float y) {
		super(24 + x * 48, 204 + y * 48);
		addImageWithBoundingBox(ResourceManager
				.getImage(TrashStasherGame.DOG_DOWNIMG_RSC));
		velocity = new Vector(0, 0);
		dogX = (int) x;
		dogY = (int) y;
		
		fillBarrierChars();
		
		setAllAnimations();
	}
	
	public void fillBarrierChars() {
		barrierChars.add('X');
		barrierChars.add('M');
		barrierChars.add('D');
		barrierChars.add('║');
		barrierChars.add('═');
		barrierChars.add('╚');
		barrierChars.add('╗');
		barrierChars.add('╔');
		barrierChars.add('╝');
		barrierChars.add('└');
		barrierChars.add('┐');
		barrierChars.add('┌');
		barrierChars.add('┘');
		barrierChars.add('╦');
		barrierChars.add('╩');
		barrierChars.add('╣');
		barrierChars.add('╠');
		barrierChars.add('^');
		barrierChars.add('v');
		barrierChars.add('<');
		barrierChars.add('>');
		barrierChars.add('╟');
		barrierChars.add('╢');
		barrierChars.add('╧');
		barrierChars.add('╤');
		barrierChars.add('1');
		barrierChars.add('2');
		barrierChars.add('3');
		barrierChars.add('4');
		barrierChars.add('5');
		barrierChars.add('6');
		barrierChars.add('7');
		barrierChars.add('8');
		barrierChars.add('9');
		barrierChars.add('a');
		barrierChars.add('┴');
		barrierChars.add('├');
		barrierChars.add('┤');
		barrierChars.add('┬');
		barrierChars.add('█');
	}
	
	public void setAllAnimations() {
		walkUp = new Animation(ResourceManager.getSpriteSheet
				(TrashStasherGame.DOG_UPWIMG_RSC, 25, 39), 100);
		walkUp.setLooping(true);
		walkDown = new Animation(ResourceManager.getSpriteSheet
				(TrashStasherGame.DOG_DOWNWIMG_RSC, 25, 39), 100);
		walkDown.setLooping(true);
		walkLeft = new Animation(ResourceManager.getSpriteSheet
				(TrashStasherGame.DOG_LEFTWIMG_RSC, 47, 29), 100);
		walkLeft.setLooping(true);
		walkRight = new Animation(ResourceManager.getSpriteSheet
				(TrashStasherGame.DOG_RIGHTWIMG_RSC, 47, 29), 100);
		walkRight.setLooping(true);
	}
	
	public void setStartLocation() {
		initDogPath();
		Random ran = new Random();
		int arrChoice = ran.nextInt(10);
		dogX = startX[arrChoice];
		dogY = startY[arrChoice];
		setPosition(24 + dogX * 48, 204 + dogY * 48);
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
		dogX = 35;
		dogY = 0;
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
			changeFace(choices[arrChoice]);
			move(choices[arrChoice]);
		}
		else {
			changeFace(choices[0]);
			moveDir = choices[0];
			move(choices[0]);
		}
		moving = true;
			
	}
	
	
	public boolean checkDir(String dir) {
		if (dir == "Up" && dogY > 0)
			return checkPath(dogX, dogY - 1);
		else if (dir == "Down" && dogY < 14)
			return checkPath(dogX, dogY + 1);
		else if (dir == "Left" && dogX > 0)
			return checkPath(dogX - 1, dogY);
		else if (dir == "Right" && dogX < 29)
			return checkPath(dogX + 1, dogY);
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
			dogY -= 1;
			moveU = true;
			setAnimation(walkUp);
		}
		else if (dir == "Down") {
			velocity = new Vector(0, .125f + speedMod);
			dogY += 1;
			moveD = true;
			setAnimation(walkDown);
		}
		else if (dir == "Left") {
			velocity = new Vector(-.125f - speedMod, 0);
			dogX -= 1;
			moveL = true;
			setAnimation(walkLeft);
		}
		else if (dir == "Right") {
			velocity = new Vector(.125f + speedMod, 0);
			dogX += 1;
			moveR = true;
			setAnimation(walkRight);
		}
		
		moving = true;
	}

	private void setAnimation(Animation a) {
		cleanSprite();
		addAnimation(a);
	}

	private void cleanSprite() {
		removeImage(ResourceManager.getImage(TrashStasherGame.DOG_UPIMG_RSC));
		removeImage(ResourceManager.getImage(TrashStasherGame.DOG_DOWNIMG_RSC));
		removeImage(ResourceManager.getImage(TrashStasherGame.DOG_LEFTIMG_RSC));
		removeImage(ResourceManager.getImage(TrashStasherGame.DOG_RIGHTIMG_RSC));
		removeAnimation(walkDown);
		removeAnimation(walkUp);
		removeAnimation(walkLeft);
		removeAnimation(walkRight);
	}

	private void changeFace(String dir) {
		cleanSprite();
		if (dir == "Up") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.DOG_UPIMG_RSC));
		else if (dir == "Down") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.DOG_DOWNIMG_RSC));
		else if (dir == "Left") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.DOG_LEFTIMG_RSC));
		else if (dir == "Right") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.DOG_RIGHTIMG_RSC));
	}
	
	public void initDogPath() {
		
		String levelText = getLevelText();
		
		int k = -1;
		int l = 0;
		for (int i = 0; i < 15; i++) {	
			for (int j = 0; j < 30; j++) {
				k += 1;
				GridElements[j][i] = levelText.charAt(k);
				if (levelText.charAt(k) == '╟' ||
					levelText.charAt(k) == '╢' ||
					levelText.charAt(k) == '╧' ||
					levelText.charAt(k) == '╤' ||
					levelText.charAt(k) == 'M') {
					startX[l] = j;
					startY[l] = i;
					l += 1;
				}
			}
		}
	}
	
	private void checkStop() {		
		if (moveL == true) {
			if (getX() < 28 + (dogX * 48)) {
				velocity = new Vector(0,0);
				setX(24 + dogX * 48);
				moving = false;
				moveL = false;
			}
		}
		if (moveR == true) {
			if (getX() > 20 + (dogX * 48) ) {
				velocity = new Vector(0,0);
				setX(24 + dogX * 48);
				moving = false;
				moveR = false;
			}
		}
		if (moveU == true) {
			if (getY() < 208 + (dogY * 48) ) {
				velocity = new Vector(0,0);
				setY(204 + dogY * 48);
				moving = false;
				moveU = false;	
			}
		}
		if (moveD == true) {
			if (getY() > 200 + (dogY * 48) ) {
				velocity = new Vector(0,0);
				setY(204 + dogY * 48);
				moving = false;
				moveD = false;	
			}
		}
	}
	
	private void checkSight() {
		int checkX = dogX;
		int checkY = dogY;
		
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
			if (chaseMode) {
				chaseTime -= 1;
				if (barkTimer <= 0) {
					ResourceManager.getSound(TrashStasherGame.DOG_BARKSND_RSC).play();
					barkTimer = 100;
				}
				else
					barkTimer -= 1;
			}
			if (chaseTime <= 0 && chaseMode)
				stopChase();
			
			translate(velocity.scale(delta));
			checkStop();
			if (moving == false) {
				if (chaseMode == false)
					choosePath();
				else {
					cleanSprite();
					changeFace(bestDir);
					move(bestDir);
				}
			}
		}
	}
	
	public String getLevelText() {
		if (TrashStasherGame.currLevel == 1)
			return TrashStasherGame.Lvl1;
		else if (TrashStasherGame.currLevel == 2)
			return TrashStasherGame.Lvl2;
		else
			return TrashStasherGame.Lvl3;
	}
}
