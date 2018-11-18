package com.unicornstudio.lanball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.unicornstudio.lanball.actions.GlobalActions;
import com.unicornstudio.lanball.network.common.Ports;
import com.unicornstudio.lanball.settings.VideoSettings;
import com.unicornstudio.lanball.views.Menu;
import com.unicornstudio.lanball.views.ServerBrowser;


public class LanBallGame extends LmlApplicationListener {

	private Game game;

	private SpriteBatch batch;

	private Injector injector;

	//private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

	@Override
	public void create () {
		batch = new SpriteBatch();
		VisUI.load("skins/vis/x1/uiskin.json");
		super.create();
		new VideoSettings().apply();
		parseTemplate();
		injector = Guice.createInjector(new LanBallGameModule());
		game = injector.getInstance(Game.class);
		game.getStageService().init();
	//	mapService.loadMap("core/assets/exampleMap.lan");
		//game.getSceneService().showMainMenuScene();
		setView(Menu.class);
		game.getServerService().start(Ports.getList().get(0));
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
	}

	public static Stage newStage() {
		return new Stage(new ScreenViewport(new OrthographicCamera(Screen.getWidth(), Screen.getHeight())));
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
	public void setView(final Class<? extends AbstractLmlView> viewClass) {
		AbstractLmlView view = injector.getInstance(viewClass);
		initiateView(view);
		setView(view, null);
	}

}
