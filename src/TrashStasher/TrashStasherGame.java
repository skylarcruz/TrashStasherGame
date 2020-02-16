package TrashStasher;

import jig.Entity;
import jig.ResourceManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TrashStasherGame extends StateBasedGame {
	
	public static AppGameContainer app;
	public static boolean fullscreen = false;
	
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;

	public final int ScreenWidth;
	public final int ScreenHeight;

	// Sounds
	// https://freesound.org/people/akelley6/sounds/453027/
	public static final String MENU_CLICKSND_RSC = "TrashStasher/Resource/Sounds/click.ogg";
	// https://freesound.org/people/ivolipa/sounds/328730/
	public static final String DOG_BARKSND_RSC = "TrashStasher/Resource/Sounds/dogBark.ogg";
	// https://freesound.org/people/SomeGuy22/sounds/431327/
	public static final String TRASH_PICKUPSND_RSC = "TrashStasher/Resource/Sounds/pickup.ogg";
	// https://freesound.org/people/ProjectsU012/sounds/341695/
	public static final String TRASH_DEPOSITSND_RSC = "TrashStasher/Resource/Sounds/depositTrash.ogg";
	// https://freesound.org/people/qubodup/sounds/172589/
	public static final String POWER_SPEEDSND_RSC = "TrashStasher/Resource/Sounds/SpeedUp.ogg";
	// https://freesound.org/people/bbrocer/sounds/398692/
	public static final String POWER_DIGSND_RSC = "TrashStasher/Resource/Sounds/dig.ogg";
	// https://freesound.org/people/qubodup/sounds/219570/
	public static final String POWER_PAUSESND_RSC = "TrashStasher/Resource/Sounds/pause.ogg";
	// https://freesound.org/people/qubodup/sounds/219571/
	public static final String POWER_UNPAUSESND_RSC = "TrashStasher/Resource/Sounds/unpause.ogg";
	
	// https://freemusicarchive.org/music/Kero_Kero_Bonito
	public static final String MUSIC_TITLESND_RSC = "TrashStasher/Resource/Sounds/KeroKeroAzureflux.ogg";
	// https://freemusicarchive.org/music/bit_shifter/Pocket_Boy/11_bit_shifter_-_reformat_the_planet__azureflux_remix
	public static final String MUSIC_GAMESND_RSC = "TrashStasher/Resource/Sounds/BitShifterAzureflux.ogg";
	
	public static final String TITLE_NAMEIMG_RSC = "TrashStasher/Resource/titleName.png";
	public static final String DROP_BOXIMG_RSC = "TrashStasher/Resource/City/DropCan2.png";
	
	
	// Wall Graphics
	//public static final String WALL_-IMG_RSC = "TrashStasher/Resource/City/Walls/-.png";
	public static final String WALL_BASEIMG_RSC = "TrashStasher/Resource/City/Walls/base.png";
	public static final String WALL_SQUAREIMG_RSC = "TrashStasher/Resource/City/Walls/Square.png";
	
	public static final String WALL_CDLIMG_RSC = "TrashStasher/Resource/City/Walls/CornerDL.png";
	public static final String WALL_CDRIMG_RSC = "TrashStasher/Resource/City/Walls/CornerDR.png";
	public static final String WALL_CULIMG_RSC = "TrashStasher/Resource/City/Walls/CornerUL.png";
	public static final String WALL_CURIMG_RSC = "TrashStasher/Resource/City/Walls/CornerUR.png";
	
	public static final String WALL_EDLIMG_RSC = "TrashStasher/Resource/City/Walls/ElbowDL.png";
	public static final String WALL_EDRIMG_RSC = "TrashStasher/Resource/City/Walls/ElbowDR.png";
	public static final String WALL_EULIMG_RSC = "TrashStasher/Resource/City/Walls/ElbowUL.png";
	public static final String WALL_EURIMG_RSC = "TrashStasher/Resource/City/Walls/ElbowUR.png";
	
	public static final String WALL_EFDLIMG_RSC = "TrashStasher/Resource/City/Walls/ElbowFragDL.png";
	public static final String WALL_EFDRIMG_RSC = "TrashStasher/Resource/City/Walls/ElbowFragDR.png";
	public static final String WALL_EFULIMG_RSC = "TrashStasher/Resource/City/Walls/ElbowFragUL.png";
	public static final String WALL_EFURIMG_RSC = "TrashStasher/Resource/City/Walls/ElbowFragUR.png";
	
	public static final String WALL_EndDIMG_RSC = "TrashStasher/Resource/City/Walls/EndD.png";
	public static final String WALL_EndLIMG_RSC = "TrashStasher/Resource/City/Walls/EndL.png";
	public static final String WALL_EndRIMG_RSC = "TrashStasher/Resource/City/Walls/EndR.png";
	public static final String WALL_EndUIMG_RSC = "TrashStasher/Resource/City/Walls/EndU.png";
	
	public static final String WALL_OSDIMG_RSC = "TrashStasher/Resource/City/Walls/oneSideD.png";
	public static final String WALL_OSLIMG_RSC = "TrashStasher/Resource/City/Walls/oneSideL.png";
	public static final String WALL_OSRIMG_RSC = "TrashStasher/Resource/City/Walls/oneSideR.png";
	public static final String WALL_OSUIMG_RSC = "TrashStasher/Resource/City/Walls/oneSideU.png";
	
	public static final String WALL_TDIMG_RSC = "TrashStasher/Resource/City/Walls/TfragD.png";
	public static final String WALL_TLIMG_RSC = "TrashStasher/Resource/City/Walls/TfragL.png";
	public static final String WALL_TRIMG_RSC = "TrashStasher/Resource/City/Walls/TfragR.png";
	public static final String WALL_TUIMG_RSC = "TrashStasher/Resource/City/Walls/TfragU.png";
	
	public static final String WALL_TUBEHIMG_RSC = "TrashStasher/Resource/City/Walls/TubeH.png";
	public static final String WALL_TUBEVIMG_RSC = "TrashStasher/Resource/City/Walls/TubeV.png";
	
	public static final String WALL_HOLEDIMG_RSC = "TrashStasher/Resource/City/Walls/MonHoleD.png";
	public static final String WALL_HOLELIMG_RSC = "TrashStasher/Resource/City/Walls/MonHoleL.png";
	public static final String WALL_HOLERIMG_RSC = "TrashStasher/Resource/City/Walls/MonHoleR.png";
	public static final String WALL_HOLEUIMG_RSC = "TrashStasher/Resource/City/Walls/MonHoleU.png";
	
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
	
	public static final String DOG_UPIMG_RSC = "TrashStasher/Resource/Dog/dogU.png";
	public static final String DOG_DOWNIMG_RSC = "TrashStasher/Resource/Dog/dogD.png";
	public static final String DOG_LEFTIMG_RSC = "TrashStasher/Resource/Dog/dogL.png";
	public static final String DOG_RIGHTIMG_RSC = "TrashStasher/Resource/Dog/dogR.png";
	public static final String DOG_UPWIMG_RSC = "TrashStasher/Resource/Dog/dogUW.png";
	public static final String DOG_DOWNWIMG_RSC = "TrashStasher/Resource/Dog/dogDW.png";
	public static final String DOG_LEFTWIMG_RSC = "TrashStasher/Resource/Dog/dogLW.png";
	public static final String DOG_RIGHTWIMG_RSC = "TrashStasher/Resource/Dog/dogRW.png";
	
	
	// Trash Graphics
	// http://jnoodle.com/sparechange/2013/10/23/106/
	public static final String TRASH_APPLEIMG_RSC = "TrashStasher/Resource/Trash/apple.png";
	public static final String TRASH_B_APPLEIMG_RSC = "TrashStasher/Resource/Trash/appleBig.png";
	public static final String TRASH_BOTTLEIMG_RSC = "TrashStasher/Resource/Trash/bottle.png";
	public static final String TRASH_B_BOTTLEIMG_RSC = "TrashStasher/Resource/Trash/bottleBig.png";
	public static final String TRASH_CANIMG_RSC = "TrashStasher/Resource/Trash/can.png";
	public static final String TRASH_B_CANIMG_RSC = "TrashStasher/Resource/Trash/canBig.png";
	public static final String TRASH_DONUTIMG_RSC = "TrashStasher/Resource/Trash/donut.png";
	public static final String TRASH_B_DONUTIMG_RSC = "TrashStasher/Resource/Trash/donutBig.png";
	public static final String TRASH_FISHIMG_RSC = "TrashStasher/Resource/Trash/fish.png";
	public static final String TRASH_B_FISHIMG_RSC = "TrashStasher/Resource/Trash/fishBig.png";
	public static final String TRASH_FISHBONEIMG_RSC = "TrashStasher/Resource/Trash/fishBone.png";
	public static final String TRASH_B_FISHBONEIMG_RSC = "TrashStasher/Resource/Trash/fishBoneBig.png";
	public static final String TRASH_FORKIMG_RSC = "TrashStasher/Resource/Trash/fork.png";
	public static final String TRASH_B_FORKIMG_RSC = "TrashStasher/Resource/Trash/forkBig.png";
	public static final String TRASH_FRIESIMG_RSC = "TrashStasher/Resource/Trash/fries.png";
	public static final String TRASH_B_FRIESIMG_RSC = "TrashStasher/Resource/Trash/friesBig.png";
	public static final String TRASH_PAPERIMG_RSC = "TrashStasher/Resource/Trash/paper.png";
	public static final String TRASH_B_PAPERIMG_RSC = "TrashStasher/Resource/Trash/paperBig.png";
	public static final String TRASH_PIZZAIMG_RSC = "TrashStasher/Resource/Trash/pizza.png";
	public static final String TRASH_B_PIZZAIMG_RSC = "TrashStasher/Resource/Trash/pizzaBig.png";
	
	public static final String BG_BGIMG_RSC = "TrashStasher/Resource/Background.png";
	public static final String HUD_LINESIMG_RSC = "TrashStasher/Resource/HudLines.png";
	
	public static final String ARROW_UPIMG_RSC = "TrashStasher/Resource/Path/UpArrow.png";
	public static final String ARROW_DOWNIMG_RSC = "TrashStasher/Resource/Path/DownArrow.png";
	public static final String ARROW_LEFTIMG_RSC = "TrashStasher/Resource/Path/LeftArrow.png";
	public static final String ARROW_RIGHTIMG_RSC = "TrashStasher/Resource/Path/RightArrow.png";
	
	public static final String POWER_DIGIMG_RSC = "TrashStasher/Resource/Powers/dig.png";
	public static final String POWER_SPDIMG_RSC = "TrashStasher/Resource/Powers/speedy.png";
	public static final String POWER_PAUSEIMG_RSC = "TrashStasher/Resource/Powers/pause.png";
	
	
	public static final String LVL1_SCORES_TXT = "lvl1Score.txt";
	public static final String LVL2_SCORES_TXT = "lvl2Score.txt";
	public static final String LVL3_SCORES_TXT = "lvl3Score.txt";
	
	public static String Lvl1 = getLevelString("Level1revamp");
	public static String Lvl2 = getLevelString("Level2revamp");
	public static String Lvl3 = getLevelString("Level3revamp");
	public static int currLevel = 1;
	public static int raccNum = 1;
	public int lives = 3;
	public int score = 0;
	
	public static int[] highScore1 = new int[10];
	public static String[] hsNames1 = new String[10];
	public static int[] highScore2 = new int[10];
	public static String[] hsNames2 = new String[10];
	public static int[] highScore3 = new int[10];
	public static String[] hsNames3 = new String[10];
	
	public static boolean cheatMode = false;
	
	public Grid tsGrid;
	public Dikjstra tsDikjstra;
	public Raccoon tsRacc;
	public Dog[] tsDogs = new Dog[10];
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
		
		ResourceManager.setFilterMethod(2);
		
		// the sound resource takes a particularly long time to load,
		// we preload it here to (1) reduce latency when we first play it
		// and (2) because loading it will load the audio libraries and
		// unless that is done now, we can't *disable* sound as we
		// attempt to do in the startUp() method.
		
		ResourceManager.loadSound(MENU_CLICKSND_RSC);
		ResourceManager.loadSound(DOG_BARKSND_RSC);
		ResourceManager.loadSound(TRASH_DEPOSITSND_RSC);
		ResourceManager.loadSound(TRASH_PICKUPSND_RSC);
		ResourceManager.loadSound(POWER_SPEEDSND_RSC);
		ResourceManager.loadSound(POWER_DIGSND_RSC);
		ResourceManager.loadSound(POWER_PAUSESND_RSC);
		ResourceManager.loadSound(POWER_UNPAUSESND_RSC);
		ResourceManager.loadMusic(MUSIC_TITLESND_RSC);
		ResourceManager.loadMusic(MUSIC_GAMESND_RSC);
		
		
		
		
		ResourceManager.loadImage(TITLE_NAMEIMG_RSC);
		ResourceManager.loadImage(DROP_BOXIMG_RSC);
		
		// Load Walls
		ResourceManager.loadImage(WALL_BASEIMG_RSC);
		ResourceManager.loadImage(WALL_SQUAREIMG_RSC);
		
		ResourceManager.loadImage(WALL_CDLIMG_RSC);
		ResourceManager.loadImage(WALL_CDRIMG_RSC);
		ResourceManager.loadImage(WALL_CULIMG_RSC);
		ResourceManager.loadImage(WALL_CURIMG_RSC);
		
		ResourceManager.loadImage(WALL_EDLIMG_RSC);
		ResourceManager.loadImage(WALL_EDRIMG_RSC);
		ResourceManager.loadImage(WALL_EULIMG_RSC);
		ResourceManager.loadImage(WALL_EURIMG_RSC);
		
		ResourceManager.loadImage(WALL_EFDLIMG_RSC);
		ResourceManager.loadImage(WALL_EFDRIMG_RSC);
		ResourceManager.loadImage(WALL_EFULIMG_RSC);
		ResourceManager.loadImage(WALL_EFURIMG_RSC);
		
		ResourceManager.loadImage(WALL_EndDIMG_RSC);
		ResourceManager.loadImage(WALL_EndLIMG_RSC);
		ResourceManager.loadImage(WALL_EndRIMG_RSC);
		ResourceManager.loadImage(WALL_EndUIMG_RSC);
		
		ResourceManager.loadImage(WALL_OSDIMG_RSC);
		ResourceManager.loadImage(WALL_OSLIMG_RSC);
		ResourceManager.loadImage(WALL_OSRIMG_RSC);
		ResourceManager.loadImage(WALL_OSUIMG_RSC);
		
		ResourceManager.loadImage(WALL_TDIMG_RSC);
		ResourceManager.loadImage(WALL_TLIMG_RSC);
		ResourceManager.loadImage(WALL_TRIMG_RSC);
		ResourceManager.loadImage(WALL_TUIMG_RSC);
		
		ResourceManager.loadImage(WALL_TUBEHIMG_RSC);
		ResourceManager.loadImage(WALL_TUBEVIMG_RSC);
		
		ResourceManager.loadImage(WALL_HOLEDIMG_RSC);
		ResourceManager.loadImage(WALL_HOLELIMG_RSC);
		ResourceManager.loadImage(WALL_HOLERIMG_RSC);
		ResourceManager.loadImage(WALL_HOLEUIMG_RSC);
		
		// Load Raccoons
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
		
		// Load Dog
		ResourceManager.loadImage(DOG_UPIMG_RSC);
		ResourceManager.loadImage(DOG_DOWNIMG_RSC);
		ResourceManager.loadImage(DOG_LEFTIMG_RSC);
		ResourceManager.loadImage(DOG_RIGHTIMG_RSC);
		ResourceManager.loadImage(DOG_UPWIMG_RSC);
		ResourceManager.loadImage(DOG_DOWNWIMG_RSC);
		ResourceManager.loadImage(DOG_LEFTWIMG_RSC);
		ResourceManager.loadImage(DOG_RIGHTWIMG_RSC);
		
		// Load Trash
		ResourceManager.loadImage(TRASH_APPLEIMG_RSC);
		ResourceManager.loadImage(TRASH_B_APPLEIMG_RSC);
		ResourceManager.loadImage(TRASH_BOTTLEIMG_RSC);
		ResourceManager.loadImage(TRASH_B_BOTTLEIMG_RSC);
		ResourceManager.loadImage(TRASH_CANIMG_RSC);
		ResourceManager.loadImage(TRASH_B_CANIMG_RSC);
		ResourceManager.loadImage(TRASH_DONUTIMG_RSC);
		ResourceManager.loadImage(TRASH_B_DONUTIMG_RSC);
		ResourceManager.loadImage(TRASH_FISHIMG_RSC);
		ResourceManager.loadImage(TRASH_B_FISHIMG_RSC);
		ResourceManager.loadImage(TRASH_FISHBONEIMG_RSC);
		ResourceManager.loadImage(TRASH_B_FISHBONEIMG_RSC);
		ResourceManager.loadImage(TRASH_FORKIMG_RSC);
		ResourceManager.loadImage(TRASH_B_FORKIMG_RSC);
		ResourceManager.loadImage(TRASH_FRIESIMG_RSC);
		ResourceManager.loadImage(TRASH_B_FRIESIMG_RSC);
		ResourceManager.loadImage(TRASH_PAPERIMG_RSC);
		ResourceManager.loadImage(TRASH_B_PAPERIMG_RSC);
		ResourceManager.loadImage(TRASH_PIZZAIMG_RSC);
		ResourceManager.loadImage(TRASH_B_PIZZAIMG_RSC);
		
		ResourceManager.loadImage(BG_BGIMG_RSC);
		ResourceManager.loadImage(HUD_LINESIMG_RSC);
		
		ResourceManager.loadImage(ARROW_UPIMG_RSC);
		ResourceManager.loadImage(ARROW_DOWNIMG_RSC);
		ResourceManager.loadImage(ARROW_LEFTIMG_RSC);
		ResourceManager.loadImage(ARROW_RIGHTIMG_RSC);
		
		ResourceManager.loadImage(POWER_DIGIMG_RSC);
		ResourceManager.loadImage(POWER_SPDIMG_RSC);
		ResourceManager.loadImage(POWER_PAUSEIMG_RSC);

		// preload all the resources to avoid warnings & minimize latency...
		
		tsGrid = new Grid();
		tsDikjstra = new Dikjstra(30, 15);
		tsRacc = new Raccoon(1, 1);

		for (int i = 0; i < 10; i++)
			tsDogs[i] = new Dog(35, 0);
		
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
	
	public static void getHighScores(String scoreTXT, int[] scoreArr, String[] names) {
		
		String str;
		String scoreParse;
		char c;
		int i;
		File fileDir;
		BufferedReader in;

		try {
			fileDir = new File(scoreTXT);
				
			in = new BufferedReader(new InputStreamReader
					(new FileInputStream(fileDir), "UTF8"));
			        
			i = 0;
			while ((str = in.readLine()) != null) {
				names[i] = "";
				scoreParse= "";
				for (int j = 0; j < str.length(); j ++) {
					c = str.charAt(j);
					if (j < 3) {
						names[i] += String.valueOf(c);
					}
					else {
						scoreParse += String.valueOf(c);
					}
				}
				scoreArr[i] = Integer.parseInt(scoreParse);
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
	
	public static void setHighScores(String scoreTXT, int[] scoreArr, String[] names) {
		
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
				out.write(names[i] += Integer.toString(scoreArr[i]));
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
		//AppGameContainer app;
		try {
			//app = new AppGameContainer(new TrashStasherGame("Trash Stasher!", 1440, 900));
			app = new AppGameContainer(new ScalableGame(new TrashStasherGame("Trash Stasher!", 1440, 900), 1440, 900, true));
			app.setDisplayMode(1440, 900, false);
			//app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
			app.setShowFPS(false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
	public static void changeResolution() {
		if (fullscreen == false)
			try {
				app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
				fullscreen = true;
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else {
			try {
				app.setDisplayMode(1440, 900, false);
				fullscreen = false;
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
