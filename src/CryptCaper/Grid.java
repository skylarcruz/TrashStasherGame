package CryptCaper;

import org.newdawn.slick.Graphics;

import jig.Entity;
import jig.ResourceManager;


public class Grid extends Entity{
	
	GridSquare[][] GridElements = new GridSquare[30][15];
	String levelText = null;
	
	public Grid(){
		for (int i = 0; i < 15; i++) {	
			for (int j = 0; j < 30; j++) {
				GridElements[j][i] = new GridSquare(24 + j*48, 204 + i*48);
			}
		}
	}
	
	public void buildGrid() {
		//Read Text File
		levelText = getLevelText();
		
		int k = -1;
		for (int i = 0; i < 15; i++) {
			//Read Across Column
			
			for (int j = 0; j < 30; j++) {
				//Read Across Row
				
				k += 1;
				GridElements[j][i].val = levelText.valueOf(k);
				if (levelText.charAt(k) == 'X') { 
				GridElements[j][i].addImage(ResourceManager
						.getImage(CryptCaperGame.WALL_WALLIMG_RSC));
				}
				
				
			}
		}
	}
	
	public void destroyGrid() {
		levelText = getLevelText();
		
		int k = -1;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {
				k += 1;
				GridElements[j][i].val = null;
				GridElements[j][i].removeImage(ResourceManager
						.getImage(CryptCaperGame.WALL_WALLIMG_RSC));
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
		int k = -1;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {
				k += 1;
				if (levelText.charAt(k) == 'X')
					GridElements[j][i].render(g);
			}
		}
	}

}
