package CryptCaper;

import jig.Entity;
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
}
