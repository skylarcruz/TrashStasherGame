package CryptCaper;


import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Explorer extends Entity {
	 
	private Vector velocity;
	private char[][] GridElements = new char[30][15];
	int expX = 1;
	int expY = 1;

	public Explorer(final float x, final float y) {				
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(CryptCaperGame.EXP_EXPIMG_RSC));
		velocity = new Vector(0, 0);
	}
	
	public boolean checkDir(String dir) {
		if (dir == "Up")
			return checkPath(expX, expY - 1);
		else if (dir == "Down")
			return checkPath(expX, expY + 1);
		else if (dir == "Left")
			return checkPath(expX - 1, expY);
		else if (dir == "Right")
			return checkPath(expX + 1, expY);
		else
			return false;
	}
	
	private boolean checkPath(int x, int y) {
		if (GridElements[x][y] == 'X')
			return false;
		else
			return true;
	}
	
	public void initExpPath() {
		
		String levelText = CryptCaperGame.Lvl1;
		
		int k = -1;
		for (int i = 0; i < 15; i++) {	
			for (int j = 0; j < 30; j++) {
				k += 1;
				GridElements[j][i] = levelText.charAt(k);
			}
		}
		
	}
}
