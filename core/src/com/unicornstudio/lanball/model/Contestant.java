package com.unicornstudio.lanball.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;


public class Contestant extends Entity {

    private String name;

    public Contestant(Actor actor, PhysicsEntity physicsEntity) {
        super(actor, physicsEntity, null, EntityType.ENTITY);
    }

}
