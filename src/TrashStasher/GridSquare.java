package TrashStasher;

import jig.Entity;
//import jig.ResourceManager;
import jig.ResourceManager;

/**
 * The Ball class is an Entity that has a velocity (since it's moving). When
 * the Ball bounces off a surface, it temporarily displays a image with
 * cracks for a nice visual effect.
 * 
 */
 class GridSquare extends Entity {
	 
	public String val = null;

	public GridSquare(final float x, final float y) {
		super(x, y);
	}
	
	public void addWall() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_WALLIMG_RSC));
		this.val = "X";
	}
	
	public void addMonsterHole() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_WALLIMG_RSC));
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.MON_HOLEIMG_RSC));
		this.val = "M";
	}
	
	public void addDropBox() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.DROP_BOXIMG_RSC));
	}
	
	public void clear() {
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_WALLIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.MON_HOLEIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.DROP_BOXIMG_RSC));
		this.val = null;
	}
}
