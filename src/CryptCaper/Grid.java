package CryptCaper;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;


public class Grid {
	
	GridSquare[][] GridElements = new GridSquare[30][15];
	String levelText = null;
	boolean built = false;
	
	public List<GridSquare> dropBoxes = new ArrayList<GridSquare>(); 
	
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
					if (levelText.charAt(k) == 'D') { 
						GridElements[j][i].addDropBox();
						dropBoxes.add(GridElements[j][i]);
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
			dropBoxes.clear();
		}
	}
	
	public boolean checkForDropBox(int x, int y) {
		boolean nearBox = false;
		for (int i = y - 1; i <= y + 1; i++) {
			for (int j = x - 1; j <= x + 1; j ++)
				if (dropBoxes.contains(GridElements[j][i]))
					nearBox = true;
		}
		return nearBox;
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