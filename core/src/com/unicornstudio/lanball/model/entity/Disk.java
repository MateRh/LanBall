package com.unicornstudio.lanball.model.entity;

import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.EntityType;
import com.unicornstudio.lanball.model.actors.DiskActor;
import com.unicornstudio.lanball.model.map.elements.FunctionalType;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;

public class Disk extends Entity {

    public Disk(DiskActor actor, PhysicsEntity physicsEntity, FunctionalType functionalType) {
        super(actor, physicsEntity, functionalType, EntityType.DISK);
    }

}
