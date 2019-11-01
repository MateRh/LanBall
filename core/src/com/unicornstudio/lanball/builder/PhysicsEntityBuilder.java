package com.unicornstudio.lanball.builder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.unicornstudio.lanball.core.Screen;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
public class PhysicsEntityBuilder {

    private World world;

    private BodyDef.BodyType bodyType;

    private Vector2 position;

    private float linearDamping = 0f;

    private boolean fixedRotation = true;


    private float friction = 0f;

    private float restitution = 0f;

    private float density = 0f;

    private boolean sensor = false;

    private Short categoryBits;

    private Short maskBits;


    private Shape.Type shapeType;

    private Float width;

    private Float height;

    private Float radius;

    private boolean active = true;

    public PhysicsEntityBuilder world(World world) {
        this.world = world;
        return this;
    }

    public PhysicsEntityBuilder bodyType(BodyDef.BodyType bodyType) {
        this.bodyType = bodyType;
        return this;
    }

    public PhysicsEntityBuilder position(Vector2 position) {
        this.position = position;
        return this;
    }

    public PhysicsEntityBuilder position(int x, int y) {
        this.position = new Vector2(x, y);
        return this;
    }

    public PhysicsEntityBuilder position(float x, float y) {
        this.position = new Vector2(x, y);
        return this;
    }

    public PhysicsEntityBuilder linearDamping(Float linearDamping) {
        this.linearDamping = linearDamping;
        return this;
    }

    public PhysicsEntityBuilder fixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
        return this;
    }

    public PhysicsEntityBuilder friction(Float friction) {
        this.friction = friction;
        return this;
    }

    public PhysicsEntityBuilder restitution(Float restitution) {
        this.restitution = restitution;
        return this;
    }

    public PhysicsEntityBuilder density(Float density) {
        this.density = density;
        return this;
    }

    public PhysicsEntityBuilder sensor(boolean sensor) {
        this.sensor = sensor;
        return this;
    }

    public PhysicsEntityBuilder categoryBits(Short categoryBits) {
        this.categoryBits = categoryBits;
        return this;
    }

    public PhysicsEntityBuilder maskBits(Short maskBits) {
        this.maskBits = maskBits;
        return this;
    }

    public PhysicsEntityBuilder shapeType(Shape.Type type) {
        this.shapeType = type;
        return this;
    }

    public PhysicsEntityBuilder width(Float width) {
        this.width = width;
        return this;
    }

    public PhysicsEntityBuilder height(Float height) {
        this.height = height;
        return this;
    }

    public PhysicsEntityBuilder radius(Float radius) {
        this.radius = radius;
        return this;
    }

    public PhysicsEntityBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public PhysicsEntity build() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.density = density;
        fixtureDef.isSensor = sensor;
        fixtureDef.filter.maskBits = Optional.ofNullable(maskBits).orElse(fixtureDef.filter.maskBits);
        fixtureDef.filter.categoryBits = Optional.ofNullable(categoryBits).orElse(fixtureDef.filter.categoryBits);

        switch (shapeType) {
            case Circle:
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(radius * Screen.getMeterPerPixel());
                fixtureDef.shape =  circleShape;
                break;
            case Polygon:
                PolygonShape polygonShape = new PolygonShape();
                float halfWidth = width / 2;
                float halfHeight = height / 2;
                polygonShape.setAsBox(halfWidth * Screen.getMeterPerPixel(), halfHeight * Screen.getMeterPerPixel(),
                        new Vector2(halfWidth * Screen.getMeterPerPixel(), halfHeight * Screen.getMeterPerPixel()), 0);
                fixtureDef.shape = polygonShape;
                break;
            case Edge:
                EdgeShape edgeShape = new EdgeShape();
                edgeShape.set(0, 0, width * Screen.getMeterPerPixel(), height * Screen.getMeterPerPixel());
                fixtureDef.shape = edgeShape;
                break;
            default:
                break;
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position.scl(Screen.getMeterPerPixel()));
        bodyDef.linearDamping = linearDamping;
        bodyDef.fixedRotation = fixedRotation;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setActive(active);

        return new PhysicsEntity(body);
    }

}
