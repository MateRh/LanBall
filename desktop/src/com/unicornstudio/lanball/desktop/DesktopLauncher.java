package com.unicornstudio.lanball.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.unicornstudio.lanball.LanBallGame;


public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.depth = 24;
		config.foregroundFPS = 0;
		config.addIcon("images/icon.png", Files.FileType.Internal);
		new LwjglApplication(new LanBallGame(), config);
	}
}
