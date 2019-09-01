package com.unicornstudio.lanball.service;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.listner.WorldContactListener;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.model.map.MapDto;
import com.unicornstudio.lanball.model.map.world.SizeDto;
import com.unicornstudio.lanball.model.map.world.WorldDto;
import com.unicornstudio.lanball.model.MapWorld;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.model.actors.MapBackground;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.unicornstudio.lanball.model.actors.MapBackground.GATE_SIZE;
import static com.unicornstudio.lanball.model.actors.MapBackground.LINE_SPACE;
import static com.unicornstudio.lanball.service.EntitiesService.BIT_PLAYER_TEAM1;
import static com.unicornstudio.lanball.service.EntitiesService.BIT_PLAYER_TEAM2;

@Data
@Singleton
public class WorldService {

    private static final Vector2 WORLD_GRAVITY_VECTOR = new Vector2(0, 0);

    private static final int GATE_HEIGHT = 100;

    private final World world;

    @Inject
    private GateService gateService;

    @Inject
    private StageService stageService;

    @Inject
    private WorldContactListener contactListener;

    private MapWorld mapWorld;

    private List<PhysicsEntity> initialRoundBounds = new ArrayList<>();

    private SizeDto sizeDto;

    public WorldService() {
        world = createWorld();
    }


    public void create(MapDto mapDto) {
        mapWorld = new MapWorld();
        mapWorld.setWorld(world);
        mapWorld.setMapBackground(createMapBackground(mapDto.getWorld()));
        sizeDto = mapDto.getWorld().getSize();
    }

    public void initialize() {
        _createWorldBounds(sizeDto);
        stageService.addActor(getMapBackground());
        world.setContactListener(contactListener);
    }

    public void dispose() {
        stageService.unload();
        //stageService.dispose();
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        bodies.forEach(world::destroyBody);
        contactListener.clear();
    }

    public World getWorld() {
        return mapWorld.getWorld();
    }

    public boolean isCreated() {
        return Objects.nonNull(mapWorld);
    }

    public MapBackground getMapBackground() {
        return mapWorld.getMapBackground();
    }

    public void setInitialRoundBoundsActive(boolean active) {
        Gdx.app.postRunnable(() -> initialRoundBounds.forEach(physicsEntity -> physicsEntity.getBody().setActive(active)));
    }

    public void updateInitialRoundBoundsFilter(TeamType teamType) {
        initialRoundBounds.forEach(
                physicsEntity -> physicsEntity.getBody().getFixtureList().
                        forEach(fixture -> {
                            Filter filter = fixture.getFilterData();
                            filter.maskBits = getPlayerBit(teamType);
                            fixture.setFilterData(filter);
                        })
        );
    }

    private World createWorld() {
        return new World(WORLD_GRAVITY_VECTOR, false);
    }

    private MapBackground createMapBackground(WorldDto worldDto) {
        return new MapBackground(worldDto.getSize(), worldDto.getForeground());
    }

    private void _createWorldBounds(SizeDto sizeDto) {

        /*
        
            Z ciekawych rzeczy:

                Box2D to silnik 2D z tzw perspektywy "z boku",
                 ta gra używa perspektywy 2D z góry,
                 z tego powodu kordynaty świata wyglądają tak:

                 ( 0, wysokość ) * --------------------------------- * ( szerokość, wysokość )
                                 |                                   |
                                 |                                   |
                                 |                                   |
                       ( 0, 0 )  * --------------------------------- * ( szrokośc, 0 )

         */

        createWorldInnerBounds(sizeDto.getWidth(), sizeDto.getHeight());
        createGates(sizeDto.getWidth(), sizeDto.getHeight());
        createWorldOuterBounds(sizeDto.getWidth(), sizeDto.getHeight());
        createCenterBounds(sizeDto.getWidth(), sizeDto.getHeight());
    }

    private void createWorldOuterBounds(int width, int height) {

        com.unicornstudio.lanball.builder.PhysicsEntityBuilder builder =
                new com.unicornstudio.lanball.builder.PhysicsEntityBuilder()
                        .world(getWorld())
                        .bodyType(BodyDef.BodyType.StaticBody)
                        .linearDamping(1f)
                        .restitution(0.5625f)
                        .density(1f)
                        .categoryBits(EntitiesService.BIT_BALL_BOUND)
                        .maskBits(EntitiesService.BIT_BALL)
                        .shapeType(Shape.Type.Edge);

        builder.position(LINE_SPACE, LINE_SPACE)
                .width((float) width - 2 * LINE_SPACE)
                .height(1f)
                .build();

        builder.position(LINE_SPACE, height - LINE_SPACE)
                .width((float) width - 2 * LINE_SPACE)
                .height(1f)
                .build();

        int halfHeight = (height - GATE_SIZE - LINE_SPACE * 2) / 2;

        // left top

        builder.position(LINE_SPACE, height - halfHeight - LINE_SPACE)
                .width(1f)
                .height((float) halfHeight)
                .build();

        // left bottom

        builder.position(LINE_SPACE, LINE_SPACE)
                .width(1f)
                .height((float) halfHeight)
                .build();

        // right top

        builder.position(width - LINE_SPACE, height - halfHeight - LINE_SPACE)
                .width(1f)
                .height((float) halfHeight)
                .build();

        // right bottom

        builder.position(width - LINE_SPACE, LINE_SPACE)
                .width(1f)
                .height((float) halfHeight)
                .build();

    }

    private void createGates(int width, int height) {
        com.unicornstudio.lanball.builder.PhysicsEntityBuilder builder =
                new com.unicornstudio.lanball.builder.PhysicsEntityBuilder()
                        .world(getWorld())
                        .bodyType(BodyDef.BodyType.StaticBody)
                        .linearDamping(1f)
                        .density(1f)
                        .categoryBits(EntitiesService.BIT_BALL_BOUND)
                        .maskBits((short) (BIT_PLAYER_TEAM1 | BIT_PLAYER_TEAM2 | EntitiesService.BIT_BALL))
                        .shapeType(Shape.Type.Edge);

        int halfHeight = (height - GATE_SIZE - LINE_SPACE * 2) / 2;

        // left gate top

        builder.position(LINE_SPACE / 2, height - halfHeight - LINE_SPACE)
                .width(LINE_SPACE / 2f)
                .height(1f)
                .build();

        // left gate bottom

        builder.position(LINE_SPACE / 2, LINE_SPACE + halfHeight)
                .width(LINE_SPACE / 2f)
                .height(1f)
                .build();

        // left gate center

        builder.position(LINE_SPACE / 2, LINE_SPACE + halfHeight)
                .width(1f)
                .height(((float) GATE_HEIGHT + LINE_SPACE))
                .build();

        // right gate top

        builder.position(width - LINE_SPACE, height - halfHeight - LINE_SPACE)
                .width(LINE_SPACE / 2f)
                .height(1f)
                .build();

        // right gate bottom

        builder.position(width - LINE_SPACE, LINE_SPACE + halfHeight)
                .width(LINE_SPACE / 2f)
                .height(1f)
                .build();

        // right gate center

        builder.position(width - LINE_SPACE / 2, LINE_SPACE + halfHeight)
                .width(1f)
                .height(((float) GATE_HEIGHT + LINE_SPACE))
                .build();

        // left goalpost top

        builder.position(LINE_SPACE, height - halfHeight - LINE_SPACE)
                .radius(4f)
                .shapeType(Shape.Type.Circle)
                .build();

        // left goalpost bottom

        builder.position(LINE_SPACE, LINE_SPACE + halfHeight )
                .radius(4f)
                .shapeType(Shape.Type.Circle)
                .build();

        // right goalpost top

        builder.position(width - LINE_SPACE, height - halfHeight - LINE_SPACE)
                .radius(4f)
                .shapeType(Shape.Type.Circle)
                .build();

        // right goalpost bottom

        builder.position(width - LINE_SPACE, LINE_SPACE + halfHeight )
                .radius(4f)
                .shapeType(Shape.Type.Circle)
                .build();

        gateService.createLeftGateSensor(LINE_SPACE -2, LINE_SPACE + halfHeight, 1f, GATE_HEIGHT + LINE_SPACE);
        gateService.createRightGateSensor(width - LINE_SPACE + 2, LINE_SPACE + halfHeight, 1, GATE_HEIGHT + LINE_SPACE);
    }

    private void createWorldInnerBounds(int width, int height) {
        com.unicornstudio.lanball.builder.PhysicsEntityBuilder builder =
        new com.unicornstudio.lanball.builder.PhysicsEntityBuilder()
                .world(getWorld())
                .bodyType(BodyDef.BodyType.StaticBody)
                .linearDamping(1f)
                .density(1f)
                .categoryBits(EntitiesService.BIT_PLAYER_BOUND)
                .maskBits((short) (BIT_PLAYER_TEAM1 | BIT_PLAYER_TEAM2))
                .shapeType(Shape.Type.Edge);

        builder.position(0, 0)
                .width((float) width)
                .height(1f)
                .build();

        builder.position(0, 0)
                .width(1f)
                .height((float) height)
                .build();

        builder.position(0, height)
                .width((float) width)
                .height(1f)
                .build();

        builder.position(width, 0)
                .width(1f)
                .height((float) height)
                .build();
    }

    private void createCenterBounds(int width, int height) {
        int x = LINE_SPACE + (width - 2 * LINE_SPACE) / 2;
        int y = LINE_SPACE + (height - 2 * LINE_SPACE) / 2;

        initialRoundBounds.add(
                new com.unicornstudio.lanball.builder.PhysicsEntityBuilder()
                        .world(getWorld())
                        .bodyType(BodyDef.BodyType.StaticBody)
                        .position(x, y)
                        .linearDamping(1f)
                        .restitution(1f)
                        .density(1f)
                        .categoryBits(EntitiesService.BIT_PLAYER_BOUND)
                        .maskBits(BIT_PLAYER_TEAM1)
                        .shapeType(Shape.Type.Circle)
                        .radius(GATE_SIZE * 0.75f)
                        .build()
        );

        initialRoundBounds.add(
                new com.unicornstudio.lanball.builder.PhysicsEntityBuilder()
                        .world(getWorld())
                        .bodyType(BodyDef.BodyType.StaticBody)
                        .position(width / 2, 0)
                        .linearDamping(1f)
                        .restitution(1f)
                        .density(1f)
                        .categoryBits(EntitiesService.BIT_PLAYER_BOUND)
                        .maskBits(BIT_PLAYER_TEAM1)
                        .shapeType(Shape.Type.Edge)
                        .width(1f)
                        .height((float) height)
                        .build()
        );

    }

    private short getPlayerBit(TeamType teamType) {
        if (teamType.equals(TeamType.TEAM1)) {
            return BIT_PLAYER_TEAM1;
        } else {
            return BIT_PLAYER_TEAM2;
        }
    }

}
