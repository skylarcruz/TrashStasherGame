package CryptCaper;


import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Explorer extends Entity {
	 
	private Vector velocity;
	private char[][] GridElements = new char[30][15];
	int expX = 1;
	int expY = 1;
	int startX;
	int startY;
	public boolean inputAccept = true;
	boolean moveH = false;
	boolean moveV = false;
	public float speedMod = 0;

	public Explorer(final float x, final float y) {				
		super(24 + x * 48, 204 + y * 48);
		addImageWithBoundingBox(ResourceManager
				.getImage(CryptCaperGame.EXP_DOWNIMG_RSC));
		velocity = new Vector(0, 0);
		expX = (int) x;
		expY = (int) y;
	}
	
	public void setVelocity(final Vector v) {
		velocity = v;
	}

	public Vector getVelocity() {
		return velocity;
	}
	
	public void reset() {
		initExpPath();
		velocity = new Vector(0, 0);
		inputAccept = true;
		moveH = false;
		moveV = false;
		expX = startX;
		expY = startY;
		speedMod = 0;
		setPosition(24 + startX * 48, 204 + startY * 48);
	}
	
	public boolean checkDir(String dir) {
		if (dir == "Up") {
			changeFace("Up");
			return checkPath(expX, expY - 1);
		}
		else if (dir == "Down") {
			changeFace("Down");
			return checkPath(expX, expY + 1);
		}
		else if (dir == "Left") {
			changeFace("Left");
			return checkPath(expX - 1, expY);
		}
		else if (dir == "Right") {
			changeFace("Right");
			return checkPath(expX + 1, expY);
		}
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
			velocity = new Vector(0, -.3f - speedMod);
			expY -= 1;
			moveV = true;
		}
		else if (dir == "Down") {
			velocity = new Vector(0, .3f + speedMod);
			expY += 1;
			moveV = true;
		}
		else if (dir == "Left") {
			velocity = new Vector(-.3f - speedMod, 0);
			expX -= 1;
			moveH = true;
		}
		else if (dir == "Right") {
			velocity = new Vector(.3f + speedMod, 0);
			expX += 1;
			moveH = true;
		}
	}
	
	private void changeFace(String dir) {
		removeImage(ResourceManager.getImage(CryptCaperGame.EXP_UPIMG_RSC));
		removeImage(ResourceManager.getImage(CryptCaperGame.EXP_DOWNIMG_RSC));
		removeImage(ResourceManager.getImage(CryptCaperGame.EXP_LEFTIMG_RSC));
		removeImage(ResourceManager.getImage(CryptCaperGame.EXP_RIGHTIMG_RSC));
		if (dir == "Up") 
			addImageWithBoundingBox(ResourceManager
					.getImage(CryptCaperGame.EXP_UPIMG_RSC));
		else if (dir == "Down") 
			addImageWithBoundingBox(ResourceManager
					.getImage(CryptCaperGame.EXP_DOWNIMG_RSC));
		else if (dir == "Left") 
			addImageWithBoundingBox(ResourceManager
					.getImage(CryptCaperGame.EXP_LEFTIMG_RSC));
		else if (dir == "Right") 
			addImageWithBoundingBox(ResourceManager
					.getImage(CryptCaperGame.EXP_RIGHTIMG_RSC));
	}
	
	public void initExpPath() {
		
		String levelText = CryptCaperGame.Lvl1;
		
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
		if (moveV == true) {
			if (getY() > 198 + (expY * 48) && getY() < 210 + (expY * 48) ) {
				velocity = new Vector(0,0);
				setY(204 + expY * 48);
				inputAccept = true;
				moveV = false;	
			}
		}
		if (moveH == true) {
			if (getX() > 18 + (expX * 48) && getX() < 30 + (expX * 48) ) {
				velocity = new Vector(0,0);
				setX(24 + expX * 48);
				inputAccept = true;
				moveH = false;
			}
		}
	}
	
	public void update(final int delta) {
		translate(velocity.scale(delta));
		checkStop();
	}
}
