package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.HackerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		HackerGame hackerGame = new HackerGame();
		hackerGame.setSplashWorker(new DesktopSplashWorker());
		config.title = "Hacker-for-Hire: Break the System!";
		config.width = 665;
		config.height = 665;
		config.resizable = false;
		config.addIcon("icon-128.png", Files.FileType.Internal);
		config.addIcon("icon-32.png", Files.FileType.Internal);
		config.addIcon("icon-16.png", Files.FileType.Internal);
		new LwjglApplication(hackerGame, config);
	}
}
