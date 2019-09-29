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
		
		ccg.ccGrid.render(g);
		
		if (showPath == true) {
			int k = -1;
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 30; j++) {	
					k += 1;
					if (ccg.ccDikjstra.graph[j][i].cost < 500) {
						String dir = ccg.ccDikjstra.getBestDir(j, i);
						if (dir == "Up")
							g.drawImage(ResourceManager.getImage(ccg.ARROW_UPIMG_RSC), 
									j*48, 180 + i*48);
						if (dir == "Down")
							g.drawImage(ResourceManager.getImage(ccg.ARROW_DOWNIMG_RSC), 
									j*48, 180 + i*48);
						if (dir == "Left")
							g.drawImage(ResourceManager.getImage(ccg.ARROW_LEFTIMG_RSC), 
									j*48, 180 + i*48);
						if (dir == "Right")
							g.drawImage(ResourceManager.getImage(ccg.ARROW_RIGHTIMG_RSC), 
									j*48, 180 + i*48);
					}
				}
			}
		}
		
		
		ccg.ccExplorer.render(g);
		
		for (int i = 0; i < 10; i++)
			ccg.ccMons[i].render(g);
		
		g.drawString("Lives: " + ccg.lives, 10, 30);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		ccg.ccDikjstra.setExpLoc(
				ccg.ccExplorer.getGridX(),ccg.ccExplorer.getGridY());
		ccg.ccDikjstra.runDikjstra();
		
		startCountdown -= 1;
		MonsterCountdown -= 1;
		
		if (MonsterCountdown <= 0 && spawn) {
			ccg.ccMons[nextMonNum].setStartLocation();
			if (nextMonNum < 9) {
				nextMonNum += 1;
				MonsterCountdown = 1000;
			}
			else
				spawn = false;
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			if (paused == false)
				paused = true;
			else 
				paused = false;
		}
		
		if (input.isKeyPressed(Input.KEY_P)) {
			if (showPath == false)
				showPath = true;
			else 
				showPath = false;
		}
		
		if (input.isKeyPressed(Input.KEY_R))
			setLevel(game);
		
		if (paused == false && startCountdown <= 0) {
			
			
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
			
			
		// Update entities
			ccg.ccExplorer.update(delta);
			
			for (int i = 0; i < 10; i++) {
				ccg.ccMons[i].update(delta);
				ccg.ccMons[i].setExpLoc(
						ccg.ccExplorer.getGridX(),ccg.ccExplorer.getGridY());
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
		
	}

	@Override
	public int getID() {
		return CryptCaperGame.PLAYINGSTATE;
	}
	
}