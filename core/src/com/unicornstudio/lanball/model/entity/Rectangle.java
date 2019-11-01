package com.unicornstudio.lanball.model.entity;

import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.EntityType;
import com.unicornstudio.lanball.model.actors.RectangleActor;
import com.unicornstudio.lanball.model.map.elements.FunctionalType;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;

public class Rectangle extends Entity {

    public Rectangle(RectangleActor actor, PhysicsEntity physicsEntity, FunctionalType functionalType) {
        super(actor, physicsEntity, functionalType, EntityType.RECTANGLE);
    }

}
