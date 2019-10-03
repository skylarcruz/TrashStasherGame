package CryptCaper;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

class PlayingState extends BasicGameState {
	
	boolean paused = false;
	boolean showPath = false;
	int startCountdown = 50;
	
	int MonsterCountdown = 1000;
	int nextMonNum = 2;
	boolean spawn = true;
	
	int tCountdown;
	int weightMod;
	float speedMod;
	
	boolean scoreCompile = false;
	int scoreTimer;
	int multiplier = 1;
	int addScore;
	int totAddScore;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
	
		setLevel(game);
	}
		
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		g.drawImage(ResourceManager.getImage(CryptCaperGame.BG_BGIMG_RSC), 0,
				0);
		g.drawImage(ResourceManager.getImage(CryptCaperGame.HUD_LINESIMG_RSC), 0,
				0);
		
		ccg.ccGrid.render(g);
		ccg.ccTT.render(g);
		
		if (showPath == true) {
			getPath(ccg, g);
		}
		
		
		ccg.ccExplorer.render(g);
		
		for (int i = 0; i < 10; i++)
			ccg.ccMons[i].render(g);
		
		g.drawString("Lives: " + ccg.lives, 10, 30);
		g.drawString("Score: " + ccg.score, 10, 50);
		if (totAddScore > 0)
			g.drawString("+" + totAddScore, 10, 70);
		
	}
	
	private void getPath(StateBasedGame game, Graphics g) {
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {	
				if (ccg.ccDikjstra.graph[j][i].cost < 500) {
					String dir = ccg.ccDikjstra.getBestDir(j, i);
					if (dir == "Up")
						g.drawImage(ResourceManager.getImage(CryptCaperGame.ARROW_UPIMG_RSC), 
								j*48, 180 + i*48);
					if (dir == "Down")
						g.drawImage(ResourceManager.getImage(CryptCaperGame.ARROW_DOWNIMG_RSC), 
								j*48, 180 + i*48);
					if (dir == "Left")
						g.drawImage(ResourceManager.getImage(CryptCaperGame.ARROW_LEFTIMG_RSC), 
								j*48, 180 + i*48);
					if (dir == "Right")
						g.drawImage(ResourceManager.getImage(CryptCaperGame.ARROW_RIGHTIMG_RSC), 
								j*48, 180 + i*48);
				}
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		// updates the dikjstra source and runs algorithm if change occurs
		if (ccg.ccDikjstra.setExpLoc(ccg.ccExplorer.getGridX(),
				ccg.ccExplorer.getGridY()) == true)
			ccg.ccDikjstra.runDikjstra();
		
		
		// Pause Game
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			if (paused == false)
				paused = true;
			else 
				paused = false;
		}
		
		
		// Displays Dikjstra generated best paths to Player
		if (input.isKeyPressed(Input.KEY_P)) {
			if (showPath == false)
				showPath = true;
			else 
				showPath = false;
		}
		
		// Resets level. Used for Testing
		if (input.isKeyPressed(Input.KEY_R))
			setLevel(game);
		
		// prevent starting monsters from spawning on the same location, also start countdown
		if (startCountdown > 0) {
			startCountdown -= 1;
			if (ccg.ccMons[0].collides(ccg.ccMons[1]) != null) {
				ccg.ccMons[1].deactivate();
				ccg.ccMons[1].setStartLocation();
				System.out.println("Thing Happened");
			}
		}
		
		if (paused == false && startCountdown <= 0) {
			
			MonsterCountdown -= 1;
			
			// Monster Spawn. Spawn until all Monsters on board
			if (MonsterCountdown <= 0 && spawn) {
				ccg.ccMons[nextMonNum].setStartLocation();
				if (nextMonNum < 9) {
					nextMonNum += 1;
					MonsterCountdown = 1000;
				}
				else
					spawn = false;
			}
			
			
			tCountdown -= 1;
			if (tCountdown <= 0) {
				ccg.ccTT.addToMap();
				tCountdown = 50;
			}
			
			// pop Treasure off Inv
			if (input.isKeyPressed(Input.KEY_J)) {
				weightMod = ccg.ccTT.popInv();
				speedMod = (float) weightMod * .0008f;
				ccg.ccExplorer.changeSpeed(speedMod);
			}
			
			// Treasure Scoring
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				if (scoreCompile == false && ccg.ccTT.invCnt > 0 &&
					ccg.ccGrid.checkForDropBox(ccg.ccExplorer.getGridX(),ccg.ccExplorer.getGridY())) {
						multiplier = ccg.ccTT.invCnt;
						scoreTimer = multiplier * 25;
						scoreCompile = true;
				}
			}
			
			if (scoreCompile == true) {
				if (scoreTimer % 25 == 0) {
					addScore = multiplier * ccg.ccTT.popScore();
					totAddScore += addScore;
					ccg.score += addScore;
					weightMod = ccg.ccTT.popInv();
					speedMod = (float) weightMod * .0008f;
					ccg.ccExplorer.changeSpeed(speedMod);
				}
				scoreTimer -= 1;
				if (scoreTimer <= 0) {
					multiplier = 1;
					scoreCompile = false;
					addScore = 0;
					totAddScore = 0;
				}			
			}
			
			// Player Movement Checks
			if (input.isKeyDown(Input.KEY_W) && ccg.ccExplorer.inputAccept)
				if (ccg.ccExplorer.checkDir("Up")) {
					ccg.ccExplorer.inputAccept = false;
					ccg.ccExplorer.move("Up");
				}
			if (input.isKeyDown(Input.KEY_S) && ccg.ccExplorer.inputAccept)
				if (ccg.ccExplorer.checkDir("Down")){
					ccg.ccExplorer.inputAccept = false;
					ccg.ccExplorer.move("Down");
				}
			if (input.isKeyDown(Input.KEY_A) && ccg.ccExplorer.inputAccept)
				if (ccg.ccExplorer.checkDir("Left")){
					ccg.ccExplorer.inputAccept = false;
					ccg.ccExplorer.move("Left");
				}
			if (input.isKeyDown(Input.KEY_D) && ccg.ccExplorer.inputAccept)
				if (ccg.ccExplorer.checkDir("Right")){
					ccg.ccExplorer.inputAccept = false;
					ccg.ccExplorer.move("Right");
				}
	
			// Loss from collision into Monsters
			for (int i = 0; i < 10; i++) {
				if ((ccg.ccMons[i].collides(ccg.ccExplorer) != null))
					loseLife(game);
			}
			
			// Treasure Pickup
			if (scoreCompile == false) {
				for (int i = 0; i < 3; i++) {
					if (ccg.ccTT.mapTreasure[i].collides(ccg.ccExplorer) != null) {
						weightMod = -1 * ccg.ccTT.moveToInv(ccg.ccTT.mapTreasure[i]);
						speedMod = (float) weightMod * .0008f;
						ccg.ccExplorer.changeSpeed(speedMod);
					}
				}
			}
			// Treasure lands on other Treasure
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i != j && ccg.ccTT.mapTreasure[i].inMap == true) {
						if (ccg.ccTT.mapTreasure[i].collides(ccg.ccTT.mapTreasure[j]) != null) {
							ccg.ccTT.mapTreasure[j].reset();
							ccg.ccTT.addToMap();
						}
					}
				}
			}			
			
			// Update entities
			ccg.ccExplorer.update(delta);
			
			for (int i = 0; i < 10; i++) {
				ccg.ccMons[i].update(delta);
				ccg.ccMons[i].setExpLoc(
						ccg.ccExplorer.getGridX(),ccg.ccExplorer.getGridY());
				if (ccg.ccMons[i].active == true) {
					String bestDir = ccg.ccDikjstra.getBestDir(
							ccg.ccMons[i].monX, ccg.ccMons[i].monY);
					ccg.ccMons[i].setBestDir(bestDir);
				}
			}
			
		}
		
	}
	
	public void loseLife(StateBasedGame game) {
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		if (ccg.lives > 1) {
			ccg.lives -= 1;
			setLevel(game);
		}
		else {
			//((GameOverState)game.getState(CryptCaperGame.GAMEOVERSTATE)).setUserScore(bounces);
			ccg.score = 0;
			game.enterState(CryptCaperGame.GAMEOVERSTATE);
		}
	}
	
	public void setLevel(StateBasedGame game) {
		
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		ccg.ccDikjstra.setGrid();
		ccg.ccDikjstra.setExpLoc(
				ccg.ccExplorer.getGridX(),ccg.ccExplorer.getGridY());
		ccg.ccDikjstra.runDikjstra();
		
		spawn = true;
		startCountdown = 50;
		
		for (int i = 0; i < 10; i++)
			ccg.ccMons[i].deactivate();
		for (int i = 0; i < 2; i++)
			ccg.ccMons[i].setStartLocation();
		nextMonNum = 2;
		MonsterCountdown = 1000;
		
		ccg.ccExplorer.reset();
		
		ccg.ccTT.reset();
		tCountdown = 50;
		
	}

	@Override
	public int getID() {
		return CryptCaperGame.PLAYINGSTATE;
	}
	
}