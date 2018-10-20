package com.unicornstudio.lanball.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
public class Player extends Entity {

    public final static Float MAX_VELOCITY = 10f;

    public final static Float VELOCITY = 0.75f;

    private PhysicsEntity sensor;

    private String name = RandomStringUtils.randomAlphabetic(8);

    public Player(Actor actor, PhysicsEntity physicsEntity, PhysicsEntity sensor) {
        super(actor, physicsEntity, EntityType.PLAYER);
        this.sensor = sensor;
    }

}
