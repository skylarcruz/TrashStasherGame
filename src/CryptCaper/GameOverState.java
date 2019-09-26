package CryptCaper;

import java.util.Iterator;

import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class GameOverState extends BasicGameState {
	
	int countdown = 300;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
		
		countdown = 300;
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		g.drawString("Game Over", 10, 30);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		CryptCaperGame ccg = (CryptCaperGame)game;
		
		countdown -= 1;
		ccg.lives = 3;
		
		if (countdown <= 0)
			game.enterState(CryptCaperGame.PLAYINGSTATE);
		
	}

	@Override
	public int getID() {
		return CryptCaperGame.GAMEOVERSTATE;
	}
	
}