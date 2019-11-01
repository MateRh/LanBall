package com.unicornstudio.lanball.model.entity;

import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.EntityType;
import com.unicornstudio.lanball.model.actors.CircleActor;
import com.unicornstudio.lanball.model.map.elements.FunctionalType;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;

public class Circle extends Entity {

    public Circle(CircleActor actor, PhysicsEntity physicsEntity, FunctionalType functionalType) {
        super(actor, physicsEntity, functionalType, EntityType.CIRCLE);
    }

}
