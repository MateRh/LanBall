package com.unicornstudio.lanball.model;

import com.badlogic.gdx.physics.box2d.World;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.model.actors.MapBackground;
import lombok.Data;

import java.util.List;

@Data
public class MapWorld {

    private World world;

    private MapBackground mapBackground;

    private List<PhysicsEntity> physicsEntities;
}
