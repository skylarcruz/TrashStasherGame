package CryptCaper;

import jig.Entity;
import jig.ResourceManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class CryptCaperGame extends StateBasedGame {
	
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;

	public final int ScreenWidth;
	public final int ScreenHeight;
	
	public static final String WALL_WALLIMG_RSC = "CryptCaper/Resource/WallTest.png";
	
	public static final String EXP_UPIMG_RSC = "CryptCaper/Resource/Explorer/ExplorerU.png";
	public static final String EXP_DOWNIMG_RSC = "CryptCaper/Resource/Explorer/ExplorerD.png";
	public static final String EXP_LEFTIMG_RSC = "CryptCaper/Resource/Explorer/ExplorerL.png";
	public static final String EXP_RIGHTIMG_RSC = "CryptCaper/Resource/Explorer/ExplorerR.png";
	
	public static final String MON_UPIMG_RSC = "CryptCaper/Resource/Monster/MonsterU.png";
	public static final String MON_DOWNIMG_RSC = "CryptCaper/Resource/Monster/MonsterD.png";
	public static final String MON_LEFTIMG_RSC = "CryptCaper/Resource/Monster/MonsterL.png";
	public static final String MON_RIGHTIMG_RSC = "CryptCaper/Resource/Monster/MonsterR.png";
	
	public static final String MON_HOLEIMG_RSC = "CryptCaper/Resource/MonsterHole.png";
	public static final String BG_BGIMG_RSC = "CryptCaper/Resource/Background.png";
	
	public static final String ARROW_UPIMG_RSC = "CryptCaper/Resource/Path/UpArrow.png";
	public static final String ARROW_DOWNIMG_RSC = "CryptCaper/Resource/Path/DownArrow.png";
	public static final String ARROW_LEFTIMG_RSC = "CryptCaper/Resource/Path/LeftArrow.png";
	public static final String ARROW_RIGHTIMG_RSC = "CryptCaper/Resource/Path/RightArrow.png";
	
	public static final String TREASURE_COINIMG_RSC = "CryptCaper/Resource/Treasure/coin.png";
	
	public static String Lvl1 = getLevelString("Level1StartSpots");
	public static int currLevel = 1;
	public int lives = 3;
	
	public Grid ccGrid;
	public Dikjstra ccDikjstra;
	public Explorer ccExplorer;
	public Monster[] ccMons = new Monster[10];
	public TreasureTracker ccTT;
	
	/**
	 * Create the CryptCaperGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public CryptCaperGame(String title, int width, int height) {
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
		ResourceManager.loadImage(WALL_WALLIMG_RSC);
		
		ResourceManager.loadImage(EXP_UPIMG_RSC);
		ResourceManager.loadImage(EXP_DOWNIMG_RSC);
		ResourceManager.loadImage(EXP_LEFTIMG_RSC);
		ResourceManager.loadImage(EXP_RIGHTIMG_RSC);
		
		ResourceManager.loadImage(MON_UPIMG_RSC);
		ResourceManager.loadImage(MON_DOWNIMG_RSC);
		ResourceManager.loadImage(MON_LEFTIMG_RSC);
		ResourceManager.loadImage(MON_RIGHTIMG_RSC);
		
		ResourceManager.loadImage(MON_HOLEIMG_RSC);
		ResourceManager.loadImage(BG_BGIMG_RSC);
		
		ResourceManager.loadImage(ARROW_UPIMG_RSC);
		ResourceManager.loadImage(ARROW_DOWNIMG_RSC);
		ResourceManager.loadImage(ARROW_LEFTIMG_RSC);
		ResourceManager.loadImage(ARROW_RIGHTIMG_RSC);
		
		ResourceManager.loadImage(TREASURE_COINIMG_RSC);

		// preload all the resources to avoid warnings & minimize latency...
		//ResourceManager.loadImage(WALL_WALLIMG_RSC);
		
		ccGrid = new Grid();
		ccDikjstra = new Dikjstra(30, 15);
		ccExplorer = new Explorer(1, 1);

		for (int i = 0; i < 10; i++)
			ccMons[i] = new Monster(35, 0);
		
		ccTT = new TreasureTracker();
		

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
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new CryptCaperGame("Crypt Caper!", 1440, 900));
			app.setDisplayMode(1440, 900, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
}
