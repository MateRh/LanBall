package com.unicornstudio.lanball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kotcrab.vis.ui.VisUI;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.LanBallGameModule;


public class DesktopLauncher {

	public static void main (String[] arg) {
		Injector injector = Guice.createInjector(new LanBallGameModule());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.depth = 24;
		config.samples = 4;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		new LwjglApplication(injector.getInstance(LanBallGame.class), config);
	}
}
