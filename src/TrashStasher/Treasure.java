package TrashStasher;

import java.util.Random;

import jig.Entity;
import jig.ResourceManager;

public class Treasure extends Entity {
	
	int id;
	int score;
	int weight;
	String name;
	
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
				setTreasure(TrashStasherGame.TRASH_PAPERIMG_RSC, 1, 20, 5);
				break;
			case 1:
				setTreasure(TrashStasherGame.TRASH_FORKIMG_RSC, 2, 40, 10);
				break;
			case 2:
				setTreasure(TrashStasherGame.TRASH_BOTTLEIMG_RSC, 3, 75, 15);
				break;
			case 3:
				setTreasure(TrashStasherGame.TRASH_CANIMG_RSC, 4, 125, 20);
				break;
			case 4:
				setTreasure(TrashStasherGame.TRASH_FISHBONEIMG_RSC, 5, 200, 25);
				break;
			case 5:
				setTreasure(TrashStasherGame.TRASH_APPLEIMG_RSC, 6, 300, 30);
				break;
			case 6:
				setTreasure(TrashStasherGame.TRASH_FISHIMG_RSC, 7, 425, 35);
				break;
			case 7:
				setTreasure(TrashStasherGame.TRASH_FRIESIMG_RSC, 8, 575, 40);
				break;
			case 8:
				setTreasure(TrashStasherGame.TRASH_DONUTIMG_RSC, 9, 750, 45);
				break;
			case 9:
				setTreasure(TrashStasherGame.TRASH_PIZZAIMG_RSC, 10, 1000, 50);
				break;
			default:
				setTreasure(TrashStasherGame.TRASH_PAPERIMG_RSC, 1, 10, 5);
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
			setInv(TrashStasherGame.TRASH_B_PAPERIMG_RSC, x, "     Paper     ");
			break;
		case 2:
			setInv(TrashStasherGame.TRASH_B_FORKIMG_RSC, x, "     Fork     ");
			break;
		case 3:
			setInv(TrashStasherGame.TRASH_B_BOTTLEIMG_RSC, x, "    Bottle    ");
			break;
		case 4:
			setInv(TrashStasherGame.TRASH_B_CANIMG_RSC, x, "      Can      ");
			break;
		case 5:
			setInv(TrashStasherGame.TRASH_B_FISHBONEIMG_RSC, x, "   Fish Bone   ");
			break;
		case 6:
			setInv(TrashStasherGame.TRASH_B_APPLEIMG_RSC, x, "     Apple     ");
			break;
		case 7:
			setInv(TrashStasherGame.TRASH_B_FISHIMG_RSC, x, "     Fish     ");
			break;
		case 8:
			setInv(TrashStasherGame.TRASH_B_FRIESIMG_RSC, x, "     Fries     ");
			break;
		case 9:
			setInv(TrashStasherGame.TRASH_B_DONUTIMG_RSC, x, "     Donut     ");
			break;
		case 10:
			setInv(TrashStasherGame.TRASH_B_PIZZAIMG_RSC, x, "     Pizza     ");
			break;
		default:
			setInv(TrashStasherGame.TRASH_B_PAPERIMG_RSC, x, "     Paper     ");
		}
	}
	
	private void setInv(String image, int x, String n) {
		this.inMap = false;
		this.inInventory = true;
		this.name = n;
		this.setPosition(311 + (x * 150), 75);
		this.addImage(ResourceManager
				.getImage(image));
	}
	
	
	
	public void reset() {
		this.setPosition(24 + 35 * 48, 204 + 0 * 48);
		this.id = 0;
		this.score = 0;
		this.weight = 0;
		resetImage();
		this.inMap = false;
		this.inInventory = false;
		this.name = null;
	}
	
	public void resetImage() {
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_PAPERIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_PAPERIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_FORKIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_FORKIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_BOTTLEIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_BOTTLEIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_CANIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_CANIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_FISHBONEIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_FISHBONEIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_APPLEIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_APPLEIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_FISHIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_FISHIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_FRIESIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_FRIESIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_DONUTIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_DONUTIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_PIZZAIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.TRASH_B_PIZZAIMG_RSC));
	}

}
