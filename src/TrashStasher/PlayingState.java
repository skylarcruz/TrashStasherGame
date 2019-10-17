package TrashStasher;

import jig.ResourceManager;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

class PlayingState extends BasicGameState {
	
	boolean paused = false;
	int pauseSel = 1;
	
	boolean showPath = false;
	int startCountdown = 50;
	
	int DogCountdown;
	static int dc = 2500;
	int nextDogNum = 2;
	boolean spawn = true;
	
	int tCountdown;
	static int tc = 450;
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
	
		TrashStasherGame tsg = (TrashStasherGame)game;
		tsg.score = 0;
		setLevel(game);
	}
		
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		
		
		
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		g.drawImage(ResourceManager.getImage(TrashStasherGame.BG_BGIMG_RSC), 0,
				0);
		g.drawImage(ResourceManager.getImage(TrashStasherGame.HUD_LINESIMG_RSC), 0,
				0);
		
		tsg.tsGrid.render(g);
		tsg.tsTT.render(g);
		
		if (showPath == true) {
			getPath(tsg, g);
		}
		
		
		tsg.tsRacc.render(g);
		
		for (int i = 0; i < 10; i++)
			tsg.tsDogs[i].render(g);
		
		g.drawString("Lives: " + tsg.lives, 10, 30);
		g.drawString("Score: " + tsg.score, 10, 50);
		if (totAddScore > 0)
			g.drawString("+" + totAddScore, 10, 70);
		
		if (tsg.tsGrid.invPower.inInventory == true) {
			g.drawString(tsg.tsGrid.invPower.name, 12, 152);
			if(tsg.tsGrid.invPower.info1 != null)
				g.drawString(tsg.tsGrid.invPower.info1, 80, 100);
			if(tsg.tsGrid.invPower.info2 != null)
				g.drawString(tsg.tsGrid.invPower.info2, 80, 120);
			if(tsg.tsGrid.invPower.info3 != null)
				g.drawString(tsg.tsGrid.invPower.info3, 80, 140);
		}
		
		if (paused == true) {
			g.setColor(new Color(0,0,0));
			g.fillRect(520, 300, 400, 300);
			
			g.setColor(new Color(255,255,255));
			g.drawString("Pause Menu", 680, 325);
			
			g.drawString("Continue", 600, 400);
			g.drawString("Quit to Main Menu", 600, 450);
			g.drawString("Quit Game", 600, 500);
			
			if (pauseSel == 1)
				g.drawString("=>", 550, 400);
			if (pauseSel == 2)
				g.drawString("=>", 550, 450);
			if (pauseSel == 3)
				g.drawString("=>", 550, 500);
		}
		
	}
	
	private void getPath(StateBasedGame game, Graphics g) {
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {	
				if (tsg.tsDikjstra.graph[j][i].cost < 500) {
					String dir = tsg.tsDikjstra.getBestDir(j, i);
					if (dir == "Up")
						g.drawImage(ResourceManager.getImage(TrashStasherGame.ARROW_UPIMG_RSC), 
								j*48, 180 + i*48);
					if (dir == "Down")
						g.drawImage(ResourceManager.getImage(TrashStasherGame.ARROW_DOWNIMG_RSC), 
								j*48, 180 + i*48);
					if (dir == "Left")
						g.drawImage(ResourceManager.getImage(TrashStasherGame.ARROW_LEFTIMG_RSC), 
								j*48, 180 + i*48);
					if (dir == "Right")
						g.drawImage(ResourceManager.getImage(TrashStasherGame.ARROW_RIGHTIMG_RSC), 
								j*48, 180 + i*48);
				}
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		if (input.isKeyPressed(Input.KEY_G)) {
			((GameOverState)game.getState(TrashStasherGame.GAMEOVERSTATE)).setUserScore(1000);
			game.enterState(TrashStasherGame.GAMEOVERSTATE);
		}
		
		// updates the dikjstra source and runs algorithm if change occurs
		if (tsg.tsDikjstra.setRaccLoc(tsg.tsRacc.getGridX(),
				tsg.tsRacc.getGridY()) == true)
			tsg.tsDikjstra.runDikjstra();
		
		
		// Pause Game
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			if (paused == false) {
				paused = true;
				pauseSel = 1;
			}
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
		
		// prevent starting dogsters from spawning on the same location, also start countdown
		if (startCountdown > 0) {
			startCountdown -= 1;
			if (tsg.tsDogs[0].collides(tsg.tsDogs[1]) != null) {
				tsg.tsDogs[1].deactivate();
				tsg.tsDogs[1].setStartLocation();
			}
		}
		
		if (paused == true)
			pauseMenu(input, tsg, container);
		
		if (paused == false && startCountdown <= 0) {
			
			tsg.tsGrid.addPowerMap();
			
			DogCountdown -= 1;
			
			// Dog Spawn. Spawn until all Dogs on board
			if (DogCountdown <= 0 && spawn) {
				tsg.tsDogs[nextDogNum].setStartLocation();
				if (nextDogNum < 9) {
					nextDogNum += 1;
					DogCountdown = dc;
				}
				else
					spawn = false;
			}
			
			
			tCountdown -= 1;
			if (tCountdown <= 0) {
				tsg.tsTT.addToMap();
				tCountdown = tc;
			}
			
			// pop Treasure off Inv
			if (input.isKeyPressed(Input.KEY_J)) {
				weightMod = tsg.tsTT.popInv();
				speedMod = (float) weightMod * .0008f;
				tsg.tsRacc.changeSpeed(speedMod);
			}
			
			// Treasure Scoring
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				if (scoreCompile == false && tsg.tsTT.invCnt > 0 &&
					tsg.tsGrid.checkForDropBox(tsg.tsRacc.getGridX(),tsg.tsRacc.getGridY())) {
						multiplier = tsg.tsTT.invCnt;
						scoreTimer = multiplier * 25;
						scoreCompile = true;
				}
			}
			
			if (scoreCompile == true) {
				if (scoreTimer % 25 == 0 && tsg.tsTT.invCnt > 0) {
					addScore = multiplier * tsg.tsTT.popScore();
					totAddScore += addScore;
					tsg.score += addScore;
					weightMod = tsg.tsTT.popInv();
					speedMod = (float) weightMod * .0008f;
					tsg.tsRacc.changeSpeed(speedMod);
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
			if (input.isKeyDown(Input.KEY_W) && tsg.tsRacc.inputAccept)
				if (tsg.tsRacc.checkDir("Up")) {
					tsg.tsRacc.inputAccept = false;
					tsg.tsRacc.move("Up");
				}
			if (input.isKeyDown(Input.KEY_S) && tsg.tsRacc.inputAccept)
				if (tsg.tsRacc.checkDir("Down")){
					tsg.tsRacc.inputAccept = false;
					tsg.tsRacc.move("Down");
				}
			if (input.isKeyDown(Input.KEY_A) && tsg.tsRacc.inputAccept)
				if (tsg.tsRacc.checkDir("Left")){
					tsg.tsRacc.inputAccept = false;
					tsg.tsRacc.move("Left");
				}
			if (input.isKeyDown(Input.KEY_D) && tsg.tsRacc.inputAccept)
				if (tsg.tsRacc.checkDir("Right")){
					tsg.tsRacc.inputAccept = false;
					tsg.tsRacc.move("Right");
				}
	
			// Loss from collision into Dogs
			for (int i = 0; i < 10; i++) {
				if ((tsg.tsDogs[i].collides(tsg.tsRacc) != null))
					loseLife(game);
			}
			
			// Treasure Pickup
			if (scoreCompile == false) {
				for (int i = 0; i < 3; i++) {
					if (tsg.tsTT.mapTreasure[i].collides(tsg.tsRacc) != null) {
						weightMod = -1 * tsg.tsTT.moveToInv(tsg.tsTT.mapTreasure[i]);
						speedMod = (float) weightMod * .0008f;
						tsg.tsRacc.changeSpeed(speedMod);
					}
				}
			}
			
			// Power Pickup
			if (tsg.tsGrid.mapPower.collides(tsg.tsRacc) != null) {
				tsg.tsGrid.powerPickup();
			}
			
			// Treasure lands on other Treasure
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i != j && tsg.tsTT.mapTreasure[i].inMap == true) {
						if (tsg.tsTT.mapTreasure[i].collides(tsg.tsTT.mapTreasure[j]) != null) {
							tsg.tsTT.mapTreasure[j].reset();
							tsg.tsTT.addToMap();
						}
					}
				}
			}			
			
			// Update entities
			tsg.tsRacc.update(delta);
			
			for (int i = 0; i < 10; i++) {
				tsg.tsDogs[i].update(delta);
				tsg.tsDogs[i].setRaccLoc(
						tsg.tsRacc.getGridX(),tsg.tsRacc.getGridY());
				if (tsg.tsDogs[i].active == true) {
					String bestDir = tsg.tsDikjstra.getBestDir(
							tsg.tsDogs[i].dogX, tsg.tsDogs[i].dogY);
					tsg.tsDogs[i].setBestDir(bestDir);
				}
			}
			
		}
		
		clearInput(input);
		
	}
	
	public void pauseMenu(Input input, StateBasedGame game, GameContainer c) {
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		if (pauseSel == 1) {
			if (input.isKeyPressed(Input.KEY_S))
				pauseSel = 2;
			if (input.isKeyPressed(Input.KEY_SPACE))
				paused = false;
		}
		
		else if (pauseSel == 2) {
			if (input.isKeyPressed(Input.KEY_W))
				pauseSel = 1;
			if (input.isKeyPressed(Input.KEY_S))
				pauseSel = 3;
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				paused = false;
				tsg.score = 0;
				tsg.enterState(TrashStasherGame.STARTUPSTATE);
			}
		}
		
		else if (pauseSel == 3) {
			if (input.isKeyPressed(Input.KEY_W))
				pauseSel = 2;
			if (input.isKeyPressed(Input.KEY_SPACE))
				c.exit();
		}
	}
	
	public void clearInput(Input input) {
		input.isKeyPressed(Input.KEY_SPACE);
		input.isKeyPressed(Input.KEY_W);
		input.isKeyPressed(Input.KEY_A);
		input.isKeyPressed(Input.KEY_S);
		input.isKeyPressed(Input.KEY_D);
	}
	
	public void loseLife(StateBasedGame game) {
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		if (tsg.lives > 1) {
			tsg.lives -= 1;
			setLevel(game);
		}
		else {
			((GameOverState)game.getState(TrashStasherGame.GAMEOVERSTATE)).setUserScore(tsg.score);
			tsg.score = 0;
			game.enterState(TrashStasherGame.GAMEOVERSTATE);
		}
	}
	
	public void setLevel(StateBasedGame game) {
		
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		tsg.tsDikjstra.setGrid();
		tsg.tsDikjstra.setRaccLoc(
				tsg.tsRacc.getGridX(),tsg.tsRacc.getGridY());
		tsg.tsDikjstra.runDikjstra();
		
		spawn = true;
		startCountdown = 50;
		
		for (int i = 0; i < 10; i++)
			tsg.tsDogs[i].deactivate();
		for (int i = 0; i < 2; i++)
			tsg.tsDogs[i].setStartLocation();
		nextDogNum = 2;
		DogCountdown = dc;
		
		tsg.tsRacc.reset();
		
		tsg.tsTT.reset();
		tCountdown = tc;
		
		scoreTimer = 0;
		multiplier = 1;
		scoreCompile = false;
		addScore = 0;
		totAddScore = 0;
		
		
	}

	@Override
	public int getID() {
		return TrashStasherGame.PLAYINGSTATE;
	}
	
}