package TrashStasher;

import jig.Entity;
import jig.ResourceManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TrashStasherGame extends StateBasedGame {
	
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;

	public final int ScreenWidth;
	public final int ScreenHeight;
	
	public static final String WALL_WALLIMG_RSC = "TrashStasher/Resource/WallTest.png";
	public static final String DROP_BOXIMG_RSC = "TrashStasher/Resource/DropBox.png";
	
	
	// Raccoon Graphics courtesy of whtdragon at rpgtileset.com
	// https://rpgtileset.com/sprite/raccoons-sprite-for-rpg-maker-mv/
	public static final String RACC_UPIMG_RSC = "TrashStasher/Resource/Racc/RaccU";
	public static final String RACC_DOWNIMG_RSC = "TrashStasher/Resource/Racc/RaccD";
	public static final String RACC_LEFTIMG_RSC = "TrashStasher/Resource/Racc/RaccL";
	public static final String RACC_RIGHTIMG_RSC = "TrashStasher/Resource/Racc/RaccR";
	public static final String RACC_UPWIMG_RSC = "TrashStasher/Resource/Racc/RaccUW";
	public static final String RACC_DOWNWIMG_RSC = "TrashStasher/Resource/Racc/RaccDW";
	public static final String RACC_LEFTWIMG_RSC = "TrashStasher/Resource/Racc/RaccLW";
	public static final String RACC_RIGHTWIMG_RSC = "TrashStasher/Resource/Racc/RaccRW";
	
	public static final String MON_UPIMG_RSC = "TrashStasher/Resource/Monster/MonsterU.png";
	public static final String MON_DOWNIMG_RSC = "TrashStasher/Resource/Monster/MonsterD.png";
	public static final String MON_LEFTIMG_RSC = "TrashStasher/Resource/Monster/MonsterL.png";
	public static final String MON_RIGHTIMG_RSC = "TrashStasher/Resource/Monster/MonsterR.png";
	
	public static final String MON_HOLEIMG_RSC = "TrashStasher/Resource/MonsterHole.png";
	public static final String BG_BGIMG_RSC = "TrashStasher/Resource/Background.png";
	public static final String HUD_LINESIMG_RSC = "TrashStasher/Resource/HudLines.png";
	
	public static final String ARROW_UPIMG_RSC = "TrashStasher/Resource/Path/UpArrow.png";
	public static final String ARROW_DOWNIMG_RSC = "TrashStasher/Resource/Path/DownArrow.png";
	public static final String ARROW_LEFTIMG_RSC = "TrashStasher/Resource/Path/LeftArrow.png";
	public static final String ARROW_RIGHTIMG_RSC = "TrashStasher/Resource/Path/RightArrow.png";
	
	public static final String TREASURE_COINIMG_RSC = "TrashStasher/Resource/Treasure/coin.png";
	public static final String TREASURE_COINBIGIMG_RSC = "TrashStasher/Resource/Treasure/coinBig.png";
	
	public static final String LVL1_SCORES_TXT = "lvl1Score.txt";
	public static final String LVL2_SCORES_TXT = "lvl2Score.txt";
	public static final String LVL3_SCORES_TXT = "lvl3Score.txt";
	
	public static String Lvl1 = getLevelString("Level1StartSpots");
	public static int currLevel = 1;
	public static int raccNum = 1;
	public int lives = 1;
	public int score = 0;
	
	public static int[] highScore1 = new int[10];
	public static int[] highScore2 = new int[10];
	public static int[] highScore3 = new int[10];
	
	public Grid tsGrid;
	public Dikjstra tsDikjstra;
	public Raccoon tsRacc;
	public Monster[] tsMons = new Monster[10];
	public TreasureTracker tsTT;
	
	/**
	 * Create the TrashStasherGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public TrashStasherGame(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
				
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		addState(new StartUpState());
		addState(new PlayingState());
		addState(new GameOverState());
		
		// the sound resource takes a particularly long time to load,
		// we preload it here to (1) reduce latency when we first play it
		// and (2) because loading it will load the audio libraries and
		// unless that is done now, we can't *disable* sound as we
		// attempt to do in the startUp() method.
		ResourceManager.setFilterMethod(2);
		
		ResourceManager.loadImage(WALL_WALLIMG_RSC);
		ResourceManager.loadImage(DROP_BOXIMG_RSC);
		
		for (int i = 1; i <= 5; i ++) {
			ResourceManager.loadImage(RACC_UPIMG_RSC + Integer.toString(i) + ".png");
			ResourceManager.loadImage(RACC_DOWNIMG_RSC + Integer.toString(i) + ".png");
			ResourceManager.loadImage(RACC_LEFTIMG_RSC + Integer.toString(i) + ".png");
			ResourceManager.loadImage(RACC_RIGHTIMG_RSC + Integer.toString(i) + ".png");
			ResourceManager.loadImage(RACC_UPWIMG_RSC + Integer.toString(i) + ".png");
			ResourceManager.loadImage(RACC_DOWNWIMG_RSC + Integer.toString(i) + ".png");
			ResourceManager.loadImage(RACC_LEFTWIMG_RSC + Integer.toString(i) + ".png");
			ResourceManager.loadImage(RACC_RIGHTWIMG_RSC + Integer.toString(i) + ".png");
		}
		
		ResourceManager.loadImage(MON_UPIMG_RSC);
		ResourceManager.loadImage(MON_DOWNIMG_RSC);
		ResourceManager.loadImage(MON_LEFTIMG_RSC);
		ResourceManager.loadImage(MON_RIGHTIMG_RSC);
		
		ResourceManager.loadImage(MON_HOLEIMG_RSC);
		ResourceManager.loadImage(BG_BGIMG_RSC);
		ResourceManager.loadImage(HUD_LINESIMG_RSC);
		
		ResourceManager.loadImage(ARROW_UPIMG_RSC);
		ResourceManager.loadImage(ARROW_DOWNIMG_RSC);
		ResourceManager.loadImage(ARROW_LEFTIMG_RSC);
		ResourceManager.loadImage(ARROW_RIGHTIMG_RSC);
		
		ResourceManager.loadImage(TREASURE_COINIMG_RSC);
		ResourceManager.loadImage(TREASURE_COINBIGIMG_RSC);

		// preload all the resources to avoid warnings & minimize latency...
		//ResourceManager.loadImage(WALL_WALLIMG_RSC);
		
		tsGrid = new Grid();
		tsDikjstra = new Dikjstra(30, 15);
		tsRacc = new Raccoon(1, 1);

		for (int i = 0; i < 10; i++)
			tsMons[i] = new Monster(35, 0);
		
		tsTT = new TreasureTracker();
		

	}
	
	private static String getLevelString(String dir) {
		
		String content = "";

		try {
			File fileDir = new File(dir);
				
			BufferedReader in = new BufferedReader(
			   new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			        
			String str;  
			while ((str = in.readLine()) != null) 
				content = content + str;
			    
	                in.close();
		    } 
		    catch (UnsupportedEncodingException e) 
		    {
				System.out.println(e.getMessage());
		    } 
		    catch (IOException e) 
		    {
				System.out.println(e.getMessage());
		    }
		    catch (Exception e)
		    {
				System.out.println(e.getMessage());
		    }
		
		return content;
		
	}
	
	public static void getHighScores(String scoreTXT, int[] scoreArr) {
		
		String str;
		int i;
		File fileDir;
		BufferedReader in;

		try {
			fileDir = new File(scoreTXT);
				
			in = new BufferedReader(new InputStreamReader
					(new FileInputStream(fileDir), "UTF8"));
			        
			i = 0;
			while ((str = in.readLine()) != null) {
				scoreArr[i] = Integer.parseInt(str);
				i += 1;
			}
	        in.close();
		} 
	    catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage()); }
	    catch (IOException e) {
			System.out.println(e.getMessage());}
	    catch (Exception e){
			System.out.println(e.getMessage()); }
		
	}
	
	public static void setHighScores(String scoreTXT, int[]scoreArr) {
		
		File fileDir;
		BufferedWriter out;

		try {
			fileDir = new File(scoreTXT);
			
			if(fileDir.delete()){
			    fileDir.createNewFile();
			}else{
			    //throw an exception indicating that the file could not be cleared
			}
			
			out = new BufferedWriter(new OutputStreamWriter
					(new FileOutputStream(fileDir)));
			     
			for (int i = 0; i < 10; i++) {
				out.write(Integer.toString(scoreArr[i]));
				out.newLine();
			}

	        out.close();
		} 
	    catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage()); }
	    catch (IOException e) {
			System.out.println(e.getMessage());}
	    catch (Exception e){
			System.out.println(e.getMessage()); }
		
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new TrashStasherGame("Trash Stasher!", 1440, 900));
			app.setDisplayMode(1440, 900, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
}
