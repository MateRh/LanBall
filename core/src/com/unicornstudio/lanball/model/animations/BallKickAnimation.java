package com.unicornstudio.lanball.model.animations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BallKickAnimation implements Animation {

    private final static int ANIMATION_TIME_MS = 450;

    private final static float multiplier = 0.5f / ANIMATION_TIME_MS;

    private final Actor actor;

    private Color startColor;

    private Color targetColor;

    private float time = 0;

    private boolean finished = false;

    public BallKickAnimation(Actor actor) {
        this.actor = actor;
        startColor = actor.getColor().cpy();
        targetColor = startColor.cpy().lerp(Color.WHITE, 0.5f);
    }

    @Override
    public void render(double frameTimeMillis) {
        time += frameTimeMillis;
        actor.getColor().lerp(targetColor, (float) Math.tan(time * multiplier));
        if (time >= ANIMATION_TIME_MS) {
            actor.setColor(startColor.cpy());
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
