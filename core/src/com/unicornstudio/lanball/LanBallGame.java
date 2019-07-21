package com.unicornstudio.lanball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
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

	private Game game;

	private SpriteBatch batch;

	private Injector injector;

	private static Stage stage;

	private Box2DDebugRenderer debugRenderer;

	@Override
	public void create () {
		DefaultSettings.generate();
		batch = new SpriteBatch();
		VisUI.load("skins/vis/x1/uiskin.json");
		super.create();
		new VideoSettings().apply();
		parseTemplate();
		injector = Guice.createInjector(new LanBallGameModule());
		game = injector.getInstance(Game.class);
		game.getStageService().init();
		stage = game.getStageService().getStage();

		//game.getSceneService().showMainMenuScene();
		setView(com.unicornstudio.lanball.views.Menu.class);
/*
		setView(com.unicornstudio.lanball.views.Game.class);
		com.unicornstudio.lanball.views.Game gameView = (com.unicornstudio.lanball.views.Game)getCurrentView();
		game.getStageService().setGroup(gameView.getWindow());
		//stage.setRoot(gameView.getWindow());

		game.getMapService().loadMap("exampleMap.lan");
		String ip = game.getClientService().scanPort(Ports.getList().get(1));
		if (ip != null) {
			System.out.println("client");
			game.getClientService().connect("localhost:" + Ports.getList().get(1), PlayerRole.PLAYER);
		} else {
			System.out.println("server");
			game.getServerService().start(Ports.getList().get(1));
		}
		*/
		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	protected LmlParser createParser() {
		return VisLml.parser()
				.actions("global", new GlobalActions())
				.i18nBundle(I18NBundle.createBundle(Gdx.files.internal("i18n/nls")))
				.build();
	}

	public LanBallGame() {
		super();
	}

	@Override
	public void render () {
		game.render();
		super.render();
		if (game.getWorldService().getMapWorld() != null) {
			Matrix4 matrix4 = new Matrix4(new OrthographicCamera(Screen.getWidth(), Screen.getHeight()).combined);
			matrix4.scale(10f, 10f, 1f);
			//debugRenderer.render(game.getWorldService().getWorld(), matrix4);
		}
	}

	public static Stage newStage() {
		return new Stage(new ScreenViewport(new OrthographicCamera(Screen.getWidth(), Screen.getHeight())));
	}

	public static Stage getStage() {
		return stage;
	}

	@Override
	public void dispose() {
		super.dispose();
		Disposables.disposeOf(batch);
		VisUI.dispose();
	}

	private void parseTemplate() {
		getParser().parseTemplate(Gdx.files.internal("views/macros/Global.lml"));
	}

	@Override
	protected AbstractLmlView getInstanceOf(final Class<? extends AbstractLmlView> viewClass) {
		return injector.getInstance(viewClass);
	}

}
