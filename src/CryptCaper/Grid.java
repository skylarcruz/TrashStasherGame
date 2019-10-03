package CryptCaper;

import org.newdawn.slick.Graphics;


public class Grid {
	
	GridSquare[][] GridElements = new GridSquare[30][15];
	String levelText = null;
	boolean built = false;
	
	public Grid(){
		for (int i = 0; i < 15; i++) {	
			for (int j = 0; j < 30; j++) {
				GridElements[j][i] = new GridSquare(24 + j*48, 204 + i*48);
			}
		}
	}
	
	public void buildGrid() {
		
		if (built == false) {
			built = true;

			levelText = getLevelText();
			int k = -1;
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 30; j++) {
					k += 1;
					if (levelText.charAt(k) == 'X') { 
						GridElements[j][i].addWall();
					}
					if (levelText.charAt(k) == 'M') { 
						GridElements[j][i].addMonsterHole();
					}
				}
			}
		}
	}
	
	public void destroyGrid() {
		
		if (built == true) {
			built = false;
			
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 30; j++) {
					GridElements[j][i].clear();
				}
			}
		}
	}
	
	public String getLevelText() {
		
		if (CryptCaperGame.currLevel == 1)
			return CryptCaperGame.Lvl1;
		else
			return CryptCaperGame.Lvl1;
		
	}
	
	public void render(Graphics g) {
		if (built == false)
			buildGrid();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {
				GridElements[j][i].render(g);
			}
		}
	}

}