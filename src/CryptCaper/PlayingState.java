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
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
	
		CryptCaperGame.ccGrid.buildGrid();
		CryptCaperGame.ccExplorer.initExpPath();
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		g.drawString("Play", 10, 30);
		ccg.ccGrid.render(g);
		ccg.ccExplorer.render(g);
		
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		if (input.isKeyDown(Input.KEY_Q))
				ccg.ccExplorer.speedMod = 0;
		if (input.isKeyDown(Input.KEY_E))
			ccg.ccExplorer.speedMod = .15f;
		
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
		
	}

	@Override
	public int getID() {
		return CryptCaperGame.PLAYINGSTATE;
	}
	
}