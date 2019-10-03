package CryptCaper;

import java.util.Random;

import jig.Entity;
import jig.ResourceManager;

public class Treasure extends Entity {
	
	int id;
	int score;
	int weight;
	
	boolean inMap = false;
	boolean inInventory = false;
	
	public Treasure() {
		id = 0;
		score = 0;
		weight = 0;
	}
	
	public void setNewTreasure(int x, int y) {
		
		Random ran = new Random();
		int arrChoice = ran.nextInt(10);
		
		this.setPosition(24 + x * 48, 204 + y * 48);
		
		switch (arrChoice) {
			case 0:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 1, 20, 5);
				break;
			case 1:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 2, 40, 10);
				break;
			case 2:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 3, 75, 15);
				break;
			case 3:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 4, 125, 20);
				break;
			case 4:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 5, 200, 25);
				break;
			case 5:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 6, 300, 30);
				break;
			case 6:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 7, 425, 35);
				break;
			case 7:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 8, 575, 40);
				break;
			case 8:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 9, 750, 45);
				break;
			case 9:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 10, 1000, 50);
				break;
			default:
				setTreasure(CryptCaperGame.TREASURE_COINIMG_RSC, 1, 10, 5);
		}
		
	}
	
	private void setTreasure(String image, int i, int s, int w) {
		this.addImage(ResourceManager
				.getImage(image));
		this.id = i;
		this.score = s;
		this.weight = w;
		this.inMap = true;
	}
	
	public void reset() {
		this.id = 0;
		this.score = 0;
		this.weight = 0;
		this.removeImage(ResourceManager
				.getImage(CryptCaperGame.TREASURE_COINIMG_RSC));
		this.inMap = false;
		this.inInventory = false;
	}

}
