package TrashStasher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;


public class Grid {
	
	GridSquare[][] GridElements = new GridSquare[30][15];
	String levelText = null;
	boolean built = false;
	
	public List<GridSquare> dropBoxes = new ArrayList<GridSquare>();
	
	public List<GridSquare> powerSpots = new ArrayList<GridSquare>();
	public Power mapPower = new Power();
	public Power invPower = new Power();
	
	public Grid(){
		for (int i = 0; i < 15; i++) {	
			for (int j = 0; j < 30; j++) {
				GridElements[j][i] = new GridSquare(24 + j*48, 204 + i*48, j, i);
			}
		}
	}
	
	public void buildGrid() {
		
		if (built == false) {
			built = true;

			levelText = getLevelText();
			int k = -1;
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 30; j++) {
					k += 1;
					wallBuild(levelText.charAt(k), GridElements[j][i]);
					if (levelText.charAt(k) == 'X') { 
						GridElements[j][i].addWall();
					}
					else if (levelText.charAt(k) == 'M') { 
						GridElements[j][i].addMonsterHole();
					}
					else if (levelText.charAt(k) == 'D') { 
						GridElements[j][i].addDropBox();
						dropBoxes.add(GridElements[j][i]);
					}
					else if (levelText.charAt(k) == 'O') { 
						powerSpots.add(GridElements[j][i]);
					}
				}
			}
		}
	}
	
	public void wallBuild(char k, GridSquare curr) {
		if (k == '║')
			curr.addTubeV();
		if (k == '═')
			curr.addTubeH();
		if (k == '█')
			curr.addSquare();
		if (k == '╚')
			curr.addElbowDL();
		if (k == '╝')
			curr.addElbowDR();
		if (k == '╔')
			curr.addElbowUL();
		if (k == '╗')
			curr.addElbowUR();
		if (k == '└')
			curr.addCornerDL();
		if (k == '┘')
			curr.addCornerDR();
		if (k == '┌')
			curr.addCornerUL();
		if (k == '┐')
			curr.addCornerUR();
		if (k == '╦')
			curr.addTdown();
		if (k == '╣')
			curr.addTleft();
		if (k == '╠')
			curr.addTright();
		if (k == '╩')
			curr.addTup();
		if (k == '┴')
			curr.addSideD();
		if (k == '├')
			curr.addSideL();
		if (k == '┤')
			curr.addSideR();
		if (k == '┬')
			curr.addSideU();
		if (k == 'v')
			curr.addEndD();
		if (k == '<')
			curr.addEndL();
		if (k == '>')
			curr.addEndR();
		if (k == '^')
			curr.addEndU();
		if (k == '╤') {
			curr.addTubeH();
			curr.addMonsterHole();
		}
		if (k == '╢') {
			curr.addTubeV();
			curr.addMonsterHole();
		}
		if (k == '╟') {
			curr.addTubeV();
			curr.addMonsterHole();
		}
		if (k == '╧') {
			curr.addTubeH();
			curr.addMonsterHole();
		}
		if (k == '1' || k == '2' || k == '3' || k == '4' || k == '5' || 
			k == '6' || k == '7' || k == '8' || k == '9' || k == 'a')
			curr.addSpecial(k);
		
	}
	
	public void destroyGrid() {
		
		if (built == true) {
			built = false;
			
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 30; j++) {
					GridElements[j][i].clear();
				}
			}
			dropBoxes.clear();
			powerSpots.clear();
		}
	}
	
	public boolean checkForDropBox(int x, int y) {
		boolean nearBox = false;
		for (int i = y - 1; i <= y + 1; i++) {
			for (int j = x - 1; j <= x + 1; j ++)
				if (dropBoxes.contains(GridElements[j][i]))
					nearBox = true;
		}
		return nearBox;
	}
	
	public void addPowerMap() {
		if (mapPower.inMap == false) {
			Random ran = new Random();
			int powerSpace = ran.nextInt(powerSpots.size());
			GridSquare temp = powerSpots.get(powerSpace);
			mapPower.setNewPower(temp.xVal, temp.yVal);
		}
	}
	
	public void powerPickup() {
		if (invPower.inInventory == true)
			invPower.reset();
		
		invPower.name = mapPower.name;
		invPower.info1 = mapPower.info1;
		invPower.info2 = mapPower.info2;
		invPower.info3 = mapPower.info3;
		mapPower.reset();
		invPower.setInv();
	}
	
	public boolean hasPower() {
		return invPower.inInventory;
	}
	
	public String getPower() {
		return invPower.name;
	}
	
	public void usePower() {
		invPower.reset();
	}
	
	public String getLevelText() {
		
		if (TrashStasherGame.currLevel == 1)
			return TrashStasherGame.Lvl1;
		else if (TrashStasherGame.currLevel == 2)
			return TrashStasherGame.Lvl2;
		else
			return TrashStasherGame.Lvl3;
		
	}
	
	public void render(Graphics g) {
//		if (built == false)
//			buildGrid();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {
				GridElements[j][i].render(g);
			}
		}
		mapPower.render(g);
		invPower.render(g);
	}
	
	public void renderPow(Graphics g) {
		mapPower.render(g);
		invPower.render(g);
	}

}