package com.unicornstudio.lanball.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Entity {

    @NonNull
    private Actor actor;

    @NonNull
    private PhysicsEntity physicsEntity;

    private EntityType type = EntityType.ENTITY;


}
