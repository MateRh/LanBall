package com.unicornstudio.lanball;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.map.MapDto;
import com.unicornstudio.lanball.map.world.SizeDto;
import com.unicornstudio.lanball.map.world.WorldDto;
import com.unicornstudio.lanball.model.MapWorld;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.stage.StageService;
import com.unicornstudio.lanball.utils.PhysicsEntityCreator;
import com.unicornstudio.lanball.utils.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.utils.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.utils.dto.ShapeDto;
import com.unicornstudio.lanball.video.MapBackground;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Singleton
public class WorldService {

    private static final Vector2 WORLD_GRAVITY_VECTOR = new Vector2(0, 0);

    private static final int GATE_HEIGHT = 100;

    private final World world;

    private MapWorld mapWorld;

    public WorldService() {
        world = createWorld();
    }

    @Inject
    private StageService stageService;

    public void create(MapDto mapDto) {
        mapWorld = new MapWorld();
        mapWorld.setWorld(world);
        mapWorld.setMapBackground(createMapBackground(mapDto.getWorld()));
        mapWorld.setPhysicsEntities(createWorldBounds(mapDto.getWorld().getSize()));
    }

    public void initialize() {
        stageService.addActor(getMapBackground());
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

    private World createWorld() {
        return new World(WORLD_GRAVITY_VECTOR, false);
    }

    private MapBackground createMapBackground(WorldDto worldDto) {
        return new MapBackground(worldDto.getSize(), worldDto.getForeground());
    }

    private List<PhysicsEntity> createWorldBounds(SizeDto sizeDto) {
        List<PhysicsEntity> worldBounds = new ArrayList<>();
        Vector2 startPosition = new Vector2(0, 0);

        List<Vector2> vectors = PhysicsEntityCreator.getVectors(sizeDto.getWidth(), 1f);
        worldBounds.add(PhysicsEntityCreator.createEntity(getWorld(),
            new BodyDefinitionDto(BodyDef.BodyType.StaticBody, startPosition, 1f),
            new ShapeDto(Shape.Type.Edge, null, null, vectors.get(0), vectors.get(1)),
            new FixtureDefinitionDto(0f, 1f, 1f, false, EntitiesService.BIT_PLAYER_BOUND,  EntitiesService.BIT_PLAYER)
        ));

         worldBounds.add(PhysicsEntityCreator.createEntity(getWorld(),
            new BodyDefinitionDto(BodyDef.BodyType.StaticBody, startPosition, 1f),
            new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(1f, sizeDto.getHeight()),null, null),
            new FixtureDefinitionDto(0f, 1f, 1f, false, EntitiesService.BIT_PLAYER_BOUND, EntitiesService.BIT_PLAYER)
        ));

         worldBounds.add(PhysicsEntityCreator.createEntity(getWorld(),
            new BodyDefinitionDto(BodyDef.BodyType.StaticBody, new Vector2(0, sizeDto.getHeight()), 1f),
            new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(sizeDto.getWidth(), 1f),null, null),
            new FixtureDefinitionDto(0f, 1f, 1f, false, EntitiesService.BIT_PLAYER_BOUND, EntitiesService.BIT_PLAYER)
        ));

         worldBounds.add(PhysicsEntityCreator.createEntity(getWorld(),
            new BodyDefinitionDto(BodyDef.BodyType.StaticBody, new Vector2(sizeDto.getWidth(), 0), 1f),
            new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(1f, sizeDto.getHeight()),null, null),
            new FixtureDefinitionDto(0f, 1f, 1f, false, EntitiesService.BIT_PLAYER_BOUND, EntitiesService.BIT_PLAYER)
        ));

        // top
        worldBounds.add(PhysicsEntityCreator.createEntity(getWorld(),
                new BodyDefinitionDto(BodyDef.BodyType.StaticBody, new Vector2(35,  35), 1f),
                new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(sizeDto.getWidth(), 1f),null, null),
                new FixtureDefinitionDto(0f, 1f, 1f, false, EntitiesService.BIT_BALL_BOUND, EntitiesService.BIT_BALL)
        ));

        // left
        worldBounds.add(PhysicsEntityCreator.createEntity(getWorld(),
                new BodyDefinitionDto(BodyDef.BodyType.StaticBody,  new Vector2(35, sizeDto.getHeight()/2+GATE_HEIGHT), 1f),
                new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(1f, sizeDto.getHeight()/2-GATE_HEIGHT ),null, null),
                new FixtureDefinitionDto(0f, 1f, 1f, false, EntitiesService.BIT_BALL_BOUND, EntitiesService.BIT_BALL)
        ));

        worldBounds.add(PhysicsEntityCreator.createEntity(getWorld(),
                new BodyDefinitionDto(BodyDef.BodyType.StaticBody,  new Vector2(35, 35), 1f),
                new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(1f, sizeDto.getHeight()/2-GATE_HEIGHT ),null, null),
                new FixtureDefinitionDto(0f, 1f, 1f, false, EntitiesService.BIT_BALL_BOUND, EntitiesService.BIT_BALL)
        ));

        // top
        worldBounds.add(PhysicsEntityCreator.createEntity(getWorld(),
                new BodyDefinitionDto(BodyDef.BodyType.KinematicBody, new Vector2(35, sizeDto.getHeight()-35), 1f),
                new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(sizeDto.getWidth(), 1f),null, null),
                new FixtureDefinitionDto(0f, 1f, 1f, false, EntitiesService.BIT_BALL_BOUND, EntitiesService.BIT_BALL)
        ));

        // right
        worldBounds.add(PhysicsEntityCreator.createEntity(getWorld(),
                new BodyDefinitionDto(BodyDef.BodyType.KinematicBody, new Vector2(sizeDto.getWidth()-35, 35), 1f),
                new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(1f, sizeDto.getHeight()),null, null),
                new FixtureDefinitionDto(0f, 1f, 1f, false, EntitiesService.BIT_BALL_BOUND, EntitiesService.BIT_BALL)
        ));
        System.out.println(500/2-50);
        return worldBounds;
    }

}
