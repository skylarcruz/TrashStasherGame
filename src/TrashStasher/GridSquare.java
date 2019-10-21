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
	public int xVal;
	public int yVal;

	public GridSquare(final float x, final float y, int gridX, int gridY) {
		super(x, y);
		xVal = gridX;
		yVal = gridY;
	}
	
	public void addMonsterHole(char k) {
		if (k == '╟') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_HOLERIMG_RSC));
		}
		if (k == '╢') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_HOLELIMG_RSC));
		}
		if (k == '╧') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_HOLEUIMG_RSC));
		}
		if (k == '╤') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_HOLEDIMG_RSC));
		}
		this.val = "M";
	}
	
	public void addDropBox() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.DROP_BOXIMG_RSC));
	}
	
	// ==================
	// Wall Add Functions
	// ==================
	
	// Tubes
	public void addTubeV() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TUBEVIMG_RSC));
		this.val = "X";
	}
	public void addTubeH() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TUBEHIMG_RSC));
		this.val = "X";
	}
	
	public void addSquare() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_SQUAREIMG_RSC));
		this.val = "X";
	}
	
	// Elbows
	public void addElbowDL() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EDLIMG_RSC));
		this.val = "X";
	}
	public void addElbowDR() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EDRIMG_RSC));
		this.val = "X";
	}
	public void addElbowUL() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EULIMG_RSC));
	}
	public void addElbowUR() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EURIMG_RSC));
		this.val = "X";
	}
	
	// Corners
	public void addCornerDL() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_CDLIMG_RSC));
		this.val = "X";
	}
	public void addCornerDR() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_CDRIMG_RSC));
		this.val = "X";
	}
	public void addCornerUL() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_CULIMG_RSC));
		this.val = "X";
	}
	public void addCornerUR() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_CURIMG_RSC));
		this.val = "X";
	}
	
	// T Intersections
	public void addTdown() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSUIMG_RSC));
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TDIMG_RSC));
		this.val = "X";
	}
	public void addTleft() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSRIMG_RSC));
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TLIMG_RSC));
		this.val = "X";
	}
	public void addTright() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSLIMG_RSC));
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TRIMG_RSC));
		this.val = "X";
	}
	public void addTup() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSDIMG_RSC));
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TUIMG_RSC));
		this.val = "X";
	}
	public void addSideD() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSDIMG_RSC));
		this.val = "X";
	}
	public void addSideL() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSLIMG_RSC));
		this.val = "X";
	}
	public void addSideR() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSRIMG_RSC));
		this.val = "X";
	}
	public void addSideU() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSUIMG_RSC));
		this.val = "X";
	}
	
	// End Pieces
	public void addEndD() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EndDIMG_RSC));
		this.val = "X";
	}
	public void addEndL() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EndLIMG_RSC));
		this.val = "X";
	}
	public void addEndR() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EndRIMG_RSC));
		this.val = "X";
	}
	public void addEndU() {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EndUIMG_RSC));
		this.val = "X";
	}
	
	// Special Cases
	public void addSpecial(char k) {
		this.addImage(ResourceManager
				.getImage(TrashStasherGame.WALL_BASEIMG_RSC));
		if (k == '1') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_TDIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFULIMG_RSC));
		}
		if (k == '2') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_OSLIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFURIMG_RSC));
		}
		if (k == '3') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_OSDIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFULIMG_RSC));
		}
		if (k == '4') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_TDIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFURIMG_RSC));
		}
		if (k == '5') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_OSDIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFURIMG_RSC));
		}
		if (k == '6') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_OSUIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFDLIMG_RSC));
		}
		if (k == '7') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_OSUIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFDRIMG_RSC));
		}
		if (k == '8') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_OSRIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFULIMG_RSC));
		}
		if (k == '9') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_OSRIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFDLIMG_RSC));
		}
		if (k == 'a') {
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_OSLIMG_RSC));
			this.addImage(ResourceManager
					.getImage(TrashStasherGame.WALL_EFDRIMG_RSC));
		}
		this.val = "X";
	}
	
	// ==================
	
	public void clear() {
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.DROP_BOXIMG_RSC));
		
		// Remove Walls
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_BASEIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_SQUAREIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_CDLIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_CDRIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_CULIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_CURIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EDLIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EDRIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EULIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EURIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EFDLIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EFDRIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EFULIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EFURIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EndDIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EndLIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EndRIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_EndUIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSDIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSLIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSRIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_OSUIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TDIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TLIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TRIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TUIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TUBEHIMG_RSC));
		this.removeImage(ResourceManager
				.getImage(TrashStasherGame.WALL_TUBEVIMG_RSC));
		// =================
		
		this.val = null;
	}
}
