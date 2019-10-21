package TrashStasher;

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
		
	}

	public void initTreasureLoc() {
	
		String levelText = getLevelText();
		
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
	
	public int moveToInv(Treasure t) {
		int newWeight = 0;
		if (invCnt < 8) {
			newWeight = t.weight;
			invTreasure[invCnt].copy(t);
			t.reset();
			invTreasure[invCnt].moveToInv(invCnt);
			invCnt += 1;
		}
		return newWeight;
	}
	
	public int popScore() {
			return invTreasure[invCnt - 1].score;
	}
	
	public int popInv() {
		if (invCnt > 0) {
			invCnt -= 1;
			int w = invTreasure[invCnt].weight;
			invTreasure[invCnt].reset();
			return w;
		}
		else
			return 0;
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < 8; i++) {
			if (invTreasure[i].inInventory == true) {
				invTreasure[i].render(g);
				g.drawString(invTreasure[i].name, 245 + (i * 150), 130);
				g.drawString(invTreasure[i].score + " pts", 245 + (i * 150), 150);
				g.drawString(invTreasure[i].weight + " wt", 328 + (i * 150), 150);
			}
		}
		for (int i = 0; i < 3; i++) {
			if (mapTreasure[i].inMap == true)
				mapTreasure[i].render(g);
		}
	}
	
	public void reset() {
		invCnt = 0;
		for (int i = 0; i < 8; i++) {
			invTreasure[i].reset();
		}
		for (int i = 0; i < 3; i++)
			mapTreasure[i].reset();
		addToMap();
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
