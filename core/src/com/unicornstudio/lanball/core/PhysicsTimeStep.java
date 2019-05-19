package com.unicornstudio.lanball.core;

import com.badlogic.gdx.physics.box2d.World;

public class PhysicsTimeStep  {

    private final static float SECOND_IN_MS = 1000f;
    private final static int VELOCITY_ITERATIONS = 10;
    private final static int POSITION_ITERATIONS = 10;

    private double lastStepTime;

    public void processStep(World world) {
        world.step(getTimeStep(), VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        lastStepTime = System.currentTimeMillis();
    }

    private float getTimeStep() {
        return (float) (System.currentTimeMillis() - lastStepTime) /SECOND_IN_MS;
    }

}
