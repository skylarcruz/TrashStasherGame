package TrashStasher;


import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Raccoon extends Entity {
	 
	private Vector velocity;
	private char[][] GridElements = new char[30][15];
	int raccX = 1;
	int raccY = 1;
	int startX;
	int startY;
	int raccType = TrashStasherGame.raccNum;
	
	public boolean inputAccept = true;
	
	boolean moveU = false;
	boolean moveD = false;
	boolean moveL = false;
	boolean moveR = false;
	String faceDir;
	
	public float speedMod = 0;
	private boolean speedy = false;
	private int speedyTime;
	
	public List<Character> barrierChars = new ArrayList<Character>(); 
	
	private Animation walkDown;
	private Animation walkUp;
	private Animation walkLeft;
	private Animation walkRight;

	public Raccoon(final float x, final float y) {				
		super(24 + x * 48, 204 + y * 48);
		addImageWithBoundingBox(ResourceManager
				.getImage(TrashStasherGame.RACC_DOWNIMG_RSC 
						+ Integer.toString(raccType) + ".png"));
	    
		velocity = new Vector(0, 0);
		raccX = (int) x;
		raccY = (int) y;
		
		barrierChars.add('X');
		barrierChars.add('M');
		barrierChars.add('D');
		
		setAllAnimations();
	}
	
	public void setVelocity(final Vector v) {
		velocity = v;
	}

	public Vector getVelocity() {
		return velocity;
	}
	
	public int getGridX() {
		return raccX;
	}
	
	public int getGridY() {
		return raccY;
	}
	
	public void reset() {
		initRaccPath();
		cleanSprite();
		raccType = TrashStasherGame.raccNum;
		setAllAnimations();
		changeFace("Down");
		velocity = new Vector(0, 0);
		inputAccept = true;
		moveU = false;
		moveD = false;
		moveL = false;
		moveR = false;
		raccX = startX;
		raccY = startY;
		speedMod = 0;
		setPosition(24 + startX * 48, 204 + startY * 48);
	}
	
	public void setAllAnimations() {
		walkUp = new Animation(ResourceManager.getSpriteSheet
				(TrashStasherGame.RACC_UPWIMG_RSC
						+ Integer.toString(raccType) + ".png", 19, 41), 100);
		walkUp.setLooping(true);
		walkDown = new Animation(ResourceManager.getSpriteSheet
				(TrashStasherGame.RACC_DOWNWIMG_RSC
						+ Integer.toString(raccType) + ".png", 19, 41), 100);
		walkDown.setLooping(true);
		walkLeft = new Animation(ResourceManager.getSpriteSheet
				(TrashStasherGame.RACC_LEFTWIMG_RSC
						+ Integer.toString(raccType) + ".png", 39, 29), 100);
		walkLeft.setLooping(true);
		walkRight = new Animation(ResourceManager.getSpriteSheet
				(TrashStasherGame.RACC_RIGHTWIMG_RSC 
						+ Integer.toString(raccType) + ".png", 39, 29), 100);
		walkRight.setLooping(true);
	}
	
	public boolean checkDir(String dir) {
		faceDir = dir;
		if (dir == "Up" && raccY > 0) {
			changeFace("Up");
			return checkPath(raccX, raccY - 1);
		}
		else if (dir == "Down" && raccY < 14) {
			changeFace("Down");
			return checkPath(raccX, raccY + 1);
		}
		else if (dir == "Left" && raccX > 0) {
			changeFace("Left");
			return checkPath(raccX - 1, raccY);
		}
		else if (dir == "Right" && raccX < 29) {
			changeFace("Right");
			return checkPath(raccX + 1, raccY);
		}
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
		
		float speedModf = speedMod;
		if (speedMod < -.2f) {
			speedModf = -.2f;
		}
		
		if (dir == "Up") {
			if (speedy == false)
				velocity = new Vector(0, -.275f - speedModf);
			else
				velocity = new Vector(0, -.300f);
			raccY -= 1;
			moveU = true;
			setAnimation(walkUp);
		}
		else if (dir == "Down") {
			if (speedy == false)
				velocity = new Vector(0, .275f + speedModf);
			else
				velocity = new Vector(0, .300f);
			raccY += 1;
			moveD = true;
			setAnimation(walkDown);
		}
		else if (dir == "Left") {
			if (speedy == false)
				velocity = new Vector(-.275f - speedModf, 0);
			else 
				velocity = new Vector(-.300f, 0);
			raccX -= 1;
			moveL = true;
			setAnimation(walkLeft);
		}
		else if (dir == "Right") {
			if (speedy == false)
				velocity = new Vector(.275f + speedModf, 0);
			else
				velocity = new Vector(.300f, 0);
			raccX += 1;
			moveR = true;
			setAnimation(walkRight);
		}
	}
	
	private void setAnimation(Animation a) {
		cleanSprite();
		addAnimation(a);
	}
	
	private void cleanSprite() {
		removeImage(ResourceManager.getImage(TrashStasherGame.RACC_UPIMG_RSC 
				+ Integer.toString(raccType) + ".png"));
		removeImage(ResourceManager.getImage(TrashStasherGame.RACC_DOWNIMG_RSC 
				+ Integer.toString(raccType) + ".png"));
		removeImage(ResourceManager.getImage(TrashStasherGame.RACC_LEFTIMG_RSC 
				+ Integer.toString(raccType) + ".png"));
		removeImage(ResourceManager.getImage(TrashStasherGame.RACC_RIGHTIMG_RSC 
				+ Integer.toString(raccType) + ".png"));
		removeAnimation(walkDown);
		removeAnimation(walkUp);
		removeAnimation(walkLeft);
		removeAnimation(walkRight);
	}
	
	private void changeFace(String dir) {
		cleanSprite();
		if (dir == "Up") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.RACC_UPIMG_RSC 
							+ Integer.toString(raccType) + ".png"));
		else if (dir == "Down") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.RACC_DOWNIMG_RSC 
							+ Integer.toString(raccType) + ".png"));
		else if (dir == "Left") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.RACC_LEFTIMG_RSC 
							+ Integer.toString(raccType) + ".png"));
		else if (dir == "Right") 
			addImageWithBoundingBox(ResourceManager
					.getImage(TrashStasherGame.RACC_RIGHTIMG_RSC 
							 + Integer.toString(raccType) + ".png"));
	}
	
	public void changeSpeed(float s) {
		speedMod += s;
	}
	
	public void initRaccPath() {
		
		String levelText = TrashStasherGame.Lvl1;
		
		int k = -1;
		for (int i = 0; i < 15; i++) {	
			for (int j = 0; j < 30; j++) {
				k += 1;
				GridElements[j][i] = levelText.charAt(k);
				if (levelText.charAt(k) == 'S') {
					startX = j;
					startY = i;
				}
			}
		}
	}
	
	private void checkStop() {
		if (moveL == true) {
			if (getX() < 24 + (raccX * 48) ) {
				velocity = new Vector(0,0);
				setX(24 + raccX * 48);
				inputAccept = true;
				moveL = false;
				changeFace("Left");
			}
		}
		if (moveR == true) {
			if (getX() > 24 + (raccX * 48)) {
				velocity = new Vector(0,0);
				setX(24 + raccX * 48);
				inputAccept = true;
				moveR = false;
				changeFace("Right");
			}
		}
		if (moveU == true) {
			if (getY() < 204 + (raccY * 48) ) {
				velocity = new Vector(0,0);
				setY(204 + raccY * 48);
				inputAccept = true;
				moveU = false;
				changeFace("Up");
			}
		}
		if (moveD == true) {
			if (getY() > 204 + (raccY * 48)) {
				velocity = new Vector(0,0);
				setY(204 + raccY * 48);
				inputAccept = true;
				moveD = false;
				changeFace("Down");
			}
		}
	}
	
	public boolean dig() {
		if (faceDir == "Up") {
			if (raccY > 1 && checkPath(raccX, raccY - 1) == false
					&& checkPath(raccX, raccY - 2) == true) {
				digTo(raccX, raccY - 2, faceDir);
				return true;
			}
		}
		if (faceDir == "Down") {
			if (raccY < 13 && checkPath(raccX, raccY + 1) == false
					&& checkPath(raccX, raccY + 2) == true) {
				digTo(raccX, raccY + 2, faceDir);
				return true;
			}
		}
		if (faceDir == "Left") {
			if (raccX > 1 && checkPath(raccX - 1, raccY) == false
					&& checkPath(raccX - 2, raccY) == true) {
				digTo(raccX - 2, raccY, faceDir);
				return true;
			}
		}
		if (faceDir == "Right") {
			if (raccX < 28 && checkPath(raccX + 1, raccY) == false
					&& checkPath(raccX + 2, raccY) == true) {
				digTo(raccX + 2, raccY, faceDir);
				return true;
			}
		}
		
		return false;
	}
	
	private void digTo(int x, int y, String f) {
		velocity = new Vector(0,0);
		setX(24 + x * 48);
		setY(204 + y * 48);
		raccX = x;
		raccY = y;
		inputAccept = true;
		moveU = false;
		moveD = false;
		moveL = false;
		moveR = false;
		changeFace(f);
	}
	
	public void speedUp() {
		speedy = true;
		speedyTime = 500;
	}
	
	public void update(final int delta) {
		translate(velocity.scale(delta));
		checkStop();
		if (speedyTime > 0) {
			speedyTime -= 1;
		}
		else
			speedy = false;
	}
}
