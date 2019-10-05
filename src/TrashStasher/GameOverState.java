package TrashStasher;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
//import org.newdawn.slick.Input;
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
		//CryptCaperGame ccg = (CryptCaperGame)game;
		
		g.drawString("Game Over", 10, 30);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		TrashStasherGame ccg = (TrashStasherGame)game;
		
		countdown -= 1;
		ccg.lives = 3;
		
		if (countdown <= 0)
			game.enterState(TrashStasherGame.PLAYINGSTATE);
		
	}

	@Override
	public int getID() {
		return TrashStasherGame.GAMEOVERSTATE;
	}
	
}