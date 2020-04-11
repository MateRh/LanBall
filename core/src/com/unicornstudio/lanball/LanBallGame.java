package com.unicornstudio.lanball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.github.czyzby.lml.util.LmlApplicationListener;
import com.github.czyzby.lml.vis.util.VisLml;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kotcrab.vis.ui.VisUI;
import com.unicornstudio.lanball.core.GlobalActions;
import com.unicornstudio.lanball.core.Game;
import com.unicornstudio.lanball.core.Screen;
import com.unicornstudio.lanball.prefernces.DefaultSettings;
import com.unicornstudio.lanball.prefernces.VideoSettings;


public class LanBallGame extends LmlApplicationListener {

	private final static String VIS_UI_SKIN = "skins/vis/x1/uiskin.json";

	private final static String GLOBAL_MACRO = "views/macros/Global.lml";

	private final static String PROPERTIES_PATH = "i18n/nls";

	private Game game;

	private Injector injector;

	private double lastTimeMillis = System.currentTimeMillis();

	//private Box2DDebugRenderer debugRenderer;

	@Override
	public void create () {
		DefaultSettings.generate();
		VisUI.load(VIS_UI_SKIN);
		super.create();
		new VideoSettings().apply();
		parseTemplate();
		injector = Guice.createInjector(new LanBallGameModule());
		game = injector.getInstance(Game.class);
		setView(com.unicornstudio.lanball.views.Menu.class);
		//debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	protected LmlParser createParser() {
		return VisLml.parser()
				.actions("global", new GlobalActions())
				.i18nBundle(I18NBundle.createBundle(Gdx.files.internal(PROPERTIES_PATH)))
				.build();
	}

	public LanBallGame() {
		super();
	}

	@Override
	public void render() {
		double frameTimeMillis = System.currentTimeMillis() - lastTimeMillis;
		try {
			game.render(frameTimeMillis);
			super.render();
			//debugRenderer.render(game.getWorldService().getWorld(), this.getCurrentView().getStage().getCamera().combined.scale(Screen.getPixelPerMeter(), Screen.getPixelPerMeter(), Screen.getPixelPerMeter()));
		} catch (Exception e) {
			System.out.println("Main loop exception: " + e);
		}
		lastTimeMillis = System.currentTimeMillis();
	}

	@Override
	public void dispose() {
		super.dispose();
		VisUI.dispose();
	}

	private void parseTemplate() {
		getParser().parseTemplate(Gdx.files.internal(GLOBAL_MACRO));
	}

	@Override
	protected AbstractLmlView getInstanceOf(final Class<? extends AbstractLmlView> viewClass) {
		return injector.getInstance(viewClass);
	}

}
