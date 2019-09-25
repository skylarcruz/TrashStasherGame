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
		
		if (input.isKeyDown(Input.KEY_W))
			System.out.println(ccg.ccExplorer.checkDir("Up"));
		if (input.isKeyDown(Input.KEY_S))
			System.out.println(ccg.ccExplorer.checkDir("Down"));
		if (input.isKeyDown(Input.KEY_A))
			System.out.println(ccg.ccExplorer.checkDir("Left"));
		if (input.isKeyDown(Input.KEY_D))
			System.out.println(ccg.ccExplorer.checkDir("Right"));
		
	}

	@Override
	public int getID() {
		return CryptCaperGame.PLAYINGSTATE;
	}
	
}