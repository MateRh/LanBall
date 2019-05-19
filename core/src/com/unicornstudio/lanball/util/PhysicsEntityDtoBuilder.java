package com.unicornstudio.lanball.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.unicornstudio.lanball.util.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.util.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.util.dto.PhysicsEntityDto;
import com.unicornstudio.lanball.util.dto.ShapeDto;

public class PhysicsEntityDtoBuilder {

    public static PhysicsEntityDto buildPhysicsEntityDto(ShapeDto shapeDto, BodyDefinitionDto bodyDefinitionDto, FixtureDefinitionDto fixtureDefinitionDto) {
        PhysicsEntityDto physicsEntityDto = new PhysicsEntityDto();

        physicsEntityDto.setShapeType(shapeDto.getType());
        physicsEntityDto.setWidth(shapeDto.getWidth());
        physicsEntityDto.setHeight(shapeDto.getHeight());
        physicsEntityDto.setRadius(shapeDto.getRadius());

        physicsEntityDto.setBodyType(bodyDefinitionDto.getType());
        physicsEntityDto.setPosition(bodyDefinitionDto.getPosition());
        physicsEntityDto.setLinearDamping(bodyDefinitionDto.getLinearDamping());

        physicsEntityDto.setFriction(fixtureDefinitionDto.getFriction());
        physicsEntityDto.setRestitution(fixtureDefinitionDto.getRestitution());
        physicsEntityDto.setDensity(fixtureDefinitionDto.getDensity());
        physicsEntityDto.setSensor(fixtureDefinitionDto.isSensor());
        physicsEntityDto.setCategoryBits(fixtureDefinitionDto.getCategoryBits());
        physicsEntityDto.setMaskBits(fixtureDefinitionDto.getMaskBits());
        return physicsEntityDto;
    }

    public static FixtureDefinitionDto buildFixtureDefinitionDto(Float friction, Float restitution, Float density, boolean sensor, Short categoryBits, Short maskBits) {
        return new FixtureDefinitionDto(friction, restitution, density, sensor, categoryBits, maskBits);
    }

    public static BodyDefinitionDto buildBodyDefinitionDto(BodyDef.BodyType type, Vector2 position, Float linearDamping) {
        return new BodyDefinitionDto(type, position, linearDamping);
    }

    public static ShapeDto buildShapeDto(Shape.Type type, Float width, Float height, Float radius) {
        ShapeDto shapeDto = new ShapeDto();
        shapeDto.setType(type);
        shapeDto.setWidth(width);
        shapeDto.setHeight(height);
        shapeDto.setRadius(radius);
        return shapeDto;
    }

}
