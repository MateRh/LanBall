package com.unicornstudio.lanball.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;

public class Ball extends Entity {

    public Ball(Actor actor, PhysicsEntity physicsEntity) {
        super(actor, physicsEntity, EntityType.BALL);
    }

}
