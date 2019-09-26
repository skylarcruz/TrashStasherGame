package CryptCaper;

import java.util.Iterator;

import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class PlayingState extends BasicGameState {
	
	boolean paused = false;
	int startCountdown = 300;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
	
		CryptCaperGame.ccGrid.buildGrid();
		CryptCaperGame.ccExplorer.initExpPath();
		
		for (int i = 0; i < 5; i++)
			CryptCaperGame.ccMons[i].setStartLocation(28,1);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		//g.drawString("Play", 10, 30);
		ccg.ccGrid.render(g);
		ccg.ccExplorer.render(g);
		
		for (int i = 0; i < 10; i++)
			CryptCaperGame.ccMons[i].render(g);
		
		g.drawString("Lives: " + ccg.lives, 10, 30);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		startCountdown -= 1;
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			if (paused == false)
				paused = true;
			else 
				paused = false;
		}
		
		if (input.isKeyPressed(Input.KEY_R))
			resetLevel();
		
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
					loseLife();
			}
			
			
		// Update entities
			ccg.ccExplorer.update(delta);
			
			for (int i = 0; i < 10; i++)
				ccg.ccMons[i].update(delta);
			
		}
		
	}
	
	public void loseLife() {
		if (CryptCaperGame.lives > 0)
			CryptCaperGame.lives -= 1;
		resetLevel();
	}
	
	public void resetLevel() {
		
		startCountdown = 300;
		
		for (int i = 0; i < 3; i++)
			CryptCaperGame.ccMons[i].setStartLocation(28,1);
		for (int i = 3; i < 10; i++)
			CryptCaperGame.ccMons[i].deactivate();
		
		CryptCaperGame.ccExplorer.reset(1, 1);
		
	}

	@Override
	public int getID() {
		return CryptCaperGame.PLAYINGSTATE;
	}
	
}