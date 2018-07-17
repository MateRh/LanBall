package com.unicornstudio.lanball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.unicornstudio.lanball.input.KeyboardInput;
import com.unicornstudio.lanball.io.mappers.MapMapper;
import com.unicornstudio.lanball.map.MapDto;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.settings.VideoSettings;
import com.unicornstudio.lanball.utils.PhysicsEntityCreator;
import com.unicornstudio.lanball.utils.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.utils.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.utils.dto.ShapeDto;
import com.unicornstudio.lanball.video.BallActor;
import com.unicornstudio.lanball.video.MapBackground;
import com.unicornstudio.lanball.video.PlayerActor;

import javax.inject.Inject;


public class LanBallGame extends ApplicationAdapter {

	private Stage stage;

	private World world;

	private Box2DDebugRenderer debugRenderer;

	private PhysicsTimeStep physicsTimeStep;


	private EntitiesService entitiesService;

	private KeyboardInput keyboardInput;

	private final BallSensorListener ballSensorListener;

	@Inject
	public LanBallGame(EntitiesService entitiesService, KeyboardInput keyboardInput, BallSensorListener ballSensorListener) {
		this.entitiesService = entitiesService;
		this.keyboardInput = keyboardInput;
		this.ballSensorListener = ballSensorListener;
	}


	@Override
	public void create () {
	    //VisUI.load();
        physicsTimeStep = new PhysicsTimeStep();
        WorldService worldService = new WorldService();


		debugRenderer = new Box2DDebugRenderer();
		new VideoSettings().apply();
        FileHandle fileHandle = new FileHandle("exampleMap.lan");
        MapDto mapDto = MapMapper.map(fileHandle).orElse(null);
        worldService.create(mapDto);
        world = worldService.getWorld().getWorld();
        stage = new Stage(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
        stage.getCamera().position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        //stage.getCamera().view.scl(1);
        //new MapChooser().show(stage);

        //Renderers.add(new MapScene(mapDto));

        stage.addActor(worldService.getWorld().getMapBackground());
        //stage.addActor(new WorldBackground(mapDto.getWorld().getBackground().getColor()));
        stage.addActor(new MapBackground(mapDto.getWorld().getSize(), mapDto.getWorld().getForeground()));
        BallActor ball = new BallActor(mapDto.getSettings().getBall());
        stage.addActor(ball);

        PlayerActor playerActor = new PlayerActor(mapDto.getSettings().getTeams().get(0));
        stage.addActor(playerActor);

        //ball.addAction(Actions.moveTo(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/3, 4));
        PhysicsEntity physicsEntity = PhysicsEntityCreator.createEntity(world,
                new BodyDefinitionDto(BodyDef.BodyType.DynamicBody, new Vector2(mapDto.getWorld().getSize().getWidth()/2, mapDto.getWorld().getSize().getHeight()/2), 0.2f),
                new ShapeDto(Shape.Type.Circle, mapDto.getSettings().getBall().getSize()/2f, null, null, null),
                new FixtureDefinitionDto(0f, 0.0001f, 0.1f, false)
        );

        PhysicsEntity playerActorPhysicsEntity = PhysicsEntityCreator.createEntity(world,
                new BodyDefinitionDto(BodyDef.BodyType.DynamicBody, new Vector2(400, 400), 0.1f),
                new ShapeDto(Shape.Type.Circle, Player.PLAYER_RADIUS /2f, null, null, null),
                new FixtureDefinitionDto(0.05f, 0.00005f, 0.6f, false)
        );

        PhysicsEntity sensor = PhysicsEntityCreator.createEntity(world,
                new BodyDefinitionDto(BodyDef.BodyType.DynamicBody, new Vector2(400, 400), 0.1f),
                new ShapeDto(Shape.Type.Circle, Player.PLAYER_RADIUS, null, null, null),
                new FixtureDefinitionDto(0f, 0f, 0f, true)
        );
		entitiesService.addEntity("ball", new Entity(ball, physicsEntity));
		entitiesService.addEntity("player", new Player(playerActor, playerActorPhysicsEntity, sensor));
        //physicsEntity.getBody().applyForceToCenter(1.0f, 0.0f, true);
		world.setContactListener(ballSensorListener);
	}

	@Override
	public void render () {
        physicsTimeStep.processStep(world);
        entitiesService.synchronizeEntitiesPosition();
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		super.render();
		stage.act();
		stage.draw();
		debugRenderer.render(world, stage.getCamera().combined);
        keyboardInput.onInput();

	}
	
	@Override
	public void dispose () {

	}
}
