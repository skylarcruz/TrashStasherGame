package CryptCaper;

import java.util.Random;

import jig.Entity;
import jig.ResourceManager;

public class Treasure extends Entity {
	
	int id;
	int score;
	int weight;
	
	boolean inMap;
	boolean inInventory;
	
	public Treasure() {
		id = 0;
		score = 0;
		weight = 0;
		inMap = false;
		inInventory = false;
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
		this.addImageWithBoundingBox(ResourceManager
				.getImage(image));
		this.id = i;
		this.score = s;
		this.weight = w;
		this.inMap = true;
	}
	
	public void copy(Treasure t) {
		this.id = t.id;
		this.score = t.score;
		this.weight = t.weight;
	}
	
	public void moveToInv(int x) {
		switch(this.id) {
		case 1:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		case 2:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		case 3:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		case 4:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		case 5:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		case 6:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		case 7:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		case 8:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		case 9:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		case 10:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
			break;
		default:
			setInv(CryptCaperGame.TREASURE_COINBIGIMG_RSC, x);
		}
	}
	
	private void setInv(String image, int x) {
		this.inMap = false;
		this.inInventory = true;
		this.setPosition(311 + (x * 150), 75);
		this.addImage(ResourceManager
				.getImage(image));
	}
	
	
	
	public void reset() {
		this.setPosition(24 + 35 * 48, 204 + 0 * 48);
		this.id = 0;
		this.score = 0;
		this.weight = 0;
		this.removeImage(ResourceManager
				.getImage(CryptCaperGame.TREASURE_COINIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(CryptCaperGame.TREASURE_COINBIGIMG_RSC));
		this.inMap = false;
		this.inInventory = false;
	}

}
