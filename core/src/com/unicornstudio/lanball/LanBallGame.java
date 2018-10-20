package com.unicornstudio.lanball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.kotcrab.vis.ui.VisUI;
import com.unicornstudio.lanball.input.KeyboardInput;
import com.unicornstudio.lanball.map.MapService;
import com.unicornstudio.lanball.settings.VideoSettings;
import com.unicornstudio.lanball.stage.StageService;
import com.unicornstudio.lanball.ui.scene.SceneService;

import javax.inject.Inject;


public class LanBallGame extends ApplicationAdapter {

	@Inject
	private EntitiesService entitiesService;

	@Inject
	private KeyboardInput keyboardInput;

	@Inject
	private SceneService sceneService;

	@Inject
	private WorldService worldService;

	@Inject
	private MapService mapService;

	@Inject
	private StageService stageService;

	@Inject
	private PhysicsTimeStep physicsTimeStep;

	@Inject
	private GameListenerService gameListenerService;

	//private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

	@Override
	public void create () {
	    VisUI.load();
		new VideoSettings().apply();
		stageService.init();
		mapService.loadMap("exampleMap.lan");
		sceneService.showMainMenuScene();
	}

	public LanBallGame() {
		super();
	}

	@Override
	public void render () {
		Gdx.gl20.glClearColor(0.25f, 0.25f, 0.25f, 1);
		Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		if (worldService.isCreated()) {
			physicsTimeStep.processStep(worldService.getWorld());
			entitiesService.synchronizeEntitiesPosition();
			stageService.render();
			//debugRenderer.render(worldService.getWorld(), stageService.getCamera().combined);
			keyboardInput.onInput();
			for (GameListener gameListener : gameListenerService.getGameListeners()) {
				gameListener.update();
			}
		}
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
