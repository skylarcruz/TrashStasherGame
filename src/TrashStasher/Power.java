package TrashStasher;

import java.util.Random;

import jig.Entity;
import jig.ResourceManager;

public class Power extends Entity {
	
	Random ran = new Random();
	
	String name;
	String info1;
	String info2;
	String info3;
	
	boolean inMap;
	boolean inInventory;
	
	public Power() {
		inMap = false;;
		inInventory = false;
		
		// Set boundaries and remove image
		this.addImageWithBoundingBox(ResourceManager
				.getImage(TrashStasherGame.POWER_DIGIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.POWER_DIGIMG_RSC));
		
	}
	
	public void setNewPower(int x, int y) {
		
		int ranPow = ran.nextInt(3);
		
		this.setPosition(24 + x * 48, 204 + y * 48);
		this.inMap = true;
		
		if (ranPow == 0) {
			this.name = " Dig";
			this.info1 = "Move past one";
			this.info2 = "wall space";
			this.info3 = null;
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.POWER_DIGIMG_RSC));
		}
		if (ranPow == 1) {
			this.name = "Speed";
			this.info1 = "Move at faster";
			this.info2 = "speed even with";
			this.info3 = "lots of trash";
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.POWER_SPDIMG_RSC));
		}
		if (ranPow == 2) {
			this.name = "Pause";
			this.info1 = "Freeze All Dogs";
			this.info2 = "in place for";
			this.info3 = "a short time";
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.POWER_PAUSEIMG_RSC));
		}
		
	}
	
	public void setInv() {
		this.inMap = false;
		this.inInventory = true;
		this.setPosition(40, 125);
		if (this.name == " Dig")
			this.addImage(ResourceManager
				.getImage(TrashStasherGame.POWER_DIGIMG_RSC));
		if (this.name == "Speed")
			this.addImage(ResourceManager
				.getImage(TrashStasherGame.POWER_SPDIMG_RSC));
		if (this.name == "Pause")
			this.addImage(ResourceManager
				.getImage(TrashStasherGame.POWER_PAUSEIMG_RSC));
	}
	
	public void reset() {
		this.setPosition(24 + 35 * 48, 204 + 0 * 48);
		this.name = null;
		this.info1 = null;
		this.info2 = null;
		this.info3 = null;
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.POWER_DIGIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.POWER_SPDIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.POWER_PAUSEIMG_RSC));
		this.inMap = false;
		this.inInventory = false;
	}

}
