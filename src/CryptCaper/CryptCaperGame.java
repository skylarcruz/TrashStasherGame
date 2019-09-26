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
	public static final String EXP_EXPIMG_RSC = "CryptCaper/Resource/Explorer.png";
	public static final String MON_MONIMG_RSC = "CryptCaper/Resource/Monster.png";
	
	public static final String Lvl1 = getLevelString("Level1");
	public static int currLevel = 1;
	public static Grid ccGrid;
	public static Explorer ccExplorer;
	public static Monster ccMon;
	public static Monster ccMon2;
	public static Monster ccMon3;
	public static Monster ccMon4;
	public static Monster ccMon5;
	
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
		ResourceManager.loadImage(WALL_WALLIMG_RSC);
		ResourceManager.loadImage(EXP_EXPIMG_RSC);
		ResourceManager.loadImage(MON_MONIMG_RSC);
		
		addState(new StartUpState());
		addState(new PlayingState());
		addState(new GameOverState());
		
		// the sound resource takes a particularly long time to load,
		// we preload it here to (1) reduce latency when we first play it
		// and (2) because loading it will load the audio libraries and
		// unless that is done now, we can't *disable* sound as we
		// attempt to do in the startUp() method.

		// preload all the resources to avoid warnings & minimize latency...
		//ResourceManager.loadImage(WALL_WALLIMG_RSC);
		
		ccGrid = new Grid();
		ccExplorer = new Explorer(1, 1);
		ccMon = new Monster(28, 1);
		ccMon2 = new Monster(28, 1); 
		ccMon3 = new Monster(28, 1); 
		ccMon4 = new Monster(28, 1); 
		ccMon5 = new Monster(28, 1); 
		

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
