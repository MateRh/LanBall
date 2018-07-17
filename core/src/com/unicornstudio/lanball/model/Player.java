package com.unicornstudio.lanball.model;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;


public class Player extends Entity {

    public final static Float PLAYER_RADIUS = 20f;

    public final static Float MAX_VELOCITY = 1000.0f;

    public final static Float VELOCITY = 260f;

    private PhysicsEntity sensor;

    public PhysicsEntity getSensor() {
        return sensor;
    }

    public Player(Actor actor, PhysicsEntity physicsEntity, PhysicsEntity sensor) {
        super(actor, physicsEntity, EntityType.PLAYER);
        this.sensor = sensor;
    }

}
