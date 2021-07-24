package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import util.Assets;
import util.Enums.Difficulty;

public class HackerGame extends Game {

	private static final String TAG = HackerGame.class.getName();

	private AssetManager am;
	private SplashWorker splashWorker;
	private Music mainMenuMusic;
	private Music playScreenMusic;
	private SpriteBatch batch;
	private boolean musicOn;

	@Override
	public void create() {
		am = new AssetManager();
		Assets.instance.init(am);
		batch = new SpriteBatch();

		mainMenuMusic = Assets.instance.musicClass.mainMenuMusic;
		playScreenMusic = Assets.instance.musicClass.playScreenMusic;
		musicOn = true;

		if(am.isFinished()){
			splashWorker.closeSplashScreen();
			showCodenameScreen();
		}
	}

	@Override
	public void dispose() {
		Assets.instance.dispose();
		batch.dispose();
	}

	public void showCodenameScreen(){
		setScreen(new CodenameScreen(this, this.batch));
	}

	public void showMenuScreen(){
		setPlayScreenMusicOff();
		setScreen(new MenuScreen(this, this.batch));

		if(musicOn){
			setMenuScreenMusicOn();
		} else{
			setMenuScreenMusicPause();
		}
	}

	public void showOptionsScreen(){
		setScreen(new OptionsScreen(this, this.batch));
	}

	public void showHelpScreen(){
		setScreen(new HelpScreen(this, this.batch));
	}

	public void showDifficultyScreen(){
		setScreen(new DifficultyScreen(this, this.batch));
	}

	public void showGameplayScreen(Difficulty difficulty){
		setMenuScreenMusicOff();
		Assets.instance.initResourcesFilePath();
		setScreen(new GameplayScreen(this, difficulty, this.batch));

		if(musicOn){
			setPlayScreeMusicOn();
		} else{
			setPlayScreenMusicPause();
		}

	}

	public void showHighScoresScreen(){
		setScreen(new HighScoresScreen(this, this.batch));
	}

	public void setPlayScreeMusicOn(){
		playScreenMusic.setLooping(true);
		playScreenMusic.play();
	}

	public void setPlayScreenMusicOff(){
		playScreenMusic.stop();
	}

	public void setPlayScreenMusicPause(){
		playScreenMusic.pause();
	}

	public void setMenuScreenMusicOn(){
		mainMenuMusic.setLooping(true);
		mainMenuMusic.play();
	}

	public void setMenuScreenMusicOff(){
		mainMenuMusic.stop();
	}

	public void setMenuScreenMusicPause(){
		mainMenuMusic.pause();
	}

	public void setMusicOnOrOff(){
		if(musicOn){
			musicOn = false;
		} else{
			musicOn = true;
		}
	}

	public boolean getMusicOn(){
		return this.musicOn;
	}

	public SplashWorker getSplashWorker(){
		return splashWorker;
	}

	public void setSplashWorker(SplashWorker splashWorker){
		this.splashWorker = splashWorker;
	}

}
