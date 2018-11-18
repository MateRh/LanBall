package com.unicornstudio.lanball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.unicornstudio.lanball.LanBallGame;


public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.depth = 24;
		config.samples = 4;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		new LwjglApplication(new LanBallGame(), config);
	}
}
