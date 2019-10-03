package CryptCaper;

import java.util.Random;

import org.newdawn.slick.Graphics;

public class TreasureTracker {
	
	int startX[] = new int[10];
	int startY[] = new int[10];
	
	Treasure[] invTreasure;
	int invCnt = 0;
	Treasure[] mapTreasure;
	
	public TreasureTracker() {
		invTreasure = new Treasure[8];
		for (int i = 0; i < 8; i ++)
			invTreasure[i] = new Treasure();
		mapTreasure = new Treasure[3];
		for (int i = 0; i < 3; i ++)
			mapTreasure[i] = new Treasure();
		
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
			if (mapTreasure[i].inMap == false) {
				placeOnMap(mapTreasure[i]);
				break;
			}
		}
	}
	
	private void placeOnMap(Treasure t) {
		
		Random ran = new Random();
		int i = ran.nextInt(10);
		
		t.setNewTreasure(startX[i], startY[i]);
		
	}
	
	public void moveToInv(Treasure t) {
		if (invCnt < 8) {
			invTreasure[invCnt].copy(t);
			t.reset();
			invTreasure[invCnt].moveToInv(invCnt);
			invCnt += 1;
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < 8; i++) {
			if (invTreasure[i].inInventory == true)
				invTreasure[i].render(g);
				g.drawString(invTreasure[i].score + " g", 250 + (i * 150), 130);
				g.drawString(invTreasure[i].weight + " lbs", 250 + (i * 150), 145);
		}
		for (int i = 0; i < 3; i++) {
			if (mapTreasure[i].inMap == true)
				mapTreasure[i].render(g);
		}
	}
	
	public void reset() {
		for (int i = 0; i < 8; i++) {
			invTreasure[i].reset();
		}
		for (int i = 0; i < 3; i++)
			mapTreasure[i].reset();
		addToMap();
		invCnt = 0;
	}
	
}
