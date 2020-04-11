package com.unicornstudio.lanball.core;

import com.badlogic.gdx.physics.box2d.World;

public class PhysicsTimeStep  {

    private final static int VELOCITY_ITERATIONS = 10;
    private final static int POSITION_ITERATIONS = 10;


    public void processStep(World world, double frameTimeMillis) {
        world.step((float) frameTimeMillis, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

}
