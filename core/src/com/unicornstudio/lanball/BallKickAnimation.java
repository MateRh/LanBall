package com.unicornstudio.lanball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;

public class BallKickAnimation {

    private final Actor actor;

    private final Timer timer;

    private Color startColor;

    private Color targetColor;

    private int steps = 0;

    private float increment = 1/60f;

    @Getter
    private boolean finished = false;

    public BallKickAnimation(Actor actor) {
        this.actor = actor;
        timer = new Timer();
        startColor = actor.getColor().cpy();
        targetColor = startColor.cpy().lerp(Color.WHITE, 0.5f);
        timer.schedule(animate(), 0, 1000/120);
    }

    private TimerTask animate() {
        return new TimerTask() {
            @Override
            public void run() {
                actor.getColor().lerp(targetColor, increment * ( steps > 60 ? steps - 60 : steps ));
                if (steps == 60) {
                    targetColor = startColor.cpy();
                    startColor = actor.getColor().cpy();
                } else if (steps == 120) {
                    this.cancel();
                    timer.cancel();
                    timer.purge();
                    actor.setColor(targetColor.cpy());
                    finished = true;
                }
                steps++;
            }
        };
    }

}
