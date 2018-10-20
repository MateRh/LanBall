package com.unicornstudio.lanball;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.map.settings.BallSettings;
import com.unicornstudio.lanball.model.Ball;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.stage.StageService;
import com.unicornstudio.lanball.utils.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.utils.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.utils.dto.ShapeDto;
import com.unicornstudio.lanball.video.BallActor;
import lombok.Data;

@Data
@Singleton
public class BallService {

    @Inject
    private StageService stageService;

    @Inject
    private EntitiesService entitiesService;

    public void createBall(BallSettings ballSettings) {
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
                        false)
        );
        entitiesService.addEntity("ball", new Ball(actor, physicsEntity));
        stageService.addActor(actor);
    }

    public Ball getBall() {
        return (Ball) entitiesService.getEntity("ball");
    }

}
