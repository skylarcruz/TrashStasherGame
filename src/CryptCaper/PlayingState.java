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
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
	
		CryptCaperGame.ccGrid.buildGrid();
		CryptCaperGame.ccExplorer.initExpPath();
		CryptCaperGame.ccMon.initMonPath();
		CryptCaperGame.ccMon2.initMonPath();
		CryptCaperGame.ccMon3.initMonPath();
		CryptCaperGame.ccMon4.initMonPath();
		CryptCaperGame.ccMon5.initMonPath();
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		g.drawString("Play", 10, 30);
		ccg.ccGrid.render(g);
		ccg.ccExplorer.render(g);
		ccg.ccMon.render(g);
		ccg.ccMon2.render(g);
		ccg.ccMon3.render(g);
		ccg.ccMon4.render(g);
		ccg.ccMon5.render(g);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			if (paused == false)
				paused = true;
			else 
				paused = false;
		}
		
		if (paused == false) {
		
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
	
			ccg.ccExplorer.update(delta);
			ccg.ccMon.update(delta);
			ccg.ccMon2.update(delta);
			ccg.ccMon3.update(delta);
			ccg.ccMon4.update(delta);
			ccg.ccMon5.update(delta);
			
		}
		
	}

	@Override
	public int getID() {
		return CryptCaperGame.PLAYINGSTATE;
	}
	
}