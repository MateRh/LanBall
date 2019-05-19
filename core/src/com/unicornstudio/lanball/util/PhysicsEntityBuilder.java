package com.unicornstudio.lanball.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.unicornstudio.lanball.core.Screen;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.util.dto.PhysicsEntityDto;

public class PhysicsEntityBuilder {

    public static PhysicsEntity buildPhysicsEntity(World world, PhysicsEntityDto physicsEntityDto) {
        Body body = world.createBody(
            buildBodyDef(physicsEntityDto.getBodyType(), physicsEntityDto.getPosition(), physicsEntityDto.getLinearDamping()));
        FixtureDef fixtureDef = buildFixtureDef(
                physicsEntityDto.getFriction(), physicsEntityDto.getDensity(), physicsEntityDto.getDensity(), physicsEntityDto.isSensor());
        fixtureDef.shape = buildShape(
                physicsEntityDto.getShapeType(), physicsEntityDto.getWidth(), physicsEntityDto.getHeight(), physicsEntityDto.getRadius());
        setFilter(fixtureDef.filter, physicsEntityDto.getCategoryBits(), physicsEntityDto.getMaskBits());
        body.createFixture(fixtureDef);
        return new PhysicsEntity(body);
    }

    private static Shape buildShape(Shape.Type type, Float width, Float height, Float radius) {
        Shape shape;
        switch (type) {
            case Circle:
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(radius * Screen.getMeterPerPixel());
                shape =  circleShape;
                break;
            case Polygon:
                PolygonShape polygonShape = new PolygonShape();
                float halfWidth = width / 2;
                float halfHeight = height / 2;
                polygonShape.setAsBox(halfWidth * Screen.getMeterPerPixel(), halfHeight * Screen.getMeterPerPixel(),
                        new Vector2(halfWidth * Screen.getMeterPerPixel(), halfHeight * Screen.getMeterPerPixel()), 0);
                shape = polygonShape;
                break;
            case Edge:
                EdgeShape edgeShape = new EdgeShape();
                edgeShape.set(0, 0, width * Screen.getMeterPerPixel(), height * Screen.getMeterPerPixel());
                shape = edgeShape;
                break;
            default:
                shape = null;
                break;
        }
        return shape;
    }

    private static FixtureDef buildFixtureDef(Float friction, Float restitution, Float density, boolean sensor) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.density = density;
        fixtureDef.isSensor = sensor;
        return fixtureDef;
    }

    private static void setFilter(Filter filter, Short categoryBits, Short maskBits) {
        if (categoryBits != null) {
            filter.categoryBits = categoryBits;
        }
        if (maskBits != null) {
            filter.maskBits = maskBits;
        }
    }

    private static BodyDef buildBodyDef(BodyDef.BodyType bodyType, Vector2 position, Float linearDamping) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position.scl(Screen.getMeterPerPixel()));
        bodyDef.linearDamping = linearDamping;
        bodyDef.fixedRotation = true;
        return bodyDef;
    }

}
