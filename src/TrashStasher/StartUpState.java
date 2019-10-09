package TrashStasher;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import jig.ResourceManager;


class StartUpState extends BasicGameState {
	
	int currSel = 1;
	
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
		
		g.drawImage(ResourceManager.getImage((
				TrashStasherGame.TITLE_NAMEIMG_RSC)), 175, 25);
		
		g.drawString("Start Game", 600, 375);
		g.drawString("Raccoon: ", 600, 425);
		renderRaccSprite(g, TrashStasherGame.raccNum);
		g.drawString("Cheat Menu", 600, 475);
		
		if (currSel == 1)
			g.drawString("=>", 550, 375);
		if (currSel == 2) {
			g.drawString("=>", 550, 425);
			g.drawString("<      >", 685, 425);
		}
		if (currSel == 3)
			g.drawString("=>", 550, 475);
	}
	
	public void renderRaccSprite(Graphics g, int r) {
		g.drawImage(ResourceManager.getImage((TrashStasherGame.RACC_RIGHTIMG_RSC 
				+ Integer.toString(r) + ".png")), 700, 415);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		if (currSel == 1) {
			if (input.isKeyPressed(Input.KEY_S))
				currSel = 2;
			if (input.isKeyPressed(Input.KEY_SPACE))
				tsg.enterState(TrashStasherGame.PLAYINGSTATE);
		}
		
		else if (currSel == 2) {
			if (input.isKeyPressed(Input.KEY_W))
				currSel = 1;
			if (input.isKeyPressed(Input.KEY_S))
				currSel = 3;
			if (input.isKeyPressed(Input.KEY_A)) {
				if (TrashStasherGame.raccNum > 1)
					TrashStasherGame.raccNum -= 1;
				else
					TrashStasherGame.raccNum = 5;
			}
			if (input.isKeyPressed(Input.KEY_D)) {
				if (TrashStasherGame.raccNum < 5)
					TrashStasherGame.raccNum += 1;
				else
					TrashStasherGame.raccNum = 1;
			}
		}
		
		else if (currSel == 3) {
			if (input.isKeyPressed(Input.KEY_W))
				currSel = 2;
		}
		
	}

	@Override
	public int getID() {
		return TrashStasherGame.STARTUPSTATE;
	}
	
}