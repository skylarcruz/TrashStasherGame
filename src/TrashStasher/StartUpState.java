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
		g.drawString("Level:  " + TrashStasherGame.currLevel, 600, 475);
		g.drawString("Cheats:", 600, 525);
		if (TrashStasherGame.cheatMode == false)
			g.drawString("Off", 682, 525);
		else
			g.drawString("On", 686, 525);
		g.drawString("Quit", 600, 575);
		
		if (currSel == 1)
			g.drawString("=>", 550, 375);
		if (currSel == 2) {
			g.drawString("=>", 550, 425);
			g.drawString("<      >", 685, 425);
		}
		if (currSel == 3) {
			g.drawString("=>", 550, 475);
			g.drawString("<  >", 658, 475);
		}
		if (currSel == 4) {
			g.drawString("=>", 550, 525);
			g.drawString("<     >", 665, 525);
		}
		if (currSel == 5)
			g.drawString("=>", 550, 575);
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
			if (input.isKeyPressed(Input.KEY_S))
				currSel = 4;
			if (input.isKeyPressed(Input.KEY_A)) {
				if (TrashStasherGame.currLevel > 1)
					TrashStasherGame.currLevel -= 1;
				else
					TrashStasherGame.currLevel = 3;
			}
			if (input.isKeyPressed(Input.KEY_D)) {
				if (TrashStasherGame.currLevel < 3)
					TrashStasherGame.currLevel += 1;
				else
					TrashStasherGame.currLevel = 1;
			}
		}
		
		else if (currSel == 4) {
			if (input.isKeyPressed(Input.KEY_W))
				currSel = 3;
			if (input.isKeyPressed(Input.KEY_S))
				currSel = 5;
			if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_D)) {
				if (TrashStasherGame.cheatMode == false)
					TrashStasherGame.cheatMode = true;
				else
					TrashStasherGame.cheatMode = false;
			}
		}
		
		else if (currSel == 5) {
			if (input.isKeyPressed(Input.KEY_W))
				currSel = 4;
			if (input.isKeyPressed(Input.KEY_SPACE))
				container.exit();
		}
		
		clearInput(input);
		
	}
	
	public void clearInput(Input input) {
		input.isKeyPressed(Input.KEY_SPACE);
		input.isKeyPressed(Input.KEY_W);
		input.isKeyPressed(Input.KEY_A);
		input.isKeyPressed(Input.KEY_S);
		input.isKeyPressed(Input.KEY_D);
	}

	@Override
	public int getID() {
		return TrashStasherGame.STARTUPSTATE;
	}
	
}