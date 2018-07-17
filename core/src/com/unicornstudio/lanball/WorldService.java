package com.unicornstudio.lanball;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.unicornstudio.lanball.map.MapDto;
import com.unicornstudio.lanball.map.world.SizeDto;
import com.unicornstudio.lanball.map.world.WorldDto;
import com.unicornstudio.lanball.model.MapWorld;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.utils.PhysicsEntityCreator;
import com.unicornstudio.lanball.utils.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.utils.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.utils.dto.ShapeDto;
import com.unicornstudio.lanball.video.MapBackground;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorldService {

    private static final Vector2 WORLD_GRAVITY_VECTOR = new Vector2(0, 0);

    private MapWorld world;

    public void create(MapDto mapDto) {
        world = new MapWorld();
        world.setWorld(createWorld());
        world.setMapBackground(createMapBackground(mapDto.getWorld()));
        world.setPhysicsEntities(createPhysicsEntities(mapDto.getWorld().getSize()));
    }

    private World createWorld() {
        return new World(WORLD_GRAVITY_VECTOR, false);
    }

    private MapBackground createMapBackground(WorldDto worldDto) {
        return new MapBackground(worldDto.getSize(), worldDto.getForeground());
    }

    private List<PhysicsEntity> createPhysicsEntities(SizeDto sizeDto) {
        List<PhysicsEntity> physicsEntities = new ArrayList<>();
        Integer x = (Gdx.graphics.getWidth() - sizeDto.getWidth())/2;
        Integer y = (Gdx.graphics.getHeight() - sizeDto.getHeight())/2;

        List<Vector2> vectors = PhysicsEntityCreator.getVectors(sizeDto.getWidth(), 1f);
        physicsEntities.add(PhysicsEntityCreator.createEntity(world.getWorld(),
            new BodyDefinitionDto(BodyDef.BodyType.StaticBody, new Vector2(x, y), 1f),
            new ShapeDto(Shape.Type.Edge, null, null,vectors.get(0), vectors.get(1)),
            new FixtureDefinitionDto(0f, 1f, 1f, false)
        ));

         physicsEntities.add(PhysicsEntityCreator.createEntity(world.getWorld(),
            new BodyDefinitionDto(BodyDef.BodyType.KinematicBody, new Vector2(x, y), 1f),
            new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(1f, sizeDto.getHeight()),null, null),
            new FixtureDefinitionDto(0f, 1f, 1f, false)
        ));

         physicsEntities.add(PhysicsEntityCreator.createEntity(world.getWorld(),
            new BodyDefinitionDto(BodyDef.BodyType.KinematicBody, new Vector2(x, y + sizeDto.getHeight()), 1f),
            new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(sizeDto.getWidth(), 1f),null, null),
            new FixtureDefinitionDto(0f, 1f, 1f, false)
        ));

         physicsEntities.add(PhysicsEntityCreator.createEntity(world.getWorld(),
            new BodyDefinitionDto(BodyDef.BodyType.KinematicBody, new Vector2(x + sizeDto.getWidth(), y), 1f),
            new ShapeDto(Shape.Type.Polygon, null, PhysicsEntityCreator.getVertices(1f, sizeDto.getHeight()),null, null),
            new FixtureDefinitionDto(0f, 1f, 1f, false)
        ));

        return physicsEntities;
    }

}
