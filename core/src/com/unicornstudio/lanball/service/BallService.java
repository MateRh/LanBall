package com.unicornstudio.lanball.service;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.core.Screen;
import com.unicornstudio.lanball.model.map.settings.BallSettings;
import com.unicornstudio.lanball.model.Ball;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.util.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.util.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.util.dto.ShapeDto;
import com.unicornstudio.lanball.model.actors.BallActor;
import lombok.Data;

@Data
@Singleton
public class BallService {

    @Inject
    private StageService stageService;

    @Inject
    private EntitiesService entitiesService;

    private BallListener ballListener;

    private BallSettings ballSettings;

    public void createBall(BallSettings ballSettings) {
        this.ballSettings = ballSettings;
        BallActor actor = new BallActor(ballSettings);
        PhysicsEntity physicsEntity = entitiesService.createEntity(
                new BodyDefinitionDto(
                        BodyDef.BodyType.DynamicBody,
                        new Vector2(ballSettings.getPositionX(), ballSettings.getPositionY()),
                        ballSettings.getLinearDamping()),
                new ShapeDto(
                        Shape.Type.Circle,
                        ballSettings.getSize()/2f,
                        null,
                        null,
                        null),
                new FixtureDefinitionDto(
                        ballSettings.getFriction(),
                        ballSettings.getRestitution(),
                        ballSettings.getDensity(),
                        false,
                        EntitiesService.BIT_BALL,
                        (short)(EntitiesService.BIT_PLAYER_TEAM1 | EntitiesService.BIT_PLAYER_TEAM2 |  EntitiesService.BIT_BALL_BOUND))
        );
        entitiesService.addEntity("ball", new Ball(actor, physicsEntity));
        stageService.addActor(actor);
        ballListener = new BallListener(physicsEntity.getBody());
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

}
