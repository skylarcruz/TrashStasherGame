package TrashStasher;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
//import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class GameOverState extends BasicGameState {
	
	int countdown = 300;
	int userScore = 0;
	boolean scoreChecked = false;
	boolean scoreReplace = false;
	int scoreArr[];
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
		
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		TrashStasherGame.getHighScores(
				TrashStasherGame.LVL1_SCORES_TXT, tsg.highScore1);
		TrashStasherGame.getHighScores(
				TrashStasherGame.LVL2_SCORES_TXT, tsg.highScore2);
		TrashStasherGame.getHighScores(
				TrashStasherGame.LVL3_SCORES_TXT, tsg.highScore3);
		
		getScoreArr();
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		//CryptCaperGame ccg = (CryptCaperGame)game;
		TrashStasherGame tsg = (TrashStasherGame)game;
		
		g.drawString("Game Over", 10, 30);
		
		for (int i = 0; i < 10; i ++) {
			g.drawString(Integer.toString(tsg.highScore1[i]), 300, 300 + i * 50);
			g.drawString(Integer.toString(tsg.highScore2[i]), 600, 300 + i * 50);
			g.drawString(Integer.toString(tsg.highScore2[i]), 900, 300 + i * 50);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		TrashStasherGame tsg = (TrashStasherGame)game;
		
		int lineNum = 10;
		if (scoreChecked == false) {
			for (int i = 0; i < 10; i++) {
				if (userScore > scoreArr[i]) {
					lineNum = i;
					scoreReplace = true;
					break;
				}
			}
			if (scoreReplace == true) {
				setTextScores(lineNum);
			}
			scoreChecked = true;
			TrashStasherGame.getHighScores(
					TrashStasherGame.LVL1_SCORES_TXT, tsg.highScore1);
			TrashStasherGame.getHighScores(
					TrashStasherGame.LVL2_SCORES_TXT, tsg.highScore2);
			TrashStasherGame.getHighScores(
					TrashStasherGame.LVL3_SCORES_TXT, tsg.highScore3);
		} 
		else
			countdown -=1;
		
		
		
		if (countdown <= 0) {
			tsg.lives = 3;
			scoreChecked = false;
			scoreReplace = false;
			userScore = 0;
			countdown = 300;
			game.enterState(TrashStasherGame.STARTUPSTATE);
		}
		
	}
	
	private void getScoreArr() {
		scoreArr = new int[10];
		
		if (TrashStasherGame.currLevel == 1)
			scoreArr = TrashStasherGame.highScore1.clone();
		if (TrashStasherGame.currLevel == 2)
			scoreArr = TrashStasherGame.highScore2.clone();
		if (TrashStasherGame.currLevel == 3)
			scoreArr = TrashStasherGame.highScore3.clone();
	}
	
	private void setTextScores(int line) {
		for (int i = 9; i > line; i --) {
			scoreArr[i] = scoreArr [i - 1];
		}
		scoreArr[line] = userScore;
		
		if (TrashStasherGame.currLevel == 1)
			TrashStasherGame.setHighScores(TrashStasherGame.LVL1_SCORES_TXT, scoreArr);
		if (TrashStasherGame.currLevel == 2)
			TrashStasherGame.setHighScores(TrashStasherGame.LVL2_SCORES_TXT, scoreArr);
		if (TrashStasherGame.currLevel == 3)
			TrashStasherGame.setHighScores(TrashStasherGame.LVL3_SCORES_TXT, scoreArr);
	}
	
	public void setUserScore(int s) {
		userScore = s;
	}

	@Override
	public int getID() {
		return TrashStasherGame.GAMEOVERSTATE;
	}
	
}