package TrashStasher;

import java.util.Random;

import jig.Entity;
import jig.ResourceManager;

public class Power extends Entity {
	
	String name;
	String info1;
	String info2;
	String info3;
	
	boolean inMap;
	boolean inInventory;
	
	public Power() {
		inMap = false;;
		inInventory = false;
	}
	
	public void setNewPower(int x, int y) {
		
		this.setPosition(24 + x * 48, 204 + y * 48);
		this.inMap = true;
		this.name = " Dig";
		this.info1 = "Move past one";
		this.info2 = "wall space";
		this.info3 = null;
		this.addImageWithBoundingBox(ResourceManager
				.getImage(TrashStasherGame.POWER_DIGIMG_RSC));
		
	}
	
	public void setInv() {
		this.inMap = false;
		this.inInventory = true;
		this.setPosition(40, 125);
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.POWER_DIGIMG_RSC));
	}
	
	public void reset() {
		this.setPosition(24 + 35 * 48, 204 + 0 * 48);
		this.name = null;
		this.info1 = null;
		this.info2 = null;
		this.info3 = null;
		//this.removeImage(ResourceManager
		//		.getImage(TrashStasherGame.TREASURE_COINIMG_RSC));
		//this.removeImage(ResourceManager
		//		.getImage(TrashStasherGame.TREASURE_COINBIGIMG_RSC));
		this.inMap = false;
		this.inInventory = false;
	}

}
