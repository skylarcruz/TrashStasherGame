package TrashStasher;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
//import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class GameOverState extends BasicGameState {
	
	int countdown = 300;
	boolean exitReady;
	int userScore = 0;
	
	boolean scoreChecked = false;
	boolean scoreReplace = false;
	boolean match = false;
	
	boolean nameInput = false;
	String newName;
	int NNi;
	
	int scoreArr[];
	String namesArr[];
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);

		exitReady = false;
		scoreChecked = false;
		scoreReplace = false;
		match = false;
		nameInput = false;
		newName = "---";
		NNi = 0;
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		TrashStasherGame.getHighScores(
				TrashStasherGame.LVL1_SCORES_TXT, tsg.highScore1, tsg.hsNames1);
		TrashStasherGame.getHighScores(
				TrashStasherGame.LVL2_SCORES_TXT, tsg.highScore2, tsg.hsNames2);
		TrashStasherGame.getHighScores(
				TrashStasherGame.LVL3_SCORES_TXT, tsg.highScore3, tsg.hsNames3);
		
		getScoreArrs();
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		g.drawString("Game Over", 660, 30);
		
		g.drawString("Level 1", 260, 150);
		g.drawString("Level 2", 660, 150);
		g.drawString("Level 3", 1060, 150);
		
		for (int i = 0; i < 10; i ++) {
			g.drawString(tsg.hsNames1[i], 250, 200 + i * 50);
			g.drawString(tsg.hsNames2[i], 650, 200 + i * 50);
			g.drawString(tsg.hsNames3[i], 1050, 200 + i * 50);
			
			g.drawString(Integer.toString(tsg.highScore1[i]), 300, 200 + i * 50);
			g.drawString(Integer.toString(tsg.highScore2[i]), 700, 200 + i * 50);
			g.drawString(Integer.toString(tsg.highScore2[i]), 1100, 200 + i * 50);
		}
		
		if (exitReady == true) {
			g.drawString("[Press Space to go to Main Menu]", 550, 800);
		}
		
		if (scoreReplace == true) {
			g.drawString("New High Score!", 630, 60);
			g.drawString(newName, 660, 90);
			g.drawString(Integer.toString(userScore), 710, 90);
			
			if (NNi == 0)
				g.drawString("^", 660, 105);
			if (NNi == 1)
				g.drawString("^", 669, 105);
			if (NNi == 2)
				g.drawString("^", 678, 105);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		Input input = container.getInput();
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		int lineNum = 10;
		match = false;
		if (scoreChecked == false) {
			for (int i = 0; i < 10; i++) {
				if (userScore > scoreArr[i]) {
					lineNum = i;
					scoreReplace = true;
					nameInput = true;
					break;
				}
				if (userScore == scoreArr[i]) {
					lineNum = i + 1;
					scoreReplace = true;
					nameInput = true;
					if (lineNum > 9) {
						scoreReplace = false;
						nameInput = false;
					}
				}
			}
			if (nameInput == true) {
				inputName(input);
			}
			if (scoreReplace == true && exitReady == false && nameInput == false) {
				if (lineNum < 10)
					setTextScores(lineNum);
				else
					scoreReplace = false;
				exitReady = true;
			}
			if (nameInput == false) {
				scoreChecked = true;
				exitReady = true;
				TrashStasherGame.getHighScores(
						TrashStasherGame.LVL1_SCORES_TXT, tsg.highScore1, tsg.hsNames1);
				TrashStasherGame.getHighScores(
						TrashStasherGame.LVL2_SCORES_TXT, tsg.highScore2, tsg.hsNames2);
				TrashStasherGame.getHighScores(
						TrashStasherGame.LVL3_SCORES_TXT, tsg.highScore3, tsg.hsNames3);
			}
		} 
		
		if (exitReady == true && input.isKeyPressed(Input.KEY_SPACE)) {
			tsg.lives = 3;
			userScore = 0;
			game.enterState(TrashStasherGame.STARTUPSTATE);
		}
		
	}
	
	private void inputName(Input input) {
		
		char c = newName.charAt(NNi);
		
		if (input.isKeyPressed(Input.KEY_W))
			if (c == '-')
				c = 'A';
			else if (c != 'Z')
				c += 1;
		if (input.isKeyPressed(Input.KEY_S))
			if (c == '-')
				c = 'Z';
			else if (c != 'A')
				c -= 1;
		
		newName = newName.substring(0, NNi)
						+ c
						+ newName.substring(NNi + 1);
		
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			if (NNi == 2)
				nameInput = false;
			NNi += 1;
		}
			
//		if (input.isKeyPressed(Input.KEY_S))
//			currSel = 3;
		
	}
	
	private void getScoreArrs() {
		scoreArr = new int[10];
		namesArr = new String[10];
		
		if (TrashStasherGame.currLevel == 1) {
			scoreArr = TrashStasherGame.highScore1.clone();
			namesArr = TrashStasherGame.hsNames1.clone();
		}
		if (TrashStasherGame.currLevel == 2) {
			scoreArr = TrashStasherGame.highScore2.clone();
			namesArr = TrashStasherGame.hsNames1.clone();
		}
		if (TrashStasherGame.currLevel == 3) {
			scoreArr = TrashStasherGame.highScore3.clone();
			namesArr = TrashStasherGame.hsNames1.clone();
		}
	}
	
	private void setTextScores(int line) {
		for (int i = 9; i > line; i --) {
			scoreArr[i] = scoreArr [i - 1];
			namesArr[i] = namesArr [i - 1];
		}
		scoreArr[line] = userScore;
		namesArr[line] = newName;
		
		if (TrashStasherGame.currLevel == 1)
			TrashStasherGame.setHighScores(TrashStasherGame.LVL1_SCORES_TXT, scoreArr, namesArr);
		if (TrashStasherGame.currLevel == 2)
			TrashStasherGame.setHighScores(TrashStasherGame.LVL2_SCORES_TXT, scoreArr, namesArr);
		if (TrashStasherGame.currLevel == 3)
			TrashStasherGame.setHighScores(TrashStasherGame.LVL3_SCORES_TXT, scoreArr, namesArr);
	}
	
	public void setUserScore(int s) {
		userScore = s;
	}

	@Override
	public int getID() {
		return TrashStasherGame.GAMEOVERSTATE;
	}
	
}