package com.unicornstudio.lanball.service;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.builder.PhysicsEntityBuilder;
import com.unicornstudio.lanball.core.Screen;
import com.unicornstudio.lanball.listner.WorldContactListener;
import com.unicornstudio.lanball.model.map.settings.BallSettings;
import com.unicornstudio.lanball.model.Ball;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.model.actors.BallActor;
import lombok.Data;

@Data
@Singleton
public class BallService {

    @Inject
    private StageService stageService;

    @Inject
    private EntitiesService entitiesService;

    @Inject
    private WorldContactListener contactListener;

    @Inject
    private WorldService worldService;

    @Inject
    private BallListener ballListener;

    private BallSettings ballSettings;

    public void createBall(BallSettings ballSettings) {
        this.ballSettings = ballSettings;
        BallActor actor = new BallActor(ballSettings);
        PhysicsEntity physicsEntity = new PhysicsEntityBuilder()
                .world(worldService.getWorld())
                .bodyType(BodyDef.BodyType.DynamicBody)
                .position(ballSettings.getPositionX().intValue(), ballSettings.getPositionY().intValue())
                .linearDamping(ballSettings.getLinearDamping())
                .friction(ballSettings.getFriction())
                .restitution(ballSettings.getRestitution())
                .density(ballSettings.getDensity())
                .categoryBits(EntitiesService.BIT_BALL)
                .maskBits((short)(EntitiesService.BIT_PLAYER_TEAM1 | EntitiesService.BIT_PLAYER_TEAM2 | EntitiesService.BIT_BALL_BOUND))
                .shapeType(Shape.Type.Circle)
                .radius(ballSettings.getSize() / 2f)
                .build();
        entitiesService.addEntity("ball", new Ball(actor, physicsEntity));
        stageService.addActor(actor);
        PhysicsEntity sensor = new PhysicsEntityBuilder()
                .world(worldService.getWorld())
                .bodyType(BodyDef.BodyType.StaticBody)
                .position(ballSettings.getPositionX().intValue(), ballSettings.getPositionY().intValue())
                .sensor(true)
                .categoryBits(EntitiesService.BIT_BALL)
                .maskBits((short)(EntitiesService.BIT_PLAYER_TEAM1 | EntitiesService.BIT_PLAYER_TEAM2 | EntitiesService.BIT_BALL_BOUND))
                .shapeType(Shape.Type.Circle)
                .radius(ballSettings.getSize() / 1.85f)
                .build();
        ballListener.setBallBody(sensor.getBody());
    }

    public Ball getBall() {
        return (Ball) entitiesService.getEntity("ball");
    }

    public void reset() {
        Body ball = getBall().getPhysicsEntity().getBody();
        ball.setLinearVelocity(0f, 0f);
        ball.setTransform(new Vector2(ballSettings.getPositionX(), ballSettings.getPositionY()).scl(Screen.getMeterPerPixel()), 0);
    }

    public void setListenerStatus(boolean status) {
        ballListener.setStatus(status);
    }

    public void addBallListener() {
        contactListener.addListener(ballListener);
    }

}
