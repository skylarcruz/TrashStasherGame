package CryptCaper;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class StartUpState extends BasicGameState {
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		//CryptCaperGame ccg = (CryptCaperGame)game;
		
		g.drawString("Start", 10, 30);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		CryptCaperGame ccg = (CryptCaperGame)game;
		
		if (input.isKeyDown(Input.KEY_SPACE))
			ccg.enterState(CryptCaperGame.PLAYINGSTATE);	
		
	}

	@Override
	public int getID() {
		return CryptCaperGame.STARTUPSTATE;
	}
	
}