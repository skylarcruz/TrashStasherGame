package CryptCaper;

import java.util.Random;

import org.newdawn.slick.Graphics;

public class TreasureTracker {
	
	int startX[] = new int[10];
	int startY[] = new int[10];
	
	Treasure[] iTreasure;
	Treasure[] mTreasure;
	
	public TreasureTracker() {
		iTreasure = new Treasure[8];
		for (int i = 0; i < 8; i ++)
			iTreasure[i] = new Treasure();
		mTreasure = new Treasure[3];
		for (int i = 0; i < 3; i ++)
			mTreasure[i] = new Treasure();
		
		initTreasureLoc();
	}

	public void initTreasureLoc() {
	
		String levelText = CryptCaperGame.Lvl1;
		
		int k = -1;
		int l = 0;
		for (int i = 0; i < 15; i++) {	
			for (int j = 0; j < 30; j++) {
				k += 1;
				if (levelText.charAt(k) == 'T') {
					startX[l] = j;
					startY[l] = i;
					l += 1;
				}
			}
		}
	}
	
	public void addToMap() {
		for (int i = 0; i < 3; i++) {
			if (mTreasure[i].inMap == false) {
				placeOnMap(mTreasure[i]);
				break;
			}
		}
	}
	
	private void placeOnMap(Treasure t) {
		
		Random ran = new Random();
		int i = ran.nextInt(10);
		
		t.setNewTreasure(startX[i], startY[i]);
		
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < 8; i++) {
			if (iTreasure[i].inInventory == true)
				iTreasure[i].render(g);
		}
		for (int i = 0; i < 3; i++) {
			if (mTreasure[i].inMap == true)
				mTreasure[i].render(g);
		}
	}
	
	public void reset() {
		for (int i = 0; i < 8; i++)
			iTreasure[i].reset();
		for (int i = 0; i < 3; i++)
			mTreasure[i].reset();
		addToMap();
	}
	
}
