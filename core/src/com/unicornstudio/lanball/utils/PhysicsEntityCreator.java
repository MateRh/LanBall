package com.unicornstudio.lanball.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.unicornstudio.lanball.Screen;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.utils.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.utils.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.utils.dto.ShapeDto;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PhysicsEntityCreator {

    public static PhysicsEntity createEntity(World world, BodyDefinitionDto bodyDefinitionDto, ShapeDto shapeDto,
            FixtureDefinitionDto fixtureDefinitionDto) {
        BodyDef bodyDefinition = createBodyDefinition(bodyDefinitionDto);
        Body body = world.createBody(bodyDefinition);
        FixtureDef fixtureDefinition = createFixtureDefinition(createShape(shapeDto), fixtureDefinitionDto);
        body.createFixture(fixtureDefinition);
        return new PhysicsEntity(body);
    }

    public static Vector2[] getVertices(float x, float y, float width, float height) {
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(x, y + height);
        vertices[1] = new Vector2(x + width, y + height);
        vertices[2] = new Vector2(x, y);
        vertices[3] = new Vector2(x + width, y);
        return vertices;
    }

    public static List<Vector2> getVectors(float width, float height) {
        List<Vector2> vectors = new ArrayList<>();
        vectors.add(new Vector2(0, 0));
        vectors.add(new Vector2(width,  height));
        return vectors;
    }

    public static Vector2[] getVertices(float width, float height) {
        return getVertices(0, 0, width, height);
    }

    private static Shape createShape(ShapeDto shapeDto) {
        switch (shapeDto.getType()) {
            case Circle:
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(shapeDto.getRadius()*Screen.getMeterPerPixel());
                return circleShape;
            case Polygon:
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.set(scale(shapeDto.getVertices()));
                return polygonShape;
            case Edge:
                EdgeShape edgeShape = new EdgeShape();
                edgeShape.set(scale(shapeDto.getV1()), scale(shapeDto.getV2()));
                return edgeShape;
        }
        return null;
    }

    private static BodyDef createBodyDefinition(BodyDefinitionDto bodyDefinitionDto) {
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = bodyDefinitionDto.getType();
        bodyDefinition.position.set(scale(bodyDefinitionDto.getPosition()));
        bodyDefinition.linearDamping = bodyDefinitionDto.getLinearDamping();
        bodyDefinition.fixedRotation = true;
        return bodyDefinition;
    }

    private static FixtureDef createFixtureDefinition(Shape shape, FixtureDefinitionDto fixtureDefinitionDto) {
        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.shape = shape;
        fixtureDefinition.density = fixtureDefinitionDto.getDensity();
        fixtureDefinition.friction = fixtureDefinitionDto.getFriction();
        fixtureDefinition.restitution = fixtureDefinitionDto.getRestitution();
        fixtureDefinition.isSensor = fixtureDefinitionDto.isSensor();
        return fixtureDefinition;
    }

    private static Vector2 scale(Vector2 vector) {
        return vector.scl(Screen.getMeterPerPixel());
    }

    private static Vector2[] scale(Vector2[] vector2s) {
        return Stream.of(vector2s).map(PhysicsEntityCreator::scale).toArray(Vector2[]::new);
    }

}
