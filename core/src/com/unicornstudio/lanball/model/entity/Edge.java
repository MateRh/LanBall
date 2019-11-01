package com.unicornstudio.lanball.model.entity;

import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.EntityType;
import com.unicornstudio.lanball.model.actors.EdgeActor;
import com.unicornstudio.lanball.model.map.elements.FunctionalType;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;

public class Edge extends Entity {

    public Edge(EdgeActor actor, PhysicsEntity physicsEntity, FunctionalType functionalType) {
        super(actor, physicsEntity, functionalType, EntityType.EDGE);
    }

}
