package com.unicornstudio.lanball.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.unicornstudio.lanball.LanBallGame;


public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.depth = 24;
		config.samples = 3;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new LanBallGame(), config);
	}
}
